package com.example.demo.repository;


import com.example.demo.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;
@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung , UUID> {

}
