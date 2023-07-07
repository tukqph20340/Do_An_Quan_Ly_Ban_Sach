package com.example.demo.service;

import com.example.demo.entity.Hoa_Don_Chi_Tiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
public interface Hoa_Don_Chi_Tiet_Service {
    Page<Hoa_Don_Chi_Tiet> getAll(Pageable pageable);
    ArrayList<Hoa_Don_Chi_Tiet> getAll();
}
