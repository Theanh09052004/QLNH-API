package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.Ban;
import java.util.List;

public interface BanService {
    List<Ban> getAll();
    void save(Ban ban);
    Ban saveAndReturn(Ban ban);
    Ban getById(int id);
    void delete(int id);
    boolean existsByTenBan(String tenBan);
    List<Ban> search(String keyword);
}
