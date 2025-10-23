package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.LoaiMonAn;
import java.util.List;
import java.util.Optional;

public interface LoaiMonAnService {
    List<LoaiMonAn> getAll();
    void save(LoaiMonAn loaiMonAn);
    LoaiMonAn saveAndReturn(LoaiMonAn loaiMonAn);
    void delete(int id);
    Optional<LoaiMonAn> getById(int id);
    Optional<LoaiMonAn> getByTenLoai(String tenLoai);
    List<LoaiMonAn> search(String keyword);
}
