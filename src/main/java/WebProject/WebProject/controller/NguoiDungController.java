package WebProject.WebProject.controller;


import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Promotion;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("")
public class NguoiDungController {
    @Autowired
    UserService service;

    @Autowired
    HttpSession session;


    @GetMapping("nguoi-dung-admin")
    public String getAll(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5);
            Page<User> pageProducer = service.getAllUser("user", pageable);
            model.addAttribute("list", pageProducer);
            return "/admin/nguoidung/nguoi-dung.html";
        }

    }

    @GetMapping("/phan-trang-nguoi-dung/{page}")
    public String phanTrang(Model model, @PathVariable() int page) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5);
            Page<User> pageProducer = service.getAllUser("user", pageable);
            model.addAttribute("list", pageProducer);
            return "/admin/nguoidung/nguoi-dung.html";
        }
    }

    @GetMapping("add-nguoi-dung")
    public String viewA(Model model) {
//        User user = service.detail("kqt2k3");
//        byte[] pass = Base64.getDecoder().decode(user.getPassword());
//        String passwword = new String(pass);
//        System.out.println("Mật Khẩu"+passwword);
        return "/admin/nguoidung/add-nguoi-dung.html";

    }

    @PostMapping("add-nguoi-dung")
    public String add(Model model, @RequestParam("user") String user,
                      @RequestParam("email") String email,
                      @RequestParam("soDienThoai") String soDienThoai,
                      @RequestParam("matKhau") String matKhau,
                      @RequestParam("hoTen") String hoTen) {
        List<User> use = service.findAll();
        for (User user1 : use) {
            if (user1.getId().equalsIgnoreCase(user)) {
                model.addAttribute("loiTk", "Tài Khoản Đã Tồn Tại");
                return "/admin/nguoidung/add-nguoi-dung.html";
            }
        }

        if (user.isEmpty()) {
            model.addAttribute("loi", "Tài Khoản Không Được Để Trống");
            return "/admin/nguoidung/add-nguoi-dung.html";
        } else if (email.isEmpty()) {
            model.addAttribute("loi1", "Email Không Được Để Trống");
            return "/admin/nguoidung/add-nguoi-dung.html";
        } else if (soDienThoai.isEmpty()) {
            model.addAttribute("loi2", "Số Điện Thoại Không Được Để Trống");
            return "/admin/nguoidung/add-nguoi-dung.html";
        } else if (soDienThoai.length() > 10 || soDienThoai.length() < 10) {
            model.addAttribute("loi11", "Số Điện Thoại Phải Để 10 Số");
            return "/admin/nguoidung/add-nguoi-dung.html";
        } else if (matKhau.isEmpty()) {
            model.addAttribute("loi3", "Mật Khẩu Không Được Để Trống");
            return "/admin/nguoidung/add-nguoi-dung.html";
        } else if (hoTen.isEmpty()) {
            model.addAttribute("loi4", "Họ Và Tên Không Được Để Trống");
            return "/admin/nguoidung/add-nguoi-dung.html";
        } else {
            String encodedValue = Base64.getEncoder().encodeToString(matKhau.getBytes());
            User newUser = new User();
            newUser.setId(user);
            newUser.setLogin_Type("default");
            newUser.setRole("user");
            newUser.setUser_Name(hoTen);
            newUser.setEmail(email);
            newUser.setPhone_Number(soDienThoai);
            newUser.setPassword(encodedValue);
            service.saveUser(newUser);
            return "redirect:/nguoi-dung-admin";

        }

    }

    @GetMapping("/nguoi-dung/sua/{id}")
    public String viewU(Model model, @PathVariable() String id) {
        User user = service.detail(id);
        byte[] pass = Base64.getDecoder().decode(user.getPassword());
        String passwword = new String(pass);

        model.addAttribute("detail", user);
        model.addAttribute("password", passwword);
        return "/admin/nguoidung/update-nguoi-dung.html";

    }

    @PostMapping("/nguoi-dung/sua/{id}")
    public String update(Model model,
                         @PathVariable() String id,
                         @RequestParam("email") String email,
                         @RequestParam("soDienThoai") String soDienThoai,
                         @RequestParam("matKhau") String matKhau,
                         @RequestParam("hoTen") String hoTen) {
        if (email.isEmpty()) {
            model.addAttribute("loi1", "Email Không Được Để Trống");
            User user11 = service.detail(id);
            byte[] pass = Base64.getDecoder().decode(user11.getPassword());
            String passwword = new String(pass);

            model.addAttribute("detail", user11);
            model.addAttribute("password", passwword);
            return "/admin/nguoidung/update-nguoi-dung.html";
        } else if (soDienThoai.isEmpty()) {
            model.addAttribute("loi2", "Số Điện Thoại Không Được Để Trống");
            User user11 = service.detail(id);
            byte[] pass = Base64.getDecoder().decode(user11.getPassword());
            String passwword = new String(pass);

            model.addAttribute("detail", user11);
            model.addAttribute("password", passwword);
            return "/admin/nguoidung/update-nguoi-dung.html";
        } else if (soDienThoai.length() > 10 || soDienThoai.length() < 10) {
            model.addAttribute("loi11", "Số Điện Thoại Phải Để 10 Số");
            User user11 = service.detail(id);
            byte[] pass = Base64.getDecoder().decode(user11.getPassword());
            String passwword = new String(pass);

            model.addAttribute("detail", user11);
            model.addAttribute("password", passwword);
            return "/admin/nguoidung/update-nguoi-dung.html";
        } else if (matKhau.isEmpty()) {
            model.addAttribute("loi3", "Mật Khẩu Không Được Để Trống");
            User user11 = service.detail(id);
            byte[] pass = Base64.getDecoder().decode(user11.getPassword());
            String passwword = new String(pass);

            model.addAttribute("detail", user11);
            model.addAttribute("password", passwword);
            return "/admin/nguoidung/update-nguoi-dung.html";
        } else if (hoTen.isEmpty()) {
            model.addAttribute("loi4", "Họ Và Tên Không Được Để Trống");
            User user11 = service.detail(id);
            byte[] pass = Base64.getDecoder().decode(user11.getPassword());
            String passwword = new String(pass);

            model.addAttribute("detail", user11);
            model.addAttribute("password", passwword);
            return "/admin/nguoidung/update-nguoi-dung.html";
        } else {
            String encodedValue = Base64.getEncoder().encodeToString(matKhau.getBytes());
            User newUser = service.detail(id);
            newUser.setUser_Name(hoTen);
            newUser.setEmail(email);
            newUser.setPhone_Number(soDienThoai);
            newUser.setPassword(encodedValue);
            service.saveUser(newUser);
            return "redirect:/nguoi-dung-admin";

        }
    }
}
