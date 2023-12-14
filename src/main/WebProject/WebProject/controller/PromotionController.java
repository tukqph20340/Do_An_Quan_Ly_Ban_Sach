package WebProject.WebProject.controller;


import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.entity.Promotion;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.PromotionService;
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
    PromotionService service;

    @Autowired
    HttpSession session;

    java.util.Date date = new java.util.Date();
    SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    String hienTai = fm.format(date);

    @GetMapping("/khuyen-mai/admin")
    public String khuyenMai(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5);
            Page<Promotion> pageProducer = service.findAll(pageable);
            model.addAttribute("list", pageProducer);
            return "/admin/khuyenmai/khuyen-mai.html";
        }
    }

    @GetMapping("/phan-trang-khuyen-mai/{page}")
    public String phanTrang(Model model, @PathVariable() int page) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5);
            Page<Promotion> pageKhuyenMai = service.findAll(pageable);
            model.addAttribute("list", pageKhuyenMai);
            return "/admin/khuyenmai/khuyen-mai.html";
        }
    }

    @GetMapping("/add-khuyen-mai")
    public String viewAdd() {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {

            return "/admin/khuyenmai/add-khuyen-mai.html";
        }
    }


    @PostMapping("/add-khuyen-mai")
    public String add(Model model,
                      @RequestParam("maCode") String maCode,
                      @RequestParam("giamGiaLoai") String giamGiaLoai,
                      @RequestParam("giaTriGiam") String giaTriGiam,
                      @RequestParam("ngayHetHan") String ngayHetHan,
                      @RequestParam("giaTriGiamToiDa") String giaTriGiamToiDa,
                      @RequestParam("moTa") String moTa
    ) {

        List<Promotion> list = service.getAll();
        for (Promotion promotion : list) {
            if (promotion.getCouponCode().equals(maCode)) {
                model.addAttribute("loiMa", "Mã đã tồn tại");
                return "/admin/khuyenmai/add-khuyen-mai.html";
            }
        }

        if (maCode.isEmpty()) {
            model.addAttribute("loi", "Mã không đc để trống");
            return "/admin/khuyenmai/add-khuyen-mai.html";
        } else if (giamGiaLoai.isEmpty()) {
            model.addAttribute("loi1", "giảm giá loại không được để trống");
            return "/admin/khuyenmai/add-khuyen-mai.html";
        } else if (Integer.valueOf(giamGiaLoai) < 1) {
            model.addAttribute("loi5", "giảm giá loại không được nhỏ hơn 1");
            return "/admin/khuyenmai/add-khuyen-mai.html";
        } else if (giaTriGiam.isEmpty()) {
            model.addAttribute("loi2", "giá trị giảm không được để trống");
            return "/admin/khuyenmai/add-khuyen-mai.html";

        } else if (Integer.valueOf(giaTriGiam) < 1) {
            model.addAttribute("loi6", "giá trị giảm không được nhỏ hơn 1");
            return "/admin/khuyenmai/add-khuyen-mai.html";
        } else if (ngayHetHan.isEmpty()) {
            model.addAttribute("loi3", "ngày hết hạn không được để trống");
            return "/admin/khuyenmai/add-khuyen-mai.html";
        } else if (Date.valueOf(ngayHetHan).before(Date.valueOf(hienTai))) {
            model.addAttribute("loi4", "ngày hết hạn không được nhỏ hơn ngày hiện tại");
            return "/admin/khuyenmai/add-khuyen-mai.html";
        } else if (giaTriGiamToiDa.isEmpty()) {
            model.addAttribute("loi8", "giá trị giảm tối được không đc để trống");
            return "/admin/khuyenmai/add-khuyen-mai.html";

        } else if (Integer.valueOf(giaTriGiamToiDa) < 1) {
            model.addAttribute("loi7", "giá trị giảm tối được không được nhỏ hơn 1");
            return "/admin/khuyenmai/add-khuyen-mai.html";
        } else if (moTa.isEmpty()) {
            model.addAttribute("loi8", "mô tả được không đc để trống");
            return "/admin/khuyenmai/add-khuyen-mai.html";
        } else {
            Promotion promotion = new Promotion();
            promotion.setCouponCode(maCode);
            promotion.setCreatedAt(Date.valueOf(hienTai));
            promotion.setDiscountType(Integer.valueOf(giamGiaLoai));
            promotion.setDiscountValue(Long.valueOf(giaTriGiam));
            promotion.setExpiredAt(Date.valueOf(ngayHetHan));
            promotion.setMaximumDiscountValue(Long.valueOf(giaTriGiamToiDa));
            promotion.setName(moTa);
            promotion.setIsActive(true);
            service.addAndUpdate(promotion);
            return "redirect:/khuyen-mai/admin";
        }
    }

    @GetMapping("/khuyen-mai/sua/{id}")
    public String viewUpdate(Model model, @PathVariable() String id) {
        Promotion promotion = service.detail(Integer.valueOf(id));
        model.addAttribute("detail", promotion);
        return "/admin/khuyenmai/update-khuyen-mai.html";
    }

    @PostMapping("/khuyen-mai/sua/{id}")
    public String update(Model model
            , @PathVariable() String id,
                         @RequestParam("maCode") String maCode,
                         @RequestParam("giamGiaLoai") String giamGiaLoai,
                         @RequestParam("giaTriGiam") String giaTriGiam,
                         @RequestParam("ngayHetHan") String ngayHetHan,
                         @RequestParam("giaTriGiamToiDa") String giaTriGiamToiDa,
                         @ModelAttribute() Promotion moTa11
    ) {


        if (maCode.isEmpty()) {
            model.addAttribute("loi", "Mã không đc để trống");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";
        } else if (giamGiaLoai.isEmpty()) {
            model.addAttribute("loi1", "giảm giá loại không được để trống");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";
        } else if (Integer.valueOf(giamGiaLoai) < 1) {
            model.addAttribute("loi5", "giảm giá loại không được nhỏ hơn 1");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";
        } else if (giaTriGiam.isEmpty()) {
            model.addAttribute("loi2", "giá trị giảm không được để trống");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";
        } else if (Integer.valueOf(giaTriGiam) < 1) {
            model.addAttribute("loi6", "giá trị giảm không được nhỏ hơn 1");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";
        } else if (ngayHetHan.isEmpty()) {
            model.addAttribute("loi3", "ngày hết hạn không được để trống");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";
        } else if (Date.valueOf(ngayHetHan).before(Date.valueOf(hienTai))) {
            model.addAttribute("loi4", "ngày hết hạn không được nhỏ hơn ngày hiện tại");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";
        } else if (giaTriGiamToiDa.isEmpty()) {
            model.addAttribute("loi8", "giá trị giảm tối được không đc để trống");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";

        } else if (Integer.valueOf(giaTriGiamToiDa) < 1) {
            model.addAttribute("loi7", "giá trị giảm tối được không được nhỏ hơn 1");
            Promotion promotion = service.detail(Integer.valueOf(id));
            model.addAttribute("detail", promotion);
            return "/admin/khuyenmai/update-khuyen-mai.html";
        } else {
            Promotion promotion = service.detail(Integer.valueOf(id));
            promotion.setCouponCode(maCode);
            promotion.setCreatedAt(Date.valueOf(hienTai));
            promotion.setDiscountType(Integer.valueOf(giamGiaLoai));
            promotion.setDiscountValue(Long.valueOf(giaTriGiam));
            promotion.setExpiredAt(Date.valueOf(ngayHetHan));
            promotion.setMaximumDiscountValue(Long.valueOf(giaTriGiamToiDa));
            promotion.setName(moTa11.getName());
            promotion.setIsActive(true);
            service.addAndUpdate(promotion);
            return "redirect:/khuyen-mai/admin";
        }
    }

}
