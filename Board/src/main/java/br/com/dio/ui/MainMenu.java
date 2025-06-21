package br.com.dio.ui;

import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardColumnKindEnum;
import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.service.BoardQueryService;
import br.com.dio.service.BoardService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.*;

public class MainMenu {

    private final Scanner scn = new Scanner(System.in);

    public void execute() throws SQLException{

        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada");
        var option = -1;
        while(true){
            System.out.println("1 -- Criar um novo board");
            System.out.println("2 -- Selecionar um board existente");
            System.out.println("3 -- Excluir um board");
            System.out.println("4 -- Sair");
            option = scn.nextInt();
            scn.nextLine();
            switch (option){
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opão inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException {

        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu board");
        entity.setName(scn.nextLine());

        System.out.println("Seu board terá colunas além das 3 padrões?");
        System.out.println("Se sim informe quantas, senão digite '0'");
        var additionalColumns = scn.nextInt();
        scn.nextLine();
        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o noma da coluna inicial do board");
        var initialColumnName = scn.nextLine();
        var initialColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);
        for(int i = 0; i < additionalColumns; i++){
            System.out.println("Informe o nome da coluna de tarefa pendente do board");
            var pendingColumnName = scn.nextLine();
            var pendingColumn = createColumn(pendingColumnName, PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final");
        var finalColumnName = scn.nextLine();
        var finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board");
        var cancelColumnName = scn.nextLine();
        var cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);

        try (Connection connection = getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
        }
    }

    private void selectBoard() throws SQLException{
        System.out.println("Informe a id do board que deseja selecionar");
        var id = scn.nextLong();
        scn.nextLine();
        try (Connection connection = getConnection()){
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            optional.ifPresentOrElse(
                    b -> new BoardMenu(b).execute(),
                    () -> System.out.printf("Não foi encontrado um board com id %s%n", id));
        }
    }

    private void deleteBoard() {
        System.out.println("Informe o id do board que será excluído");
        Long id = scn.nextLong();
        scn.nextLine();
        try(Connection connection = getConnection()) {
            var service = new BoardService(connection);
            if (service.delete(id)){
                System.out.printf("O board %s foi excluído%n", id);
            }else{
                System.out.printf("Board id = %s não encontrado%n", id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }

}
