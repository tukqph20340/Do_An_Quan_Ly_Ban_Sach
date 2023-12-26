package WebProject.WebProject.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import WebProject.WebProject.entity.ActiveOrder;
import WebProject.WebProject.entity.Promotion;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.model.Mail;
import WebProject.WebProject.repository.OrderRepository;
import WebProject.WebProject.repository.WalletRepository;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.MailService;
import WebProject.WebProject.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;

import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Order_Item;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.CartService;
import WebProject.WebProject.service.OrderService;
import WebProject.WebProject.service.Order_ItemService;
import WebProject.WebProject.service.ProductService;
import momo.MomoModel;
import momo.ResultMoMo;
import utils.Constant;
import utils.Decode;

@Controller
public class OrderController {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CookieService cookie;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ProductService productService;

    @Autowired
    Order_ItemService order_ItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    HttpSession session;

    @Autowired
    PromotionService promotionService;

    @Autowired
    MailService mailService;

    @GetMapping("checkout")
    public String CheckOutView(Model model, @RequestParam(name = "discountCode", required = false) String discountCode) {
        User user = (User) session.getAttribute("acc");
        List<Cart> listCart1 = cartService.getListCartSelected();
        List<Promotion> listPo = promotionService.getAll();
        model.addAttribute("listKhuyenMai", listPo);
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        if (user == null) {
            session.setAttribute("AddToCartErr", "Vui lòng đăng nhập trước khi thực hiện thao tác!");
            return "redirect:/home";
        } else {


            List<Cart> Cart = cartService.GetAllCartByUser_id(user.getId());

            System.out.println(Cart);
            long Total = 0;

            if (!Cart.isEmpty() && !listCart1.isEmpty()) {
                for (Cart cart : Cart) {
                    Product listP = productService.getProductById(cart.getProduct().getId());
                    if (cart.getCount() <= listP.getQuantity()) {
                        System.out.println(cart.getCount());
                        if (cart.isSelected()) {
                            long price = cart.getProduct().getPrice();
                            System.out.println(price);
                            long quantity = cart.getCount();
                            Total += price * quantity;
                        }

                    } else {
                        session.setAttribute("loiSL", "Số Lượng Không Đủ ");
                        List<Cart> listCart = cartService.GetAllCartByUser_id(user.getId());
                        for (Cart y : listCart) {
                            if (y.isSelected()) {
                                y.setCount(Long.valueOf(1));
                                cartService.saveCart(cart);
                                Total = Total + y.getCount() * y.getProduct().getPrice();
                            }
                            if (listCart != null) {
                                model.addAttribute("Total", Total);
                                model.addAttribute("listCart", listCart);

                            }
                            model.addAttribute("soLuong", listCart.size());
                        }
                        return "shopping-cart";
                    }

                }
                model.addAttribute("Total", Total);
                session.setAttribute("Total", Total);
                if (discountCode != null && !discountCode.isEmpty()) {
                    // Tìm mã giảm giá trong cơ sở dữ liệu dựa trên discountCode
                    Promotion discount = promotionService.checkPromotion(discountCode);
                    if (discount != null) {
//                        int giamGia = Math.toIntExact(discount.getDiscountValue()); // Lấy giá trị giảm giá từ mã
//                        int discountType = discount.getDiscountType();
//                        if (discountType == 0) {
//                            // Giảm theo giá trị cố định
//                            giamGia = Math.toIntExact(discount.getDiscountValue());
//
//                        } else if (discountType == 1) {
                        // Giảm theo phần trăm
                        int giamGia = 0;
                        giamGia = (int) (Total * (discount.getDiscountValue() / 100.0));
//                        }


                        int maximumDiscountValue = Math.toIntExact(discount.getMaximumDiscountValue());
                        if (giamGia > maximumDiscountValue) {
                            giamGia = maximumDiscountValue;
                        }
                        long totalAfterDiscount = Total - giamGia;
                        if (totalAfterDiscount < 0) {
                            totalAfterDiscount = 0;
                        }
                        System.out.println(Total);
                        System.out.println(giamGia);
                        model.addAttribute("discount", giamGia);
                        model.addAttribute("totalAfterDiscount", totalAfterDiscount);
                    } else {
                        // Hiển thị thông báo mã giảm giá không tồn tại
                        model.addAttribute("discountNotFound", "Mã giảm giá không tồn tại.");
                    }
                }
                @SuppressWarnings("unchecked")
                List<Cart> listCart = cartService.getListCartSelected();
                System.out.println(listCart1);
                model.addAttribute("listCart", listCart);
                return "checkout";
            } else {
                session.setAttribute("CartIsEmpty", "CartIsEmpty");
                return "redirect:/cart";
            }
        }

    }


    @PostMapping("/checkout")
    public String CheckOut(@ModelAttribute("fullname") String fullname, @ModelAttribute("country") String country,
                           @ModelAttribute("address") String address, @ModelAttribute("phone") String phone,
                           @ModelAttribute("wards") String wards,
                           @ModelAttribute("email") String email, @ModelAttribute("note") String note, @ModelAttribute("active") String active,
                           @RequestParam(value = "payOndelivery", defaultValue = "false") boolean payOndelivery,
                           @RequestParam(value = "payWithMomo", defaultValue = "false") boolean payWithMomo,
                           @RequestParam(value = "payVi", defaultValue = "false") boolean payVi, Model model,
                           HttpServletResponse resp, HttpServletRequest request1) throws Exception {
        try {

            String vi = request1.getParameter("discal");
            System.out.println(vi);
            String giaShip = request1.getParameter("ship");
            String toTalFinal = request1.getParameter("toTalFinal");
            System.out.println(giaShip);
            System.out.println(toTalFinal);
            String a = session.getAttribute("Total").toString();
            User user = (User) session.getAttribute("acc");
            Mail mail1 = new Mail();
            mail1.setMailFrom("nguyentrunganhnta43@gmail.com");
            mail1.setMailTo(user.getEmail());
            mail1.setMailSubject("Nhà sách Opacarophile");
            mail1.setMailContent("Quý Khách Đã Mua Hàng Thành Công Đơn Hàng Đang Chờ Xác Nhận");
            mailService.sendEmail(mail1);
            long Total = Long.valueOf(a);
            int toTalShip = Integer.parseInt(giaShip);
            Long toTalAll = Long.valueOf(toTalFinal);
            String diaChiChon = request1.getParameter("diaChiChon");
            if ("diaChiCu".equals(diaChiChon) || "diaChiMoi".equals(diaChiChon)) {
                TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Jakarta");


                // Tạo đối tượng SimpleDateFormat với múi giờ của Việt Nam
                SimpleDateFormat vietnamDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                vietnamDateFormat.setTimeZone(vietnamTimeZone);

                // Lấy ngày giờ hiện tại theo múi giờ của Việt Nam và định dạng thành chuỗi
                long millis = System.currentTimeMillis();
                String booking_date = vietnamDateFormat.format(millis);
                System.out.println(booking_date);
                @SuppressWarnings("unchecked")
                List<Cart> listCart = cartService.getListCartSelected();

                String status = "Pending";
                String payment_method = null;
                if (payOndelivery == true) {
                    payment_method = "Payment on delivery";
                } else if (payWithMomo == true) {
                    payment_method = "Payment with momo";
                } else {
                    payment_method = "Ví";
                }
                Order newOrder = new Order();

                String tenKhachHangCu = request1.getParameter("fullname1");
                System.out.println("test" + tenKhachHangCu);
                String soDienThoaiCu = request1.getParameter("phone1");
                String mail = request1.getParameter("email1");
                String quocGiaCu = request1.getParameter("province1");
                String thanhPhoCu = request1.getParameter("districts1");
                String diaChiCu = request1.getParameter("wards1");
                String messCu = request1.getParameter("note1");
                if (vi == null) {
                    newOrder.setTotal(toTalAll);
                    newOrder.setTotalShip(toTalShip);
                    newOrder.setAddress("diaChiMoi".equals(diaChiChon) ? thanhPhoCu : address);
                    newOrder.setBooking_Date(booking_date);
                    newOrder.setCountry("diaChiMoi".equals(diaChiChon) ? quocGiaCu : country);
                    newOrder.setWards("diaChiMoi".equals(diaChiChon) ? diaChiCu : wards);
                    newOrder.setEmail("diaChiMoi".equals(diaChiChon) ? mail : email);
                    newOrder.setFullname("diaChiMoi".equals(diaChiChon) ? tenKhachHangCu : fullname);
                    newOrder.setNote("diaChiMoi".equals(diaChiChon) ? messCu : note);
                    newOrder.setPayment_Method(payment_method);
                    newOrder.setPhone("diaChiMoi".equals(diaChiChon) ? soDienThoaiCu : phone);
                    newOrder.setStatus(status);
                    newOrder.setUser(user);
                    newOrder.setActiveOrder(ActiveOrder.builder().id("1").build());
                    newOrder.setTotalDiscount(Float.valueOf(0));
                } else {
                    newOrder.setTotal(toTalAll);
                    newOrder.setTotalShip(toTalShip);
                    newOrder.setAddress("diaChiMoi".equals(diaChiChon) ? thanhPhoCu : address);
                    newOrder.setBooking_Date(booking_date);
                    newOrder.setCountry("diaChiMoi".equals(diaChiChon) ? quocGiaCu : country);
                    newOrder.setWards("diaChiMoi".equals(diaChiChon) ? diaChiCu : wards);
                    newOrder.setEmail("diaChiMoi".equals(diaChiChon) ? mail : email);
                    newOrder.setFullname("diaChiMoi".equals(diaChiChon) ? tenKhachHangCu : fullname);
                    newOrder.setNote("diaChiMoi".equals(diaChiChon) ? messCu : note);
                    newOrder.setPayment_Method(payment_method);
                    newOrder.setPhone("diaChiMoi".equals(diaChiChon) ? soDienThoaiCu : phone);
                    newOrder.setStatus(status);
                    newOrder.setUser(user);
                    newOrder.setActiveOrder(ActiveOrder.builder().id("1").build());
                    newOrder.setTotalDiscount(Float.valueOf(vi));
                }


                if (payment_method == "Payment with momo") {
                    session.setAttribute("newOrder", newOrder);
                    ObjectMapper mapper = new ObjectMapper();
                    int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
                    String orderId = Integer.toString(code);
                    MomoModel jsonRequest = new MomoModel();
                    jsonRequest.setPartnerCode(Constant.IDMOMO);
                    jsonRequest.setOrderId(orderId);
                    jsonRequest.setStoreId(orderId);
                    jsonRequest.setRedirectUrl(Constant.redirectUrl);
                    jsonRequest.setIpnUrl(Constant.ipnUrl);
                    jsonRequest.setAmount(String.valueOf(toTalAll));
                    jsonRequest.setOrderInfo("Thanh toán Opacarophlie");
                    jsonRequest.setRequestId(orderId);
                    jsonRequest.setOrderType(Constant.orderType);
                    jsonRequest.setRequestType(Constant.requestType);
                    jsonRequest.setTransId("1");
                    jsonRequest.setResultCode("200");
                    jsonRequest.setMessage("");
                    jsonRequest.setPayType(Constant.payType);
                    jsonRequest.setResponseTime("300000");
                    jsonRequest.setExtraData("");
                    String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
                            + jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
                            + jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
                            + Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
                            + Constant.requestType;
                    String signature = Decode.encode(Constant.serectkey, decode);
                    jsonRequest.setSignature(signature);
                    String json = mapper.writeValueAsString(jsonRequest);
                    HttpClient client = HttpClient.newHttpClient();
                    ResultMoMo res = new ResultMoMo();

                    try {
                        HttpRequest request = HttpRequest.newBuilder().uri(new URI(Constant.Url))
                                .POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        res = mapper.readValue(response.body(), ResultMoMo.class);
                    } catch (InterruptedException | URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (res == null) {
                        session.setAttribute("error_momo", "Thanh toán thất bại");
                        return "redirect:/home";
                    } else {
//					return "redirect:/shop";
//				resp.sendRedirect(res.payUrl);
                        return "redirect:" + res.payUrl;
                    }
                } else if (payment_method.equals("Ví")) {
                    Wallet wallet = walletRepository.findByUserId(user.getId());
                    Float tien = wallet.getPrice() - toTalAll;
                    if (tien < 0) {
                        session.setAttribute("AddToCartErr", "Thanh toán không thành công!");
                        return "redirect:/checkout";
                    } else {
                        wallet.setPrice(tien);
                        walletRepository.save(wallet);
                        orderService.saveOrder(newOrder);
                        List<Order> listOrder = orderService.getAllOrderByUser_Id(user.getId());
                        newOrder = listOrder.get(listOrder.size() - 1);
                        for (Cart y : listCart) {
                            Product product = y.getProduct();
                            product.setQuantity(Math.toIntExact(Long.valueOf(product.getQuantity() - y.getCount())));
                            product.setSold(Math.toIntExact(Long.valueOf(product.getSold() + y.getCount())));
                            productService.saveProduct(product);
                            Order_Item newOrder_Item = new Order_Item();
                            newOrder_Item.setCount(Math.toIntExact(Long.valueOf(y.getCount())));
                            newOrder_Item.setOrder(newOrder);
                            newOrder_Item.setProduct(y.getProduct());
                            order_ItemService.saveOrder_Item(newOrder_Item);
                            cartService.deleteById(y.getId());
                        }
                        listOrder = orderService.getAllOrderByUser_Id(user.getId());
                        newOrder = listOrder.get(listOrder.size() - 1);
                        session.setAttribute("order", newOrder);
                        return "redirect:/invoice";

                    }


                } else {
                    orderService.saveOrder(newOrder);
                    List<Order> listOrder = orderService.getAllOrderByUser_Id(user.getId());
                    newOrder = listOrder.get(listOrder.size() - 1);
                    for (Cart y : listCart) {
                        Product product = y.getProduct();
                        product.setQuantity(Math.toIntExact(Long.valueOf(product.getQuantity() - y.getCount())));
                        product.setSold(Math.toIntExact(Long.valueOf(product.getSold() + y.getCount())));
                        productService.saveProduct(product);
                        Order_Item newOrder_Item = new Order_Item();
                        newOrder_Item.setCount(Math.toIntExact(Long.valueOf(y.getCount())));
                        newOrder_Item.setOrder(newOrder);
                        newOrder_Item.setProduct(y.getProduct());
                        order_ItemService.saveOrder_Item(newOrder_Item);
                        cartService.deleteById(y.getId());
                    }
                    listOrder = orderService.getAllOrderByUser_Id(user.getId());
                    newOrder = listOrder.get(listOrder.size() - 1);
                    session.setAttribute("order", newOrder);
                    return "redirect:/invoice";
                }


            }


        } catch (Exception e) {
            session.setAttribute("AddToCartErr", "Thanh toán không thành công!");
            return "redirect:/checkout";
        }

        return null;
    }

    @GetMapping("paywithmomo")
    public String PayWithMomoGet(@ModelAttribute("message") String message, Model model) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        if (!message.equals("Successful.")) {
            session.setAttribute("error_momo", "Thanh toán không thành công!");
            return "redirect:/home";
        } else {
            @SuppressWarnings("unchecked")
            List<Cart> listCart = cartService.getListCartSelected();
            User user = (User) session.getAttribute("acc");
            Order newOrder = (Order) session.getAttribute("newOrder");
            orderService.saveOrder(newOrder);
            List<Order> listOrder = orderService.getAllOrderByUser_Id(user.getId());
            newOrder = listOrder.get(listOrder.size() - 1);
            for (Cart y : listCart) {
                Product product = y.getProduct();
                product.setQuantity(Math.toIntExact(Long.valueOf(product.getQuantity() - y.getCount())));
                product.setSold(Math.toIntExact(Long.valueOf(product.getSold() + y.getCount())));
                productService.saveProduct(product);
                Order_Item newOrder_Item = new Order_Item();
                newOrder_Item.setCount(Math.toIntExact(Long.valueOf(y.getCount())));
                newOrder_Item.setOrder(newOrder);
                newOrder_Item.setProduct(y.getProduct());
                order_ItemService.saveOrder_Item(newOrder_Item);
                cartService.deleteById(y.getId());
            }
            listOrder = orderService.getAllOrderByUser_Id(user.getId());
            newOrder = listOrder.get(listOrder.size() - 1);
            session.setAttribute("order", newOrder);
            System.out.println(newOrder);
            return "redirect:/invoice";
        }
    }

    @GetMapping("invoice")
    public String Invoice(Model model) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        Order order = (Order) session.getAttribute("order");
        String invoiceView = (String) session.getAttribute("invoiceView");
        session.setAttribute("invoiceView", null);
        List<Order_Item> listOrder_Item = order_ItemService.getAllByOrder_Id(order.getId());
        model.addAttribute("invoiceView", invoiceView);
        model.addAttribute("listOrder_Item", listOrder_Item);
        model.addAttribute("order", order);
        return "invoice";
    }

    @GetMapping("/invoice/{id}")
    public String InvoiceView(@PathVariable int id, Model model, HttpServletRequest request) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        String referer = request.getHeader("Referer");
        model.addAttribute("referer", referer);
        Order order = orderService.findById(id);
        session.setAttribute("order", order);
        session.setAttribute("invoiceView", "view");
        return "redirect:/invoice";
    }

    @GetMapping("statistic")
    public String Statistic(Model model) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        Order order = (Order) session.getAttribute("order");
        String invoiceView = (String) session.getAttribute("invoiceView");
        session.setAttribute("invoiceView", null);
        List<Order_Item> listOrder_Item = order_ItemService.getAllByOrder_Id(order.getId());
        model.addAttribute("invoiceView", invoiceView);
        model.addAttribute("listOrder_Item", listOrder_Item);
        System.out.println(listOrder_Item);
        model.addAttribute("order", order);
        return "statistic";
    }

    @GetMapping("/statistic/{id}")
    public String StatisticView(@PathVariable int id, Model model, HttpServletRequest request) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        String referer = request.getHeader("Referer");
        model.addAttribute("referer", referer);
        Order order = orderService.findById(id);
        session.setAttribute("order", order);
        session.setAttribute("invoiceView", "view");
        return "redirect:/statistic";
    }

    @GetMapping("/myhistory")
    public String Myhistory(Model model, HttpServletRequest request) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        String referer = request.getHeader("Referer");
        User user = (User) session.getAttribute("acc");
        if (user == null) {
            return "redirect:" + referer;
        } else {
            List<Order> listOrderActive1 = orderService.getAllOrderByUser_Id(user.getId());
            Collections.reverse(listOrderActive1);
            model.addAttribute("listOrder", listOrderActive1);
            System.out.println(listOrderActive1);

            List<Order> listOrderActive2 = orderService.getOrderActive2((user.getId()));
            Collections.reverse(listOrderActive2);
            model.addAttribute("listOrderActive2", listOrderActive2);

            List<Order> listOrderActive3 = orderService.getOrderActive3((user.getId()));
            Collections.reverse(listOrderActive3);
            model.addAttribute("listOrderActive3", listOrderActive3);

            List<Order> listOrderActive4 = orderService.getOrderActive4((user.getId()));
            Collections.reverse(listOrderActive4);
            model.addAttribute("listOrderActive4", listOrderActive4);

            List<Order> listOrderActive6 = orderService.getOrderActive6((user.getId()));
            Collections.reverse(listOrderActive6);
            model.addAttribute("listOrderActive6", listOrderActive6);

            List<Order> listOrderActive7 = orderService.getOrderActive7((user.getId()));
            Collections.reverse(listOrderActive7);
            model.addAttribute("listOrderActive7", listOrderActive7);


            List<Order> listOrderActive5 = orderService.getOrderActive5((user.getId()));
            Collections.reverse(listOrderActive5);
            model.addAttribute("listOrderActive5", listOrderActive5);

            List<Order> listOrderActive11 = orderRepository.findAllByUser_idAndActiveOrder((user.getId()));
            Collections.reverse(listOrderActive11);
            model.addAttribute("listOrderActive11", listOrderActive11);
            System.out.println(listOrderActive1);
            for (Order y : listOrderActive1) {
                System.out.println(y.getOrder_Item());
            }
        }
        return "myhistory";
    }

    @GetMapping("/dashboard-orders8")
    public String DashboardOrderView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderRepository.findAllByUser_idAndActiveOrder11(pageable);
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders8";
        }
    }

    @GetMapping("/dashboard-orders8/{page}")
    public String DashboardOrderPageView(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderRepository.findAllByUser_idAndActiveOrder11(pageable);
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders8";
        }
    }
}
