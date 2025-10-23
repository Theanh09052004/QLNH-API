package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.DonHang;
import com.nhahang.nhahang_api.repository.DonHangRepository;
import com.nhahang.nhahang_api.service.DonHangService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonHangServiceImpl implements DonHangService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Override
    @Transactional
    public List<DonHang> getAll() {
        return donHangRepository.findAll();
    }

    @Override
    @Transactional
    public DonHang findById(int id) {
        return donHangRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(DonHang donHang) {
        donHangRepository.save(donHang);
    }

    @Override
    @Transactional
    public DonHang saveAndReturn(DonHang donHang) {
        return donHangRepository.save(donHang);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        donHangRepository.deleteById(id);
    }

    @Override
    public String generateMaDH() {
        return "DH" + System.currentTimeMillis();
    }

    @Override
    @Transactional
    public List<DonHang> searchByKhachHangOrNhanVien(String keyword) {
        return donHangRepository.findByKhachHang_TenKHContainingIgnoreCaseOrNhanVien_TenNVContainingIgnoreCase(keyword, keyword);
    }

    // === Mới thêm: đảm bảo load cả danh sách bàn phụ khi lấy đơn hàng ===
    @Override
    @Transactional
    public List<DonHang> getAllWithBans() {
        List<DonHang> list = donHangRepository.findAll();
        // ép truy cập để Hibernate load danh sách bàn phụ (tránh lazy)
        for (DonHang dh : list) {
            if (dh.getBansPhu() != null) {
                dh.getBansPhu().size(); // ép load
            }
        }
        return list;
    }
}
