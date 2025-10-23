package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    boolean existsByMaKH(String maKH);

    boolean existsBySdt(String sdt);

    boolean existsByEmail(String email);

    List<KhachHang> findByTenKHContainingIgnoreCaseOrSdtContaining(String tenKH, String sdt);
}
