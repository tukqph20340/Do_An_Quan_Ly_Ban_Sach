package com.example.demo.service;

import com.example.demo.entity.NhaCungCap;
import com.example.demo.entity.NhaXuatBan;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.UUID;

public interface NhaXuatBanService {
    Page<NhaXuatBan> getAll(Integer pageNo, Integer size);

    void delete(UUID id);

    NhaXuatBan detail(UUID id);

    void add(NhaXuatBan nhaXuatBan);

    void update(NhaXuatBan nhaXuatBan, UUID id);

    ArrayList<NhaXuatBan> getAll();
}
