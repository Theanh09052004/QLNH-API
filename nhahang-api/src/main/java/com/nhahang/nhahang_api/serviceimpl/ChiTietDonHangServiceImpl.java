package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.ChiTietDonHang;
import com.nhahang.nhahang_api.model.DonHang;
import com.nhahang.nhahang_api.model.MonAn;
import com.nhahang.nhahang_api.repository.ChiTietDonHangRepository;
import com.nhahang.nhahang_api.repository.MonAnRepository;
import com.nhahang.nhahang_api.service.ChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {

    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Autowired
    private MonAnRepository monAnRepository;

    @Override
    public void save(ChiTietDonHang chiTietDonHang) {
        chiTietDonHangRepository.save(chiTietDonHang);
    }

    @Transactional
    @Override
    public ChiTietDonHang saveAndReturn(ChiTietDonHang chiTietDonHang) {
        MonAn monAn = monAnRepository.findById(chiTietDonHang.getMonAn().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn!"));

        int soLuongDat = chiTietDonHang.getSoLuong();

        // ✅ Kiểm tra tồn kho
        if (monAn.getSoLuong() == 0)
            throw new RuntimeException("Món '" + monAn.getTenMon() + "' đã hết hàng!");
        if (soLuongDat > monAn.getSoLuong())
            throw new RuntimeException("Món '" + monAn.getTenMon() + "' chỉ còn " + monAn.getSoLuong() + " phần!");

        // ✅ Trừ tồn kho
        monAn.setSoLuong(monAn.getSoLuong() - soLuongDat);
        monAnRepository.save(monAn);

        // ✅ Lưu chi tiết đơn hàng
        return chiTietDonHangRepository.save(chiTietDonHang);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        ChiTietDonHang chiTiet = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng để xóa!"));

        // ✅ Khi xóa, hoàn lại tồn kho
        MonAn monAn = monAnRepository.findById(chiTiet.getMonAn().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn!"));

        monAn.setSoLuong(monAn.getSoLuong() + chiTiet.getSoLuong());
        monAnRepository.save(monAn);

        chiTietDonHangRepository.deleteById(id);
    }

    @Override
    public List<ChiTietDonHang> getByDonHang(DonHang donHang) {
        return chiTietDonHangRepository.findByDonHang(donHang);
    }

    @Override
    public double tinhTongTien(DonHang donHang) {
        List<ChiTietDonHang> ds = getByDonHang(donHang);
        return ds.stream().mapToDouble(ct -> ct.getSoLuong() * ct.getDonGia()).sum();
    }

    @Override
    public List<ChiTietDonHang> getAll() {
        return chiTietDonHangRepository.findAll();
    }

    @Override
    public ChiTietDonHang getById(int id) {
        return chiTietDonHangRepository.findById(id).orElse(null);
    }
}
