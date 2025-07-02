package br.com.dio.hangman.model;

import java.util.Objects;

public class HangmanChar {

    private final char character;
    private boolean isVisible;
    private int position;

    public HangmanChar(final char character){
        this.character = character;
        this.isVisible = false;
    }

    public HangmanChar(final char character,
                       final int position){
        this.character = character;
        this.position = position;
        this.isVisible = true;
    }

    public char getCharacter(){
        return character;
    }

    public boolean isVisible(){
        return isVisible;
    }

    public boolean isInvisible(){
        return !isVisible;
    }


    public void enableVisibility(){
        isVisible = true;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(final int position){
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HangmanChar that = (HangmanChar) o;
        return character == that.character && isVisible == that.isVisible && position == that.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, isVisible, position);
    }

    @Override
    public String toString() {
        return "HangmanChar{" +
                "character=" + character +
                ", inVisible=" + isVisible +
                ", position=" + position +
                '}';
    }
}
