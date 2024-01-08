package WebProject.WebProject.controller;

import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Order_Item;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.OrderService;
import WebProject.WebProject.service.Order_ItemService;
import WebProject.WebProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.session.CookieWebSessionIdResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class SuaHoaDonController {

    @Autowired
    CookieService cookieService;

    @Autowired
    Order_ItemService order_ItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    HttpSession session;


    @GetMapping("/sua-hang/{id}")
    public String Statistic1(@PathVariable int id, Model model, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {

        Order order = orderService.findById(id);

        List<Order_Item> listOrder_Item = order_ItemService.getAllByOrder_Id(order.getId());
        if (listOrder_Item.size() == 1) {
            model.addAttribute("a", null);
        } else {
            model.addAttribute("a", "a");
        }


        Page<Product> pageProduct = productService.findAll(pageNo, 5);
        for (Product product : pageProduct) {
            if (product.getProductImage().isEmpty()) {
                model.addAttribute("img", null);
            } else {
                model.addAttribute("img", "img");
            }
        }
        model.addAttribute("pageProduct", pageProduct.getContent());
        model.addAttribute("pageProductPage", pageProduct.getTotalPages());
        model.addAttribute("pageNumber", pageNo);

        model.addAttribute("listOrder_Item", listOrder_Item);
        model.addAttribute("order", order);
        model.addAttribute("idHoaDon", id);
        cookieService.create("idHoaDon", String.valueOf(id), 1);
        return "sua-don-hang.html";
    }

    @GetMapping("/xoa-don-hang/{id}")
    public String xoaDonHang(@PathVariable int id, Model model) {
        Order_Item order_ite1m = order_ItemService.detail(id);
        Product product = productService.getProductById(order_ite1m.getProduct().getId());
        product.setQuantity(product.getQuantity() + order_ite1m.getCount());
        productService.saveProduct(product);
        order_ItemService.deleteById(id);
        Cookie cookie = cookieService.read("idHoaDon");
        List<Order_Item> orderList = order_ItemService.getAllByOrder_Id(Integer.valueOf(cookie.getValue()));
        Long giaTien = Long.valueOf(0);
        for (Order_Item order_item : orderList) {
            giaTien += order_item.getUnit_price();
        }


        List<Order> orderList1 = orderService.findOrderById(Integer.valueOf(cookie.getValue()));
        for (Order order : orderList1) {
            order.setTotal(giaTien + order.getTotalShip());
            orderService.saveOrder(order);
        }


        return "redirect:/sua-hang/" + cookie.getValue();
    }

    @PostMapping("/them-don-hang/{id}")
    public String themDonHang(@PathVariable int id, @RequestParam("idSP") String idSP, Model model) {
        Product product = productService.getProductById(Integer.valueOf(idSP));
        List<Order> orderList = orderService.findOrderById(id);
        List<Order_Item> list = order_ItemService.getAllByOrder_Id(id);
        Long gia = Long.valueOf(0);
        for (Order_Item order_item : list) {
            if (order_item.getProduct().getId() == Integer.valueOf(idSP)) {
                return "redirect:/sua-hang/" + id;
            }
            gia += order_item.getUnit_price() * order_item.getCount();
        }


        for (Order order : orderList) {
            Order_Item order_item1 = new Order_Item();
            order_item1.setCount(1);
            order_item1.setUnit_price(product.getPrice());
            order_item1.setProduct(product);
            order_item1.setOrder(order);

            System.out.println(gia + product.getPrice() + order.getTotalShip());
            order.setTotal(gia + product.getPrice() + order.getTotalShip());
            orderService.saveOrder(order);
            order_ItemService.saveOrder_Item(order_item1);
            product.setQuantity(product.getQuantity() - 1);
            productService.saveProduct(product);
            return "redirect:/sua-hang/" + id;
        }

        return "redirect:/sua-hang/" + id;

    }


    @GetMapping("/sua-don-hang/{id}")
    public String giaoDien(@PathVariable() int id, Model model) {
        model.addAttribute("id", id);
        Order order = orderService.findById(id);

        model.addAttribute("detail", order);
        return "sua-thong-tin-don-hang-admin.html";
    }

    @PostMapping("/sua-thong-tin-khach-hang/{id}")
    public String suaThongTin(@PathVariable() int id, @RequestParam("fullname1") String fullname1,
                              @RequestParam("phone1") String phone1,
                              @RequestParam("province1") String province1,
                              @RequestParam("districts1") String districts1,
                              @RequestParam("wards1") String wards1,
                              @RequestParam("note1") String note1,
                              @RequestParam("ship") String ship,
                              Model model) {
        if (fullname1.trim().isEmpty()) {
            model.addAttribute("loiten", "Tên không được để trống");
            model.addAttribute("id", id);
            Order order = orderService.findById(id);
            model.addAttribute("detail", order);
            return "sua-thong-tin-don-hang-admin.html";

        } else if (phone1.trim().isEmpty()) {
            model.addAttribute("loisdt", "Số điện thoại không được để trống");
            model.addAttribute("id", id);
            Order order = orderService.findById(id);
            model.addAttribute("detail", order);
            return "sua-thong-tin-don-hang-admin.html";
        } else if (phone1.trim().length() < 10 || phone1.trim().length() > 10) {
            model.addAttribute("loisdt1", "Số điện thoại phải có 10 số");
            model.addAttribute("id", id);
            Order order = orderService.findById(id);
            model.addAttribute("detail", order);
            return "sua-thong-tin-don-hang-admin.html";
        } else if (Integer.valueOf(phone1) < 1) {
            model.addAttribute("loisdt2", "Số điện thoại không được âm");
            model.addAttribute("id", id);
            Order order = orderService.findById(id);
            model.addAttribute("detail", order);
            return "sua-thong-tin-don-hang-admin.html";
        } else if (province1.isEmpty()) {
            model.addAttribute("tinh", "Vui lòng chọn tỉnh ");
            model.addAttribute("id", id);
            Order order = orderService.findById(id);
            model.addAttribute("detail", order);
            return "sua-thong-tin-don-hang-admin.html";
        } else if (note1.trim().isEmpty()) {
            model.addAttribute("soNha", "Vui lòng nhập số nhà cụ thể ");
            model.addAttribute("id", id);
            Order order = orderService.findById(id);
            model.addAttribute("detail", order);
            return "sua-thong-tin-don-hang-admin.html";
        } else {
            try {
                Order order = orderService.findById(id);

                Long giaTong = order.getTotal();
                Long giaShip = Long.valueOf(order.getTotalShip());
                Long shi1p = Long.valueOf(ship);
                System.out.println(giaTong);
                System.out.println(giaShip);
                System.out.println(shi1p);
                Long a1 = giaTong - giaShip + shi1p;
                System.out.println(a1);
                order.setFullname(fullname1);
                order.setPhone(phone1);
                order.setCountry(province1);
                order.setAddress(districts1);
                order.setWards(wards1);
                order.setNote(note1);
                order.setTotalShip(Integer.valueOf(ship));
                order.setTotal(a1);
                orderService.saveOrder(order);
                return "redirect:/sua-hang/" + id;
            } catch (Exception e) {
                model.addAttribute("huyen", "Vui lòng chọn huyện ");
                model.addAttribute("xa", "Vui lòng chọn xã ");
                model.addAttribute("id", id);
                Order order = orderService.findById(id);
                model.addAttribute("detail", order);
                return "sua-thong-tin-don-hang-admin.html";
            }
        }

    }


    @PostMapping("/cap-nhap/{id}")
    public String capNhap(@PathVariable int id, HttpServletRequest request, Model model) {
        List<Order_Item> orderItems = order_ItemService.getAllByOrder_Id(id);
        Order order = orderService.findById(id);
        int a = 1;
        Long giaTong = Long.valueOf(0);
        for (Order_Item orderItem : orderItems) {
            Product product = productService.getProductById(orderItem.getProduct().getId());


            String soLuongParameter = request.getParameter("soLuong" + a);
            String giaTienParameter = request.getParameter("giaTien" + a);

            // Validate and parse parameters
            try {
                int count = Integer.parseInt(soLuongParameter);
                int giaTien = Integer.parseInt(giaTienParameter);

                // Update the order item with the new values
                System.out.println(count);
                System.out.println(giaTien);
                product.setQuantity(product.getQuantity()+orderItem.getCount()-count);
                productService.saveProduct(product);
                orderItem.setCount(count);
                orderItem.setUnit_price(Long.valueOf(giaTien));
                order_ItemService.saveOrder_Item(orderItem);



            } catch (NumberFormatException e) {
                // Handle the case where parsing fails (invalid input)
                e.printStackTrace(); // Log or handle the exception as needed
            }

            a++;
        }
        for (Order_Item orderItem : orderItems) {
            giaTong += orderItem.getUnit_price();
        }
        Long b= giaTong + order.getTotalShip();
        System.out.println(giaTong);
        System.out.println(b);
        order.setTotal(giaTong+order.getTotalShip());
        orderService.saveOrder(order);


        return "redirect:/sua-hang/" + id;
    }
}
