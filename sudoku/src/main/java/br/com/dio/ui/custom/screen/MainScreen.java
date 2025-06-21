package br.com.dio.ui.custom.screen;

<<<<<<< HEAD
<<<<<<< HEAD
import br.com.dio.model.Space;
import br.com.dio.service.BoardService;
import br.com.dio.service.EventEnum;
import br.com.dio.service.NotifierService;
import br.com.dio.ui.custom.button.CheckGameStatusButton;
import br.com.dio.ui.custom.button.FinishGameButton;
import br.com.dio.ui.custom.button.ResetButton;
import br.com.dio.ui.custom.frame.MainFrame;
import br.com.dio.ui.custom.input.NumberText;
import br.com.dio.ui.custom.panel.MainPanel;
import br.com.dio.ui.custom.panel.SudokuSector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.dio.service.EventEnum.CLEAR_SPACE;
=======
=======
import br.com.dio.model.Space;
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
import br.com.dio.service.BoardService;
import br.com.dio.service.EventEnum;
import br.com.dio.service.NotifierService;
import br.com.dio.ui.custom.button.CheckGameStatusButton;
import br.com.dio.ui.custom.button.FinishGameButton;
import br.com.dio.ui.custom.button.ResetButton;
import br.com.dio.ui.custom.frame.MainFrame;
import br.com.dio.ui.custom.input.NumberText;
import br.com.dio.ui.custom.panel.MainPanel;
import br.com.dio.ui.custom.panel.SudokuSector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
import static br.com.dio.model.GameStatusEnum.*;
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
import static br.com.dio.service.EventEnum.CLEAR_SPACE;
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
<<<<<<< HEAD
<<<<<<< HEAD
    private final NotifierService notifierService;
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
    private final NotifierService notifierService;
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
<<<<<<< HEAD
<<<<<<< HEAD
        this.notifierService = new NotifierService();
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
        this.notifierService = new NotifierService();
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)

        for(int r = 0; r < 9; r+=3){
            var endRow = r + 2;
            for(int c = 0; c < 9; c+=3){
                var endCol = c + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);
                JPanel sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
<<<<<<< HEAD
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
    private JPanel generateSection(final List<Space> spaces){
        
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscriber(CLEAR_SPACE, t));

        return new SudokuSector(fields);
    }

    private List<Space> getSpacesFromSector(List<List<Space>> spaces,
                                            final int initCol,
                                            final int endCol,
                                            final int initRow,
                                            final int endRow){
        List<Space> spacesSector = new ArrayList<>();

        for (int r = initRow; r <= endRow; r++){
            for (int c = initCol; c <= endCol; c++){
                spacesSector.add(spaces.get(c).get(r));
            }
        }
        return spacesSector;
    }

<<<<<<< HEAD
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
    private void addResetButton(final JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE);
            if (dialogResult == 0) {
                boardService.reset();
<<<<<<< HEAD
<<<<<<< HEAD
                notifierService.notify(CLEAR_SPACE);
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
                notifierService.notify(CLEAR_SPACE);
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
            }
        });
        mainPanel.add(resetButton);
    }

    private void addCheckGameStatusButton(final JPanel mainPanel) {
<<<<<<< HEAD
<<<<<<< HEAD
        checkGameStatusButton = new CheckGameStatusButton(e -> {
=======
        checkGameStatusButton = new FinishGameButton(e -> {
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
        checkGameStatusButton = new CheckGameStatusButton(e -> {
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
            var hasErrors = boardService.hasErros();
            var gameStatus = boardService.gameStatus();
            var message = switch (gameStatus){
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };
            message += hasErrors ? " e contém erros" : " e naõ contém erros";
            JOptionPane.showConfirmDialog(null, message);
        });
        mainPanel.add(MainScreen.this.checkGameStatusButton);
    }

    private void addFinishGameButton(final JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
                if(boardService.gameIsFinished()) {
                    JOptionPane.showConfirmDialog(
                            null,
                            "Parabéns você concluiu o jogo");
                    resetButton.setEnabled(false);
                    checkGameStatusButton.setEnabled(false);
                    finishGameButton.setEnabled(false);
                }else{
                    JOptionPane.showConfirmDialog(
                            null,
                            "Seu jogo tem alguma inconsistência, ajuste e tente novamente");
                }
        });
        mainPanel.add(finishGameButton);
    }

}
