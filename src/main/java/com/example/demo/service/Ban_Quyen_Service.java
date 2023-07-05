package com.example.demo.service;

import com.example.demo.entity.Ban_Quyen;
import com.example.demo.entity.GiayPhep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface Ban_Quyen_Service {
    ArrayList<Ban_Quyen> getAll();

    void delete(UUID id);

    Ban_Quyen detail(UUID id);

    Ban_Quyen add(Ban_Quyen ban_quyen);

    Ban_Quyen update(Ban_Quyen ban_quyen);

    ArrayList<Ban_Quyen> timKiem(LocalDate ngayBatDau, LocalDate ngayHetHan);

    Page<Ban_Quyen> phanTrang(Pageable pageable);
}
