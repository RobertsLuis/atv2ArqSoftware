// Repositório (abstração)
package com.example.atv2arqsoftware.repository;

import com.example.atv2arqsoftware.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}