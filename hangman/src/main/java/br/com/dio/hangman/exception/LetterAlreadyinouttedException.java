package br.com.dio.hangman.exception;

public class LetterAlreadyinouttedException extends RuntimeException {
    public LetterAlreadyinouttedException(String message) {
        super(message);
    }
}
