// Interface segregada para leitura
package com.example.atv2arqsoftware.serviceInterface;

import com.example.atv2arqsoftware.model.Produto;
import java.util.List;

public interface ProdutoReadService {
    List<Produto> buscarTodos();
    Produto buscarPorId(Long id);
}