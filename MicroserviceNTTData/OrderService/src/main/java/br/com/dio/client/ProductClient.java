package br.com.dio.client;

import br.com.dio.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="product-service")
public interface ProductClient {

    @GetMapping("/produtos/{id}")
    Product getProductById(@PathVariable Long id);
}
