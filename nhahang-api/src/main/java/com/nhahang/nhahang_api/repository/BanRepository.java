package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BanRepository extends JpaRepository<Ban, Integer> {
    boolean existsByTenBan(String tenBan);
    List<Ban> findByTenBanContainingIgnoreCase(String keyword);
    List<Ban> findByTrangThai(String trangThai);
}
