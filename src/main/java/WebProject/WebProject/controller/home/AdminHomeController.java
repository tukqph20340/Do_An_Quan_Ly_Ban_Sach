package WebProject.WebProject.controller.home;


import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.repository.OrderRepository;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("")
public class AdminHomeController {

    @Autowired
    OrderRepository orderService;
    @Autowired
    CookieService cookie;

    @Autowired
    HttpSession session;

    @GetMapping("/quan-ly-don-hang")
    public String hienThiHomeAdmin(Model model) {


        List<Order> listOrderActive1 = orderService.findByActiveOrder_Id("1");
        Collections.reverse(listOrderActive1);
        model.addAttribute("listOrder", listOrderActive1);
        System.out.println(listOrderActive1);

        List<Order> listOrderActive2 = orderService.findByActiveOrder_Id("2");
        Collections.reverse(listOrderActive2);
        model.addAttribute("listOrderActive2", listOrderActive2);

        List<Order> listOrderActive3 = orderService.findByActiveOrder_Id("3");
        Collections.reverse(listOrderActive3);
        model.addAttribute("listOrderActive3", listOrderActive3);

        List<Order> listOrderActive4 = orderService.findByActiveOrder_Id("4");
        Collections.reverse(listOrderActive4);
        model.addAttribute("listOrderActive4", listOrderActive4);

        List<Order> listOrderActive6 = orderService.findByActiveOrder_Id("6");
        Collections.reverse(listOrderActive6);
        model.addAttribute("listOrderActive6", listOrderActive6);

        List<Order> listOrderActive7 = orderService.findByActiveOrder_Id("7");
        Collections.reverse(listOrderActive7);
        model.addAttribute("listOrderActive7", listOrderActive7);


        List<Order> listOrderActive5 = orderService.findByActiveOrder_Id("5");
        Collections.reverse(listOrderActive5);
        model.addAttribute("listOrderActive5", listOrderActive5);

        List<Order> listOrderActive8 = orderService.findByActiveOrder_Id("8");
        Collections.reverse(listOrderActive8);
        model.addAttribute("listOrderActive8", listOrderActive8);
        List<Order> listOrderActive9 = orderService.findByActiveOrder_Id("9");
        Collections.reverse(listOrderActive9);
        model.addAttribute("listOrderActive9", listOrderActive9);
        List<Order> listOrderActive10 = orderService.findByActiveOrder_Id("10");
        Collections.reverse(listOrderActive10);
        model.addAttribute("listOrderActive10", listOrderActive10);
        System.out.println(listOrderActive1);
        for (Order y : listOrderActive1) {
            System.out.println(y.getOrder_Item());
        }
        return"/admin/quanlydonhang/quan-ly-don-hang.html";
    }
}

