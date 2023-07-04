package com.example.demo.service.impl;

import com.example.demo.entity.NguoiDung;
import com.example.demo.repository.NguoiDungRepository;
import com.example.demo.service.NguoiDungSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
@Service
public class NguoiDungSeviceImpl implements NguoiDungSevice {
    @Autowired
    NguoiDungRepository repository;

    @Override
    public ArrayList<NguoiDung> getAll() {
        return (ArrayList<NguoiDung>) repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public NguoiDung addAndUpdate(NguoiDung nguoiDung) {
        return repository.save(nguoiDung);
    }

    @Override
    public NguoiDung getOne(UUID id) {
        return repository.getOne(id);
    }
}
