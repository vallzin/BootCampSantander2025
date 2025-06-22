package br.com.dio.persistence.entity;

import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringExclude;

public class CardEntity {

    private Long id;
    private String title;
    private String description;
    @ToStringExclude
    @HashCodeExclude
    @EqualsExclude
    private BoardColumnEntity boardColumn = new BoardColumnEntity();

    public CardEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BoardColumnEntity getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(BoardColumnEntity boardColumn) {
        this.boardColumn = boardColumn;
    }
}
