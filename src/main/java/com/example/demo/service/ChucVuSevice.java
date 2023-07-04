package com.example.demo.service;

import com.example.demo.entity.ChucVu;

import java.util.ArrayList;
import java.util.UUID;
public interface ChucVuSevice {

    ArrayList<ChucVu> getAll();
    void delete (UUID id);
    ChucVu addAndUpdate(ChucVu chucVu);
    ChucVu getOne(UUID id);
}
