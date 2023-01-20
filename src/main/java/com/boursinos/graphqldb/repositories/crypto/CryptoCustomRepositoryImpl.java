package com.boursinos.graphqldb.repositories.crypto;

import com.boursinos.graphqldb.model.crypto.Crypto;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;

public class CryptoCustomRepositoryImpl implements CryptoCustomRepository {

    EntityManager entityManager;

    public CryptoCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public String saveCrypto(Crypto crypto){
        crypto.setCreatedAt(new Date());
        crypto.setUpdatedAt(new Date());
        entityManager.persist(crypto);
        return crypto.getCryptoId();
    }

}
