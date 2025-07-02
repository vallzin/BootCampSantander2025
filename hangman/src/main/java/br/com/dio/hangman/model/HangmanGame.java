package br.com.dio.hangman.model;

import br.com.dio.hangman.exception.GameIsFinishedException;
import br.com.dio.hangman.exception.LetterAlreadyinouttedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static br.com.dio.hangman.model.HangmanGameStatus.*;

public class HangmanGame {

    private final static int HANGMAN_INITIAL_LINE_LENGTH = 9;
    private final static int HANGMAN_INITIAL_LINE_LENGTH_WITH_LIME_SEPARATOR = 10;

    private final int lineSize;
    private final int hangmanInitialSize;
    private final List<HangmanChar> hangmanPaths;
    private final List<HangmanChar> characters;
    private final List<Character> failAttempts = new ArrayList<>();

    private String hangman;
    private HangmanGameStatus hangmanGameStatus;

    public HangmanGame(final List<HangmanChar> characters){
        var whiteSpaces = " ".repeat(characters.size());
        var charactersSpace = "-".repeat(characters.size());
        this.lineSize = HANGMAN_INITIAL_LINE_LENGTH_WITH_LIME_SEPARATOR + whiteSpaces.length();
        this.hangmanGameStatus = PENDING;
        this.hangmanPaths = buildHangmanPathsPositions();
        buildHangmanDesign(whiteSpaces, charactersSpace);
        this.characters = setCharacterSpacesPositionInGame(characters, whiteSpaces.length());
        this.hangmanInitialSize = hangman.length();
    }

    public void inputCharacter(final char character){
        if (this.hangmanGameStatus != PENDING){
            var message = this.hangmanGameStatus == WIN ?
                    "Parabéns, você ganho!" :
                    "Você perdeu, tente novamente";
            throw new GameIsFinishedException(message);
        }

        var found = this.characters
                .stream()
                .filter(c -> c.getCharacter() == character)
                .toList();

        if(this.failAttempts.contains(character)){
            throw new LetterAlreadyinouttedException("Letra ' " + character + "' já informada");
        }

        if (found.isEmpty()){
            failAttempts.add(character);
            if (failAttempts.size() >= 6){
                this.hangmanGameStatus = LOSE;
            }
            rebuildHangman(this.hangmanPaths.removeFirst());
            return;
        }
        if (found.getFirst().isInvisible()){
            throw new LetterAlreadyinouttedException("Letra '" + character + "' repetida");
        }

        this.characters.forEach(c -> {
            if (c.getCharacter() == found.getFirst().getCharacter()){
                c.enableVisibility();
            }
        });

        if (this.characters.stream().noneMatch(HangmanChar::isInvisible)){
            this.hangmanGameStatus = WIN;
        }

        rebuildHangman(found.toArray(HangmanChar[]::new));
    }

    private void rebuildHangman(final HangmanChar... hangmanChars){
        var hangmanBuilder = new StringBuilder(this.hangman);
        Stream.
                of(hangmanChars)
                .forEach(
                        h -> hangmanBuilder.setCharAt(h.getPosition(), h.getCharacter())
                );
        var failMessage = this.failAttempts.isEmpty() ? " " : "Tentativas" + this.failAttempts;
        this.hangman = hangmanBuilder.substring(0, hangmanInitialSize) + failMessage;
    }

    private List<HangmanChar> buildHangmanPathsPositions(){
        final int HEAD_LINE = 4;
        final int BODY_LINE = 5;
        final int LEGS_LINE = 6;

        return new ArrayList<>(
                List.of(
                        new HangmanChar('O', this.lineSize * HEAD_LINE - 2),
                        new HangmanChar('|', this.lineSize * BODY_LINE),
                        new HangmanChar('/', this.lineSize * BODY_LINE - 1),
                        new HangmanChar('\\',this.lineSize * BODY_LINE + 1),
                        new HangmanChar('/', this.lineSize * LEGS_LINE + 1),
                        new HangmanChar('\\', this.lineSize * LEGS_LINE + 3)
                )
        );
    }

    private List<HangmanChar> setCharacterSpacesPositionInGame(final List<HangmanChar> characters,
                                                  final int whiteSpacesAmount){
        final var LINE_LETTER = 6;
        for (int i = 0; i < characters.size(); i++){
            characters.get(i).setPosition(this.lineSize * LINE_LETTER + HANGMAN_INITIAL_LINE_LENGTH + i);
        }
        return characters;

    }

    private void buildHangmanDesign(final String whiteSpace,
                                    final String characterSpace){
        this.hangman = "  |----|  " + whiteSpace + System.lineSeparator()
                     + "  |    |  " + whiteSpace + System.lineSeparator()
                     + "  |    |  " + whiteSpace + System.lineSeparator()
                     + "  |       " + whiteSpace + System.lineSeparator()
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
