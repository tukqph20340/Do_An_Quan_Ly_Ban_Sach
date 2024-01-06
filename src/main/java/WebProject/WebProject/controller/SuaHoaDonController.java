package WebProject.WebProject.controller;

import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Order_Item;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.OrderService;
import WebProject.WebProject.service.Order_ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    HttpSession session;


    @GetMapping("/sua-hang/{id}")
    public String Statistic1(@PathVariable int id,Model model) {

        Order order = orderService.findById(id);

        List<Order_Item> listOrder_Item = order_ItemService.getAllByOrder_Id(order.getId());
        if (listOrder_Item.size()==1){
            model.addAttribute("a", null);
        }else {
            model.addAttribute("a", "a");
        }
        model.addAttribute("listOrder_Item", listOrder_Item);
        System.out.println(listOrder_Item);
        model.addAttribute("order", order);
        return "sua-don-hang.html";
    }


}
