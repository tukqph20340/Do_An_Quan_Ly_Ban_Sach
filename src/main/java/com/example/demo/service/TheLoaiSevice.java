package com.example.demo.service;

import com.example.demo.entity.NhaCungCap;
import com.example.demo.entity.TheLoai;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.UUID;
public interface TheLoaiSevice {
    Page<TheLoai> getAll(Integer pageNo, Integer size);

    void delete(UUID id);

    TheLoai detail(UUID id);

    void add(TheLoai theLoai);

    void update(TheLoai theLoai, UUID id);

    ArrayList<TheLoai> getAll();
}
