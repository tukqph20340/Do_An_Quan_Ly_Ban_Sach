package com.example.demo.service.impl;

import com.example.demo.entity.NhaCungCap;
import com.example.demo.repository.NhaCungCapRepository;
import com.example.demo.service.NhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {
    @Autowired
    NhaCungCapRepository nhaCungCapRepository;


    @Override
    public Page<NhaCungCap> getAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return nhaCungCapRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID id) {
        nhaCungCapRepository.deleteById(id);
    }

    @Override
    public NhaCungCap detail(UUID id) {
        return nhaCungCapRepository.getReferenceById(id);
    }

    @Override
    public void add(NhaCungCap nhaCungCap) {
        nhaCungCapRepository.save(nhaCungCap);
    }

    @Override
    public void update(NhaCungCap nhaCungCap,UUID id) {
        NhaCungCap ncc = nhaCungCapRepository.getReferenceById(id);
        ncc.setMa(nhaCungCap.getMa());
        ncc.setTen(nhaCungCap.getTen());
        ncc.setDiaChi(nhaCungCap.getDiaChi());
        ncc.setSdt(nhaCungCap.getSdt());
        ncc.setMoTa(nhaCungCap.getMoTa());
        ncc.setTrangThai(nhaCungCap.getTrangThai());
        nhaCungCapRepository.save(nhaCungCap);
    }

    @Override
    public ArrayList<NhaCungCap> getAll() {
        return (ArrayList<NhaCungCap>) nhaCungCapRepository.findAll();
    }
}
