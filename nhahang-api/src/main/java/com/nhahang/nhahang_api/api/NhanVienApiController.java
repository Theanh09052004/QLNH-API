package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.NhaCungCap;
import com.nhahang.nhahang_api.model.NhanVien;
import com.nhahang.nhahang_api.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienApiController {

    @Autowired
    private NhanVienService service;

    @GetMapping
    public List<NhanVien> getAll() {
        return service.getAll();
    }
    
    @GetMapping("/search")
    public List<NhanVien> search(@RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return service.getAll();
        }
        return service.search(keyword);
    }



    @GetMapping("/{id}")
    public NhanVien getById(@PathVariable int id) {
        return service.getById(id);
    }


    @PostMapping
    public NhanVien save(@RequestBody NhanVien nv) {
        nv.setId(0); // đảm bảo insert
        return service.saveAndReturn(nv);
    }

    @PutMapping("/{id}")
    public NhanVien update(@PathVariable int id, @RequestBody NhanVien nv) {
        nv.setId(id);
        return service.saveAndReturn(nv);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/exists/maNV")
    public boolean existsMaNV(@RequestParam String value) {
        return service.existsByMaNV(value);
    }

    @GetMapping("/exists/sdt")
    public boolean existsSdt(@RequestParam String value) {
        return service.existsBySdt(value);
    }

    @GetMapping("/exists/email")
    public boolean existsEmail(@RequestParam String value) {
        return service.existsByEmail(value);
    }

}
