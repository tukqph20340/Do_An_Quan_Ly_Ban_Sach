package com.example.demo.controller;

import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    NhaCungCapService nhaCungCapService;

    @Autowired
    NhaXuatBanService nhaXuatBanService;

    @Autowired
    KhuyenMaiService khuyenMaiService;

    @Autowired
    SachService sachService;

    @Autowired
    GiayPhepService giayPhepService;

    @GetMapping("/hien-thi")
    public String hien(Model model){
        return "home/home";


    }
}
