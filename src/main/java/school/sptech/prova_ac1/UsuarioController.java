package school.sptech.prova_ac1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService service = new UsuarioService();

    // 1. Cadastro
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        Optional<Usuario> novo = service.cadastrar(usuario);
        return novo.map(u -> ResponseEntity.status(HttpStatus.CREATED).body(u))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    // 2. Listagem
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {
        List<Usuario> lista = service.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

    // 3. Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 4. Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (service.deletar(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 5. Filtro por data
    @GetMapping("/filtro-data")
    public ResponseEntity<List<Usuario>> buscarPorDataNascimento(@RequestParam("nascimento") String nascimento) {
        LocalDate data = LocalDate.parse(nascimento);
        List<Usuario> lista = service.buscarPorDataMaiorQue(data);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

    // 6. Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> atualizado = service.atualizar(id, usuario);
        return atualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}


