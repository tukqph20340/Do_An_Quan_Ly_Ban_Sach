package com.example.demo.service.impl;

import com.example.demo.entity.NhaCungCap;
import com.example.demo.entity.NhaXuatBan;
import com.example.demo.repository.NhaXuatBanRepository;
import com.example.demo.service.NhaXuatBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class NhaXuatBanServiceImpl implements NhaXuatBanService {
    @Autowired
    NhaXuatBanRepository nhaXuatBanRepository;


    @Override
    public Page<NhaXuatBan> getAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return nhaXuatBanRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID id) {
        nhaXuatBanRepository.deleteById(id);
    }

    @Override
    public NhaXuatBan detail(UUID id) {
        return nhaXuatBanRepository.getReferenceById(id);
    }

    @Override
    public void add(NhaXuatBan nhaXuatBan) {
        nhaXuatBanRepository.save(nhaXuatBan);
    }

    @Override
    public void update(NhaXuatBan nhaXuatBan, UUID id) {
        NhaXuatBan ncc = nhaXuatBanRepository.getReferenceById(id);
        ncc.setMa(nhaXuatBan.getMa());
        ncc.setTen(nhaXuatBan.getTen());
        ncc.setDiaChi(nhaXuatBan.getDiaChi());
        ncc.setSdt(nhaXuatBan.getSdt());
        ncc.setMoTa(nhaXuatBan.getMoTa());
        ncc.setTrangThai(nhaXuatBan.getTrangThai());
        nhaXuatBanRepository.save(nhaXuatBan);
    }

    @Override
    public ArrayList<NhaXuatBan> getAll() {
        return (ArrayList<NhaXuatBan>) nhaXuatBanRepository.findAll();
    }
}
