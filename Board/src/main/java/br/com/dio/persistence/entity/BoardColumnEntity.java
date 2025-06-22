package br.com.dio.persistence.entity;

import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.ArrayList;
import java.util.List;

public class BoardColumnEntity {

    private Long id;
    private String name;
    private int order;
    private BoardColumnKindEnum kind;
    private BoardEntity board = new BoardEntity();
    @ToStringExclude
    @EqualsExclude
    @HashCodeExclude
    private List<CardEntity> cards = new ArrayList<>();

    public BoardColumnEntity() {
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

    public BoardColumnKindEnum getKind() {
        return kind;
    }

    public void setKind(BoardColumnKindEnum kind) {
        this.kind = kind;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public List<CardEntity> getCards() {
        return cards;
    }

    public void setCards(List<CardEntity> cards) {
        this.cards = cards;
    }
}
