package br.com.dio.repository;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.PixUserException;
import br.com.dio.model.AccountWallet;

import java.util.List;

import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private List<AccountWallet> accounts;

    public AccountWallet create(final List<String> pix,
                                final long initialFunds){
        var pixInUse = accounts
                .stream()
                .flatMap(a -> a.getPix().stream())
                .toList();
        for (var p : pix) {
            if (pixInUse.contains(p)) {
                throw new PixUserException("O pix '" + p + "' já está em uso");
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix,
                        final long fundsAmount){
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "depósito");
    }

    public long withdraw(final String pix,
                         final long amount){
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix,
                              final String targetPix,
                              final long amount){
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var mensage = "pix enviado de ' " + "' para ' " + targetPix + " ' ";
        target.addMoney(source.reduceMoney(amount), source.getService(), mensage);
    }

    public AccountWallet findByPix(final String pix){
        return accounts
                .stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("A conta com a chave pic ' " + pix + " ' não existe"));
    }

}
