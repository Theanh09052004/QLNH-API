package com.nhahang.nhahang_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "ban")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Tên bàn không được để trống")
    @Column(unique = true)
    private String tenBan;

    @Enumerated(EnumType.STRING)
    private TrangThaiBan trangThai = TrangThaiBan.TRONG;

    private String ghiChu;

    public enum TrangThaiBan {
        TRONG, DANG_SU_DUNG
    }

        public Ban() {
        // constructor mặc định để tránh lỗi khi binding form
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTenBan() { return tenBan; }
    public void setTenBan(String tenBan) { this.tenBan = tenBan; }
    public TrangThaiBan getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiBan trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ban ban = (Ban) o;
    return id == ban.id;
}

@Override
public int hashCode() {
    return Integer.hashCode(id);
}

}
