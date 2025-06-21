package br.com.dio;

import br.com.dio.ui.custom.frame.MainFrame;
import br.com.dio.ui.custom.panel.MainPanel;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
import br.com.dio.ui.custom.screen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
<<<<<<< HEAD
=======

import javax.swing.*;
import java.awt.*;
>>>>>>> 760f8dc (criado as class CheckGameStatusButton, FinishGameButton, ResetButton, MainFrame, MainPanel e SudokuSector)
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)

public class UIMain {

    public static void main(String[] args) {
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
        final var gameConfig = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0], v ->v.split(";")[1]));
        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
<<<<<<< HEAD
=======
        var dimension = new Dimension(600, 600);
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();

>>>>>>> 760f8dc (criado as class CheckGameStatusButton, FinishGameButton, ResetButton, MainFrame, MainPanel e SudokuSector)
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
    }
}
