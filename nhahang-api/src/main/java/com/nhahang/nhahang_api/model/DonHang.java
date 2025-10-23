package com.nhahang.nhahang_api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "donhang")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // tránh lỗi lazy khi convert JSON
public class DonHang {

    public enum TrangThaiDonHang {
        DANG_XU_LY,
        DA_THANH_TOAN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String maDH;

    // ===== Bàn chính (giữ nguyên code cũ) =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBan", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ban ban;

    // ===== Bàn phụ (mở rộng thêm để hỗ trợ nhiều bàn) =====
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "donhang_ban",
        joinColumns = @JoinColumn(name = "idDonHang"),
        inverseJoinColumns = @JoinColumn(name = "idBan")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Ban> bansPhu;

    // ===== Khách hàng =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKhachHang", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private KhachHang khachHang;

    // ===== Nhân viên =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNhanVien", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private NhanVien nhanVien;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngayTao")
    private Date ngayTao;

    @Column(name = "tongTien")
    private double tongTien;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThai")
    private TrangThaiDonHang trangThai;

    // ===== Constructor =====
    public DonHang() {
        this.ban = new Ban();
        this.khachHang = new KhachHang();
        this.nhanVien = new NhanVien();
    }

    // ===== Getter & Setter =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public List<Ban> getBansPhu() {
        return bansPhu;
    }

    public void setBansPhu(List<Ban> bansPhu) {
        this.bansPhu = bansPhu;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public TrangThaiDonHang getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiDonHang trangThai) {
        this.trangThai = trangThai;
    }
}
