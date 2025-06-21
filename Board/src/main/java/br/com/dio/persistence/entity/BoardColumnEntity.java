package br.com.dio.persistence.entity;

import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.ArrayList;
import java.util.List;

public class BoardColumnEntity {

    private Long id;
    private String name;
    private int order;
    private BoardColumnEnumKind kind;
    private BoardEntity board = new BoardEntity();

    public BoardColumnEntity(Long id, String name, int order, BoardColumnEnumKind kind) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.kind = kind;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public BoardColumnEnumKind getKind() {
        return kind;
    }

    public void setKind(BoardColumnEnumKind kind) {
        this.kind = kind;
    }
}
