package com.example.demo.repository;

import com.example.demo.entity.Gio_Hang_Chi_tiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Gio_Hang_Chi_Tiet_Repository extends JpaRepository<Gio_Hang_Chi_tiet, UUID> {
}
