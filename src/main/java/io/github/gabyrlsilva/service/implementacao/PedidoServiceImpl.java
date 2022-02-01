package io.github.gabyrlsilva.service.implementacao;

import io.github.gabyrlsilva.domain.entity.Cliente;
import io.github.gabyrlsilva.domain.entity.ItemPedido;
import io.github.gabyrlsilva.domain.entity.Pedido;
import io.github.gabyrlsilva.domain.entity.Produto;
import io.github.gabyrlsilva.domain.enums.StatusPedido;
import io.github.gabyrlsilva.domain.repository.Clientes;
import io.github.gabyrlsilva.domain.repository.ItensPedidos;
import io.github.gabyrlsilva.domain.repository.Pedidos;
import io.github.gabyrlsilva.domain.repository.Produtos;
import io.github.gabyrlsilva.exception.PedidoNotFoundException;
import io.github.gabyrlsilva.exception.RegraNegocioException;
import io.github.gabyrlsilva.rest.dto.ItemPedidoDTO;
import io.github.gabyrlsilva.rest.dto.PedidoDTO;
import io.github.gabyrlsilva.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedidos itensPedidosRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto){
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow( () -> new RegraNegocioException("Código do cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedidos = converterItens(pedido, dto.getItens());
        repository.save(pedido);
        itensPedidosRepository.saveAll(itensPedidos);
        pedido.setItens(itensPedidos);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow( () -> new PedidoNotFoundException() );
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar o pedido sem itens.");
        }

        return itens.stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository.findById(idProduto)
                            .orElseThrow( () -> new RegraNegocioException("Código de produto inválido: "+ idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());

    }
}
