package br.com.dio.repository;

import br.com.dio.exception.NoFoundsEnoughException;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;
import br.com.dio.model.Wallet;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.dio.model.BankService.ACCOUNT;

public final class CommonsRepository {


    public static void checkFundsForTransaction(final Wallet source,
                                                final long amount){
        if (source.getFunds() < amount){
            throw new NoFoundsEnoughException("Sua conta não tem dinheiro suficiente para realizar essa tranzação");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId,
                                            final long funds,
                                            final String description){
        var history = new MoneyAudit(transactionId, ACCOUNT,description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
    }


}
