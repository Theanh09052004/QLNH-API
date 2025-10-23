package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.Ban;
import com.nhahang.nhahang_api.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ban")
@CrossOrigin(origins = "*")
public class BanApiController {

    @Autowired
    private BanService service;

    @GetMapping
    public List<Ban> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Ban getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public Ban add(@RequestBody Ban ban) {
        return service.saveAndReturn(ban);
    }

    @PutMapping("/{id}")
    public Ban update(@PathVariable int id, @RequestBody Ban ban) {
        ban.setId(id);
        return service.saveAndReturn(ban);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<Ban> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @PutMapping("/toggle/{id}")
    public Ban toggleTrangThai(@PathVariable int id) {
        Ban b = service.getById(id);
        if (b != null) {
            switch (b.getTrangThai()) {
                case TRONG -> b.setTrangThai(Ban.TrangThaiBan.DANG_SU_DUNG);
                case DANG_SU_DUNG -> b.setTrangThai(Ban.TrangThaiBan.TRONG);
            }
            return service.saveAndReturn(b);
        }
        return null;
    }
    @GetMapping("/trong")
    public List<Ban> getBanTrong() {
        return service.getAll().stream()
                .filter(b -> b.getTrangThai() == Ban.TrangThaiBan.TRONG)
                .toList();
    }

}
