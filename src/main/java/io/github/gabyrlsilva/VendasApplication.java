package io.github.gabyrlsilva;

import io.github.gabyrlsilva.domain.entity.Cliente;
import io.github.gabyrlsilva.domain.entity.Pedido;
import io.github.gabyrlsilva.domain.repository.Clientes;
import io.github.gabyrlsilva.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(
            @Autowired Clientes clientes,
            @Autowired Pedidos pedidos){
        return args -> {
            System.out.println("Salvando clientes");
            Cliente pessoa = new Cliente("Gaby");
            clientes.save(pessoa);

            Pedido p = new Pedido();
            p.setCliente(pessoa);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));

            pedidos.save(p);

//            Cliente cliente = clientes.findClienteFetchPedidos(pessoa.getId());
//            System.out.println(cliente);
//            System.out.println(cliente.getPedidos());

            pedidos.findByCliente(pessoa).forEach(System.out::println);

//
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
