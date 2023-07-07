package com.example.demo.service.impl;

import com.example.demo.entity.Ban_Quyen;
import com.example.demo.repository.Ban_Quyen_Repository;
import com.example.demo.repository.GiayPhepRepository;
import com.example.demo.service.Ban_Quyen_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class Ban_Quyen_Serviceimpl implements Ban_Quyen_Service {
    @Autowired
    Ban_Quyen_Repository ban_quyen_repository;

    @Override
    public ArrayList<Ban_Quyen> getAll() {
        return (ArrayList<Ban_Quyen>) ban_quyen_repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        ban_quyen_repository.deleteById(id);
    }

    @Override
    public Ban_Quyen detail(UUID id) {
        return ban_quyen_repository.getOne(id);
    }

    @Override
    public Ban_Quyen add(Ban_Quyen ban_quyen) {
        return ban_quyen_repository.save(ban_quyen);
    }

    @Override
    public Ban_Quyen update(Ban_Quyen ban_quyen) {
        return ban_quyen_repository.save(ban_quyen);
    }

//    @Override
//    public ArrayList<Ban_Quyen> timKiem(LocalDate ngayBatDau, LocalDate ngayHetHan) {
//        return ban_quyen_repository.findByNgayBatDauAndNgayHetHan(ngayBatDau, ngayHetHan);
//    }

    @Override
    public Page<Ban_Quyen> phanTrang(Pageable pageable) {
        return ban_quyen_repository.findAll(pageable);
    }
}
