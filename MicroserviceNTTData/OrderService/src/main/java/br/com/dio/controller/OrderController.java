package br.com.dio.controller;


import br.com.dio.model.Order;
import br.com.dio.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> makeOrder(@RequestBody Order order){
        try {
            if (order.getCliente() == null || order.getCliente().isBlank()){
                return ResponseEntity
                        .badRequest()
                        .body("Cliente é obrigatório");
            }

            if (order.getItems() == null || order.getItems().isEmpty()){
                return ResponseEntity
                        .badRequest()
                        .body("Pedido deve conter itens");
            }

            Order orderProcessed = orderService.createOrder(order);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orderProcessed);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar pedido: " + e.getMessage());
        }

    }


    @GetMapping("/teste")
    public String test(){
        return "serviço de pedidos funcionando!";
    }

    @GetMapping
    public String listOrder(){
        return "Lista de pedidos: [Pedido 1001, Pedido 1002]";
    }
}
