package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "sach")
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_ncc")
    private NhaCungCap nhaCungCap;

//    @ManyToOne
//    @JoinColumn(name = "id_tac_gia")
//    private TacGia tacGia;

    @ManyToOne
    @JoinColumn(name = "id_nxb")
    private NhaXuatBan nhaXuatBan;

//    @ManyToOne
//    @JoinColumn(name = "id_the_loai")
//    private TheLoai theLoai;

    @ManyToOne
    @JoinColumn(name = "id_khuyen_mai")
    private KhuyenMai khuyenMai;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "file_anh")
    private String fileAnh;

    @Column(name = "ngay_xuat_ban")
    private Date ngayXuatBan;

    @Column(name = "gia_ban")
    private Integer giaBan;

    @Column(name = "gia_nhap")
    private Integer giaNhap;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
