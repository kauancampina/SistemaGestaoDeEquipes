package Utilitario;

public class Validacoes {

    public static boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean validarCPF(String cpf) {
        return cpf.length() == 11;
    }
}