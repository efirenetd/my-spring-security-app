package org.efire.net.controller;

import org.efire.net.entity.Coupon;
import org.efire.net.repository.CouponRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {
    private CouponRepository couponRepository;

    public CouponController(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        var save = couponRepository.save(coupon);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable("code") String code) {
        var byCode = couponRepository.findByCode(code);
        return ResponseEntity.of(byCode);
    }
}
