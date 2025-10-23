package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.NhapHang;
import com.nhahang.nhahang_api.repository.NhapHangRepository;
import com.nhahang.nhahang_api.service.NhapHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhapHangServiceImpl implements NhapHangService {

    @Autowired
    private NhapHangRepository nhapHangRepository;

    @Override
    public List<NhapHang> getAll() {
        return nhapHangRepository.findAll();
    }

    @Override
    public NhapHang getById(int id) {
        return nhapHangRepository.findById(id).orElse(null);
    }

    // ✅ Trả về phiếu nhập sau khi lưu
    @Override
    public NhapHang saveAndReturn(NhapHang nhapHang) {
        return nhapHangRepository.save(nhapHang);
    }

    // ✅ Nếu chỉ muốn lưu mà không cần trả kết quả
    @Override
    public NhapHang save(NhapHang nhapHang) {
        return nhapHangRepository.save(nhapHang);
    }

    @Override
    public void delete(int id) {
        nhapHangRepository.deleteById(id);
    }
}
