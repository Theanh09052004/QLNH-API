package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.LoaiMonAn;
import com.nhahang.nhahang_api.repository.LoaiMonAnRepository;
import com.nhahang.nhahang_api.service.LoaiMonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiMonAnServiceImpl implements LoaiMonAnService {

    @Autowired
    private LoaiMonAnRepository repository;

    @Override
    public List<LoaiMonAn> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(LoaiMonAn loaiMonAn) {
        repository.save(loaiMonAn);
    }

    @Override
    public LoaiMonAn saveAndReturn(LoaiMonAn loaiMonAn) {
        return repository.save(loaiMonAn);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<LoaiMonAn> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Optional<LoaiMonAn> getByTenLoai(String tenLoai) {
        return repository.findByTenLoai(tenLoai);
    }

    @Override
    public List<LoaiMonAn> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return repository.findAll();
        }
        return repository.findByTenLoaiContainingIgnoreCase(keyword);
    }
}
