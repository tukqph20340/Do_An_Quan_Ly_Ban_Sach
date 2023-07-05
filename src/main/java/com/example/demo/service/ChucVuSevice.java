package com.example.demo.service;

import com.example.demo.entity.ChucVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;
public interface ChucVuSevice {
    ArrayList<ChucVu> getAll();
    Page<ChucVu> getAll(Pageable pageable);
    void delete (UUID id);
    ChucVu addAndUpdate(ChucVu chucVu);
    ChucVu getOne(UUID id);
}
