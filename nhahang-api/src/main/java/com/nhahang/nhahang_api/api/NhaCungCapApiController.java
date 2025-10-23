package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.NhaCungCap;
import com.nhahang.nhahang_api.service.NhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ncc")
public class NhaCungCapApiController {

    @Autowired
    private NhaCungCapService service;

    @GetMapping
    public List<NhaCungCap> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<NhaCungCap> search(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return service.getAll(); // nếu không có từ khóa → trả về toàn bộ
        }
        return service.search(keyword);
    }

    @GetMapping("/{id}")
    public NhaCungCap getById(@PathVariable int id) {
        return service.getById(id);
    }
    
    @PostMapping
    public ResponseEntity<?> save(@RequestBody NhaCungCap ncc) {
        try {
            return ResponseEntity.ok(service.saveAndReturn(ncc));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody NhaCungCap ncc) {
        ncc.setId(id);
        try {
            return ResponseEntity.ok(service.saveAndReturn(ncc));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
