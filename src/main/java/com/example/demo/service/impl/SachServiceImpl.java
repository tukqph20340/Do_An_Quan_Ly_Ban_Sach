package com.example.demo.service.impl;

import com.example.demo.entity.Sach;
import com.example.demo.repository.SachRepository;
import com.example.demo.service.SachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class SachServiceImpl implements SachService {
    @Autowired
    SachRepository sachRepository;

    @Override
    public ArrayList<Sach> getAll() {
        return (ArrayList<Sach>) sachRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        sachRepository.deleteById(id);
    }

    @Override
    public Sach detail(UUID id) {
        return sachRepository.getOne(id);
    }

    @Override
    public Sach add(Sach sach) {
        return sachRepository.save(sach);
    }

    @Override
    public Sach update(Sach sach) {
        return sachRepository.save(sach);
    }

    @Override
    public Page<Sach> phanTrang(Pageable pageable) {
        return null;
    }
}
