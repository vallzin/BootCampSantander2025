package br.com.dio;


import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFoundsEnoughException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class Main {

    static Scanner scn = new Scanner(System.in);

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    public static void main(String[] args) {

        System.out.println("Olá! Seja bem vindo ao DIO Bank");

        while (true){
            System.out.println("Selecione a operaçaõ desejada:");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Criar uma carteira de investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Transferência entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar investimentos");
            System.out.println("11 - Listar carteiras de investimentos");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Histórico de conta");
            System.out.println("14 - Sair");

            var option = scn.nextInt();
            scn.nextLine();

            switch (option){
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 4 -> deposit();
                case 3 -> createWalletInvestment();
                case 5 -> withdray();
                case 6 -> transferToAccount();
                case 7 -> incInvestment();
                case 8 -> rescueInvestment();
                case 9 -> accountRepository.list().forEach(System.out::println);
                case 10 -> investmentRepository.list().forEach(System.out::println);
                case 11 -> investmentRepository.listWallets().forEach(System.out::println);
                case 12 -> {
                    investmentRepository.updateAmount();
                    System.out.println("investimentos reajustados");
                }
                case 13 -> checkHistory();
                case 14 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }

        }

    }

    private static void createAccount(){
        System.out.println("Informe as chaves pix (separadas por ';')");
        var pix = Arrays.stream(scn.next().split(";")).toList();
        System.out.println("Informe o valor inicial de depósito");
        var amount = scn.nextLong();
        scn.nextLine();
        var wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada: " + wallet);
    }

    private static void createInvestment(){
        System.out.println("Informe a taxa do investimento");
        var tax = scn.nextInt();
        scn.nextLine();
        System.out.println("Inform o valor inicial do depósito");
        var initialFunds = scn.nextLong();
        scn.nextLine();
        var investment = investmentRepository.create(tax, initialFunds);
        System.out.println("Investimento criado: " + investment);
    }

    private static void deposit(){
        System.out.println("Informe a chave da conta pix para depósito");
        var pix = scn.next();
        System.out.println("Informe o valor que será depositado");
        var amount = scn.nextLong();
        scn.nextLine();
        try {
            accountRepository.deposit(pix, amount);
        }catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void withdray(){
        System.out.println("Informe a chave pix da conta para saque:");
        var pix = scn.next();
        System.out.println("Informe o valor que será sacado:");
        var amount = scn.nextLong();
        scn.nextLine();
        try{
            accountRepository.withdraw(pix, amount);
        }catch (NoFoundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void transferToAccount(){
        System.out.println("Informe a chave pix da conta de origem:");
        var source = scn.next();
        System.out.println("Informe a chave pix da conta de destino");
        var target = scn.next();
        System.out.println("Informe o valor que será depositado:");
        var amount = scn.nextLong();
        scn.nextLine();
        try{
            accountRepository.transferMoney(source, target, amount);
        }catch (AccountNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void createWalletInvestment(){
        System.out.println("Informe a chave pix da conta:");
        var pix = scn.next();
        var account = accountRepository.findByPix(pix);
        System.out.println("Informe o identificador de investimento:");
        var investmentId = scn.nextInt();
        scn.nextLine();
        var investmentWallet = investmentRepository.initInvestment(account, investmentId);
        System.out.println("Conta de investimento criada:  " + investmentWallet);
    }

    private static void incInvestment(){
        System.out.println("Informe a chave pix da conta para investimento:");
        var pix = scn.next();
        System.out.println("Informe o valor que será investido:");
        var amount = scn.nextLong();
        scn.nextLine();
        try{
            investmentRepository.deposit(pix, amount);
        }catch (WalletNotFoundException | AccountNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void rescueInvestment(){
        System.out.println("Informe a chave pix da conta para resgate do investimento:");
        var pix = scn.next();
        System.out.println("Informe o valor que será sacado:");
        var amount = scn.nextLong();
        try{
            investmentRepository.withdraw(pix, amount);
        } catch (NoFoundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void checkHistory(){
        System.out.println("Informe a chave pix da conta para verificar extrato:");
        var pix = scn.next();
        AccountWallet wallet;
        try {
            var sortedHistory = accountRepository.getHistory(pix);
            sortedHistory.forEach((k, v) -> {
                System.out.println(k.format(ISO_DATE_TIME));
                System.out.println(v.getFirst().transactionId());
                System.out.println(v.getFirst().description());
                System.out.println("R$" + (v.size() / 100) + "," + (v.size() % 100));
            });
        } catch (AccountNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }
}