package com.example.demo.service.impl;

import com.example.demo.entity.TheLoai;
import com.example.demo.repository.TheLoaiRepository;
import com.example.demo.service.TheLoaiSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class TheLoaiSeviceImpl implements TheLoaiSevice {
    @Autowired
    TheLoaiRepository repository;


    @Override
    public Page<TheLoai> getAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return repository.findAll(pageable);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public TheLoai detail(UUID id) {
        return repository.getReferenceById(id);
    }

    @Override
    public void add(TheLoai theLoai) {
        repository.save(theLoai);
    }

    @Override
    public void update(TheLoai theLoai, UUID id) {
        TheLoai tl = repository.getReferenceById(id);
        tl.setMaTL(theLoai.getMaTL());
        tl.setTen(theLoai.getTen());
        tl.setMoTa(theLoai.getMoTa());
        tl.setTrangThai(theLoai.getTrangThai());
        repository.save(theLoai);
    }

    @Override
    public ArrayList<TheLoai> getAll() {
        return (ArrayList<TheLoai>) repository.findAll();
    }
}
