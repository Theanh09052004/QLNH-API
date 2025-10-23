package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.NhaCungCap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, Integer> {
    boolean existsByMaNCC(String maNCC);
    boolean existsByEmail(String email);
    boolean existsBySoDienThoai(String soDienThoai);

    boolean existsByMaNCCAndIdNot(String maNCC, int id);
    boolean existsByEmailAndIdNot(String email, int id);
    boolean existsBySoDienThoaiAndIdNot(String soDienThoai, int id);

    List<NhaCungCap> findByTenNCCContainingIgnoreCaseOrMaNCCContainingIgnoreCase(String tenNCC, String maNCC);
    
}
