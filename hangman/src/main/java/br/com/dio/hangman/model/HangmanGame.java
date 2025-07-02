package br.com.dio.hangman.model;

import java.util.List;

import static br.com.dio.hangman.model.HangmaonGameStatus.PENDING;

public class HangmanGame {

    private final static int HANGMAN_INITIAL_LINE_LENGTH = 9;
    private final static int HANGMAN_INITIAL_LINE_LENGTH_WHITH_LIME_SEPARATOR = 10;

    private final int lineSize;
    private final List<HangmanChar> characters;

    private String hangman;
    private HangmaonGameStatus hangmanStatus;

    public HangmanGame(final List<HangmanChar> characters){
        var whitheSpaces = " ".repeat(characters.size());
        var charactersSpace = "-".repeat(characters.size());
        this.lineSize = HANGMAN_INITIAL_LINE_LENGTH_WHITH_LIME_SEPARATOR + whitheSpaces.length();
        this.hangmanStatus = PENDING;
        buildHangmanDesing(whitheSpaces, charactersSpace);
        this.characters = setCharacterSpacesPositionInGame(characters, whitheSpaces.length());
    }

    private List<HangmanChar> setCharacterSpacesPositionInGame(final List<HangmanChar> characters,
                                                  final int whiteSpacesAmount){
        final var LINE_LETTER = 6;
        for (int i = 0; i < characters.size(); i++){
            characters.get(i).setPosition(this.lineSize * LINE_LETTER + HANGMAN_INITIAL_LINE_LENGTH + i);
        }
        return characters;

    }

    private void buildHangmanDesing(final String whiteSpace,
                                    final String characterSpace){
        this.hangman = "          " + whiteSpace + System.lineSeparator()
                     + "  |----|  " + whiteSpace + System.lineSeparator()
                     + "  |    |  " + whiteSpace + System.lineSeparator()
                     + "  |    |  " + whiteSpace + System.lineSeparator()
                     + "  |       " + whiteSpace + System.lineSeparator()
                     + "  |       " + whiteSpace + System.lineSeparator()
                     + "  |       " + whiteSpace + System.lineSeparator()
                     + "  |       " + whiteSpace + System.lineSeparator()
                     + "  ========" + characterSpace + System.lineSeparator();


    }

    @Override
    public String toString() {
        return hangman;
    }
}
