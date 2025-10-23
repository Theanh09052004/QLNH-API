package com.nhahang.nhahang_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "khachhang")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Mã khách hàng không được để trống")
    @Column(unique = true)
    private String maKH;

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String tenKH;

    @NotBlank(message = "SĐT không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải đúng 10 chữ số")
    @Column(unique = true)
    private String sdt;

    @Pattern(regexp = "^[\\w.%+-]+@gmail\\.com$", message = "Email phải có định dạng đúng và kết thúc bằng @gmail.com")
    @NotBlank(message = "Email không được để trống")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    public KhachHang() {
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        KhachHang kh = (KhachHang) o;
        return id == kh.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
