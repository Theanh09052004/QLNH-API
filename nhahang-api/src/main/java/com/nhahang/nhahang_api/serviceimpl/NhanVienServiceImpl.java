package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.NhanVien;
import com.nhahang.nhahang_api.repository.NhanVienRepository;
import com.nhahang.nhahang_api.service.NhanVienService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository repo;

    @Override
    public List<NhanVien> getAll() {
        return repo.findAll();
    }

    @Override
    public NhanVien getById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void save(NhanVien nv) {
        repo.save(nv);
    }

    @Override
    public NhanVien saveAndReturn(NhanVien nv) {
        return repo.save(nv);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsByMaNV(String maNV) {
        return repo.existsByMaNV(maNV);
    }

    @Override
    public boolean existsBySdt(String sdt) {
        return repo.existsBySdt(sdt);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }


    @Override
    public List<NhanVien> search(String keyword) {
        return repo.findByMaNVContainingIgnoreCaseOrTenNVContainingIgnoreCaseOrChucVuContainingIgnoreCase(keyword, keyword, keyword);
    }
}
