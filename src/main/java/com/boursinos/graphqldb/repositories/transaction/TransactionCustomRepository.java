package com.boursinos.graphqldb.repositories.transaction;

import com.boursinos.graphqldb.model.transaction.Transaction;

public interface TransactionCustomRepository {

    String saveTransaction(Transaction transaction);

}
