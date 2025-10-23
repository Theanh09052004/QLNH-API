package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.model.User;
import com.nhahang.nhahang_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> userOpt = userService.login(username, password);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            response.put("hoTen", user.getHoTen());
            response.put("email", user.getEmail());
            response.put("role", user.getRole().toString());
            response.put("message", "Đăng nhập thành công");

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(404).body(Map.of("message", "Sai tài khoản hoặc mật khẩu"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String hoTen = body.get("hoTen");
        String email = body.get("email");

        if (userService.findByUsername(username).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "Username đã tồn tại"));
        }
        if (userService.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "Email đã được sử dụng"));
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setHoTen(hoTen);
        user.setEmail(email);
        user.setRole(User.Role.NHANVIEN); // Mặc định là NHANVIEN

        userService.save(user);
        return ResponseEntity.ok(Map.of("message", "Đăng ký thành công"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            // return ResponseEntity.ok(Map.of(
            return ResponseEntity.status(200).body(Map.of(
                    "message", "Email hợp lệ. Tiếp tục đặt lại mật khẩu.",
                    "email", email));
        }

        return ResponseEntity.status(404).body(Map.of("message", "Email không tồn tại trong hệ thống"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String newPassword = body.get("newPassword");

        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(newPassword);
            userService.save(user);
            return ResponseEntity.ok(Map.of("message", "Cập nhật mật khẩu thành công"));
        }

        return ResponseEntity.status(404).body(Map.of("message", "Không tìm thấy tài khoản với email đã nhập"));
    }

    // ✅ Thêm API kiểm tra username tồn tại
    @GetMapping("/check-username")
    public ResponseEntity<User> checkUsername(@RequestParam String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Thêm API kiểm tra email tồn tại
    @GetMapping("/check-email")
    public ResponseEntity<User> checkEmail(@RequestParam String email) {
        Optional<User> userOpt = userService.findByEmail(email);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
