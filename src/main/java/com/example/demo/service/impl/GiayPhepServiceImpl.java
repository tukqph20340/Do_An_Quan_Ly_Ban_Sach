package com.example.demo.service.impl;

import com.example.demo.entity.GiayPhep;
import com.example.demo.repository.GiayPhepRepository;
import com.example.demo.service.GiayPhepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class GiayPhepServiceImpl implements GiayPhepService {
    @Autowired
    GiayPhepRepository giayPhepRepository;

    @Override
    public ArrayList<GiayPhep> getAll() {
        return (ArrayList<GiayPhep>) giayPhepRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        giayPhepRepository.deleteById(id);
    }

    @Override
    public GiayPhep detail(UUID id) {
        return giayPhepRepository.getOne(id);
    }

    @Override
    public GiayPhep add(GiayPhep giayPhep) {
        return giayPhepRepository.save(giayPhep);
    }

    @Override
    public GiayPhep update(GiayPhep giayPhep) {
        return giayPhepRepository.save(giayPhep);
    }

    @Override
    public Page<GiayPhep> phanTrang(Pageable pageable) {
        return null;
    }
}
