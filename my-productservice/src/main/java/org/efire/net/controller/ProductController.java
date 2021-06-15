package org.efire.net.controller;

import org.efire.net.common.ProductProperties;
import org.efire.net.dto.CouponDTO;
import org.efire.net.entity.Product;
import org.efire.net.repository.ProductRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductRepository productRepository;
    private RestTemplate restTemplate;
    private ProductProperties productProperties;

    public ProductController(ProductRepository productRepository,
                             RestTemplate restTemplate, ProductProperties
                                     productProperties) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
        this.productProperties = productProperties;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        var uri = UriComponentsBuilder.newInstance()
                .fromHttpUrl(productProperties.getCouponUrl())
                .path("/{code}")
                .buildAndExpand(product.getCouponCode()).toUri();

        //Hard-coded for testing
        //String auth = "doug@bailey.com" + ":" + "doug";
        String auth = "john@ferguson.com" + ":" + "john";
        byte [] authentication = auth.getBytes();
        var baseCredential = new String(Base64Utils.encode(authentication));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Basic "+ baseCredential);
        var requestEntity = new HttpEntity<String>(requestHeaders);

        //var couponResponse = restTemplate.getForEntity(uri, CouponDTO.class);
        var couponResponse = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, CouponDTO.class);
        if (couponResponse.getStatusCode().is2xxSuccessful()) {
            var couponDTO = couponResponse.getBody();
            product.setPrice(product.getPrice().subtract(couponDTO.getDiscount()));
            var save = productRepository.save(product);
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> retrieveAll() {
        var all = productRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> retrieveById(@PathVariable("id") Long id) {
        var productOptional = productRepository.findById(id);
        return ResponseEntity.of(productOptional);
    }
}
