package com.example.demo.service;

import com.example.demo.entity.DiaChi;

import java.util.ArrayList;
import java.util.UUID;
public interface DiaChiSevice {
    ArrayList<DiaChi> getAll();
    void delete (UUID id);
    DiaChi addAndUpdate(DiaChi diaChi);
    DiaChi getOne(UUID id);
}
