package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.NhaCungCap;
import java.util.List;

public interface NhaCungCapService {
    List<NhaCungCap> getAll();
    void save(NhaCungCap nhaCungCap);
    NhaCungCap saveAndReturn(NhaCungCap nhaCungCap);
    NhaCungCap getById(int id);
    void delete(int id);
    boolean existsByMaNCC(String maNCC);
    List<NhaCungCap> search(String keyword);
    boolean isMaNCCDuplicated(String maNCC, Integer excludeId);
    boolean isEmailDuplicated(String email, Integer excludeId);
    boolean isPhoneDuplicated(String soDienThoai, Integer excludeId);
}
