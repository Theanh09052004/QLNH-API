package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.NhapHang;
import java.util.List;

public interface NhapHangService {
    List<NhapHang> getAll();
    NhapHang getById(int id);
    NhapHang save(NhapHang nhapHang);
    void delete(int id);
    NhapHang saveAndReturn(NhapHang nhapHang); 
}
