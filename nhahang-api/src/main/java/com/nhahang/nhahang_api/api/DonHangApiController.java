package com.nhahang.nhahang_api.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nhahang.nhahang_api.dto.DonHangDTO;
import com.nhahang.nhahang_api.model.Ban;
import com.nhahang.nhahang_api.model.DonHang;
import com.nhahang.nhahang_api.model.DonHang.TrangThaiDonHang;
import com.nhahang.nhahang_api.model.Ban.TrangThaiBan;
import com.nhahang.nhahang_api.model.KhachHang;
import com.nhahang.nhahang_api.model.NhanVien;
import com.nhahang.nhahang_api.repository.BanRepository;
import com.nhahang.nhahang_api.repository.KhachHangRepository;
import com.nhahang.nhahang_api.repository.NhanVienRepository;
import com.nhahang.nhahang_api.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/donhang")
@CrossOrigin(origins = "*")
public class DonHangApiController {

    @Autowired
    private DonHangService donHangService;
    @Autowired
    private BanRepository banRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private NhanVienRepository nhanVienRepository;

    // ==================== LẤY DANH SÁCH ====================
    @GetMapping
    public List<DonHang> getAll() {
        List<DonHang> list = donHangService.getAllWithBans();
        list.forEach(dh -> {
            if (dh.getBan() != null) dh.setBan(ignoreBanRelations(dh.getBan()));
            if (dh.getBansPhu() != null)
                dh.setBansPhu(dh.getBansPhu().stream().map(this::ignoreBanRelations).toList());
        });
        return list;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonHang> getById(@PathVariable int id) {
        DonHang dh = donHangService.findById(id);
        if (dh == null) return ResponseEntity.notFound().build();
        if (dh.getBan() != null) dh.setBan(ignoreBanRelations(dh.getBan()));
        if (dh.getBansPhu() != null)
            dh.setBansPhu(dh.getBansPhu().stream().map(this::ignoreBanRelations).toList());
        return ResponseEntity.ok(dh);
    }

    // ==================== TẠO MỚI ====================
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DonHangDTO dto) {
        try {
            if (dto.getIdBans() == null || dto.getIdBans().isEmpty())
                return ResponseEntity.badRequest().body("Phải chọn ít nhất một bàn!");

            DonHang donHang = new DonHang();
            donHang.setMaDH(donHangService.generateMaDH());
            donHang.setNgayTao(new Date());
            donHang.setTongTien(dto.getTongTien());
            donHang.setTrangThai(TrangThaiDonHang.DANG_XU_LY);

            KhachHang kh = khachHangRepository.findById(dto.getIdKhachHang()).orElse(null);
            NhanVien nv = nhanVienRepository.findById(dto.getIdNhanVien()).orElse(null);
            donHang.setKhachHang(kh);
            donHang.setNhanVien(nv);

            Ban banChinh = banRepository.findById(dto.getIdBans().get(0))
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy bàn chính"));
            donHang.setBan(banChinh);

            if (dto.getIdBans().size() > 1) {
                List<Integer> idPhu = dto.getIdBans().subList(1, dto.getIdBans().size());
                List<Ban> bansPhu = banRepository.findAllById(idPhu);
                donHang.setBansPhu(bansPhu);
            }

            DonHang saved = donHangService.saveAndReturn(donHang);

            for (Integer idBan : dto.getIdBans()) {
                banRepository.findById(idBan).ifPresent(b -> {
                    b.setTrangThai(TrangThaiBan.DANG_SU_DUNG);
                    banRepository.save(b);
                });
            }

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Lỗi khi tạo đơn hàng: " + e.getMessage());
        }
    }

    // ==================== CẬP NHẬT (SỬA ĐƠN HÀNG) ====================
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody DonHangDTO dto) {
        DonHang old = donHangService.findById(id);
        if (old == null) return ResponseEntity.notFound().build();

        // Nếu đơn hàng đã thanh toán thì KHÔNG cho đổi bàn nữa
        if (old.getTrangThai() == DonHang.TrangThaiDonHang.DA_THANH_TOAN) {
            // Chỉ cho phép cập nhật thông tin KH, NV, tổng tiền (nếu cần)
            KhachHang kh = khachHangRepository.findById(dto.getIdKhachHang()).orElse(null);
            NhanVien nv = nhanVienRepository.findById(dto.getIdNhanVien()).orElse(null);
            old.setKhachHang(kh);
            old.setNhanVien(nv);
            old.setTongTien(dto.getTongTien());
            donHangService.save(old);
            return ResponseEntity.ok(old);
        }

        // Nếu đơn hàng đang xử lý thì xử lý bàn
        // Giải phóng các bàn cũ
        if (old.getBan() != null) {
            banRepository.findById(old.getBan().getId()).ifPresent(b -> {
                b.setTrangThai(Ban.TrangThaiBan.TRONG);
                banRepository.save(b);
            });
        }
        if (old.getBansPhu() != null) {
            for (Ban b : old.getBansPhu()) {
                b.setTrangThai(Ban.TrangThaiBan.TRONG);
                banRepository.save(b);
            }
        }

        // Cập nhật bàn mới
        if (dto.getIdBans() != null && !dto.getIdBans().isEmpty()) {
            Ban banChinh = banRepository.findById(dto.getIdBans().get(0))
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy bàn chính"));
            old.setBan(banChinh);

            if (dto.getIdBans().size() > 1) {
                List<Integer> idPhu = dto.getIdBans().subList(1, dto.getIdBans().size());
                List<Ban> bansPhu = banRepository.findAllById(idPhu);
                old.setBansPhu(bansPhu);
            } else {
                old.setBansPhu(null);
            }

            // Chỉ đánh dấu bàn sử dụng nếu đơn đang xử lý
            for (Integer idBan : dto.getIdBans()) {
                banRepository.findById(idBan).ifPresent(b -> {
                    b.setTrangThai(Ban.TrangThaiBan.DANG_SU_DUNG);
                    banRepository.save(b);
                });
            }
        }

        // Cập nhật các thông tin khác
        KhachHang kh = khachHangRepository.findById(dto.getIdKhachHang()).orElse(null);
        NhanVien nv = nhanVienRepository.findById(dto.getIdNhanVien()).orElse(null);
        old.setKhachHang(kh);
        old.setNhanVien(nv);
        old.setTongTien(dto.getTongTien());

        DonHang updated = donHangService.saveAndReturn(old);
        return ResponseEntity.ok(updated);
    }


    // ==================== XÓA ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        DonHang dh = donHangService.findById(id);
        if (dh == null) return ResponseEntity.notFound().build();

        if (dh.getBan() != null)
            banRepository.findById(dh.getBan().getId()).ifPresent(b -> {
                b.setTrangThai(TrangThaiBan.TRONG);
                banRepository.save(b);
            });

        if (dh.getBansPhu() != null)
            for (Ban b : dh.getBansPhu()) {
                b.setTrangThai(TrangThaiBan.TRONG);
                banRepository.save(b);
            }

        donHangService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ==================== THANH TOÁN ====================
    @PutMapping("/thanhtoan/{id}")
    public ResponseEntity<DonHang> thanhToan(@PathVariable int id) {
        DonHang dh = donHangService.findById(id);
        if (dh == null) return ResponseEntity.notFound().build();

        if (dh.getTrangThai() == TrangThaiDonHang.DANG_XU_LY) {
            dh.setTrangThai(TrangThaiDonHang.DA_THANH_TOAN);
            DonHang saved = donHangService.saveAndReturn(dh);

            if (saved.getBan() != null)
                banRepository.findById(saved.getBan().getId()).ifPresent(b -> {
                    b.setTrangThai(TrangThaiBan.TRONG);
                    banRepository.save(b);
                });

            if (saved.getBansPhu() != null)
                for (Ban b : saved.getBansPhu()) {
                    b.setTrangThai(TrangThaiBan.TRONG);
                    banRepository.save(b);
                }

            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.badRequest().body(null);
    }

    // ==================== HÀM HỖ TRỢ ====================
    private Ban ignoreBanRelations(Ban ban) {
        Ban copy = new Ban();
        copy.setId(ban.getId());
        copy.setTenBan(ban.getTenBan());
        copy.setTrangThai(ban.getTrangThai());
        return copy;
    }
}
