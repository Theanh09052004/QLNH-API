package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.User;
import com.nhahang.nhahang_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taikhoan")
public class TaiKhoanApiController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String keyword) {
        return userService.search(keyword);
    }

    @PutMapping("/role/{id}")
    public User updateRole(@PathVariable int id) {
        return userService.changeRole(id);
    }
}
