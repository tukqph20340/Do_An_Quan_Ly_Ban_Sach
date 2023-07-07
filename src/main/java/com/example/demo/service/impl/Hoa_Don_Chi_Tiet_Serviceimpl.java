package com.example.demo.service.impl;

import com.example.demo.entity.Hoa_Don_Chi_Tiet;
import com.example.demo.repository.Hoa_Don_Chi_Tiet_Repository;
import com.example.demo.service.Hoa_Don_Chi_Tiet_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Hoa_Don_Chi_Tiet_Serviceimpl implements Hoa_Don_Chi_Tiet_Service {
    @Autowired
    Hoa_Don_Chi_Tiet_Repository repository;
    @Override
    public Page<Hoa_Don_Chi_Tiet> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ArrayList<Hoa_Don_Chi_Tiet> getAll() {
        return (ArrayList<Hoa_Don_Chi_Tiet>) repository.findAll();
    }


}
