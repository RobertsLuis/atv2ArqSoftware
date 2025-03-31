// Interface segregada para escrita
package com.example.atv2arqsoftware.serviceInterface;

import com.example.atv2arqsoftware.model.Produto;

public interface ProdutoWriteService {
    Produto salvar(Produto produto);
    Produto atualizar(Long id, Produto produto);
    void deletar(Long id);
}