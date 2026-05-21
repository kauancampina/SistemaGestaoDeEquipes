package Utilitario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public final class Validacoes {

    private static final Pattern EMAIL = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    private static final DateTimeFormatter FORMATO_DATA_BRASILEIRO =
            DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);

    private Validacoes() {
    }

    public static void campoObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nomeCampo + " e obrigatorio.");
        }
    }

    public static void cpf(String cpf) {
        campoObrigatorio(cpf, "CPF");
        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF invalido. Informe exatamente 11 digitos, sem pontos, tracos, acentos ou pontuacoes.");
        }
    }

    public static void email(String email) {
        campoObrigatorio(email, "Email");
        if (!EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Email invalido.");
        }
    }

    public static void senha(String senha) {
        campoObrigatorio(senha, "Senha");
        if (senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve possuir pelo menos 6 caracteres.");
        }
    }

    public static LocalDate data(String valor, String nomeCampo) {
        campoObrigatorio(valor, nomeCampo);
        try {
            return LocalDate.parse(valor, FORMATO_DATA_BRASILEIRO);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(nomeCampo + " deve estar no formato DD-MM-AAAA.");
        }
    }

    public static void periodo(LocalDate inicio, LocalDate termino) {
        if (inicio == null || termino == null) {
            throw new IllegalArgumentException("Datas de inicio e termino sao obrigatorias.");
        }
        if (termino.isBefore(inicio)) {
            throw new IllegalArgumentException("A data de termino nao pode ser menor que a data de inicio.");
        }
    }

    public static void idPositivo(int id, String entidade) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de " + entidade + " invalido.");
        }
    }
}
