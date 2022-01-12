package io.github.gabyrlsilva.service;

import io.github.gabyrlsilva.model.Cliente;
import io.github.gabyrlsilva.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {
    private ClientesRepository repository;

    @Autowired
    //injecao de dependencia atraves do set ou construtor
    public  ClientesService(ClientesRepository repository){
        this.repository = repository;
    }

    public  void salvarClientes(Cliente cliente){
        validarCliente(cliente);
        this.repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente){
        //aplica validacao
    }
}
