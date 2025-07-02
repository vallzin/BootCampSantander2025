package br.com.dio.hangman.model;

import java.util.Objects;

public class HangmanChar {

    private final char character;
    private boolean inVisible;
    private int position;

    public HangmanChar(final char character){
        this.character = character;
        this.inVisible = true;
    }

    public char getCharacter(){
        return character;
    }

    public boolean isInVisible(){
        return inVisible;
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
        return character == that.character && inVisible == that.inVisible && position == that.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, inVisible, position);
    }

    @Override
    public String toString() {
        return "HangmanChar{" +
                "character=" + character +
                ", inVisible=" + inVisible +
                ", position=" + position +
                '}';
    }
}
