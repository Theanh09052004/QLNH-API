package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.MonAn;
import java.util.List;

public interface MonAnService {
    List<MonAn> getAll();
    void save(MonAn monAn);
    MonAn saveAndReturn(MonAn monAn);
    MonAn getById(int id);
    void delete(int id);
    boolean existsByTenMon(String tenMon);
    List<MonAn> search(String keyword);
}
