// Controller
package com.example.atv2arqsoftware.controller;

import com.example.atv2arqsoftware.DTOs.ProdutoCriarDTO;
import com.example.atv2arqsoftware.model.Produto;
import com.example.atv2arqsoftware.serviceInterface.ProdutoReadService;
import com.example.atv2arqsoftware.serviceInterface.ProdutoWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoReadService produtoReadService;
    private final ProdutoWriteService produtoWriteService;

    // Injeção por construtor - um exemplo de DIP
    public ProdutoController(ProdutoReadService produtoReadService,
                             ProdutoWriteService produtoWriteService) {
        this.produtoReadService = produtoReadService;
        this.produtoWriteService = produtoWriteService;
    }

    @GetMapping
    @Operation(summary = "Buscar todos os produtos", description = "Retorna uma lista de todos os produtos")
    public ResponseEntity<List<Produto>> buscarTodos() {
        return ResponseEntity.ok(produtoReadService.buscarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoReadService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody ProdutoCriarDTO dto) {
        // Converter DTO para entidade
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());

        // Salvar e retornar
        Produto produtoSalvo = produtoWriteService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoWriteService.atualizar(id, produto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Exclui um produto pelo ID")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoWriteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}