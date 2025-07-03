package br.com.dio.model;

import java.util.List;

public class Order {

    private Long id;
    private String cliente;
    private List<OrderItem> items;
    private Double total;

    public Order(){}

    public Order(String cliente,
                 List<OrderItem> items){
        this.cliente = cliente;
        this.items = items;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCliente(){
        return cliente;
    }

    public void setCliente(String cliente){
        this.cliente = cliente;
    }

    public List<OrderItem> getItems(){
        return items;
    }

    public void setItems(List<OrderItem> items){
        this.items = items;
    }

    public Double getTotal(){
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
