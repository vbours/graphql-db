package com.boursinos.graphqldb.repositories.client;

import com.boursinos.graphqldb.model.client.Client;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;

public class ClientCustomRepositoryImpl implements ClientCustomRepository {

    EntityManager entityManager;

    public ClientCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public String saveClient(Client client){
        client.setCreatedAt(new Date());
        client.setUpdatedAt(new Date());
        entityManager.persist(client);
        return client.getClientId();
    }

}
