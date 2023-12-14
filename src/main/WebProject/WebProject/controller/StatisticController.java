package WebProject.WebProject.controller;

import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.Statistic;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.OrderService;
import WebProject.WebProject.service.StatisticService;
import WebProject.WebProject.service.impl.OrderServiceImpl;
import WebProject.WebProject.service.impl.StatisticServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

@Controller
public class StatisticController {

    @Autowired
    private StatisticService statisticService = new StatisticServiceImpl();

    @Autowired
    HttpSession session;

    @Autowired
    OrderService orderService = new OrderServiceImpl();


    @PostMapping("/addToStatistic")
    public String addToStatistic(@RequestParam("starRating") int starRating, @RequestParam("description") String description,
                                 @RequestParam("order_id") int order_id, @RequestParam("id_product") int id_product,
                                 HttpServletRequest request, HttpSession session) {
        long millis = System.currentTimeMillis();
        Date createAt = new java.sql.Date(millis);
        String referer = request.getHeader("Referer");
        User user = (User) session.getAttribute("acc");
        if (user == null) {
            return "redirect:" + referer;
        } else {
            Statistic statistic = new Statistic();
            statistic.setOrder(Order.builder().id(Integer.valueOf(order_id)).build());
            statistic.setProduct(Product.builder().id(Integer.valueOf(id_product)).build());
            statistic.setDescription(description);
            statistic.setCreateAt(createAt);
            statistic.setUser(user);
            statistic.setStarRating(starRating);
            statisticService.saveStatistic(statistic);
            return "redirect:/myhistory";
        }
    }

}
