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
        Cookie cookie = cookieService.read("idHoaDon");
        order_ItemService.deleteById(id);
        return "redirect:/sua-hang/" + cookie.getValue();
    }

    @PostMapping("/them-don-hang/{id}")
    public String themDonHang(@PathVariable int id, @RequestParam("idSP") String idSP, Model model) {
        Product product = productService.getProductById(Integer.valueOf(idSP));
        List<Order> orderList = orderService.findOrderById(id);
        List<Order_Item> list = order_ItemService.getAllByOrder_Id(id);
        for (Order_Item order_item : list) {

            if (order_item.getProduct().getId() == Integer.valueOf(idSP)) {

                return "redirect:/sua-hang/" + id;
            }
        }
        for (Order order : orderList) {
            Order_Item order_item1 = new Order_Item();
            order_item1.setCount(1);
            order_item1.setUnit_price(product.getPrice());
            order_item1.setProduct(product);
            order_item1.setOrder(order);
            order_ItemService.saveOrder_Item(order_item1);
            return "redirect:/sua-hang/" + id;
        }


        return "redirect:/sua-hang/" + id;
    }


}
