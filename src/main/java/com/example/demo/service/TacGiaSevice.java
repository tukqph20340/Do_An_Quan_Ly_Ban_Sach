package com.example.demo.service;

import com.example.demo.entity.TacGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;
public interface TacGiaSevice {
    ArrayList<TacGia> getAll();
    void delete (UUID id);
    TacGia addAndUpdate(TacGia tacGia);
    TacGia getOne(UUID id);
    Page<TacGia> phanTrang(Pageable pageable);
}
