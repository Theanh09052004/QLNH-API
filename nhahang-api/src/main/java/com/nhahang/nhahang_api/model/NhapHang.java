package com.nhahang.nhahang_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nhaphang")
public class NhapHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idMonAn")
    private MonAn monAn;

    private int soLuong;
    private double giaNhap;
    private LocalDate ngayNhap;

    public NhapHang() {}

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public MonAn getMonAn() { return monAn; }
    public void setMonAn(MonAn monAn) { this.monAn = monAn; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }

    public LocalDate getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(LocalDate ngayNhap) { this.ngayNhap = ngayNhap; }

    public double getTongTien() {
        return soLuong * giaNhap;
    }
}
