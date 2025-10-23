package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    boolean existsByMaNV(String maNV);
    boolean existsBySdt(String sdt);
    boolean existsByEmail(String email);

    List<NhanVien> findByMaNVContainingIgnoreCaseOrTenNVContainingIgnoreCaseOrChucVuContainingIgnoreCase(
        String maNV, String tenNV, String chucVu
    );
}
