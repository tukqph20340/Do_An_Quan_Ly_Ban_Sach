package com.example.demo.service;

import com.example.demo.entity.KhachHang;

import java.util.ArrayList;
import java.util.UUID;
public interface KhachHangSevice {
    ArrayList<KhachHang> getAll();
    void delete (UUID idKH);
    KhachHang addAndUpdate(KhachHang khachHang);
    KhachHang getOne(UUID idKH);
    ArrayList<KhachHang> timKiem(String ma, String hoVaTen);
}
