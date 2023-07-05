package com.example.demo.repository;

import com.example.demo.entity.GiayPhep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface GiayPhepRepository extends JpaRepository<GiayPhep, UUID> {
    ArrayList<GiayPhep> findByNgayBatDauAndNgayHetHan(Date ngayBatDau, Date ngayHetHan);
}
