package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.DonHang;

import java.util.List;

public interface DonHangService {
    List<DonHang> getAll();
    DonHang findById(int id);

    void save(DonHang donHang);
    DonHang saveAndReturn(DonHang donHang);

    void deleteById(int id);
    String generateMaDH();
    List<DonHang> searchByKhachHangOrNhanVien(String keyword);

    // === Thêm mới: lấy tất cả đơn hàng kèm danh sách bàn phụ ===
    List<DonHang> getAllWithBans();
}
