package com.example.demo.repository;

import com.example.demo.entity.Hoa_Don_Chi_Tiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;
@Repository
public interface Hoa_Don_Chi_Tiet_Repository extends JpaRepository<Hoa_Don_Chi_Tiet, UUID> {


}



