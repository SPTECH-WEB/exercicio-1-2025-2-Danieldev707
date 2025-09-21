package school.sptech.prova_ac1;

import school.sptech.prova_ac1.Usuario;

import java.util.*;

public class UsuarioRepository {

    private static Map<Integer, Usuario> banco = new HashMap<>();
    private static Integer contadorId = 1;

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(contadorId++);
        }
        banco.put(usuario.getId(), usuario);
        return usuario;
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return Optional.ofNullable(banco.get(id));
    }

    public void deletar(Integer id) {
        banco.remove(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return banco.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Usuario> buscarPorCpf(String cpf) {
        return banco.values().stream()
                .filter(u -> u.getCpf().equals(cpf))
                .findFirst();
    }
}

