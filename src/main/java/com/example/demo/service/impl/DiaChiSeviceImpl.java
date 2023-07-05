package com.example.demo.service.impl;

import com.example.demo.entity.DiaChi;
import com.example.demo.repository.DiaChiRepository;
import com.example.demo.service.DiaChiSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
@Service
public class DiaChiSeviceImpl implements DiaChiSevice {
    @Autowired
    DiaChiRepository repository;

    @Override
    public Page<DiaChi> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public DiaChi addAndUpdate(DiaChi diaChi) {
        return repository.save(diaChi);
    }

    @Override
    public DiaChi getOne(UUID id) {
        return repository.getOne(id);
    }

}
