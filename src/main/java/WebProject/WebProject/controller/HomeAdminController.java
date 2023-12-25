package WebProject.WebProject.controller;


import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeAdminController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    Order_ItemService order_ItemService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    MailService mailService;

    @Autowired
    HttpSession session;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    AuthorService authorService;

    @Autowired
    ProducerService producerService;

    @Autowired
    BookCoverService bookCoverService;


    java.util.Date date = new java.util.Date();
    SimpleDateFormat fm = new SimpleDateFormat("yyyy");
    String hienTai = fm.format(date);

    @GetMapping("/home-admin")
    public String hienThi(Model model){
            User admin = (User) session.getAttribute("admin");
            if (admin == null) {
                return "redirect:/signin-admin";
            } else {
                List<Order> listOrder = orderService.findAll();
                List<Product> listProduct = productService.getAllProduct();
                List<User> listUser = userService.findAll();
                List<Category> listCategory = categoryService.getAllCategories();

                List<Order> recentOrders = orderService.findTop5RecentOrder();
                List<String> recentUser = orderService.findTop5RecentCustomer();
                List<User> recentCustomer = new ArrayList<>();
                for (String y : recentUser) {
                    recentCustomer.add(userService.findByIdAndRole(y, "user"));
                }
                model.addAttribute("Total_Order", listOrder.size());
                model.addAttribute("Total_Product", listProduct.size());
                model.addAttribute("Total_User", listUser.size());
                model.addAttribute("Total_Category", listCategory.size());
                model.addAttribute("recentOrders", recentOrders);
                model.addAttribute("recentCustomer", recentCustomer);
                return "/admin/home";
            }
        }

    }

