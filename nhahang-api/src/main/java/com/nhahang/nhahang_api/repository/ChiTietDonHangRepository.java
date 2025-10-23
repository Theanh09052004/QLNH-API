package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.ChiTietDonHang;
import com.nhahang.nhahang_api.model.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Integer> {
    List<ChiTietDonHang> findByDonHang(DonHang donHang);
    // ✅ Thống kê doanh thu theo ngày — chỉ tính đơn hàng đã thanh toán
    @Query("""
        SELECT c.donHang.ngayTao, SUM(c.soLuong * c.donGia)
        FROM ChiTietDonHang c
        WHERE c.donHang.trangThai = com.nhahang.nhahang_api.model.DonHang$TrangThaiDonHang.DA_THANH_TOAN
        GROUP BY c.donHang.ngayTao
        ORDER BY c.donHang.ngayTao
    """)
    List<Object[]> thongKeDoanhThuTheoNgay();

    // ✅ Thống kê doanh thu theo tháng — chỉ tính đơn hàng đã thanh toán
    @Query("""
        SELECT CONCAT(FUNCTION('MONTH', c.donHang.ngayTao), '-', FUNCTION('YEAR', c.donHang.ngayTao)),
               SUM(c.soLuong * c.donGia)
        FROM ChiTietDonHang c
        WHERE c.donHang.trangThai = com.nhahang.nhahang_api.model.DonHang$TrangThaiDonHang.DA_THANH_TOAN
        GROUP BY FUNCTION('YEAR', c.donHang.ngayTao), FUNCTION('MONTH', c.donHang.ngayTao)
        ORDER BY FUNCTION('YEAR', c.donHang.ngayTao), FUNCTION('MONTH', c.donHang.ngayTao)
    """)
    List<Object[]> thongKeDoanhThuTheoThang();

    // ✅ Thống kê doanh thu theo năm — chỉ tính đơn hàng đã thanh toán
    @Query("""
        SELECT FUNCTION('YEAR', c.donHang.ngayTao), SUM(c.soLuong * c.donGia)
        FROM ChiTietDonHang c
        WHERE c.donHang.trangThai = com.nhahang.nhahang_api.model.DonHang$TrangThaiDonHang.DA_THANH_TOAN
        GROUP BY FUNCTION('YEAR', c.donHang.ngayTao)
        ORDER BY FUNCTION('YEAR', c.donHang.ngayTao)
    """)
    List<Object[]> thongKeDoanhThuTheoNam();
}
