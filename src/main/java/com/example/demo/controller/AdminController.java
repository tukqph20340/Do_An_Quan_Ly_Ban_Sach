package com.example.demo.controller;


import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    KhachHangSevice khachHangSevice;

    @Autowired
    GiayPhepService giayPhepService;

    @Autowired
    Ban_Quyen_Service ban_quyen_service;

    @Autowired
    TacGiaSevice tacGiaSevice;

    @Autowired
    DiaChiSevice diaChiSevice;

    @Autowired
    SachService sachService;

    @GetMapping("khach-hang/hien-thi")
    public String khachHang(Model model) {
        ArrayList<KhachHang> listNV = khachHangSevice.getAll();
        ArrayList<DiaChi> listDC = diaChiSevice.getAll();
        model.addAttribute("list", listNV);
        model.addAttribute("listDC", listDC);
        return "/admin/khachhang/khach-hang";
    }

    @GetMapping("khach-hang/delete")
    public String deleteKH(Model model, @RequestParam("id") UUID idKH) {
        khachHangSevice.delete(idKH);
        return "redirect:/admin/khach-hang/hien-thi";
    }

    @GetMapping("khach-hang/viewUpdate")
    public String viewUpdateKH(Model model, @RequestParam("id") UUID idKH) {
        KhachHang listNV = khachHangSevice.getOne(idKH);
        model.addAttribute("detail", listNV);
        return "/admin/khachhang/update-khach-hang";
    }

    @PostMapping("khach-hang/add")
    public String khachHangAdd(Model model,
                               @RequestParam("idDC") UUID idDC,
                               @RequestParam("maKH") String maKH,
                               @RequestParam("hoVaTen") String hoVaTen,
                               @RequestParam("gioiTinh") Integer gioiTinh,
                               @RequestParam("ngaySinh") Date ngaySinh,
                               @RequestParam("email") String email,
                               @RequestParam("moTa") String moTa,
                               @RequestParam("sdt") String sdt,
                               @RequestParam("trangThai") Integer trangThai
    ) {
        KhachHang kh = new KhachHang();
        DiaChi dc = new DiaChi();
        dc.setIdDC(idDC);
        kh.setDiaChi(dc);
        kh.setMaKH(maKH);
        kh.setHoVaTen(hoVaTen);
        kh.setGioiTinh(gioiTinh);
        kh.setNgaySinh(ngaySinh);
        kh.setEmail(email);
        kh.setMoTa(moTa);
        kh.setSdt(sdt);
        kh.setTrangThai(trangThai);

        khachHangSevice.addAndUpdate(kh);
        return "redirect:/admin/khach-hang/hien-thi";
    }

    @PostMapping("khach-hang/update")
    public String khachHangUpdate(Model model,
                                  @RequestParam("idKH") UUID idKH,
                                  @RequestParam("maKH") String maKH,
                                  @RequestParam("hoVaTen") String hoVaTen,
                                  @RequestParam("gioiTinh") Integer gioiTinh,
                                  @RequestParam("ngaySinh") Date ngaySinh,
                                  @RequestParam("email") String email,
                                  @RequestParam("moTa") String moTa,
                                  @RequestParam("sdt") String sdt,
                                  @RequestParam("trangThai") Integer trangThai
    ) {
        KhachHang kh = khachHangSevice.getOne(idKH);
        kh.setMaKH(maKH);
        kh.setHoVaTen(hoVaTen);
        kh.setGioiTinh(gioiTinh);
        kh.setNgaySinh(ngaySinh);
        kh.setEmail(email);
        kh.setMoTa(moTa);
        kh.setSdt(sdt);
        kh.setTrangThai(trangThai);

        khachHangSevice.addAndUpdate(kh);
        return "redirect:/admin/khach-hang/hien-thi";
    }

    @GetMapping("khach-hang/tim-kiem")
    public String timKiem(Model model,
                               @RequestParam("maKH") String maKH,
                               @RequestParam("hoVaTen") String hoVaTen
    ) {
     ArrayList<KhachHang> timKiem = khachHangSevice.timKiem(maKH, hoVaTen);
     model.addAttribute("list", timKiem);
        return "/admin/khachhang/khach-hang";
    }

    @GetMapping("giay-phep/hien-thi")
    public String giayPhep(Model model) {
        ArrayList<GiayPhep> listGP = giayPhepService.getAll();
        model.addAttribute("list", listGP);
        return "/admin/giayphep/giay-phep";
    }

    @GetMapping("giay-phep/delete")
    public String deleteGP(Model model, @RequestParam("id") UUID id) {
        giayPhepService.delete(id);
        return "redirect:/admin/giay-phep/hien-thi";
    }

    @GetMapping("giay-phep/viewUpdate")
    public String viewUpdateGP(Model model, @RequestParam("id") UUID id) {
        GiayPhep listGP = giayPhepService.detail(id);
        model.addAttribute("detail", listGP);
        return "/admin/giayphep/update-giay-phep";
    }

    @PostMapping("giay-phep/add")
    public String giayPhepAdd(Model model,
                               @RequestParam("ngayBatDau") Date ngayBatDau,
                               @RequestParam("ngayHetHan") Date ngayHetHan,
                               @RequestParam("soGiayPhep") Integer soGiayPhep,
                               @RequestParam("moTa") String moTa,
                               @RequestParam("trangThai") Integer trangThai
    ) {
        GiayPhep gp = new GiayPhep();
        gp.setNgayBatDau(ngayBatDau);
        gp.setNgayHetHan(ngayHetHan);
        gp.setSoGiayPhep(soGiayPhep);
        gp.setMoTa(moTa);
        gp.setTrangThai(trangThai);

        giayPhepService.add(gp);
        return "redirect:/admin/giay-phep/hien-thi";
    }

    @PostMapping("giay-phep/update")
    public String giayPhepUpdate(Model model,
                                  @RequestParam("id") UUID id,
                                  @RequestParam("ngayBatDau") Date ngayBatDau,
                                  @RequestParam("ngayHetHan") Date ngayHetHan,
                                  @RequestParam("soGiayPhep") Integer soGiayPhep,
                                  @RequestParam("moTa") String moTa,
                                  @RequestParam("trangThai") Integer trangThai
    ) {
        GiayPhep gp = giayPhepService.detail(id);
        gp.setNgayBatDau(ngayBatDau);
        gp.setNgayHetHan(ngayHetHan);
        gp.setSoGiayPhep(soGiayPhep);
        gp.setMoTa(moTa);
        gp.setTrangThai(trangThai);

        giayPhepService.update(gp);
        return "redirect:/admin/giay-phep/hien-thi";
    }

    @GetMapping("giay-phep/tim-kiem")
    public String timKiem(Model model,
                          @RequestParam("ngayBatDau") Date ngayBatDau,
                          @RequestParam("ngayHetHan") Date ngayHetHan
    ) {
        ArrayList<GiayPhep> timKiem = giayPhepService.timKiem(ngayBatDau, ngayHetHan);
        model.addAttribute("list", timKiem);
        return "/admin/giayphep/giay-phep";
    }

    @GetMapping("ban-quyen/hien-thi")
    public String banQuyen(Model model) {
        ArrayList<Ban_Quyen> listBQ = ban_quyen_service.getAll();
        ArrayList<GiayPhep> listGP = giayPhepService.getAll();
        ArrayList<Sach> listS = sachService.getAll();
        model.addAttribute("list", listBQ);
        model.addAttribute("listGP", listGP);
        model.addAttribute("listS", listS);
        return "/admin/banquyen/ban-quyen";
    }

    @GetMapping("ban-quyen/delete")
    public String deleteBG(Model model, @RequestParam("id") UUID id) {
        ban_quyen_service.delete(id);
        return "redirect:/admin/ban-quyen/hien-thi";
    }

    @GetMapping("ban-quyen/viewUpdate")
    public String viewUpdateBG(Model model, @RequestParam("id") UUID id) {
        Ban_Quyen listBG = ban_quyen_service.detail(id);
        model.addAttribute("detail", listBG);
        return "/admin/banquyen/update-ban-quyen";
    }

    @PostMapping("ban-quyen/add")
    public String banQuyenAdd(Model model,
                              @RequestParam("id") UUID id,
                              @RequestParam("idSach") UUID idSach,
                              @RequestParam("ngayBatDau") LocalDate ngayBatDau,
                              @RequestParam("ngayHetHan") LocalDate ngayHetHan,
                              @RequestParam("nguoiKy") String nguoiKy,
                              @RequestParam("moTa") String moTa,
                              @RequestParam("trangThai") Integer trangThai
    ) {
        Ban_Quyen bg = new Ban_Quyen();
        GiayPhep gp = new GiayPhep();
        Sach s = new Sach();
        gp.setId(id);
        s.setId(idSach);
        bg.setGiayPhep(gp);
        bg.setSach(s);
        bg.setNgayBatDau(ngayBatDau);
        bg.setNgayHetHan(ngayHetHan);
        bg.setNguoiKy(nguoiKy);
        bg.setMoTa(moTa);
        bg.setTrangThai(trangThai);

        ban_quyen_service.add(bg);
        return "redirect:/admin/ban-quyen/hien-thi";
    }

    @PostMapping("ban-quyen/update")
    public String giayPhepUpdate(Model model,
                                 @RequestParam("id") UUID id,
                                 @RequestParam("ngayBatDau") LocalDate ngayBatDau,
                                 @RequestParam("ngayHetHan") LocalDate ngayHetHan,
                                 @RequestParam("nguoiKy") String nguoiKy,
                                 @RequestParam("moTa") String moTa,
                                 @RequestParam("trangThai") Integer trangThai
    ) {
        Ban_Quyen bg = ban_quyen_service.detail(id);
        bg.setNgayBatDau(ngayBatDau);
        bg.setNgayHetHan(ngayHetHan);
        bg.setNguoiKy(nguoiKy);
        bg.setMoTa(moTa);
        bg.setTrangThai(trangThai);

        ban_quyen_service.update(bg);
        return "redirect:/admin/ban-quyen/hien-thi";
    }

    @GetMapping("ban-quyen/tim-kiem")
    public String timKiem(Model model,
                          @RequestParam("ngayBatDau") LocalDate ngayBatDau,
                          @RequestParam("ngayHetHan") LocalDate ngayHetHan
    ) {
        ArrayList<Ban_Quyen> timKiem = ban_quyen_service.timKiem(ngayBatDau, ngayHetHan);
        model.addAttribute("list", timKiem);
        return "/admin/banquyen/ban-quyen";
    }

    @GetMapping("tac-gia/hien-thi")
    public String tacGia(Model model) {
        ArrayList<TacGia> listTG = tacGiaSevice.getAll();
        model.addAttribute("list", listTG);
        return "/admin/tacgia/tac-gia";
    }

    @GetMapping("tac-gia/delete")
    public String deleteTG(Model model, @RequestParam("id") UUID idTG) {
        tacGiaSevice.delete(idTG);
        return "redirect:/admin/tac-gia/hien-thi";
    }

    @GetMapping("tac-gia/viewUpdate")
    public String viewUpdateTG(Model model, @RequestParam("id") UUID idTG) {
        TacGia listTG = tacGiaSevice.getOne(idTG);
        model.addAttribute("detail", listTG);
        return "/admin/tacgia/update-tac-gia";
    }

    @PostMapping("tac-gia/add")
    public String tacGiaAdd(Model model,
                               @RequestParam("maTG") String maTG,
                               @RequestParam("hoVaTen") String hoVaTen,
                               @RequestParam("ngaySinh") Date ngaySinh,
                               @RequestParam("diaChi") String diaChi,
                               @RequestParam("trangThai") Integer trangThai
    ) {
        TacGia tg = new TacGia();
        tg.setMaTG(maTG);
        tg.setHoVaTen(hoVaTen);
        tg.setNgaySinh(ngaySinh);
        tg.setDiaChi(diaChi);
        tg.setTrangThai(trangThai);

        tacGiaSevice.addAndUpdate(tg);
        return "redirect:/admin/tac-gia/hien-thi";
    }

    @PostMapping("tac-gia/update")
    public String tacGiaUpdate(Model model,
                                  @RequestParam("idTG") UUID idTG,
                                  @RequestParam("maTG") String maTG,
                                  @RequestParam("hoVaTen") String hoVaTen,
                                  @RequestParam("ngaySinh") Date ngaySinh,
                                  @RequestParam("diaChi") String diaChi,
                                  @RequestParam("trangThai") Integer trangThai
    ) {
        TacGia tg = tacGiaSevice.getOne(idTG);
        tg.setMaTG(maTG);
        tg.setHoVaTen(hoVaTen);
        tg.setNgaySinh(ngaySinh);
        tg.setDiaChi(diaChi);
        tg.setTrangThai(trangThai);

        tacGiaSevice.addAndUpdate(tg);
        return "redirect:/admin/tac-gia/hien-thi";
    }

//    @GetMapping("tac-gia/tim-kiem")
//    public String timKiem1(Model model,
//                          @RequestParam("maTG") String maTG,
//                          @RequestParam("hoVaTen") String hoVaTen
//    ) {
//        ArrayList<TacGia> timKiem = tacGiaSevice.timKiem(maTG, hoVaTen);
//        model.addAttribute("list", timKiem);
//        return "/admin/tacgia/tac-gia";
//    }
}
