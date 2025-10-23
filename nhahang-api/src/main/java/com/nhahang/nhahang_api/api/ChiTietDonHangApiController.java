package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.Ban;
import com.nhahang.nhahang_api.model.ChiTietDonHang;
import com.nhahang.nhahang_api.model.DonHang;
import com.nhahang.nhahang_api.repository.BanRepository;
import com.nhahang.nhahang_api.service.ChiTietDonHangService;
import com.nhahang.nhahang_api.service.DonHangService;
import com.nhahang.nhahang_api.service.MonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ctdonhang")
public class ChiTietDonHangApiController {

    @Autowired
    private ChiTietDonHangService chiTietService;

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private MonAnService monAnService;

    @Autowired
    private BanRepository banRepository;

    @GetMapping
    public List<ChiTietDonHang> getAll() {
        return chiTietService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietDonHang> getById(@PathVariable int id) {
        ChiTietDonHang ct = chiTietService.getById(id);
        if (ct == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ct);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ChiTietDonHang ct) {
        try {
            ChiTietDonHang saved = chiTietService.saveAndReturn(ct);
            capNhatTongTien(saved.getDonHang());
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ChiTietDonHang ctUpdate) {
        ChiTietDonHang old = chiTietService.getById(id);
        if (old == null) return ResponseEntity.notFound().build();

        ctUpdate.setId(id);
        ChiTietDonHang saved = chiTietService.saveAndReturn(ctUpdate);
        capNhatTongTien(saved.getDonHang());
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ChiTietDonHang ct = chiTietService.getById(id);
        if (ct == null) return ResponseEntity.notFound().build();

        DonHang dh = ct.getDonHang();
        chiTietService.deleteById(id);
        capNhatTongTien(dh);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-donhang/{idDonHang}")
    public List<ChiTietDonHang> getByDonHang(@PathVariable int idDonHang) {
        DonHang dh = donHangService.findById(idDonHang);
        return chiTietService.getByDonHang(dh);
    }

    private void capNhatTongTien(DonHang donHang) {
        double tongTien = chiTietService.tinhTongTien(donHang);

        // Load lại DonHang từ DB
        DonHang donHangFromDb = donHangService.findById(donHang.getId());

        // Gán lại tổng tiền mới
        donHangFromDb.setTongTien(tongTien);

        // Load lại Ban từ DB nếu có
        if (donHang.getBan() != null && donHang.getBan().getId() > 0) {
            Ban ban = banRepository.findById(donHang.getBan().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy bàn với ID = " + donHang.getBan().getId()));
            donHangFromDb.setBan(ban);
        }

        // Lưu lại đơn hàng đã cập nhật tổng tiền
        donHangService.save(donHangFromDb);
    }

    
}
