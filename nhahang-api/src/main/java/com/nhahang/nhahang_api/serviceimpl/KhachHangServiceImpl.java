package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.KhachHang;
import com.nhahang.nhahang_api.repository.KhachHangRepository;
import com.nhahang.nhahang_api.service.KhachHangService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository repo;

    @Override
    public List<KhachHang> getAll() {
        return repo.findAll();
        //return repo.findAll().stream().limit(4).toList();
    }

    @Override
    public KhachHang getById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void save(KhachHang kh) {
        repo.save(kh);
    }

    @Override
    public KhachHang saveAndReturn(KhachHang kh) {
        return repo.save(kh);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsByMaKH(String maKH) {
        return repo.existsByMaKH(maKH);
    }

    @Override
    public boolean existsBySdt(String sdt) {
        return repo.existsBySdt(sdt);
    }

    @Override
    public List<KhachHang> search(String keyword) {
        return repo.findByTenKHContainingIgnoreCaseOrSdtContaining(keyword, keyword);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

}
