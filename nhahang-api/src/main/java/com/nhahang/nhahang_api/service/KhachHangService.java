package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.KhachHang;

import java.util.List;

public interface KhachHangService {
    List<KhachHang> getAll();
    KhachHang getById(int id);
    void save(KhachHang kh);
    KhachHang saveAndReturn(KhachHang kh);
    void delete(int id);
    boolean existsByMaKH(String maKH);
    boolean existsBySdt(String sdt);
    boolean existsByEmail(String email);

    List<KhachHang> search(String keyword);
}
