package com.nhahang.nhahang_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ctdonhang")
public class ChiTietDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idDonHang")
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "idMonAn")
    private MonAn monAn;

    private int soLuong;
    private double donGia;

    // Constructors
    public ChiTietDonHang() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public DonHang getDonHang() { return donHang; }
    public void setDonHang(DonHang donHang) { this.donHang = donHang; }

    public MonAn getMonAn() { return monAn; }
    public void setMonAn(MonAn monAn) { this.monAn = monAn; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public double getThanhTien() {
        return soLuong * donGia;
    }
}
