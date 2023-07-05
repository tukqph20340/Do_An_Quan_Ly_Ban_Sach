package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="ban_quyen")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Ban_Quyen {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_giay_phep")
    private GiayPhep giayPhep;

    @ManyToOne
    @JoinColumn(name = "id_sach")
    private Sach sach;

    @Column(name = "ngay_bat_dau")
    private LocalDate ngayBatDau;

    @Column(name = "ngay_het_han")
    private LocalDate ngayHetHan;

    @Column(name = "nguoi_ky")
    private String nguoiKy;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
