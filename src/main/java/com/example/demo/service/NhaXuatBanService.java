package com.example.demo.service;

import com.example.demo.entity.NhaXuatBan;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.UUID;

public interface NhaXuatBanService {
    ArrayList<NhaXuatBan> getAll();

    void delete(UUID id);

    NhaXuatBan detail(UUID id);

    NhaXuatBan add(NhaXuatBan nhaXuatBan);

    NhaXuatBan update(NhaXuatBan nhaXuatBan);

    Page<NhaXuatBan> phanTrang(Pageable pageable);
}
