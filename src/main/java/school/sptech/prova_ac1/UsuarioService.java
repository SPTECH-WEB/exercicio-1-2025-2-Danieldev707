package school.sptech.prova_ac1;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioService {

    private UsuarioRepository repository = new UsuarioRepository();

    public Optional<Usuario> cadastrar(Usuario usuario) {
        if (repository.buscarPorEmail(usuario.getEmail()).isPresent()
                || repository.buscarPorCpf(usuario.getCpf()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(repository.salvar(usuario));
    }

    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return repository.buscarPorId(id);
    }

    public boolean deletar(Integer id) {
        if (repository.buscarPorId(id).isPresent()) {
            repository.deletar(id);
            return true;
        }
        return false;
    }

    public List<Usuario> buscarPorDataMaiorQue(LocalDate data) {
        return repository.listarTodos().stream()
                .filter(u -> u.getDataNascimento().isAfter(data))
                .collect(Collectors.toList());
    }

    public Optional<Usuario> atualizar(Integer id, Usuario usuarioAtualizado) {
        Optional<Usuario> existente = repository.buscarPorId(id);

        if (existente.isEmpty()) {
            return Optional.empty();
        }

        // valida duplicidade de email/cpf (ignora o próprio usuário)
        Optional<Usuario> emailDuplicado = repository.buscarPorEmail(usuarioAtualizado.getEmail());
        if (emailDuplicado.isPresent() && !emailDuplicado.get().getId().equals(id)) {
            return Optional.empty();
        }

        Optional<Usuario> cpfDuplicado = repository.buscarPorCpf(usuarioAtualizado.getCpf());
        if (cpfDuplicado.isPresent() && !cpfDuplicado.get().getId().equals(id)) {
            return Optional.empty();
        }

        usuarioAtualizado.setId(id);
        return Optional.of(repository.salvar(usuarioAtualizado));
    }
}

