package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.LoaiMonAn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoaiMonAnRepository extends JpaRepository<LoaiMonAn, Integer> {
    Optional<LoaiMonAn> findByTenLoai(String tenLoai);
    List<LoaiMonAn> findByTenLoaiContainingIgnoreCase(String keyword);
}
