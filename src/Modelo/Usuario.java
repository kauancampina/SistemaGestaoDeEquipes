package Modelo;

import java.io.Serializable;

public class Usuario implements Entidade {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String cargo;
    private String login;
    private String senha;
    private PerfilUsuario perfil;

    public Usuario(String nomeCompleto, String cpf, String email,
                   String cargo, String login, String senha, PerfilUsuario perfil) {
        this(0, nomeCompleto, cpf, email, cargo, login, senha, perfil);
    }

    public Usuario(int id, String nomeCompleto, String cpf, String email,
                   String cargo, String login, String senha, PerfilUsuario perfil) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Nome: " + nomeCompleto +
                " | CPF: " + cpf +
                " | Email: " + email +
                " | Cargo: " + cargo +
                " | Perfil: " + perfil;
    }
}
