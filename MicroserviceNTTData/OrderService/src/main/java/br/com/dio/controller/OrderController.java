package br.com.dio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

    @GetMapping("/teste")
    public String test(){
        return "serviço de pedidos funcionando!";
    }

    @GetMapping
    public String listOrder(){
        return "Lista de pedidos: [Pedido 1001, Pedido 1002]";
    }
}
