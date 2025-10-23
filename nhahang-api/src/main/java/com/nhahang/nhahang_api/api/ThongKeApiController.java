package com.nhahang.nhahang_api.api;

import com.nhahang.nhahang_api.dto.ThongKeDTO;
import com.nhahang.nhahang_api.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/thongke")
@CrossOrigin(origins = "*")
public class ThongKeApiController {

    @Autowired
    private ThongKeService thongKeService;

    @GetMapping("/ngay")
    public List<ThongKeDTO> thongKeNgay(@RequestParam(required = false) String keyword) {
        return thongKeService.thongKeTheoNgay(keyword);
    }

    @GetMapping("/thang")
    public List<ThongKeDTO> thongKeThang(@RequestParam(required = false) String keyword) {
        return thongKeService.thongKeTheoThang(keyword);
    }

    @GetMapping("/nam")
    public List<ThongKeDTO> thongKeNam(@RequestParam(required = false) String keyword) {
        return thongKeService.thongKeTheoNam(keyword);
    }
}
