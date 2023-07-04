package com.example.demo.service.impl;

import com.example.demo.entity.KhuyenMai;
import com.example.demo.repository.KhuyenMaiRepository;
import com.example.demo.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {
    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    @Override
    public ArrayList<KhuyenMai> getAll() {
        return (ArrayList<KhuyenMai>) khuyenMaiRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        khuyenMaiRepository.deleteById(id);
    }

    @Override
    public KhuyenMai detail(UUID id) {
        return khuyenMaiRepository.getOne(id);
    }

    @Override
    public KhuyenMai add(KhuyenMai khuyenMai) {
        return khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    public KhuyenMai update(KhuyenMai khuyenMai) {
        return khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    public Page<KhuyenMai> phanTrang(Pageable pageable) {
        return null;
    }
}
