package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.NhanVien;

import java.util.List;

public interface NhanVienService {
    List<NhanVien> getAll();
    NhanVien getById(int id);
    void save(NhanVien nv);
    NhanVien saveAndReturn(NhanVien nv);
    void delete(int id);
    boolean existsByMaNV(String maNV);
    boolean existsBySdt(String sdt);
    boolean existsByEmail(String email);
    List<NhanVien> search(String keyword);
}
