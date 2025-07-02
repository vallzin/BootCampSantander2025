package br.com.dio;


import br.com.dio.hangman.exception.GameIsFinishedException;
import br.com.dio.hangman.exception.LetterAlreadyinouttedException;
import br.com.dio.hangman.model.HangmanChar;
import br.com.dio.hangman.model.HangmanGame;
import br.com.dio.hangman.model.HangmanGameStatus;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.dio.hangman.model.HangmanGameStatus.WIN;

public class Main {

    private final static Scanner scn = new Scanner(System.in);

    public static void main(String... args) {

        var characters = Stream.of(args)
                .map(a -> a.toLowerCase().charAt(0))
                .map(HangmanChar::new).toList();
        System.out.println(characters);
        var hangmanGame = new HangmanGame(characters);
        System.out.println("Bem vindo ao jogo da forca, tente adivinhar a palavra, boa sorte");
        System.out.println(hangmanGame);
        var option = -1;
        while (true){
            System.out.println("Selecione uma das opções:");
            System.out.println("1 - Informar uma letra");
            System.out.println("2 - Verificar status do jogo");
            System.out.println("3 - Sair do jogo");
            option = scn.nextInt();
            switch (option){
                case 1 -> inputCharacter(hangmanGame);
                case 2 -> showGameStatus(hangmanGame);
                case 3 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }

        }
    }

    private static void showGameStatus(final HangmanGame hangmanGame) {
        System.out.println(hangmanGame.getHangmanGameStatus());
        System.out.println(hangmanGame);
    }

    private static void inputCharacter(final HangmanGame hangmanGame) {
        System.out.println("Informe uma letra");
        var character = scn.next().charAt(0);
        try {
            hangmanGame.inputCharacter(character);
        } catch (LetterAlreadyinouttedException ex) {
            System.out.println(ex.getMessage());
        } catch (GameIsFinishedException ex) {
            System.out.println(ex.getMessage());
            if(hangmanGame.getHangmanGameStatus() == WIN){
                System.out.println("Palavra da forca : " + getRevealedWord(hangmanGame));
            }
            System.exit(0);
        }
        System.out.println(hangmanGame);
    }

    private static String getRevealedWord(HangmanGame game) {
        return game
                .getCharacters()
                .stream()
                .map(c -> String.valueOf(c.getCharacter()))
                .collect(Collectors.joining());
    }

}
