package com.example.demo.controller;

import com.example.demo.entity.*;

import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    Ban_Quyen_Service ban_quyen_service;

    @Autowired
    KhachHangSevice khachHangSevice;

    @Autowired
    GiayPhepService giayPhepService;

    @Autowired
    SachService sachService;

    @Autowired
    NhaCungCapService nhaCungCapService;

    @Autowired
    TacGiaSevice tacGiaSevice;

    @Autowired
    NhaXuatBanService nhaXuatBanService;

    @Autowired
    TheLoaiSevice theLoaiSevice;


    @Autowired
    NguoiDungSevice nguoiDungSevice;

    @Autowired
    ChucVuSevice chucVuSevice;

    @Autowired
    DiaChiSevice diaChiSevice;

    @Autowired
    KhuyenMaiService khuyenMaiService;

    @Autowired
    Hoa_Don_Chi_Tiet_Service hoa_don_chi_tiet_service;

    // Sản Phẩm

    @GetMapping("/hien-thi")
    public String hienThi(Model model) {
        return "admin/trang-chu";
    }

    @GetMapping("/san-pham/hien-thi")
    public String hienThiSach(Model model
            , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        ArrayList<TacGia> listTG = tacGiaSevice.getAll();
        ArrayList<NhaCungCap> listNCC = nhaCungCapService.getAll();
        ArrayList<NhaXuatBan> listNXB = nhaXuatBanService.getAll();
        ArrayList<TheLoai> listTL = theLoaiSevice.getAll();

        Pageable pageable = PageRequest.of(number, 4);
        Page<Sach> listSach = sachService.phanTrang(pageable);
        model.addAttribute("list", listSach);

        model.addAttribute("listTG", listTG);
        model.addAttribute("listNCC", listNCC);
        model.addAttribute("listNXB", listNXB);
        model.addAttribute("listTl", listTL);
        return "admin/sanpham/san-pham";
    }


    @GetMapping("/san-pham/viewUpdate")
    public String detailSach(Model model, @RequestParam("id") UUID id) {
        ArrayList<TacGia> listTG = tacGiaSevice.getAll();
        ArrayList<NhaCungCap> listNCC = nhaCungCapService.getAll();
        ArrayList<NhaXuatBan> listNXB = nhaXuatBanService.getAll();
        ArrayList<TheLoai> listTL = theLoaiSevice.getAll();

        Sach listSach = sachService.detail(id);
        model.addAttribute("detail", listSach);

        model.addAttribute("listTG", listTG);
        model.addAttribute("listNCC", listNCC);
        model.addAttribute("listNXB", listNXB);
        model.addAttribute("listTl", listTL);
        return "admin/sanpham/update-san-pham";
    }

    @GetMapping("/san-pham/delete")
    public String deleteSach(Model model, @RequestParam("id") UUID id) {
        sachService.delete(id);
        return "redirect:/admin/san-pham/hien-thi";
    }

    @PostMapping("san-pham/add")
    public String addSach(Model model, @RequestParam("tenNhaCungCap") UUID tenNhaCungCap
            , @RequestParam("tenTacGia") UUID tenTacGia
            , @RequestParam("tenNhaXuatBan") UUID tenNhaXuatBan
            , @RequestParam("theLoai") UUID theLoai
            , @RequestParam("ma") String ma
            , @RequestParam("ten") String ten
            , @RequestParam("linkAnh") String linkAnh
            , @RequestParam("ngayXuatBan") String ngayXuatBan
            , @RequestParam("giaBan") Integer giaBan
            , @RequestParam("giaNhap") Integer giaNhap
            , @RequestParam("soLuong") Integer soLuong
            , @RequestParam("moTa") String moTa
    ) {
        if (soLuong > 0) {
            Sach sach = new Sach();
            NhaCungCap ncc = new NhaCungCap();
            ncc.setId(tenNhaCungCap);
            sach.setNhaCungCap(ncc);

            TacGia tg = new TacGia();
            tg.setIdTG(tenTacGia);
            sach.setTacGia(tg);


            NhaXuatBan nxb = new NhaXuatBan();
            nxb.setId(tenNhaXuatBan);
            sach.setNhaXuatBan(nxb);


            TheLoai theLoai1 = new TheLoai();
            theLoai1.setIdTL(theLoai);
            sach.setTheLoai(theLoai1);

            sach.setMa(ma);
            sach.setTen(ten);
            sach.setFileAnh(linkAnh);
            sach.setNgayXuatBan(Date.valueOf(ngayXuatBan));
            sach.setGiaBan(giaBan);
            sach.setGiaNhap(giaNhap);
            sach.setSoLuong(soLuong);
            sach.setMoTa(moTa);
            sach.setTrangThai(1);
            sachService.add(sach);
            return "redirect:/admin/san-pham/hien-thi";
        } else {
            Sach sach = new Sach();
            NhaCungCap ncc = new NhaCungCap();
            ncc.setId(tenNhaCungCap);
            sach.setNhaCungCap(ncc);

            TacGia tg = new TacGia();
            tg.setIdTG(tenTacGia);
            sach.setTacGia(tg);


            NhaXuatBan nxb = new NhaXuatBan();
            nxb.setId(tenNhaXuatBan);
            sach.setNhaXuatBan(nxb);


            TheLoai theLoai1 = new TheLoai();
            theLoai1.setIdTL(theLoai);
            sach.setTheLoai(theLoai1);

            sach.setMa(ma);
            sach.setTen(ten);
            sach.setFileAnh(linkAnh);
            sach.setNgayXuatBan(Date.valueOf(ngayXuatBan));
            sach.setGiaBan(giaBan);
            sach.setGiaNhap(giaNhap);
            sach.setSoLuong(soLuong);
            sach.setMoTa(moTa);
            sach.setTrangThai(0);
            sachService.add(sach);
            return "redirect:/admin/san-pham/hien-thi";
        }
    }


    @PostMapping("san-pham/update")
    public String updateSach(Model model, @RequestParam("id") UUID id,
                             @RequestParam("tenNhaCungCap") UUID tenNhaCungCap
            , @RequestParam("tenTacGia") UUID tenTacGia
            , @RequestParam("tenNhaXuatBan") UUID tenNhaXuatBan
            , @RequestParam("theLoai") UUID theLoai
            , @RequestParam("ma") String ma
            , @RequestParam("ten") String ten
            , @RequestParam("linkAnh") String linkAnh
            , @RequestParam("ngayXuatBan") String ngayXuatBan
            , @RequestParam("giaBan") Integer giaBan
            , @RequestParam("giaNhap") Integer giaNhap
            , @RequestParam("soLuong") Integer soLuong
            , @RequestParam("moTa") String moTa
    ) {
        if (soLuong > 0) {
            Sach sach = sachService.detail(id);
            NhaCungCap ncc = new NhaCungCap();
            ncc.setId(tenNhaCungCap);
            sach.setNhaCungCap(ncc);

            TacGia tg = new TacGia();
            tg.setIdTG(tenTacGia);
            sach.setTacGia(tg);


            NhaXuatBan nxb = new NhaXuatBan();
            nxb.setId(tenNhaXuatBan);
            sach.setNhaXuatBan(nxb);


            TheLoai theLoai1 = new TheLoai();
            theLoai1.setIdTL(theLoai);
            sach.setTheLoai(theLoai1);

            sach.setMa(ma);
            sach.setTen(ten);
            sach.setFileAnh(linkAnh);
            sach.setNgayXuatBan(Date.valueOf(ngayXuatBan));
            sach.setGiaBan(giaBan);
            sach.setGiaNhap(giaNhap);
            sach.setSoLuong(soLuong);
            sach.setMoTa(moTa);
            sach.setTrangThai(1);
            sachService.add(sach);
            return "redirect:/admin/san-pham/hien-thi";
        } else {
            Sach sach = sachService.detail(id);
            NhaCungCap ncc = new NhaCungCap();
            ncc.setId(tenNhaCungCap);
            sach.setNhaCungCap(ncc);

            TacGia tg = new TacGia();
            tg.setIdTG(tenTacGia);
            sach.setTacGia(tg);


            NhaXuatBan nxb = new NhaXuatBan();
            nxb.setId(tenNhaXuatBan);
            sach.setNhaXuatBan(nxb);


            TheLoai theLoai1 = new TheLoai();
            theLoai1.setIdTL(theLoai);
            sach.setTheLoai(theLoai1);

            sach.setMa(ma);
            sach.setTen(ten);
            sach.setFileAnh(linkAnh);
            sach.setNgayXuatBan(Date.valueOf(ngayXuatBan));
            sach.setGiaBan(giaBan);
            sach.setGiaNhap(giaNhap);
            sach.setSoLuong(soLuong);
            sach.setMoTa(moTa);
            sach.setTrangThai(0);
            sachService.add(sach);
            return "redirect:/admin/san-pham/hien-thi";
        }
    }

    // Người Dùng
    @GetMapping("/nguoi-dung/hien-thi")
    public String hienThiNguoiDung(Model model
            , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        ArrayList<ChucVu> listCV = chucVuSevice.getAll();

        Pageable pageable = PageRequest.of(number, 4);
        Page<NguoiDung> listNguoiDung = nguoiDungSevice.getAll(pageable);
        model.addAttribute("list", listNguoiDung);
        model.addAttribute("listCV", listCV);

        return "admin/nguoidung/nguoi-dung";
    }

    @GetMapping("/nguoi-dung/viewUpdate")
    public String detaiNguoiDung(Model model
            , @RequestParam("id") UUID id) {
        ArrayList<ChucVu> listCV = chucVuSevice.getAll();
        NguoiDung listNguoiDung = nguoiDungSevice.getOne(id);
        model.addAttribute("detail", listNguoiDung);
        model.addAttribute("listCV", listCV);

        return "admin/nguoidung/update-nguoi-dung";
    }

    @GetMapping("/nguoi-dung/delete")
    public String deleteNguoiDung(Model model
            , @RequestParam("id") UUID id) {
        nguoiDungSevice.delete(id);
        return "redirect:/admin/nguoi-dung/hien-thi";
    }

    @PostMapping("/nguoi-dung/add")
    public String addNguoiDung(Model model
            , @RequestParam("chucVu") UUID chucVu
            , @RequestParam("ten") String ten
            , @RequestParam("gioiTinh") Integer gioiTinh
            , @RequestParam("ngaySinh") Date ngaySinh
            , @RequestParam("diaChi") String diaChi
            , @RequestParam("email") String email
            , @RequestParam("mo_ta") String mo_ta
            , @RequestParam("sdt") String sdt
            , @RequestParam("anh") String anh
            , @RequestParam("tk") String tk
            , @RequestParam("mk") String mk
            , @RequestParam("ma") String ma) {
        ChucVu sv = new ChucVu();
        sv.setIdCV(chucVu);

        NguoiDung nd = new NguoiDung();
        nd.setChucVu(sv);
        nd.setMaND(ma);
        nd.setHoVaTen(ten);
        nd.setGioiTinh(gioiTinh);
        nd.setNgaySinh(ngaySinh);
        nd.setDiaChi(diaChi);
        nd.setEmail(email);
        nd.setMoTa(mo_ta);
        nd.setSdt(sdt);
        nd.setAnh(anh);
        nd.setTaiKhoan(tk);
        nd.setMatKhau(mk);


        nguoiDungSevice.addAndUpdate(nd);

        return "redirect:/admin/nguoi-dung/hien-thi";
    }

    @PostMapping("/nguoi-dung/update")
    public String updateNguoiDung(Model model
            , @RequestParam("id") UUID id
            , @RequestParam("chucVu") UUID chucVu
            , @RequestParam("ten") String ten
            , @RequestParam("gioiTinh") Integer gioiTinh
            , @RequestParam("ngaySinh") Date ngaySinh
            , @RequestParam("diaChi") String diaChi
            , @RequestParam("email") String email
            , @RequestParam("mo_ta") String mo_ta
            , @RequestParam("sdt") String sdt
            , @RequestParam("anh") String anh
            , @RequestParam("tk") String tk
            , @RequestParam("mk") String mk
            , @RequestParam("ma") String ma) {
        ChucVu sv = new ChucVu();
        sv.setIdCV(chucVu);

        NguoiDung nd = nguoiDungSevice.getOne(id);
        nd.setChucVu(sv);
        nd.setMaND(ma);
        nd.setHoVaTen(ten);
        nd.setGioiTinh(gioiTinh);
        nd.setNgaySinh(ngaySinh);
        nd.setDiaChi(diaChi);
        nd.setEmail(email);
        nd.setMoTa(mo_ta);
        nd.setSdt(sdt);
        nd.setAnh(anh);
        nd.setTaiKhoan(tk);
        nd.setMatKhau(mk);


        nguoiDungSevice.addAndUpdate(nd);

        return "redirect:/admin/nguoi-dung/hien-thi";
    }

    // địa chỉ
    @GetMapping("/dia-chi/hien-thi")
    public String hienThidiachi(Model model
            , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<DiaChi> listDiaChi = diaChiSevice.getAll(pageable);
        model.addAttribute("list", listDiaChi);

        return "admin/diachi/dia-chi";
    }

    @GetMapping("/dia-chi/viewUpdate")
    public String viewUpatediachi(Model model
            , @RequestParam("id") UUID id) {
        DiaChi listDiaChi = diaChiSevice.getOne(id);
        model.addAttribute("detail", listDiaChi);

        return "admin/diachi/update-dia-chi";
    }

    @GetMapping("/dia-chi/delete")
    public String deletediachi(Model model
            , @RequestParam("id") UUID id) {
        diaChiSevice.delete(id);
        return "redirect:/admin/dia-chi/hien-thi";
    }

    @PostMapping("/dia-chi/add")
    public String adddiachi(Model model
            , @RequestParam("ma") String ma
            , @RequestParam("diaChi") String diaChi
            , @RequestParam("thanhPho") String thanhPho
            , @RequestParam("quocGia") String quocGia) {
        DiaChi dc = new DiaChi();
        dc.setMaDC(ma);
        dc.setDiaChi(diaChi);
        dc.setThanhPho(thanhPho);
        dc.setQuocGia(quocGia);
        diaChiSevice.addAndUpdate(dc);
        return "redirect:/admin/dia-chi/hien-thi";
    }

    @PostMapping("/dia-chi/update")
    public String updatediachi(Model model
            , @RequestParam("id") UUID id
            , @RequestParam("ma") String ma
            , @RequestParam("diaChi") String diaChi
            , @RequestParam("thanhPho") String thanhPho
            , @RequestParam("quocGia") String quocGia) {
        DiaChi dc = diaChiSevice.getOne(id);
        dc.setMaDC(ma);
        dc.setDiaChi(diaChi);
        dc.setThanhPho(thanhPho);
        dc.setQuocGia(quocGia);
        diaChiSevice.addAndUpdate(dc);
        return "redirect:/admin/dia-chi/hien-thi";
    }

    // Chức Vụ
    @GetMapping("/chuc-vu/hien-thi")
    public String hienThichucvu(Model model
            , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<ChucVu> listChucVU = chucVuSevice.getAll(pageable);
        model.addAttribute("list", listChucVU);

        return "admin/chucvu/chuc-vu";
    }

    @GetMapping("/chuc-vu/viewUpdate")
    public String viewUpateChucVu(Model model
            , @RequestParam("id") UUID id) {
        ChucVu listChucVU = chucVuSevice.getOne(id);
        model.addAttribute("detail", listChucVU);

        return "admin/chucvu/update-chuc-vu";
    }

    @GetMapping("/chuc-vu/delete")
    public String deleteChucVu(Model model
            , @RequestParam("id") UUID id) {
        chucVuSevice.delete(id);
        return "redirect:/admin/chuc-vu/hien-thi";
    }

    @PostMapping("/chuc-vu/add")
    public String addchucvu(Model model
            , @RequestParam("ma") String ma
            , @RequestParam("ten") String ten
            , @RequestParam("moTa") String mota
    ) {
        ChucVu dc = new ChucVu();
        dc.setMaCV(ma);
        dc.setTenCV(ten);
        dc.setMoTa(mota);
        chucVuSevice.addAndUpdate(dc);
        return "redirect:/admin/chuc-vu/hien-thi";
    }

    @PostMapping("/chuc-vu/update")
    public String updatediachi(Model model
            , @RequestParam("id") UUID id
            , @RequestParam("ma") String ma
            , @RequestParam("ten") String ten
            , @RequestParam("moTa") String mota
    ) {
        ChucVu dc = chucVuSevice.getOne(id);
        dc.setMaCV(ma);
        dc.setTenCV(ten);
        dc.setMoTa(mota);
        chucVuSevice.addAndUpdate(dc);
        return "redirect:/admin/chuc-vu/hien-thi";
    }

    @GetMapping("khach-hang/hien-thi")
    public String khachHang(Model model , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<KhachHang> listNV = khachHangSevice.phanTrang(pageable);
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

//    @GetMapping("khach-hang/tim-kiem")
//    public String timKiem(Model model,
//                          @RequestParam("maKH") String maKH,
//                          @RequestParam("hoVaTen") String hoVaTen
//    ) {
//        ArrayList<KhachHang> timKiem = khachHangSevice.timKiem(maKH, hoVaTen);
//        model.addAttribute("list", timKiem);
//        return "/admin/khachhang/khach-hang";
//    }

    @GetMapping("giay-phep/hien-thi")
    public String giayPhep(Model model , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<GiayPhep> listGP = giayPhepService.phanTrang(pageable);
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

//    @GetMapping("giay-phep/tim-kiem")
//    public String timKiem(Model model,
//                          @RequestParam("ngayBatDau") Date ngayBatDau,
//                          @RequestParam("ngayHetHan") Date ngayHetHan
//    ) {
//        ArrayList<GiayPhep> timKiem = giayPhepService.timKiem(ngayBatDau, ngayHetHan);
//        model.addAttribute("list", timKiem);
//        return "/admin/giayphep/giay-phep";
//    }

    @GetMapping("ban-quyen/hien-thi")
    public String banQuyen(Model model , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<Ban_Quyen> listBQ = ban_quyen_service.phanTrang(pageable);
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

//    @GetMapping("ban-quyen/tim-kiem")
//    public String timKiem(Model model,
//                          @RequestParam("ngayBatDau") LocalDate ngayBatDau,
//                          @RequestParam("ngayHetHan") LocalDate ngayHetHan
//    ) {
//        ArrayList<Ban_Quyen> timKiem = ban_quyen_service.timKiem(ngayBatDau, ngayHetHan);
//        model.addAttribute("list", timKiem);
//        return "/admin/banquyen/ban-quyen";
//    }

    @GetMapping("tac-gia/hien-thi")
    public String tacGia(Model model , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 6);
        Page<TacGia> listTG = tacGiaSevice.phanTrang(pageable);
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
    @GetMapping("nha-cung-cap/hien-thi")
    public String hienThi(Model model, @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<NhaCungCap> page = nhaCungCapService.getAll(pageable.getPageNumber(), pageable.getPageSize());
        model.addAttribute("list", page);
        model.addAttribute("ncc", new NhaCungCap());
        return "/admin/nhacungcap/Nha_Cung_Cap";
    }

    @GetMapping("nha-cung-cap/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        nhaCungCapService.delete(UUID.fromString(id));
        return "redirect:/admin/nha-cung-cap/hien-thi";
    }

    @GetMapping("nha-cung-cap/viewUpdate/{id}")
    public String viewupdate(@PathVariable("id") UUID id, Model model) {
        NhaCungCap ncc = nhaCungCapService.detail(id);
        model.addAttribute("detail", ncc);
        return "/admin/nhacungcap/update_Nha_Cung_Cap";
    }

    @PostMapping("nha-cung-cap/add")
    public String Add(@ModelAttribute("ncc") NhaCungCap nhaCungCap, Model model) {
        nhaCungCapService.add(nhaCungCap);
        return "redirect:/admin/nha-cung-cap/hien-thi";
    }

    @PostMapping("nha-cung-cap/update/{id}")
    public String update(@ModelAttribute("ncc") NhaCungCap nhaCungCap, @PathVariable("id") UUID id) {
        nhaCungCapService.update(nhaCungCap, id);
        return "redirect:/admin/nha-cung-cap/hien-thi";
    }

    @GetMapping("nha-xuat-ban/hien-thi")
    public String hienThiNXB(Model model, @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<NhaXuatBan> page = nhaXuatBanService.getAll(pageable.getPageNumber(), pageable.getPageSize());
        model.addAttribute("list", page);
        model.addAttribute("nxb", new NhaXuatBan());
        return "/admin/nhaxuatban/nha_xuat_ban";
    }

    @GetMapping("nha-xuat-ban/delete/{id}")
    public String deleteNXB(@PathVariable("id") String id) {
        nhaXuatBanService.delete(UUID.fromString(id));
        return "redirect:/admin/nha-xuat-ban/hien-thi";
    }

    @GetMapping("nha-xuat-ban/viewUpdate/{id}")
    public String viewupdateNXB(@PathVariable("id") UUID id, Model model) {
        NhaXuatBan ncc = nhaXuatBanService.detail(id);
        model.addAttribute("detail", ncc);
        return "/admin/nhaxuatban/update_nha_xuat_ban";
    }

    @PostMapping("nha-xuat-ban/add")
    public String AddNXB(@ModelAttribute("nxb") NhaXuatBan nhaXuatBan, Model model) {
        nhaXuatBanService.add(nhaXuatBan);
        return "redirect:/admin/nha-xuat-ban/hien-thi";
    }

    @PostMapping("nha-xuat-ban/update/{id}")
    public String updateNXB(@ModelAttribute("nxb") NhaXuatBan nhaXuatBan, @PathVariable("id") UUID id) {
        nhaXuatBanService.update(nhaXuatBan, id);
        return "redirect:/admin/nha-xuat-ban/hien-thi";
    }

    @GetMapping("the-loai/hien-thi")
    public String hienThiTL(Model model, @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<TheLoai> page = theLoaiSevice.getAll(pageable.getPageNumber(), pageable.getPageSize());
        model.addAttribute("list", page);
        model.addAttribute("tl", new TheLoai());
        return "/admin/theloai/the_loai";
    }

    @GetMapping("the-loai/delete/{id}")
    public String deleteTL(@PathVariable("id") String id) {
        theLoaiSevice.delete(UUID.fromString(id));
        return "redirect:/admin/the-loai/hien-thi";
    }

    @GetMapping("the-loai/viewUpdate/{id}")
    public String viewupdateTL(@PathVariable("id") UUID id, Model model) {
        TheLoai tl = theLoaiSevice.detail(id);
        model.addAttribute("detail", tl);
        return "/admin/theloai/update_the_loai";
    }

    @PostMapping("the-loai/add")
    public String AddTL (@ModelAttribute("tl") TheLoai theLoai, Model model) {
        theLoaiSevice.add(theLoai);
        return "redirect:/admin/the-loai/hien-thi";
    }

    @PostMapping("the-loai/update/{idTL}")
    public String updateTL(@ModelAttribute("tl") TheLoai theLoai, @PathVariable("idTL") UUID id) {
        theLoaiSevice.update(theLoai, id);
        return "redirect:/admin/the-loai/hien-thi";
    }

    //khuyeens mai

    @GetMapping("khuyen-mai/hien-thi")
    public String hienThiKM(Model model, @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 4);
        Page<KhuyenMai> page = khuyenMaiService.getAll(pageable.getPageNumber(), pageable.getPageSize());
        model.addAttribute("list", page);
        model.addAttribute("khuyenmai", new KhuyenMai());
        return "/admin/khuyenmai/khuyen_mai";
    }

    @GetMapping("khuyen-mai/delete/{id}")
    public String deleteKM(@PathVariable("id") String id) {
        khuyenMaiService.delete(UUID.fromString(id));
        return "redirect:/admin/khuyen-mai/hien-thi";
    }

    @GetMapping("khuyen-mai/viewUpdate/{id}")
    public String viewupdateKM(@PathVariable("id") UUID id, Model model) {
        KhuyenMai tl = khuyenMaiService.detail(id);
        model.addAttribute("detail", tl);
        return "/admin/khuyenmai/update_khuyen_mai";
    }

    @PostMapping("khuyen-mai/add")
    public String AddKM (@ModelAttribute("khuyenmai") KhuyenMai khuyenMai, Model model) {
        khuyenMaiService.add(khuyenMai);
        return "redirect:/admin/khuyen-mai/hien-thi";
    }

    @PostMapping("khuyen-mai/update/{id}")
    public String updateKM(@ModelAttribute("khuyenmai") KhuyenMai khuyenMai, @PathVariable("id") UUID id) {
        khuyenMaiService.update(khuyenMai, id);
        return "redirect:/admin/khuyen-mai/hien-thi";
    }

    @GetMapping("/hoa-don-chi-tiet/hien-thi")
    public String hoaDonChiTiet(Model model
            , @RequestParam(defaultValue = "0", name = "page") Integer number) {
        Pageable pageable = PageRequest.of(number, 7);
        Page<Hoa_Don_Chi_Tiet> listHoaDon = hoa_don_chi_tiet_service.getAll(pageable);
        model.addAttribute("list", listHoaDon);

        return "admin/hoadonchitiet/hoa-don-chi-tiet";
    }
}
