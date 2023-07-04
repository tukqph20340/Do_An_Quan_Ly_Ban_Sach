package com.example.demo.service;

import com.example.demo.entity.NhaCungCap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public interface NhaCungCapService {
    ArrayList<NhaCungCap> getAll();

    void delete(UUID id);

    NhaCungCap detail(UUID id);

    NhaCungCap add(NhaCungCap nhaCungCap);

    NhaCungCap update(NhaCungCap nhaCungCap);

    Page<NhaCungCap> phanTrang(Pageable pageable);
}
