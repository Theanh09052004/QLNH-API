package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.MonAn;
import com.nhahang.nhahang_api.service.MonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monan")
public class MonAnApiController {

    @Autowired
    private MonAnService monAnService;

    // Lấy danh sách tất cả món ăn
    @GetMapping
    public List<MonAn> getAll() {
        return monAnService.getAll();
    }

    // Tìm kiếm theo từ khóa
    @GetMapping("/search")
    public List<MonAn> search(@RequestParam("keyword") String keyword) {
        return monAnService.search(keyword);
    }

    // Lấy theo ID
    @GetMapping("/{id}")
    public ResponseEntity<MonAn> getById(@PathVariable int id) {
        MonAn monAn = monAnService.getById(id);
        if (monAn == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(monAn);
    }

    // Thêm món ăn
    @PostMapping
    public ResponseEntity<?> create(@RequestBody MonAn monAn) {
        if (monAnService.existsByTenMon(monAn.getTenMon())) {
            return ResponseEntity.badRequest().body("Tên món ăn đã tồn tại!");
        }
        MonAn saved = monAnService.saveAndReturn(monAn);
        return ResponseEntity.ok(saved);
    }

    // Cập nhật món ăn
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody MonAn monAn) {
        MonAn existing = monAnService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.getTenMon().equals(monAn.getTenMon())
                && monAnService.existsByTenMon(monAn.getTenMon())) {
            return ResponseEntity.badRequest().body("Tên món ăn đã tồn tại!");
        }

        monAn.setId(id);
        MonAn updated = monAnService.saveAndReturn(monAn);
        return ResponseEntity.ok(updated);
    }

    // Xóa món ăn
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        MonAn existing = monAnService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        monAnService.delete(id);
        return ResponseEntity.ok().build();
    }
}
