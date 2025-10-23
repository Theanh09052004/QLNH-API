package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.MonAn;
import com.nhahang.nhahang_api.model.NhapHang;
import com.nhahang.nhahang_api.service.MonAnService;
import com.nhahang.nhahang_api.service.NhapHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/nhaphang")
@CrossOrigin(origins = "*")
public class NhapHangApiController {

    @Autowired
    private NhapHangService nhapHangService;

    @Autowired
    private MonAnService monAnService;

    // ✅ Lấy toàn bộ danh sách nhập hàng
    @GetMapping
    public List<NhapHang> getAll() {
        return nhapHangService.getAll();
    }

    // ✅ Lấy chi tiết 1 phiếu nhập
    @GetMapping("/{id}")
    public ResponseEntity<NhapHang> getById(@PathVariable int id) {
        NhapHang nhap = nhapHangService.getById(id);
        if (nhap == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nhap);
    }

    // ✅ Tạo mới phiếu nhập + cập nhật số lượng món ăn
    @PostMapping
    public ResponseEntity<?> create(@RequestBody NhapHang nhapHang) {
        try {
            // 1️⃣ Kiểm tra món ăn có tồn tại không
            MonAn monAn = monAnService.getById(nhapHang.getMonAn().getId());
            if (monAn == null) {
                return ResponseEntity.badRequest().body("Món ăn không tồn tại!");
            }

            // 2️⃣ Cộng thêm số lượng vào tồn kho
            int soLuongMoi = monAn.getSoLuong() + nhapHang.getSoLuong();
            monAn.setSoLuong(soLuongMoi);
            monAnService.save(monAn);

            // 3️⃣ Lưu phiếu nhập
            nhapHang.setMonAn(monAn);
            nhapHang.setNgayNhap(LocalDate.now());
            NhapHang saved = nhapHangService.saveAndReturn(nhapHang);

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Lỗi khi nhập hàng: " + e.getMessage());
        }
    }

    // ✅ Xóa phiếu nhập
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            NhapHang nhap = nhapHangService.getById(id);
            if (nhap == null) {
                return ResponseEntity.badRequest().body("Phiếu nhập không tồn tại!");
            }

            MonAn monAn = nhap.getMonAn();
            if (monAn != null) {
                // ⚠️ Trừ lại số lượng đã nhập
                int soLuongMoi = monAn.getSoLuong() - nhap.getSoLuong();
                if (soLuongMoi < 0) soLuongMoi = 0;
                monAn.setSoLuong(soLuongMoi);
                monAnService.save(monAn);
            }

            nhapHangService.delete(id);
            return ResponseEntity.ok().body("Đã xóa phiếu nhập và cập nhật tồn kho!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Không thể xóa phiếu nhập!");
        }
    }

}
