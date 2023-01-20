package com.boursinos.graphqldb.repositories.transaction;

import com.boursinos.graphqldb.model.crypto.Crypto;
import com.boursinos.graphqldb.model.transaction.Transaction;
import com.boursinos.graphqldb.repositories.crypto.CryptoCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>, TransactionCustomRepository {

}
