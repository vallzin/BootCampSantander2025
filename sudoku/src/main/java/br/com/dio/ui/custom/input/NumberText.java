package br.com.dio.ui.custom.input;

import br.com.dio.model.Space;
<<<<<<< HEAD
<<<<<<< HEAD
import br.com.dio.service.EventEnum;
import br.com.dio.service.EventListener;
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
import br.com.dio.service.EventEnum;
import br.com.dio.service.EventListener;
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

<<<<<<< HEAD
<<<<<<< HEAD
import static br.com.dio.service.EventEnum.CLEAR_SPACE;
import static java.awt.Font.PLAIN;

public class NumberText extends JTextField implements EventListener {
=======
import static java.awt.Font.PLAIN;

public class NumberText extends JTextField {
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
import static br.com.dio.service.EventEnum.CLEAR_SPACE;
import static java.awt.Font.PLAIN;

public class NumberText extends JTextField implements EventListener {
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)

    private final Space space;

    public NumberText(final Space space){
        this.space = space;
        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        if (space.isFixed()){
            this.setText(space.getActual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSpace();
            }

            private void changeSpace(){
                if (getText().isEmpty()){
                    space.clearSpace();
                    return;
                }
                space.setActual(Integer.parseInt(getText()));
            }

        });

    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
    @Override
    public void update(EventEnum evenType) {
        if (evenType.equals(CLEAR_SPACE) && (this.isEnabled())){
            this.setText("");
        }
    }
<<<<<<< HEAD
=======
>>>>>>> 5b246d2 (creado class NumberText, NumberTextLimit)
=======
>>>>>>> 9f6fd80 (criado class NotifierService, Enum EventeEnum e interface EventeListener)
}
