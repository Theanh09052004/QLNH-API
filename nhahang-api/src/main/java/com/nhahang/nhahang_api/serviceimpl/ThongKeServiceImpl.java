package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.dto.ThongKeDTO;
import com.nhahang.nhahang_api.repository.ChiTietDonHangRepository;
import com.nhahang.nhahang_api.repository.NhapHangRepository;
import com.nhahang.nhahang_api.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Autowired
    private NhapHangRepository nhapHangRepository;

    @Override
    public List<ThongKeDTO> thongKeTheoNgay(String keyword) {
        try {
            List<Object[]> doanhThu = chiTietDonHangRepository.thongKeDoanhThuTheoNgay();
            List<Object[]> nhap = nhapHangRepository.thongKeNhapHangTheoNgay();
            List<ThongKeDTO> result = merge(doanhThu, nhap);
            return filter(result, keyword);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<ThongKeDTO> thongKeTheoThang(String keyword) {
        try {
            List<Object[]> doanhThu = chiTietDonHangRepository.thongKeDoanhThuTheoThang();
            List<Object[]> nhap = nhapHangRepository.thongKeNhapHangTheoThang();
            List<ThongKeDTO> result = merge(doanhThu, nhap);
            return filter(result, keyword);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<ThongKeDTO> thongKeTheoNam(String keyword) {
        try {
            List<Object[]> doanhThu = chiTietDonHangRepository.thongKeDoanhThuTheoNam();
            List<Object[]> nhap = nhapHangRepository.thongKeNhapHangTheoNam();
            List<ThongKeDTO> result = merge(doanhThu, nhap);
            return filter(result, keyword);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 🔹 Gộp doanh thu & nhập hàng theo thời gian
    private List<ThongKeDTO> merge(List<Object[]> dt, List<Object[]> nh) {
        LinkedHashMap<String, ThongKeDTO> map = new LinkedHashMap<>();

        if (dt != null) {
            for (Object[] r : dt) {
                String key = r[0] == null ? "null" : r[0].toString();
                double tong = r[1] == null ? 0.0 : ((Number) r[1]).doubleValue();
                map.put(key, new ThongKeDTO(key, tong, 0.0));
            }
        }

        if (nh != null) {
            for (Object[] r : nh) {
                String key = r[0] == null ? "null" : r[0].toString();
                double tong = r[1] == null ? 0.0 : ((Number) r[1]).doubleValue();
                ThongKeDTO dto = map.getOrDefault(key, new ThongKeDTO(key, 0.0, tong));
                dto.setTongNhapHang(tong);
                dto.setLoiNhuan(dto.getTongDoanhThu() - dto.getTongNhapHang());
                map.put(key, dto);
            }
        }

        for (ThongKeDTO t : map.values()) {
            t.setLoiNhuan(t.getTongDoanhThu() - t.getTongNhapHang());
        }

        return new ArrayList<>(map.values());
    }

    // 🔹 Lọc theo từ khóa (nếu có)
    private List<ThongKeDTO> filter(List<ThongKeDTO> list, String keyword) {
        if (keyword == null || keyword.isBlank()) return list;
        keyword = keyword.trim();

        // "2025-10" => "10-2025"
        if (keyword.matches("\\d{4}-\\d{2}")) {
            String[] parts = keyword.split("-");
            keyword = parts[1] + "-" + parts[0];
        }

        List<ThongKeDTO> filtered = new ArrayList<>();
        for (ThongKeDTO dto : list) {
            if (dto.getThoiGian() != null && dto.getThoiGian().contains(keyword)) {
                filtered.add(dto);
            }
        }
        return filtered;
    }
}
