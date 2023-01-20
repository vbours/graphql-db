package com.boursinos.graphqldb.repositories.transaction;

import com.boursinos.graphqldb.model.crypto.Crypto;
import com.boursinos.graphqldb.model.transaction.Transaction;
import com.boursinos.graphqldb.repositories.crypto.CryptoCustomRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;

public class TransactionCustomRepositoryImpl implements TransactionCustomRepository {

    EntityManager entityManager;

    public TransactionCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public String saveTransaction(Transaction transaction){
        transaction.setCreatedAt(new Date());
        entityManager.persist(transaction);
        return transaction.getTransactionId();
    }

}
