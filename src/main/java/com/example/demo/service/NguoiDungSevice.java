package com.example.demo.service;

import com.example.demo.entity.NguoiDung;

import java.util.ArrayList;
import java.util.UUID;
public interface NguoiDungSevice {
    ArrayList<NguoiDung> getAll();
    void delete (UUID id);
    NguoiDung addAndUpdate(NguoiDung nguoiDung);
    NguoiDung getOne(UUID id);

}
