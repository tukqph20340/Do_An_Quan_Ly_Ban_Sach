package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="gio_hang_chi_tiet")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Gio_Hang_Chi_tiet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "tong_gia_tien")
    private Integer tongGiaTien;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
