package com.example.demo.repository;

import com.example.demo.entity.Ban_Quyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Ban_Quyen_Repository extends JpaRepository<Ban_Quyen, UUID> {
}
