package com.example.demo.service;

import com.example.demo.entity.NguoiDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;
public interface NguoiDungSevice {

    void delete (UUID id);
    NguoiDung addAndUpdate(NguoiDung nguoiDung);
    NguoiDung getOne(UUID id);
    Page<NguoiDung> getAll(Pageable pageable);

}
