package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.KhachHang;
import com.nhahang.nhahang_api.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khachhang")
@CrossOrigin(origins = "*")
public class KhachHangApiController {

    @Autowired
    private KhachHangService service;

    @GetMapping
    public List<KhachHang> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public KhachHang getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public KhachHang add(@RequestBody KhachHang kh) {
        return service.saveAndReturn(kh);
    }

    @PutMapping("/{id}")
    public KhachHang update(@PathVariable int id, @RequestBody KhachHang kh) {
        kh.setId(id);
        return service.saveAndReturn(kh);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<KhachHang> search(@RequestParam String keyword) {
        return service.search(keyword);
    }
}
