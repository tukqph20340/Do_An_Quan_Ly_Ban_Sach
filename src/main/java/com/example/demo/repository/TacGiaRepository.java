package com.example.demo.repository;

import com.example.demo.entity.TacGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;
@Repository
public interface TacGiaRepository extends JpaRepository<TacGia, UUID> {
    ArrayList<TacGia> findByMaTGAndHoVaTen(String maTG, String hoVaTen);
}
