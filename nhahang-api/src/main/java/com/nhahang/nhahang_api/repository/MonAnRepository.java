package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.MonAn;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MonAnRepository extends JpaRepository<MonAn, Integer> {
    boolean existsByTenMon(String tenMon);
    List<MonAn> findByTenMonContainingIgnoreCase(String tenMon);
}
