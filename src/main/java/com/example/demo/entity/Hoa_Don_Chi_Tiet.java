package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name="hoa_don_chi_tiet")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hoa_Don_Chi_Tiet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idHDCT;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private Hoa_Don hoa_don;

    @ManyToOne
    @JoinColumn(name = "id_sach")
    private Sach sach;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "tong_gia_tien")
    private Integer tongGiaTien;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
