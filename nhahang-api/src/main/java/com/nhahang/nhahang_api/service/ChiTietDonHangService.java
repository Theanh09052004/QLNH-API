package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.ChiTietDonHang;
import com.nhahang.nhahang_api.model.DonHang;

import java.util.List;

public interface ChiTietDonHangService {
    List<ChiTietDonHang> getByDonHang(DonHang donHang);
    void save(ChiTietDonHang chiTietDonHang);
    ChiTietDonHang saveAndReturn(ChiTietDonHang chiTietDonHang);
    void deleteById(int id);
    double tinhTongTien(DonHang donHang);
    List<ChiTietDonHang> getAll();
    ChiTietDonHang getById(int id);
}
