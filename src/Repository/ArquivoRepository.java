package Repository;

import Modelo.Entidade;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArquivoRepository<T extends Entidade> implements Repository<T> {

    private final File arquivo;
    private final List<T> dados;
    private int proximoId;

    public ArquivoRepository(String nomeArquivo) {
        // A persistencia fica isolada no Repository para permitir trocar arquivo por JDBC sem alterar View/Controller.
        File pastaDados = new File("dados");
        if (!pastaDados.exists()) {
            pastaDados.mkdirs();
        }
        this.arquivo = new File(pastaDados, nomeArquivo);
        this.dados = carregar();
        this.proximoId = calcularProximoId();
    }

    @Override
    public T salvar(T entidade) {
        if (entidade.getId() <= 0) {
            entidade.setId(proximoId++);
        }
        dados.add(entidade);
        persistir();
        return entidade;
    }

    @Override
    public void atualizar(T entidade) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getId() == entidade.getId()) {
                dados.set(i, entidade);
                persistir();
                return;
            }
        }
        throw new IllegalArgumentException("Registro nao encontrado para atualizacao.");
    }

    @Override
    public boolean remover(int id) {
        boolean removido = dados.removeIf(entidade -> entidade.getId() == id);
        if (removido) {
            persistir();
        }
        return removido;
    }

    @Override
    public Optional<T> buscarPorId(int id) {
        return dados.stream()
                .filter(entidade -> entidade.getId() == id)
                .findFirst();
    }

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(dados);
    }

    protected void persistir() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            output.writeObject(dados);
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao persistir dados em arquivo.", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private List<T> carregar() {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<T>) input.readObject();
        } catch (EOFException ex) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException ex) {
            throw new IllegalStateException("Erro ao carregar dados persistidos.", ex);
        }
    }

    private int calcularProximoId() {
        return dados.stream()
                .mapToInt(Entidade::getId)
                .max()
                .orElse(0) + 1;
    }
}
