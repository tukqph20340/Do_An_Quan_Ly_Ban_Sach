package com.example.demo.controller;

import com.example.demo.entity.*;

import com.example.demo.service.ChucVuSevice;
import com.example.demo.service.DiaChiSevice;
import com.example.demo.service.NguoiDungSevice;
import com.example.demo.service.NhaCungCapService;
import com.example.demo.service.NhaXuatBanService;
import com.example.demo.service.SachService;
import com.example.demo.service.TacGiaSevice;
import com.example.demo.service.TheLoaiSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Controller
@RequestMapping("/admin")

public class AdminController {

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


    // Sản Phẩm
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
            sach.setTrangThai(1);
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
            sach.setTrangThai(1);
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
}
