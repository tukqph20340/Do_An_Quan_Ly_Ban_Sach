package com.example.demo.repository;

import com.example.demo.entity.Hoa_Don;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Hoa_Don_Repository extends JpaRepository<Hoa_Don, UUID> {
}
