package com.nhahang.nhahang_api.serviceimpl;

import com.nhahang.nhahang_api.model.Ban;
import com.nhahang.nhahang_api.repository.BanRepository;
import com.nhahang.nhahang_api.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanServiceImpl implements BanService {

    @Autowired
    private BanRepository repo;

    @Override
    public List<Ban> getAll() {
        return repo.findAll();
    }

    @Override
    public void save(Ban ban) {
        repo.save(ban);
    }

    @Override
    public Ban saveAndReturn(Ban ban) {
        return repo.save(ban);
    }

    @Override
    public Ban getById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsByTenBan(String tenBan) {
        return repo.existsByTenBan(tenBan);
    }

    @Override
    public List<Ban> search(String keyword) {
        return repo.findByTenBanContainingIgnoreCase(keyword);
    }
}
