package com.example.demo.service;

import com.example.demo.entity.TacGia;

import java.util.ArrayList;
import java.util.UUID;
public interface TacGiaSevice {
    ArrayList<TacGia> getAll();
    void delete (UUID id);
    TacGia addAndUpdate(TacGia tacGia);
    TacGia getOne(UUID id);
}
