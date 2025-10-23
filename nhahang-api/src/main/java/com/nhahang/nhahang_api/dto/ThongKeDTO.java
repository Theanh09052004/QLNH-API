package com.nhahang.nhahang_api.dto;

public class ThongKeDTO {
    private String thoiGian;      // ex: "2025-10-16" or "10-2025" or "2025"
    private double tongDoanhThu;
    private double tongNhapHang;
    private double loiNhuan;

    public ThongKeDTO() {}

    public ThongKeDTO(String thoiGian, double tongDoanhThu, double tongNhapHang) {
        this.thoiGian = thoiGian;
        this.tongDoanhThu = tongDoanhThu;
        this.tongNhapHang = tongNhapHang;
        this.loiNhuan = tongDoanhThu - tongNhapHang;
    }

    public String getThoiGian() { return thoiGian; }
    public void setThoiGian(String thoiGian) { this.thoiGian = thoiGian; }
    public double getTongDoanhThu() { return tongDoanhThu; }
    public void setTongDoanhThu(double tongDoanhThu) { this.tongDoanhThu = tongDoanhThu; }
    public double getTongNhapHang() { return tongNhapHang; }
    public void setTongNhapHang(double tongNhapHang) { this.tongNhapHang = tongNhapHang; }
    public double getLoiNhuan() { return loiNhuan; }
    public void setLoiNhuan(double loiNhuan) { this.loiNhuan = loiNhuan; }
}
