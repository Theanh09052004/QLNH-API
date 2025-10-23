package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.NhapHang;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NhapHangRepository extends JpaRepository<NhapHang, Integer> {
     @Query("SELECT n.ngayNhap, SUM(n.soLuong * n.giaNhap) FROM NhapHang n GROUP BY n.ngayNhap ORDER BY n.ngayNhap")
    List<Object[]> thongKeNhapHangTheoNgay();

    @Query("SELECT CONCAT(FUNCTION('MONTH', n.ngayNhap), '-', FUNCTION('YEAR', n.ngayNhap)), SUM(n.soLuong * n.giaNhap) FROM NhapHang n GROUP BY FUNCTION('YEAR', n.ngayNhap), FUNCTION('MONTH', n.ngayNhap) ORDER BY FUNCTION('YEAR', n.ngayNhap), FUNCTION('MONTH', n.ngayNhap)")
    List<Object[]> thongKeNhapHangTheoThang();

    @Query("SELECT FUNCTION('YEAR', n.ngayNhap), SUM(n.soLuong * n.giaNhap) FROM NhapHang n GROUP BY FUNCTION('YEAR', n.ngayNhap) ORDER BY FUNCTION('YEAR', n.ngayNhap)")
    List<Object[]> thongKeNhapHangTheoNam();
}
