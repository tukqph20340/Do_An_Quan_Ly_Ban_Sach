package WebProject.WebProject.controller;

import WebProject.WebProject.entity.ActiveOrder;
import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.BookCover;
import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Order_Item;
import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.ProductImage;
import WebProject.WebProject.entity.Returns;
import WebProject.WebProject.entity.ReturnsImage;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.model.Mail;
import WebProject.WebProject.repository.OrderRepository;
import WebProject.WebProject.repository.ProductRepository;
import WebProject.WebProject.repository.ReturImageRepository;
import WebProject.WebProject.repository.ReturnsRepository;
import WebProject.WebProject.repository.WalletRepository;
import WebProject.WebProject.service.AuthorService;
import WebProject.WebProject.service.CartService;
import WebProject.WebProject.service.CategoryService;
import WebProject.WebProject.service.CloudinaryService;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.MailService;
import WebProject.WebProject.service.OrderService;
import WebProject.WebProject.service.Order_ItemService;
import WebProject.WebProject.service.ProducerService;
import WebProject.WebProject.service.ProductService;
import WebProject.WebProject.service.UserService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

@Controller
public class TraHangController {


    @Autowired
    Order_ItemService order_itemService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    ReturnsRepository returnsRepository;

    @Autowired
    MailService mailService;


    @Autowired
    ReturImageRepository returImageRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartService cartService;

    @Autowired
    HttpSession session;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    CookieService cookie;


    @GetMapping("/xac-nhan-tra-hang/{id}")
    public String InvoiceView(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        Order order = orderService.findById(id);
        session.setAttribute("order", order);
        Order order1 = (Order) session.getAttribute("order");
        List<Order_Item> listOrder_Item = order_itemService.getAllByOrder_Id(order.getId());
        model.addAttribute("listOrder_Item", listOrder_Item);
        model.addAttribute("order", order1);
        return "trahang.html";
    }

    @PostMapping("/xac-nhan-tra-hang/{id}")
    public String DashboardAddProductHandel(@PathVariable String id, Model model, @RequestParam("lyDo") String lyDo,
                                            @RequestParam("listImage") MultipartFile[] listImage
            , HttpServletRequest request) throws Exception {
        try {
            if (lyDo.isEmpty()) {
                model.addAttribute("loi1", "Lý do không được để trống không được để trống");
                Cookie user_name = cookie.read("user_name");
                Wallet vi = walletRepository.findByUserId(user_name.getValue());
                model.addAttribute("vi", vi);
                String referer = request.getHeader("Referer");
                model.addAttribute("referer", referer);
                Order order = orderService.findById(Integer.valueOf(id));
                session.setAttribute("order", order);
                Order order1 = (Order) session.getAttribute("order");
                List<Order_Item> listOrder_Item = order_itemService.getAllByOrder_Id(order.getId());
                model.addAttribute("listOrder_Item", listOrder_Item);
                model.addAttribute("order", order1);
                return "trahang.html";
            }else {
                long millis = System.currentTimeMillis();
                Date create_at = new java.sql.Date(millis);
                Order order = orderService.findById(Integer.valueOf(id));
                Returns returns = new Returns();
                returns.setReason(lyDo);
                returns.setOrder(order);
                returns.setReturnsDate(create_at);
                returns.setStatus(1);
                returnsRepository.save(returns);
                order.setActiveOrder(ActiveOrder.builder().id("8").build());
                orderService.saveOrder(order);
                Returns returns1 = returnsRepository.findByOrder_Id(order.getId());
                for (MultipartFile y : listImage) {
                    String urlImg = cloudinaryService.uploadFile(y);
                    ReturnsImage img = new ReturnsImage();
                    img.setUrlImage(urlImg);
                    img.setReturns(returns1);
                    returImageRepository.save(img);

                }
                return "redirect:/myhistory";
            }

        } catch (Exception e) {
            return "redirect:/myhistory";
        }

    }

    @GetMapping("/dashboard-invoice-tra-hang")
    public String Invoice1(Model model) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        Order order = (Order) session.getAttribute("order");
        String invoiceView = (String) session.getAttribute("invoiceView");
        session.setAttribute("invoiceView", null);
        List<Order_Item> listOrder_Item = order_itemService.getAllByOrder_Id(order.getId());
        Returns returns = returnsRepository.findByOrder_Id(order.getId());
        model.addAttribute("lyDo", returns.getReason());
        List<ReturnsImage> returnsImages = returImageRepository.findByReturnsId(returns.getId());
        model.addAttribute("returnsImages", returnsImages);
        model.addAttribute("invoiceView", invoiceView);
        model.addAttribute("listOrder_Item", listOrder_Item);
        model.addAttribute("order", order);
        return "/invoice-tra-hang.html";
    }

    @GetMapping("/dashboard-invoice-tra-hang/{id}")
    public String InvoiceView1(@PathVariable int id, Model model, HttpServletRequest request) {
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        String referer = request.getHeader("Referer");
        model.addAttribute("referer", referer);
        Order order = orderService.findById(id);
        session.setAttribute("order", order);
        session.setAttribute("invoiceView", "view");
        return "redirect:/dashboard-invoice-tra-hang";
    }

    @PostMapping("/tu-choi-hang/{id}")
    public String InvoiceView11(@PathVariable() Integer id, Model model, @RequestParam("description") String lyDo
            , @RequestParam("email") String email,
                                HttpServletRequest request) {
        Mail mail = new Mail();
        mail.setMailFrom("nguyentrunganhnta43@gmail.com");
        mail.setMailTo(email);
        mail.setMailSubject("Nhà sách Opacarophile");
        mail.setMailContent(lyDo);
        mailService.sendEmail(mail);
        Order order = orderService.findById(id);
        order.setActiveOrder(ActiveOrder.builder().id("9").build());
        orderService.saveOrder(order);
        return "redirect:/quan-ly-don-hang";
    }

    @GetMapping("/xac-nhan-hang/{id}")
    public String InvoiceView111(@PathVariable() Integer id, Model model,

                                 HttpServletRequest request) {
        Order order = orderService.findById(id);
        Mail mail = new Mail();
        mail.setMailFrom("nguyentrunganhnta43@gmail.com");
        mail.setMailTo(order.getEmail());
        mail.setMailSubject("Nhà sách Opacarophile");
        mail.setMailContent("Đơn hàng đã được xác nhận hoàn trả liên hệ qua sđt:0964925872 để được tư vấn");
        mailService.sendEmail(mail);
        order.setActiveOrder(ActiveOrder.builder().id("10").build());
        orderService.saveOrder(order);

        return "redirect:/quan-ly-don-hang";
    }


    @GetMapping("/dashboard-orders8/selected")
    public String locTraHang(Model model, @RequestParam("category-selected") String trangThai) {
        if (trangThai.equals("0")) {
            return "redirect:/dashboard-orders8";
        } else if (trangThai.equals("1")) {
            List<Order> pageOrder = orderRepository.findByActiveOrder_Id("8");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders11";
        } else if (trangThai.equals("2")) {
            List<Order> pageOrder = orderRepository.findByActiveOrder_Id("9");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders11";
        } else {
            List<Order> pageOrder = orderRepository.findByActiveOrder_Id("10");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders11";
        }
    }

    @GetMapping("/gui-thu")
    public String SendMessage(Model model,@RequestParam("gmail") String gmail) throws Exception {


        Mail mail = new Mail();
        mail.setMailFrom("thanhlol2k3@gmail.com");
        mail.setMailTo("nguyentrunganhnta43@gmail.com");
        mail.setMailSubject("Nhà sách Opacarophile");
        mail.setMailContent("test");
        mailService.sendEmail(mail);
        return "redirect:/home";
    }



}






