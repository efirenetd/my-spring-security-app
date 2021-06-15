package org.efire.net.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "product-service")
@Getter @Setter
public class ProductProperties {
    private String couponUrl;
}
