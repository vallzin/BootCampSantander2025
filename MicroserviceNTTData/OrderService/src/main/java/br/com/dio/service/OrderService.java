package br.com.dio.service;

import br.com.dio.client.ProductClient;
import br.com.dio.model.Order;
import br.com.dio.model.OrderItem;
import br.com.dio.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    private final ProductClient productClient;

    public OrderService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public Order createOrder(Order order){

        order.setId(System.currentTimeMillis());

        double total = 0.0;
        List<OrderItem> updatedItems = new ArrayList<>();

        for (OrderItem item : order.getItems()){
            Product product = this.productClient.getProductById(item.getProductId());

            if (product == null){
                throw new RuntimeException("Produto não encontrado: " + item.getProductId());
            }

            OrderItem updatedItem = new OrderItem();
            updatedItem.setProductId(item.getProductId());
            updatedItem.setQuantity(item.getQuantity());
            updatedItem.getUnitPrice(product.getPrice());

            updatedItems.add(updatedItem);

            double subtotal = item.getQuantity() * updatedItem.getUnitPrice(product.getPrice());
            total += subtotal;
        }

        order.setItems(updatedItems);
        order.setTotal(total);

        return order;
    }
}
