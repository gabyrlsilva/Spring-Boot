package io.github.gabyrlsilva.domain.repository;

import io.github.gabyrlsilva.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidos extends JpaRepository<ItemPedido, Integer> {
}
