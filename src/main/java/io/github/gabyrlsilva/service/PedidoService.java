package io.github.gabyrlsilva.service;

import io.github.gabyrlsilva.domain.entity.Pedido;
import io.github.gabyrlsilva.domain.enums.StatusPedido;
import io.github.gabyrlsilva.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional <Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
