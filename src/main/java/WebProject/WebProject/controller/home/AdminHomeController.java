package WebProject.WebProject.controller.home;


import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.repository.OrderRepository;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.ProductService;
import WebProject.WebProject.repository.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class AdminHomeController {

    @Autowired
    OrderRepository orderService;


    @Autowired
    ProductService productService;


    @Autowired
     ThongKeRepository thongKeRepository;
    @Autowired
    CookieService cookie;

    @Autowired
    HttpSession session;

    @GetMapping("/quan-ly-don-hang")
    public String hienThiHomeAdmin(Model model) {


        List<Order> listOrderActive1 = orderService.findByActiveOrder_Id("1");
        Collections.reverse(listOrderActive1);
        model.addAttribute("listOrderActive1", listOrderActive1);
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

        return"/admin/quanlydonhang/quan-ly-don-hang.html";
    }

    @GetMapping("/thong-ke")
    public String thongKe(Model model,@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {

        Page<Product> listSP = productService.findAll(pageNo, 5);
        model.addAttribute("listSP", listSP.getContent());
        model.addAttribute("pageProductPage", listSP.getTotalPages());
        model.addAttribute("pageNumber", pageNo);
        model.addAttribute("Cate", new Product());

        // don hang
        List<Order> list1 = orderService.findByActiveOrder_Id("1");
        int trangThai1Sum = 0;
        for (Order order : list1) {
            trangThai1Sum++;
        }
        model.addAttribute("trangThai1Sum", trangThai1Sum);

        List<Order> list2 = orderService.findByActiveOrder_Id("2");

        int trangThai2Sum = 0;
        for (Order order : list2) {
            trangThai2Sum++;
        }
        model.addAttribute("trangThai2Sum", trangThai2Sum);

        List<Order> list3 = orderService.findByActiveOrder_Id("3");
        int trangThai3Sum = 0;
        for (Order order : list3) {
            trangThai3Sum++;
        }
        System.out.println(trangThai3Sum);
        List<Order> list4 = orderService.findByActiveOrder_Id("4");
        int trangThai4Sum = 0;
        for (Order order : list4) {
            trangThai4Sum++;
        }
        System.out.println(trangThai4Sum);
        List<Order> list5 = orderService.findByActiveOrder_Id("5");
        int trangThai5Sum = 0;
        for (Order order : list5) {
            trangThai5Sum++;
        }
        System.out.println(trangThai5Sum);
        List<Order> list6 = orderService.findByActiveOrder_Id("6");
        int trangThai6Sum = 0;
        for (Order order : list6) {
            trangThai6Sum++;
        }
        System.out.println(trangThai6Sum);
        List<Order> list7 = orderService.findByActiveOrder_Id("7");
        int trangThai7Sum = 0;
        for (Order order : list7) {
            trangThai7Sum++;
        }
        System.out.println(trangThai7Sum);
        List<Order> list8 = orderService.findByActiveOrder_Id("8");
        int trangThai8Sum = 0;
        for (Order order : list8) {
            trangThai8Sum++;
        }
        System.out.println(trangThai8Sum);
        List<Order> list9 = orderService.findByActiveOrder_Id("9");
        int trangThai9Sum = 0;
        for (Order order : list9) {
            trangThai9Sum++;
        }
        System.out.println(trangThai9Sum);
        List<Order> list10 = orderService.findByActiveOrder_Id("10");
        int trangThai10Sum = 0;
        for (Order order : list10) {
            trangThai10Sum++;
        }
        System.out.println(trangThai10Sum);
        model.addAttribute("trangThai3Sum", trangThai3Sum);
        model.addAttribute("trangThai4Sum", trangThai4Sum);
        model.addAttribute("trangThai5Sum", trangThai5Sum);
        model.addAttribute("trangThai6Sum", trangThai6Sum);
        model.addAttribute("trangThai7Sum", trangThai7Sum);
        model.addAttribute("trangThai8Sum", trangThai8Sum);
        model.addAttribute("trangThai9Sum", trangThai9Sum);
        model.addAttribute("trangThai10Sum", trangThai10Sum);

        Pageable pageable = PageRequest.of(pageNo,5);
        Page<Map<String, Object>> resultList = thongKeRepository.getCountByStatusAndDate(pageable);
        model.addAttribute("resultList", resultList.getContent());
        model.addAttribute("resultListPage", resultList.getTotalPages());
        model.addAttribute("pageNumber1", pageNo);
       // doanh thu


            List<Map<String, Object>> resultList1 = thongKeRepository.getTotalRevenueByDate();
            model.addAttribute("resultList1", resultList1);


            List<Order> listOrder = orderService.findAll();
            List<Order> listPaymentWithMomo = orderService.findAllByPayment_Method("Payment with momo");
            List<Order> listVi = orderService.findAllByPayment_Method("Ví");
            List<Order> listPaymentOnDelivery = orderService.findAllByPayment_Method("Payment on delivery");
            long TotalMomo = 0;
            long vi = 0;
            long TotalDelivery = 0;
            for (Order y : listPaymentWithMomo) {
                TotalMomo = TotalMomo + y.getTotal();
            }
            for (Order y : listPaymentOnDelivery) {
                TotalDelivery = TotalDelivery + y.getTotal();
            }
            for (Order y : listVi) {
                vi = vi + y.getTotal();
            }
            List<Order> listRecentMomo = orderService.findTop5OrderByPaymentMethod("Payment with momo");
            List<Order> vi1 = orderService.findTop5OrderByPaymentMethod("Ví");
            List<Order> listRecentDelivery = orderService.findTop5OrderByPaymentMethod("Payment on delivery");


            model.addAttribute("vi", vi);
            model.addAttribute("vi1", vi1);
            model.addAttribute("TotalMomo", TotalMomo);
            model.addAttribute("TotalDelivery", TotalDelivery);
            model.addAttribute("TotalOrder", listOrder.size());
            model.addAttribute("listRecentDelivery", listRecentDelivery);
            model.addAttribute("listRecentMomo", listRecentMomo);
        return"/admin/quanlydonhang/thong-ke.html";
    }
}

