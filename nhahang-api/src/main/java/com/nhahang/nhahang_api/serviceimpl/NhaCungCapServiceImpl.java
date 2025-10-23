package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.NhaCungCap;
import com.nhahang.nhahang_api.repository.NhaCungCapRepository;
import com.nhahang.nhahang_api.service.NhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {

    @Autowired
    private NhaCungCapRepository repository;

    @Override
    public List<NhaCungCap> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(NhaCungCap nhaCungCap) {
        repository.save(nhaCungCap); // ❗ dùng cho Thymeleaf (web)
    }

    @Override
    public NhaCungCap getById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByMaNCC(String maNCC) {
        return repository.existsByMaNCC(maNCC);
    }

    @Override
    public List<NhaCungCap> search(String keyword) {
        return repository.findByTenNCCContainingIgnoreCaseOrMaNCCContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public boolean isMaNCCDuplicated(String maNCC, Integer excludeId) {
        return excludeId == null ?
            repository.existsByMaNCC(maNCC) :
            repository.existsByMaNCCAndIdNot(maNCC, excludeId);
    }

    @Override
    public boolean isEmailDuplicated(String email, Integer excludeId) {
        return excludeId == null ?
            repository.existsByEmail(email) :
            repository.existsByEmailAndIdNot(email, excludeId);
    }

    @Override
    public boolean isPhoneDuplicated(String phone, Integer excludeId) {
        return excludeId == null ?
            repository.existsBySoDienThoai(phone) :
            repository.existsBySoDienThoaiAndIdNot(phone, excludeId);
    }

    @Override
    public NhaCungCap saveAndReturn(NhaCungCap ncc) {
        Integer id = (ncc.getId() == 0) ? null : ncc.getId();

        if (isMaNCCDuplicated(ncc.getMaNCC(), id))
            throw new IllegalArgumentException("Mã nhà cung cấp đã tồn tại!");

        if (isEmailDuplicated(ncc.getEmail(), id))
            throw new IllegalArgumentException("Email đã tồn tại!");

        if (isPhoneDuplicated(ncc.getSoDienThoai(), id))
            throw new IllegalArgumentException("Số điện thoại đã tồn tại!");

        return repository.save(ncc);
    }
}
