package com.example.demo.service.impl;


import com.example.demo.entity.ChucVu;
import com.example.demo.repository.ChucVuRepository;
import com.example.demo.service.ChucVuSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
@Service
public class ChucVuSeviceImpl implements ChucVuSevice {
    @Autowired
    ChucVuRepository repository;
    @Override
    public ArrayList<ChucVu> getAll() {
        return (ArrayList<ChucVu>) repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public ChucVu addAndUpdate(ChucVu chucVu) {
        return repository.save(chucVu);
    }

    @Override
    public ChucVu getOne(UUID id) {
        return repository.getOne(id);
    }
}
