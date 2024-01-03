package WebProject.WebProject.controller;

import WebProject.WebProject.dto.OrderDto;
import WebProject.WebProject.entity.ActiveOrder;
import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.repository.OrderDtoPository;
import WebProject.WebProject.repository.OrderRepository;
import WebProject.WebProject.repository.Order_ItemRepository;
import WebProject.WebProject.repository.ThongKeRepository;
import WebProject.WebProject.repository.WalletRepository;
import WebProject.WebProject.service.CartService;
import WebProject.WebProject.service.CategoryService;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.OrderService;
import WebProject.WebProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Controller
public class thongKeTableController {
    @Autowired
    Order_ItemRepository repository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ThongKeRepository thongKeRepository;
    @Autowired
    OrderDtoPository orderDtoPository;
    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;


    @Autowired
    WalletRepository walletRepository;

    @Autowired
    OrderRepository orderService;

    @Autowired
    CookieService cookie;

    @Autowired
    HttpSession session;

//    @GetMapping("/thong-ke-admin-san-pham")
//    public String getAll(Model model) {
//        List<Category> listCate = categoryService.getAll();
//        List<Map<String, Object>> resultList = thongKeRepository.thongKeProduct();
//        model.addAttribute("resultList", resultList);
//        model.addAttribute("listCate", listCate);
//
//        List<Map<String, Object>> resultList1 = thongKeRepository.thongKeProductByCategory("Văn Học");
//        model.addAttribute("resultList1", resultList1);
//        return "thongKeSP.html";
//    }
//
//    @GetMapping("/thong-ke-admin-san-pham/tim-kiem")
//    public String getAll1(Model model, @RequestParam("loai") String ten) {
//        List<Category> listCate = categoryService.getAll();
//        List<Map<String, Object>> resultList = thongKeRepository.thongKeProduct();
//        model.addAttribute("resultList", resultList);
//        model.addAttribute("listCate", listCate);
//
//        List<Map<String, Object>> resultList1 = thongKeRepository.thongKeProductByCategory(ten);
//        model.addAttribute("resultList1", resultList1);
//        return "thongKeSP.html";
//    }

//    @GetMapping("/thong-ke-admin-don-hang")
//    public String getAll4(Model model) {
//            List<Order> list1 = orderService.findByActiveOrder_Id("1");
//            int trangThai1Sum = 0;
//            for (Order order : list1) {
//                trangThai1Sum++;
//            }
//            model.addAttribute("trangThai1Sum", trangThai1Sum);
//
//            List<Order> list2 = orderService.findByActiveOrder_Id("2");
//
//            int trangThai2Sum = 0;
//            for (Order order : list2) {
//                trangThai2Sum++;
//            }
//            model.addAttribute("trangThai2Sum", trangThai2Sum);
//
//            List<Order> list3 = orderService.findByActiveOrder_Id("3");
//            int trangThai3Sum = 0;
//            for (Order order : list3) {
//                trangThai3Sum++;
//            }
//            System.out.println(trangThai3Sum);
//            List<Order> list4 = orderService.findByActiveOrder_Id("4");
//            int trangThai4Sum = 0;
//            for (Order order : list4) {
//                trangThai4Sum++;
//            }
//            System.out.println(trangThai4Sum);
//            List<Order> list5 = orderService.findByActiveOrder_Id("5");
//            int trangThai5Sum = 0;
//            for (Order order : list5) {
//                trangThai5Sum++;
//            }
//            System.out.println(trangThai5Sum);
//            List<Order> list6 = orderService.findByActiveOrder_Id("6");
//            int trangThai6Sum = 0;
//            for (Order order : list6) {
//                trangThai6Sum++;
//            }
//            System.out.println(trangThai6Sum);
//            List<Order> list7 = orderService.findByActiveOrder_Id("7");
//            int trangThai7Sum = 0;
//            for (Order order : list7) {
//                trangThai7Sum++;
//            }
//            System.out.println(trangThai7Sum);
//            List<Order> list8 = orderService.findByActiveOrder_Id("8");
//            int trangThai8Sum = 0;
//            for (Order order : list8) {
//                trangThai8Sum++;
//            }
//            System.out.println(trangThai8Sum);
//            List<Order> list9 = orderService.findByActiveOrder_Id("9");
//            int trangThai9Sum = 0;
//            for (Order order : list9) {
//                trangThai9Sum++;
//            }
//            System.out.println(trangThai9Sum);
//            List<Order> list10 = orderService.findByActiveOrder_Id("10");
//            int trangThai10Sum = 0;
//            for (Order order : list10) {
//                trangThai10Sum++;
//            }
//            System.out.println(trangThai10Sum);
//            model.addAttribute("trangThai3Sum", trangThai3Sum);
//            model.addAttribute("trangThai4Sum", trangThai4Sum);
//            model.addAttribute("trangThai5Sum", trangThai5Sum);
//            model.addAttribute("trangThai6Sum", trangThai6Sum);
//            model.addAttribute("trangThai7Sum", trangThai7Sum);
//            model.addAttribute("trangThai8Sum", trangThai8Sum);
//            model.addAttribute("trangThai9Sum", trangThai9Sum);
//            model.addAttribute("trangThai10Sum", trangThai10Sum);
//            List<Map<String, Object>> resultList = thongKeRepository.getCountByStatusAndDate();
//            model.addAttribute("resultList", resultList);
//        return "thongKeDH.html";
//    }

    @GetMapping("/thong-ke-admin-don-hang/tim-kiem")
    public String getAll51(Model model, @RequestParam("ngay1") String ngay1
            , @RequestParam("ngay2") String ngay2) {

        if (ngay1.isEmpty() || ngay2.isEmpty()) {
            return "redirect:/thong-ke-admin-don-hang";
        } else {

            List<Map<String, Object>> resultList = thongKeRepository.getCountByStatusAndDate12(ngay1, ngay2);
            model.addAttribute("resultList", resultList);

            return "thongKeDH.html";
        }

    }

    @GetMapping("/thong-tin")
    public String getAll10(Model model) {
        Cookie user_name = cookie.read("user_name");
        User user = (User) session.getAttribute("acc");
        if (user == null) {
            return "redirect:/home";
        } else {
            Cookie remember = cookie.read("remember");
            String error_momo = (String) session.getAttribute("error_momo");
            String NoSignIn = (String) session.getAttribute("NoSignIn");
            session.setAttribute("NoSignIn", null);
            session.setAttribute("error_momo", null);
            String a = (String) session.getAttribute("NoSignIn");
            System.out.println(a);
            System.out.println(NoSignIn);
            User acc = (User) session.getAttribute("acc");


            List<Map<String, Object>> listOrder = orderDtoPository.getCountByStatus(user_name.getValue());
            model.addAttribute("listOrder", listOrder);

            if (remember != null) {
                acc = userService.findByIdAndRole(user_name.getValue(), "user");
                session.setAttribute("acc", acc);
                List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
                session.setAttribute("countCart", listCart.size());
            }
            if (acc != null) {
                List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
                session.setAttribute("countCart", listCart.size());
            }
            if (session.getAttribute("acc") == null)
                session.setAttribute("countCart", "0");
            model.addAttribute("error_momo", error_momo);
            model.addAttribute("NoSignIn", NoSignIn);
            Wallet vi = walletRepository.findByUserId(user_name.getValue());
            model.addAttribute("vi", vi);

            return "thong-tin-vi";


        }
    }

}
