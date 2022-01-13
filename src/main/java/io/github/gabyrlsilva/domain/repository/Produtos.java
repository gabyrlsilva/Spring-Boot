package io.github.gabyrlsilva.domain.repository;

import io.github.gabyrlsilva.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
