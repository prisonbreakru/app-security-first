package uz.pdp.appsecurityfirst.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appsecurityfirst.entity.Product;
import uz.pdp.appsecurityfirst.repository.ProductRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //Manager, Director
//    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getProduct(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    //Director
//    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product){
        return ResponseEntity.ok(productRepository.save(product));
    }

    //Director
//    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id,@RequestBody Product product){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product editProduct = optionalProduct.get();
            editProduct.setName(product.getName());
            productRepository.save(editProduct);
            return ResponseEntity.ok(editProduct);
        }
        return ResponseEntity.notFound().build();
    }

    //Director
//    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //Manager, Director,User
//    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getProduct(@PathVariable Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
//        if (optionalProduct.isPresent()){
//            return ResponseEntity.ok(optionalProduct.get());
//        }
//        return ResponseEntity.notFound().build();
        return ResponseEntity.status(optionalProduct.isPresent()?200:404).body(optionalProduct.orElse(null));
    }

}
