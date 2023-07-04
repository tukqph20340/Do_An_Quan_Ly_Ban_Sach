package com.example.demo.service;


import com.example.demo.entity.Sach;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.UUID;

public interface SachService {
    ArrayList<Sach> getAll();

    void delete(UUID id);

    Sach detail(UUID id);

    Sach add(Sach sach);

    Sach update(Sach sach);

    Page<Sach> phanTrang(Pageable pageable);
}
