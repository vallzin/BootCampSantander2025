package br.com.dio.ui;

import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.persistence.entity.CardEntity;
import br.com.dio.service.BoardColumnQueryService;
import br.com.dio.service.BoardQueryService;
import br.com.dio.service.CardQueryService;
import br.com.dio.service.CardService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;

public class BoardMenu {

    private final Scanner scn = new Scanner(System.in).useDelimiter("\n");

    private final BoardEntity entity;

    public BoardMenu(BoardEntity entity) {
        this.entity = entity;
    }

    public BoardMenu(Optional<BoardEntity> optional, BoardEntity entity) {
        this.entity = entity;
    }

    public void execute(){
        try {

            System.out.printf("Bem vindo ao board %s, selecione a operação desejada%n", entity.getId());
            var option = -1;

            while (option != 9) {
                System.out.println("1 -- Criar um Card");
                System.out.println("2 -- Mover um Card");
                System.out.println("3 -- Bloquear um Card");
                System.out.println("4 -- Desbloquear um Card");
                System.out.println("5 -- Cancelar um Card");
                System.out.println("6 -- Visualizar board");
                System.out.println("7 -- Visualizar coluna com cards");
                System.out.println("8 -- Visualizar cards");
                System.out.println("9 -- Voltar para menu anterior");
                System.out.println("0 -- Sair");

                option = scn.nextInt();
                scn.nextLine();

                switch (option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                    case 9 -> System.out.println("Voltando para o menu anterior");
                    case 0 -> System.exit(0);
                    default -> System.out.println("Opção inválida, informe uma opção do menu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
    private void createCard() throws SQLException {
        var card = new CardEntity();
        System.out.println("Informe o título do card");
        card.setTitle(scn.nextLine());
        System.out.println("Informe a descrição do card");
        card.setDescription(scn.nextLine());
        card.setBoardColumn(entity.getInitialColumn());

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                var cardDAO = new CardDAO(connection);
                card = cardDAO.insert(card); // Atribui o card com ID atualizado
                connection.commit();
                System.out.printf("Card %s - %s criado com sucesso!%n", card.getId(), card.getTitle());
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                throw e;
            }
        }
    }
*/
private void createCard() throws SQLException {
    var card = new CardEntity();
    System.out.println("Informe o título do card");
    card.setTitle(scn.nextLine());
    System.out.println("Informe a descrição do card");
    card.setDescription(scn.nextLine());
    card.setBoardColumn(entity.getInitialColumn());

    try (Connection connection = getConnection()) {
        connection.setAutoCommit(false);
        new CardService(connection).insert(card);
    }
}

    private void moveCardToNextColumn() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a próxima coluna");
        var cardId = scn.nextLong();
        scn.nextLine();
        var boardColumnsInfo = entity
                .getBoardColumns()
                .stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        try(var connection = getConnection()){
            new CardService(connection).moveToNextColumn(cardId, boardColumnsInfo);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a coluna de cancelamento");
        var cardId = scn.nextLong();
        scn.nextLine();
        var cancelColumn = entity.getCancelColumn();
        var boardColumnsInfo = entity
                .getBoardColumns()
                .stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        try(var connection = getConnection()){
            new CardService(connection).cancel(cardId, cancelColumn.getId(), boardColumnsInfo);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void showBoard() throws SQLException {
        try (Connection connection = getConnection()){
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                System.out.printf("Board [%s,%s]\n", b.id(), b.name());
                b.columns().forEach(
                        c ->
                                System.out.printf("Coluna [%S] tipo: [%s] tem %s cards\n", c.name(), c.kind(), c.cardsAmount()));

            });
        }
    }

    private void showColumn() throws SQLException {

        var columnsId = entity.getBoardColumns().stream().map(BoardColumnEntity::getId).toList();
        var selectedColumn = -1L;
        while(!columnsId.contains(selectedColumn)){
            System.out.printf("Escolha uma coluna do board %s%n", entity.getName());
            entity.getBoardColumns().forEach(c -> System.out.printf(
                    "%s - %s [%s]%n", c.getId(), c.getName(), c.getKind()));
            selectedColumn = scn.nextLong();
        }
        try(Connection connection = getConnection()){
            var column = new BoardColumnQueryService(connection).findById(selectedColumn);
            column.ifPresent(co -> {
                System.out.printf("Coluna %s tipo %s%n", co.getName(), co.getKind());
                co.getCards().forEach(ca -> System.out.printf(
                        "Card %s - %s%nDescrição: %s%n",ca.getId(), ca.getTitle(), ca.getDescription()));
            });
        }

    }

    private void showCard() throws SQLException {
        System.out.println("Informe o id do card que deseja visualizar");
        var selectedCardId = scn.nextLong();
        try (Connection connection = getConnection()){
            new CardQueryService(connection).findById(selectedCardId)
                    .ifPresentOrElse(
                            c -> {
                                System.out.printf("Card %s - %s%n", c.id(), c.title());
                                System.out.printf("Descrição: %s%n", c.description());
                                System.out.println(c.blocked() ? "Está bloqueado. Motivo: " + c.blockedReason() : "Não está bloqueado");
                                System.out.printf("Já foi bloqueado %s vezes%n", c.blocksAmount());
                                System.out.printf("Está no momento na coluna %s - %s%n", c.columnId(), c.columnName());
                            },
                            () -> System.out.printf("Não existe um card com o id %s%n", selectedCardId)
                    );
        }
    }
}
