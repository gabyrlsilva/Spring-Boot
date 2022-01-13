package io.github.gabyrlsilva.domain.repository;

import io.github.gabyrlsilva.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {
    //Utilizando o Jpa
    //select c from Cliente c where c.nome like :nome

    @Query(value = " select * from cliente c where c.nome like '%:nome%'", nativeQuery = true)
    List<Cliente> encontrarPorNome( @Param("nome") String nome);

    @Query(value = " delete from Cliente c where c.nome = :nome ")
    @Modifying
    void deleteByNome(String nome);

    boolean existsByNome(String nome);

    @Query(value = " select c from Cliente c left join fetch c.pedidos where c.id = :id " )
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

}


//    Utilizando o Entity Manager

//    @Autowired
//    private EntityManager entityManager;
//
//    @Transactional
//    public Cliente salvar(Cliente cliente){
//        entityManager.persist(cliente);
//        return cliente;
//    }
//
//    @Transactional
//    public Cliente atualizar(Cliente cliente){
//        entityManager.merge(cliente);
//        return cliente;
//    }
//
//    @Transactional
//    public void deletar(Cliente cliente){
//        if (!entityManager.contains(cliente)){
//            cliente = entityManager.merge(cliente);
//        }
//        entityManager.remove(cliente);
//    }
//
//    @Transactional
//    public void deletar(Integer id){
//        Cliente cliente = entityManager.find( Cliente.class, id );
//        deletar(cliente);
//    }
//
//    @Transactional(readOnly = true)
//    public List<Cliente> buscarNome(String nome){
//        String jpql = " select c from Cliente c where c.nome like :nome ";
//        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
//        query.setParameter("nome", "%" + nome + "%");
//        return query.getResultList();
//    }
//
//    @Transactional(readOnly = true)
//    public List<Cliente> obterTodos(){
//        return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
//    }