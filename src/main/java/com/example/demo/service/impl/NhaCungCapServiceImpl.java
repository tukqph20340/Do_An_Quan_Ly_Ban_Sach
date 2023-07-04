package com.example.demo.service.impl;

import com.example.demo.entity.NhaCungCap;
import com.example.demo.repository.NhaCungCapRepository;
import com.example.demo.service.NhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {
    @Autowired
    NhaCungCapRepository nhaCungCapRepository;

    @Override
    public ArrayList<NhaCungCap> getAll() {
        return (ArrayList<NhaCungCap>) nhaCungCapRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        nhaCungCapRepository.deleteById(id);
    }

    @Override
    public NhaCungCap detail(UUID id) {
        return nhaCungCapRepository.getOne(id);
    }

    @Override
    public NhaCungCap add(NhaCungCap nhaCungCap) {
        return nhaCungCapRepository.save(nhaCungCap);
    }

    @Override
    public NhaCungCap update(NhaCungCap nhaCungCap) {
        return nhaCungCapRepository.save(nhaCungCap);
    }

    @Override
    public Page<NhaCungCap> phanTrang(Pageable pageable) {
        return null;
    }
}
