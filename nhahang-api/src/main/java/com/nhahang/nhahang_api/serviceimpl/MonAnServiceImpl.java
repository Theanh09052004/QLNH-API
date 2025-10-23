package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.MonAn;
import com.nhahang.nhahang_api.repository.MonAnRepository;
import com.nhahang.nhahang_api.service.MonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonAnServiceImpl implements MonAnService {

    @Autowired
    private MonAnRepository monAnRepository;

    @Override
    public List<MonAn> getAll() {
        return monAnRepository.findAll();
    }

    @Override
    public void save(MonAn monAn) {
        monAnRepository.save(monAn);
    }

    @Override
    public MonAn saveAndReturn(MonAn monAn) {
        return monAnRepository.save(monAn);
    }

    @Override
    public MonAn getById(int id) {
        return monAnRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        monAnRepository.deleteById(id);
    }

    @Override
    public boolean existsByTenMon(String tenMon) {
        return monAnRepository.existsByTenMon(tenMon);
    }

    @Override
    public List<MonAn> search(String keyword) {
        return monAnRepository.findByTenMonContainingIgnoreCase(keyword);
    }
}
