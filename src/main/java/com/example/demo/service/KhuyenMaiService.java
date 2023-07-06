package com.example.demo.service;

import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.NhaXuatBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public interface KhuyenMaiService {
    Page<KhuyenMai> getAll(Integer pageNo, Integer size);

    void delete(UUID id);

    KhuyenMai detail(UUID id);

    void add(KhuyenMai khuyenMai);

    void update(KhuyenMai khuyenMai, UUID id);

    ArrayList<KhuyenMai> getAll();
}
