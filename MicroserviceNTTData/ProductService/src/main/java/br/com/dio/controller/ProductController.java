package br.com.dio.controller;

import br.com.dio.model.Product;
import br.com.dio.exceptions.ResourceNotFoundException;
import br.com.dio.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;

        repository.save(new Product("Tablet", "10 polegadas 64GB", 1200.0));
        repository.save(new Product("Smartwatch", "Monitor Cardíaco 4GB", 800.0));
        repository.save(new Product("Fone de Ouvido", "Bluetooth Noise Cancelling", 350.0));
        repository.save(new Product("Monitor", "27 polegadas 4K", 2000.0));
        repository.save(new Product("Teclado Mecânico", "RGB Switch Blue", 400.0));
    }

    @GetMapping
    public List<Product> listProducts(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product searchProductById(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct){
        Product savedProduct = repository.save(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product productUpdated){
        return repository.findById(id)
                .map(product -> {
                    if (productUpdated.getName() != null) {
                        product.setName(productUpdated.getName());
                    }
                    if (productUpdated.getDescription() != null) {
                        product.setDescription(productUpdated.getDescription());
                    }
                    if (productUpdated.getPrice() > 0) {
                        product.setPrice(productUpdated.getPrice());
                    }

                    return repository.save(product);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Produto com o id: " + id + ", não encontrado " ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    /*
    @GetMapping("/teste")
    public String teste(){
        return "Serviço de produtos funcionando";
    }

    public String listProducts(){
        return "Lista de produtos: [Produto 1, Produto 1]";
    }
    */


}
