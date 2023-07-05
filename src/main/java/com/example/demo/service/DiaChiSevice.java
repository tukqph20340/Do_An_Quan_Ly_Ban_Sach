package com.example.demo.service;

import com.example.demo.entity.DiaChi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;
public interface DiaChiSevice {
    Page<DiaChi> getAll(Pageable pageable);
    ArrayList<DiaChi> getAll();
    void delete (UUID id);
    DiaChi addAndUpdate(DiaChi diaChi);
    DiaChi getOne(UUID id);
}
