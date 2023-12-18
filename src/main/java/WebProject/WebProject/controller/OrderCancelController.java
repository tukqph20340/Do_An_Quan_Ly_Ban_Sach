package WebProject.WebProject.controller;

import WebProject.WebProject.entity.ActiveOrder;
import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.OrderCancellation;
import WebProject.WebProject.entity.Order_Item;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.Statistic;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.model.Mail;
import WebProject.WebProject.repository.WalletRepository;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.MailService;
import WebProject.WebProject.service.OrderCancellationService;
import WebProject.WebProject.service.OrderService;
import WebProject.WebProject.service.Order_ItemService;
import WebProject.WebProject.service.ProductService;
import WebProject.WebProject.service.UserService;
import WebProject.WebProject.service.impl.OrderCancellationServiceImpl;
import WebProject.WebProject.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Controller
public class OrderCancelController {


    @Autowired
    OrderService orderService = new OrderServiceImpl();

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;
    @Autowired
    Order_ItemService order_itemService;

    @Autowired
    MailService mailService;

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    CookieService cookie;

    @Autowired
    OrderCancellationService orderCancellationService = new OrderCancellationServiceImpl();

    @PostMapping("/addToOrderCancel")
    public String addToCancel(@RequestParam("reason") String reason,
                              @RequestParam("order_id") int order_id,
                              Model model,
                              HttpServletRequest request, HttpSession session) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Jakarta");

        // Tạo đối tượng SimpleDateFormat với múi giờ của Việt Nam
        SimpleDateFormat vietnamDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vietnamDateFormat.setTimeZone(vietnamTimeZone);

        // Lấy ngày giờ hiện tại theo múi giờ của Việt Nam và định dạng thành chuỗi
        long millis = System.currentTimeMillis();
        String createAt = vietnamDateFormat.format(millis);
        String referer = request.getHeader("Referer");
        User user = (User) session.getAttribute("acc");
        List<Order> listOrders = orderService.findAll();
        if (user == null) {
            return "redirect:" + referer;
        } else {
            User user1 = userService.detail(user_name.getValue());
            Mail mail = new Mail();
            mail.setMailFrom("nguyentrunganhnta43@gmail.com");
            mail.setMailTo(user.getEmail());
            mail.setMailSubject("Nhà sách Opacarophile");
            mail.setMailContent("Quý Khách Đã Hủy Đơn Hàng Này Cảm Ơn Khác Hàng Đã Mua");
            mailService.sendEmail(mail);
            OrderCancellation order = new OrderCancellation();
            Wallet wallet = new Wallet();
            int cart = 0;
            for (Order order1 : listOrders) {
                if (order1.getPayment_Method().equals("Payment with momo") && order1.getUser().getId().equals(user.getId())) {
                    // Tìm thấy đơn hàng phù hợp với điều kiện
                    Order orderset = orderService.findById(order_id);
                    List<Order_Item> listOder = order_itemService.getAllByOrder_Id(orderset.getId());

                    System.out.println(listOder);
                    if (orderset != null) {
                        order.setReason(reason);
                        order.setCancellationDate(Timestamp.valueOf(createAt));
                        order.setActiveCancel(true);
                        order.setOrder(Order.builder().id(Integer.valueOf(order_id)).build());

                        if ("1".equals(orderset.getActiveOrder().getId())) {
                            orderset.setActiveOrder(ActiveOrder.builder().id("7").build());
                        }

                        for (Order_Item order_item : listOder) {
                            Product product = productService.getProductById(order_item.getProduct().getId());
                            product.setQuantity(product.getQuantity() + order_item.getCount());
                            productService.saveProduct(product);
                        }


                        orderset.setDeliveryFailedDate(createAt);



                        orderService.saveOrder(orderset);
                        orderCancellationService.saveOrderCancel(order);

                        // Cộng tiền vào ví
                        Wallet wallet1 = walletRepository.findByUserId(user.getId());
                        if (wallet1 == null) {
                            wallet = new Wallet();
                            wallet.setPrice(Float.valueOf(order1.getTotal()));
                            wallet.setUser(user);
                            walletRepository.save(wallet);
                            return "redirect:/myhistory";


                        } else {
                            System.out.println(wallet1.getPrice());
                            System.out.println(Float.valueOf(order1.getTotal()));
                            System.out.println(wallet1.getPrice() + Float.valueOf(orderset.getTotal()));
                            wallet1.setPrice(wallet1.getPrice() + Float.valueOf(orderset.getTotal()));
                            wallet1.setUser(user);
                            walletRepository.save(wallet1);
                            return "redirect:/myhistory";
                        }

                    }
                }
                if (order1.getPayment_Method().equals("Ví") && order1.getUser().getId().equals(user.getId())) {
                    Order orderset = orderService.findById(order_id);
                    List<Order_Item> listOder = order_itemService.getAllByOrder_Id(orderset.getId());
                    // Tìm thấy đơn hàng phù hợp với điều kiện
                    if (orderset != null) {
                        order.setReason(reason);
                        order.setCancellationDate(Timestamp.valueOf(createAt));
                        order.setActiveCancel(true);
                        order.setOrder(Order.builder().id(Integer.valueOf(order_id)).build());

                        if ("1".equals(orderset.getActiveOrder().getId())) {
                            orderset.setActiveOrder(ActiveOrder.builder().id("7").build());
                        }
                        orderset.setDeliveryFailedDate(createAt);
                        orderService.saveOrder(orderset);
                        orderCancellationService.saveOrderCancel(order);
                        for (Order_Item order_item : listOder) {
                            Product product = productService.getProductById(order_item.getProduct().getId());
                            product.setQuantity(product.getQuantity() + order_item.getCount());
                            productService.saveProduct(product);
                        }
                        // Cộng tiền vào ví
                        Wallet wallet1 = walletRepository.findByUserId(user.getId());
                        if (wallet1 == null) {
                            wallet = new Wallet();
                            wallet.setPrice(Float.valueOf(orderset.getTotal()));
                            wallet.setUser(user);
                            walletRepository.save(wallet);
                            return "redirect:/myhistory";
                            // Nếu ví đã tồn tại, cộng thêm tiền

                        } else {
                            // Nếu ví chưa tồn tại, tạo mới và cộng tiền
                            wallet1.setPrice(Float.valueOf(wallet1.getPrice() + orderset.getTotal()));
                            wallet1.setUser(user);
                            walletRepository.save(wallet1);
                            return "redirect:/myhistory";
                        }

                    }
                }
            }
            order.setReason(reason);
            order.setCancellationDate(Timestamp.valueOf(createAt));
            order.setActiveCancel(true);
            order.setOrder(Order.builder().id(Integer.valueOf(order_id)).build());
            Order orderset = orderService.findById(order_id);
            List<Order_Item> listOder = order_itemService.getAllByOrder_Id(orderset.getId());


            if ("1".equals(orderset.getActiveOrder().getId())) {
                orderset.setActiveOrder(ActiveOrder.builder().id("7").build());
            }

            for (Order_Item order_item : listOder) {
                Product product = productService.getProductById(order_item.getProduct().getId());
                product.setQuantity(product.getQuantity() + order_item.getCount());
                productService.saveProduct(product);
            }
            orderService.saveOrder(orderset);
            orderCancellationService.saveOrderCancel(order);




            return "redirect:/myhistory";


        }


    }

    @GetMapping("/huy-admin/{id}")
    public String addToCancel1(
            @PathVariable String id,
            Model model,
            HttpServletRequest request, HttpSession session) {

        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Jakarta");

        // Tạo đối tượng SimpleDateFormat với múi giờ của Việt Nam
        SimpleDateFormat vietnamDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vietnamDateFormat.setTimeZone(vietnamTimeZone);

        // Lấy ngày giờ hiện tại theo múi giờ của Việt Nam và định dạng thành chuỗi
        long millis = System.currentTimeMillis();
        String createAt = vietnamDateFormat.format(millis);
        Order listOrders = orderService.findById(Integer.valueOf(id));
        OrderCancellation order = new OrderCancellation();
        Wallet wallet = new Wallet();
        int cart = 0;
        if (listOrders.getPayment_Method().equals("Payment with momo")) {
            // Tìm thấy đơn hàng phù hợp với điều kiện
            List<Order_Item> listOder = order_itemService.getAllByOrder_Id(listOrders.getId());

            System.out.println(listOder);
            if (listOrders != null) {
                order.setCancellationDate(Timestamp.valueOf(createAt));
                order.setActiveCancel(true);
                order.setOrder(Order.builder().id(Integer.valueOf(Integer.valueOf(id))).build());

                if ("1".equals(listOrders.getActiveOrder().getId())) {
                    listOrders.setActiveOrder(ActiveOrder.builder().id("7").build());
                }

                for (Order_Item order_item : listOder) {
                    Product product = productService.getProductById(order_item.getProduct().getId());
                    product.setQuantity(product.getQuantity() + order_item.getCount());
                    productService.saveProduct(product);
                }
                listOrders.setDeliveryFailedDate(createAt);
                orderService.saveOrder(listOrders);
                orderCancellationService.saveOrderCancel(order);

                // Cộng tiền vào ví
                Wallet wallet1 = walletRepository.findByUserId(listOrders.getUser().getId());
                User use = userService.detail(listOrders.getUser().getId());
                if (wallet1 == null) {
                    wallet = new Wallet();
                    wallet.setPrice(Float.valueOf(listOrders.getTotal()));
                    wallet.setUser(use);
                    walletRepository.save(wallet);
                    return "redirect:/dashboard-orders1";


                } else {
                    System.out.println(wallet1.getPrice());
                    System.out.println(Float.valueOf(listOrders.getTotal()));
                    System.out.println(wallet1.getPrice() + Float.valueOf(listOrders.getTotal()));
                    wallet1.setPrice(wallet1.getPrice() + Float.valueOf(listOrders.getTotal()));
                    wallet1.setUser(use);
                    walletRepository.save(wallet1);
                    return "redirect:/dashboard-orders1";
                }

            }
        } else if (listOrders.getPayment_Method().equals("Ví")) {

            List<Order_Item> listOder = order_itemService.getAllByOrder_Id(listOrders.getId());
            // Tìm thấy đơn hàng phù hợp với điều kiện
            if (listOrders != null) {
                order.setCancellationDate(Timestamp.valueOf(createAt));
                order.setActiveCancel(true);
                order.setOrder(Order.builder().id(Integer.valueOf(id)).build());

                if ("1".equals(listOrders.getActiveOrder().getId())) {
                    listOrders.setActiveOrder(ActiveOrder.builder().id("7").build());
                }

                orderService.saveOrder(listOrders);
                orderCancellationService.saveOrderCancel(order);
                for (Order_Item order_item : listOder) {
                    Product product = productService.getProductById(order_item.getProduct().getId());
                    product.setQuantity(product.getQuantity() + order_item.getCount());
                    productService.saveProduct(product);
                }
                // Cộng tiền vào ví
                Wallet wallet1 = walletRepository.findByUserId(listOrders.getUser().getId());
                User user = userService.detail(listOrders.getUser().getId());
                if (wallet1 == null) {
                    wallet = new Wallet();
                    wallet.setPrice(Float.valueOf(listOrders.getTotal()));
                    wallet.setUser(user);
                    walletRepository.save(wallet);
                    return "redirect:/dashboard-orders1";
                    // Nếu ví đã tồn tại, cộng thêm tiền

                } else {
                    // Nếu ví chưa tồn tại, tạo mới và cộng tiền
                    wallet1.setPrice(Float.valueOf(wallet1.getPrice() + listOrders.getTotal()));
                    wallet1.setUser(user);
                    walletRepository.save(wallet1);
                    return "redirect:/dashboard-orders1";
                }

            }
        } else {
            order.setCancellationDate(Timestamp.valueOf(createAt));
            order.setActiveCancel(true);
            order.setOrder(Order.builder().id(Integer.valueOf(id)).build());
            List<Order_Item> listOder = order_itemService.getAllByOrder_Id(listOrders.getId());
            if ("1".equals(listOrders.getActiveOrder().getId())) {
                listOrders.setActiveOrder(ActiveOrder.builder().id("7").build());
            }

            for (Order_Item order_item : listOder) {
                Product product = productService.getProductById(order_item.getProduct().getId());
                product.setQuantity(product.getQuantity() + order_item.getCount());
                productService.saveProduct(product);
            }
            orderService.saveOrder(listOrders);
            orderCancellationService.saveOrderCancel(order);
            return "redirect:/dashboard-orders1";


        }
        return null;
    }
}