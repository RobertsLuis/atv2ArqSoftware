package com.example.atv2arqsoftware.serviceInterface;

import com.example.atv2arqsoftware.model.Produto;
import java.util.List;

public interface ProdutoService {
    List<Produto> buscarTodos();
    Produto buscarPorId(Long id);
    Produto salvar(Produto produto);
    Produto atualizar(Long id, Produto produto);
    void deletar(Long id);
}
