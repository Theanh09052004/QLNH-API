package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.DonHang;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByKhachHang_TenKHContainingIgnoreCaseOrNhanVien_TenNVContainingIgnoreCase(String tenKH, String tenNV);

}
