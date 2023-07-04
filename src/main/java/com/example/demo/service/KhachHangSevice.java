package com.example.demo.service;

import com.example.demo.entity.KhachHang;

import java.util.ArrayList;
import java.util.UUID;
public interface KhachHangSevice {
    ArrayList<KhachHang> getAll();
    void delete (UUID id);
    KhachHang addAndUpdate(KhachHang khachHang);
    KhachHang getOne(UUID id);

}
