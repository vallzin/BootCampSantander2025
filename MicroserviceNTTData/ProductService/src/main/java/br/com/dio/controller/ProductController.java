package br.com.dio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @GetMapping("/teste")
    public String teste(){
        return "Serviço de produtos funcionando";
    }

    public String listProducts(){
        return "Lista de produtos: [Produto 1, Produto 1]";
    }
}
