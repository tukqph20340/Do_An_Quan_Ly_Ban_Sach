package com.example.demo.service;

import com.example.demo.entity.GiayPhep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public interface GiayPhepService {
    ArrayList<GiayPhep> getAll();

    void delete(UUID id);

    GiayPhep detail(UUID id);

    GiayPhep add(GiayPhep giayPhep);

    GiayPhep update(GiayPhep giayPhep);

    Page<GiayPhep> phanTrang(Pageable pageable);
}
