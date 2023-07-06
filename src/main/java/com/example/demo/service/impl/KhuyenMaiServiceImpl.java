package com.example.demo.service.impl;

import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.NhaCungCap;
import com.example.demo.repository.KhuyenMaiRepository;
import com.example.demo.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {
    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;


    @Override
    public Page<KhuyenMai> getAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return khuyenMaiRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID id) {
        khuyenMaiRepository.deleteById(id);
    }

    @Override
    public KhuyenMai detail(UUID id) {
        return khuyenMaiRepository.getReferenceById(id);
    }

    @Override
    public void add(KhuyenMai khuyenMai) {
    khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    public void update(KhuyenMai khuyenMai, UUID id) {
        KhuyenMai ncc = khuyenMaiRepository.getReferenceById(id);
        ncc.setMa(khuyenMai.getMa());
        ncc.setTen(khuyenMai.getTen());
        ncc.setNgayTao(khuyenMai.getNgayTao());
        ncc.setNgayBatDau(khuyenMai.getNgayKetThuc());
        ncc.setNgayKetThuc(khuyenMai.getNgayKetThuc());
        ncc.setMoTa(khuyenMai.getMoTa());
        ncc.setTrangThai(khuyenMai.getTrangThai());
        khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    public ArrayList<KhuyenMai> getAll() {
        return (ArrayList<KhuyenMai>) khuyenMaiRepository.findAll();
    }
}
