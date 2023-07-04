package com.example.demo.service;

import com.example.demo.entity.TheLoai;

import java.util.ArrayList;
import java.util.UUID;
public interface TheLoaiSevice {
    ArrayList<TheLoai> getAll();
    void delete (UUID id);
    TheLoai addAndUpdate(TheLoai theLoai);
    TheLoai getOne(UUID id);
}
