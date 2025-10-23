package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.LoaiMonAn;
import com.nhahang.nhahang_api.service.LoaiMonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loaimonan")
public class LoaiMonAnApiController {

    @Autowired
    private LoaiMonAnService service;

    @GetMapping
    public ResponseEntity<List<LoaiMonAn>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<LoaiMonAn> loaiMonAn = service.getById(id);
        return loaiMonAn.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LoaiMonAn loaiMonAn) {
        if (service.getByTenLoai(loaiMonAn.getTenLoai()).isPresent()) {
            return ResponseEntity.badRequest().body("Tên loại món ăn đã tồn tại!");
        }
        return ResponseEntity.ok(service.saveAndReturn(loaiMonAn));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody LoaiMonAn loaiMonAn) {
        Optional<LoaiMonAn> existingOpt = service.getById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LoaiMonAn existing = existingOpt.get();

        if (!existing.getTenLoai().equals(loaiMonAn.getTenLoai()) &&
            service.getByTenLoai(loaiMonAn.getTenLoai()).isPresent()) {
            return ResponseEntity.badRequest().body("Tên loại món ăn đã tồn tại!");
        }

        loaiMonAn.setId(id);
        return ResponseEntity.ok(service.saveAndReturn(loaiMonAn));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<LoaiMonAn>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.search(keyword));
    }
}
