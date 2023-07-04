package com.example.demo.controller;

import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
