package org.efire.net.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "coupon", schema = "mydb")
@Entity
public class Coupon {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "discount", precision = 8, scale = 3)
    private BigDecimal discount;

    @Column(name = "exp_date", length = 100)
    private String expDate;

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}