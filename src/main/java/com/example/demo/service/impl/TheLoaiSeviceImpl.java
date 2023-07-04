package com.example.demo.service.impl;

import com.example.demo.entity.TheLoai;
import com.example.demo.repository.TheLoaiRepository;
import com.example.demo.service.TheLoaiSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
@Service
public class TheLoaiSeviceImpl implements TheLoaiSevice {
    @Autowired
    TheLoaiRepository repository;

    @Override
    public ArrayList<TheLoai> getAll() {
        return (ArrayList<TheLoai>) repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public TheLoai addAndUpdate(TheLoai theLoai) {
        return repository.save(theLoai);
    }

    @Override
    public TheLoai getOne(UUID id) {
        return repository.getOne(id);
    }
}
