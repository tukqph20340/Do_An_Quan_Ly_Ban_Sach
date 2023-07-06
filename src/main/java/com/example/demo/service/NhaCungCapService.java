package com.example.demo.service;

import com.example.demo.entity.NhaCungCap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public interface NhaCungCapService {
    Page<NhaCungCap> getAll(Integer pageNo,Integer size);

    void delete(UUID id);

    NhaCungCap detail(UUID id);

    void add(NhaCungCap nhaCungCap);

    void update(NhaCungCap nhaCungCap, UUID id);

    ArrayList<NhaCungCap> getAll();
}
