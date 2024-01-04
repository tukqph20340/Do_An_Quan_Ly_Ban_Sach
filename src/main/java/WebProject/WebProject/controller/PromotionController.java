package WebProject.WebProject.controller;


import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.entity.Promotion;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.PromotionService;
import WebProject.WebProject.service.impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("")
public class PromotionController {

    @Autowired
    PromotionService service = new PromotionServiceImpl();

    @Autowired
    HttpSession session;


    @GetMapping("/khuyen-mai/admin")
    public String load(Model model, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {
        Page<Promotion> pagePromotion = service.findAll(pageNo, 5);
        model.addAttribute("pagePromotion", pagePromotion.getContent());
        model.addAttribute("pagePromotionPage", pagePromotion.getTotalPages());
        model.addAttribute("pageNumber", pageNo);
        model.addAttribute("Promotion", new Promotion());
        return "/admin/khuyenmai/khuyen-mai";

    }

    @GetMapping("/tim-kiem-khuyen-mai")
    public String DashboardMyProducerView111(Model model,
                                             @RequestParam("name") String name, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {
        Page<Promotion> book = service.fillByName(pageNo, 5, '%' + name + '%');
        model.addAttribute("pagePromotion", book.getContent());
        model.addAttribute("pagePromotionPage", book.getTotalPages());
        model.addAttribute("pageNumber", pageNo);
        model.addAttribute("Promotion", new Promotion());
        model.addAttribute("a", "a");
        return "/admin/khuyenmai/khuyen-mai";

    }


    @GetMapping("/view-add-khuyen-mai")
    public String DashboardAddProducerView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String addPromotion = (String) session.getAttribute("addPromotion");
            model.addAttribute("addPromotion", addPromotion);
            session.setAttribute("addPromotion", null);
            return "/admin/khuyenmai/add-khuyen-mai";
        }
    }


    @PostMapping("/add-khuyen-mai")
    public String DashboardAddProducerHandel(Model model,
                                             @RequestParam("couponCode") String couponCode,
                                             @RequestParam("createdAt") Date createdAt,
                                             @RequestParam("discountValue") Long discountValue,
                                             @RequestParam("expiredAt") Date expiredAt,
                                             @RequestParam("maximumDiscountValue") Long maximumDiscountValue,
                                             @RequestParam("name") String name
    ) throws Exception {

        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (couponCode.trim().isEmpty()) {
                model.addAttribute("loi", "Không được để trống");
                return "/admin/khuyenmai/add-khuyen-mai";
            } else if (name.trim().isEmpty()) {
                model.addAttribute("loi2", "Không được để trống");
                return "/admin/khuyenmai/add-khuyen-mai";
            } else {
                Promotion promotion = new Promotion();
                promotion.setCouponCode(couponCode);
                promotion.setCreatedAt(createdAt);
                promotion.setDiscountValue(discountValue);
                promotion.setExpiredAt(expiredAt);
                promotion.setMaximumDiscountValue(maximumDiscountValue);
                promotion.setName(name);
                service.savePromotion(promotion);
                System.out.println(promotion);
                return "redirect:/khuyen-mai/admin";
            }
        }
    }

    @GetMapping("/view-sua-khuyen-mai/{id}")
    public String DashboardMyProducerEditView(@PathVariable int id, Model model) {
        Promotion promotion = service.getAllPromotionById(id);
        model.addAttribute("detail", promotion);
        return "/admin/khuyenmai/update-khuyen-mai";

    }

    @GetMapping("/view-all-khuyen-mai/{id}")
    public String DashboardMyProducerDetailView(@PathVariable int id, Model model) {
        Promotion promotion = service.getAllPromotionById(id);
        model.addAttribute("detail", promotion);
        return "/admin/khuyenmai/detail-khuyen-mai";

    }

    @PostMapping("/sua-khuyen-mai/{id}")
    public String DashboardAddProducerHandel(Model model, @PathVariable("id") int id,
                                             @RequestParam("couponCode") String couponCode,
                                             @RequestParam("createdAt") Date createdAt,
                                             @RequestParam("discountValue") Long discountValue,
                                             @RequestParam("expiredAt") Date expiredAt,
                                             @RequestParam("maximumDiscountValue") Long maximumDiscountValue,
                                             @RequestParam("name") String name
    ) throws Exception {

        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (couponCode.trim().isEmpty()) {
                model.addAttribute("loi", "Không được để trống");
                return "/admin/khuyenmai/add-khuyen-mai";
            } else if (name.trim().isEmpty()) {
                model.addAttribute("loi2", "Không được để trống");
                return "/admin/khuyenmai/add-khuyen-mai";
            } else {
                Promotion promotion = service.getAllPromotionById(id);
                promotion.setCouponCode(couponCode);
                promotion.setCreatedAt(createdAt);
                promotion.setDiscountValue(discountValue);
                promotion.setExpiredAt(expiredAt);
                promotion.setMaximumDiscountValue(maximumDiscountValue);
                promotion.setName(name);
                service.savePromotion(promotion);
                return "redirect:/khuyen-mai/admin";

            }

        }

    }
}