package Utilitario;

import java.util.Scanner;

public class ConsoleHelper {

    private final Scanner scanner;

    public ConsoleHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String lerTexto(String rotulo) {
        System.out.print(rotulo);
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String rotulo) {
        while (true) {
            try {
                System.out.print(rotulo);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Informe um numero valido.");
            }
        }
    }

    public boolean lerBooleano(String rotulo) {
        while (true) {
            String valor = lerTexto(rotulo + " (S/N): ");
            if (valor.equalsIgnoreCase("S")) {
                return true;
            }
            if (valor.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Opcao invalida. Digite S ou N.");
        }
    }

    public <E extends Enum<E>> E lerEnum(String rotulo, Class<E> tipo) {
        E[] valores = tipo.getEnumConstants();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + " - " + valores[i]);
        }
        while (true) {
            int opcao = lerInteiro(rotulo);
            if (opcao >= 1 && opcao <= valores.length) {
                return valores[opcao - 1];
            }
            System.out.println("Opcao invalida.");
        }
    }
}
