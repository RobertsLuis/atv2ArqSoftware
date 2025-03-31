# Aplicação dos Princípios SOLID
### Roberto Luis de Jesus Santos Junior

## 1. Princípio da Responsabilidade Única (SRP)
**Conceito**: Uma classe deve ter apenas uma razão para mudar, ou seja, deve ter apenas uma responsabilidade.

**Aplicação no projeto**:
- O `ProdutoController` tem apenas a responsabilidade de tratar requisições HTTP, não contendo lógica de negócio:

```java
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoWriteService produtoService;

    public ProdutoController(ProdutoWriteService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody ProdutoCriarDTO dto) {
        // Apenas converte DTO para entidade e delega ao serviço
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());

        Produto produtoSalvo = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }
}
```

- A classe `ProdutoValidator` tem a responsabilidade única de definir o contrato para validação:

```java
public abstract class ProdutoValidator {
    public abstract boolean validar(Produto produto);

    public String getMensagemErro() {
        return "Validação falhou";
    }
}
```

## 2. Princípio Aberto/Fechado (OCP)
**Conceito**: Entidades devem estar abertas para extensão, mas fechadas para modificação.

**Aplicação no projeto**:
- A classe abstrata `ProdutoValidator` permite criar novos validadores sem modificar o código existente:

```java
// Extensão do validador para preço sem modificar ProdutoValidator
public class PrecoValidator extends ProdutoValidator {
    @Override
    public boolean validar(Produto produto) {
        return produto.getPreco() != null && produto.getPreco() > 0;
    }

    @Override
    public String getMensagemErro() {
        return "O preço deve ser maior que zero";
    }
}

// Outra extensão para nome
public class NomeValidator extends ProdutoValidator {
    @Override
    public boolean validar(Produto produto) {
        return produto.getNome() != null && !produto.getNome().trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "Nome do produto é obrigatório";
    }
}
```

## 3. Princípio da Substituição de Liskov (LSP)
**Conceito**: Objetos de uma classe derivada devem poder substituir objetos da classe base sem afetar a corretude do programa.

**Aplicação no projeto**:
- As implementações de `ProdutoValidator` podem ser usadas de forma intercambiável:

```java
// Código que usa qualquer implementação de ProdutoValidator
public class ValidacaoProdutoService {
    private final List<ProdutoValidator> validadores;
    
    public ValidacaoProdutoService(List<ProdutoValidator> validadores) {
        this.validadores = validadores;
    }
    
    public boolean validarProduto(Produto produto) {
        for (ProdutoValidator validador : validadores) {
            if (!validador.validar(produto)) {
                return false;
            }
        }
        return true;
    }
}
```

## 4. Princípio da Segregação de Interface (ISP)
**Conceito**: Clientes não devem ser forçados a depender de interfaces que não utilizam.

**Aplicação no projeto**:
- Separação de interfaces para leitura e escrita:

```java
// Interface para operações de leitura
public interface ProdutoReadService {
    List<Produto> listarTodos();
    Optional<Produto> buscarPorId(Long id);
}

// Interface para operações de escrita
public interface ProdutoWriteService {
    Produto salvar(Produto produto);
    void excluir(Long id);
}

// Controller que usa apenas as operações de leitura
@RestController
@RequestMapping("/api/produtos/consulta")
public class ProdutoConsultaController {
    private final ProdutoReadService readService;
    
    public ProdutoConsultaController(ProdutoReadService readService) {
        this.readService = readService;
    }
    
    @GetMapping
    public List<Produto> listarTodos() {
        return readService.listarTodos();
    }
}
```

## 5. Princípio da Inversão de Dependência (DIP)
**Conceito**: Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações.

**Aplicação no projeto**:
- O `ProdutoController` depende de abstrações (`ProdutoWriteService`), não de implementações concretas:

```java
// O controller depende de abstrações
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    // Depende da abstração, não da implementação concreta
    private final ProdutoWriteService produtoService;

    // Injeção por construtor
    public ProdutoController(ProdutoWriteService produtoService) {
        this.produtoService = produtoService;
    }
    
    // Métodos do controller...
}

// A implementação também depende de abstrações
@Service
public class ProdutoServiceImpl implements ProdutoReadService, ProdutoWriteService {
    // Depende da abstração ProdutoRepository, não de uma implementação específica
    private final ProdutoRepository produtoRepository;
    
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    
    // Implementações dos métodos...
}
```

Estes exemplos demonstram como os princípios SOLID são aplicados no projeto, tornando o código mais modular, flexível e fácil de manter. A estrutura do projeto favorece a extensibilidade e testabilidade, permitindo adicionar novas funcionalidades com mínimo impacto no código existente.
