package com.boursinos.graphqldb.services.transaction;

import com.boursinos.graphqldb.model.client.Client;
import com.boursinos.graphqldb.model.crypto.Crypto;
import com.boursinos.graphqldb.model.transaction.TradeType;
import com.boursinos.graphqldb.model.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getAllTransactions(int count);

    public Optional<Transaction> getTransaction(String id);

    String saveTransaction(Transaction transaction);

    Optional<Transaction> deleteTransaction(String id);

    public Transaction createTransaction(String clientId, TradeType tradeType, String cryptoId, double amount, double totalValue);
}
