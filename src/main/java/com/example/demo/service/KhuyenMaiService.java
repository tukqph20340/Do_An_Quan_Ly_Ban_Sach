package com.example.demo.service;

import com.example.demo.entity.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public interface KhuyenMaiService {
    ArrayList<KhuyenMai> getAll();

    void delete(UUID id);

    KhuyenMai detail(UUID id);

    KhuyenMai add(KhuyenMai khuyenMai);

    KhuyenMai update(KhuyenMai khuyenMai);

    Page<KhuyenMai> phanTrang(Pageable pageable);
}
