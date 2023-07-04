package com.example.demo.service.impl;

import com.example.demo.entity.NhaXuatBan;
import com.example.demo.repository.NhaXuatBanRepository;
import com.example.demo.service.NhaXuatBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class NhaXuatBanServiceImpl implements NhaXuatBanService {
    @Autowired
    NhaXuatBanRepository nhaXuatBanRepository;

    @Override
    public ArrayList<NhaXuatBan> getAll() {
        return (ArrayList<NhaXuatBan>) nhaXuatBanRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        nhaXuatBanRepository.deleteById(id);
    }

    @Override
    public NhaXuatBan detail(UUID id) {
        return nhaXuatBanRepository.getOne(id);
    }

    @Override
    public NhaXuatBan add(NhaXuatBan nhaXuatBan) {
        return nhaXuatBanRepository.save(nhaXuatBan);
    }

    @Override
    public NhaXuatBan update(NhaXuatBan nhaXuatBan) {
        return nhaXuatBanRepository.save(nhaXuatBan);
    }

    @Override
    public Page<NhaXuatBan> phanTrang(Pageable pageable) {
        return null;
    }
}
