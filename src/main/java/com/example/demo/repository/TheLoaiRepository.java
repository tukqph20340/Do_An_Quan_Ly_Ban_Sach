package com.example.demo.repository;

import com.example.demo.entity.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai, UUID> {
}
