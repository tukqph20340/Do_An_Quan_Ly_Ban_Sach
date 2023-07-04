package com.example.demo.service.impl;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.service.KhachHangSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
@Service
public class KhachHangSeviceImpl implements KhachHangSevice {
    @Autowired
    KhachHangRepository repository;

    @Override
    public ArrayList<KhachHang> getAll() {
        return (ArrayList<KhachHang>) repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public KhachHang addAndUpdate(KhachHang khachHang) {
        return repository.save(khachHang);
    }

    @Override
    public KhachHang getOne(UUID id) {
        return repository.getOne(id);
    }

}
