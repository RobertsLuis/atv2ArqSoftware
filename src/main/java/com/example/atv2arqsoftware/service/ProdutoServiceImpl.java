package com.example.atv2arqsoftware.service;

import com.example.atv2arqsoftware.model.Produto;
import com.example.atv2arqsoftware.repository.ProdutoRepository;
import com.example.atv2arqsoftware.serviceInterface.ProdutoReadService;
import com.example.atv2arqsoftware.serviceInterface.ProdutoWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoReadService, ProdutoWriteService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Override
    public Produto salvar(Produto produto) {
        // Se o ID for nulo, é uma nova entidade
        if (produto.getId() == null) {
            return produtoRepository.save(produto);
        }

        // Se já tiver ID, busque a entidade atual e atualize seus campos
        Produto produtoExistente = produtoRepository.findById(produto.getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + produto.getId()));

        // Atualize os campos (modifique conforme os campos do seu modelo)
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setPreco(produto.getPreco());
        // Atualize outros campos conforme necessário

        return produtoRepository.save(produtoExistente);
    }

    @Override
    public Produto atualizar(Long id, Produto produto) {
        Produto produtoExistente = buscarPorId(id);
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setPreco(produto.getPreco());
        return produtoRepository.save(produtoExistente);
    }

    @Override
    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}