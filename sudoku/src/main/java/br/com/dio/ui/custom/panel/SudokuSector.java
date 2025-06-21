package br.com.dio.ui.custom.panel;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
import br.com.dio.ui.custom.input.NumberText;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
<<<<<<< HEAD
=======
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
>>>>>>> 760f8dc (criado as class CheckGameStatusButton, FinishGameButton, ResetButton, MainFrame, MainPanel e SudokuSector)
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)

import static java.awt.Color.black;

public class SudokuSector extends JPanel {

<<<<<<< HEAD
<<<<<<< HEAD
    public SudokuSector(final List<NumberText> textField){
=======
    public SudokuSector(){
>>>>>>> 760f8dc (criado as class CheckGameStatusButton, FinishGameButton, ResetButton, MainFrame, MainPanel e SudokuSector)
=======
    public SudokuSector(final List<NumberText> textField){
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
        var dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);
<<<<<<< HEAD
<<<<<<< HEAD
        textField.forEach(this::add);
=======
>>>>>>> 760f8dc (criado as class CheckGameStatusButton, FinishGameButton, ResetButton, MainFrame, MainPanel e SudokuSector)
=======
        textField.forEach(this::add);
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
    }
}
