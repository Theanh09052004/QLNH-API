package com.nhahang.nhahang_api.service;

import java.util.List;
import com.nhahang.nhahang_api.dto.ThongKeDTO;

public interface ThongKeService {
    List<ThongKeDTO> thongKeTheoNgay(String keyword);
    List<ThongKeDTO> thongKeTheoThang(String keyword);
    List<ThongKeDTO> thongKeTheoNam(String keyword);
}
