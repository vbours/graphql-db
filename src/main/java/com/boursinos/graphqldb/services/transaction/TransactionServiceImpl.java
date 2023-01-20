package com.boursinos.graphqldb.services.transaction;

import com.boursinos.graphqldb.model.client.Client;
import com.boursinos.graphqldb.model.crypto.Crypto;
import com.boursinos.graphqldb.model.transaction.TradeType;
import com.boursinos.graphqldb.model.transaction.Transaction;
import com.boursinos.graphqldb.repositories.client.ClientRepository;
import com.boursinos.graphqldb.repositories.crypto.CryptoRepository;
import com.boursinos.graphqldb.repositories.transaction.TransactionRepository;
import com.boursinos.graphqldb.services.crypto.CryptoService;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GraphQLApi
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @GraphQLQuery
    @Override
    public List<Transaction> getAllTransactions(int count) {
        return this.transactionRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @GraphQLQuery
    @Override
    public Optional<Transaction> getTransaction(String id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public String saveTransaction(Transaction transaction) {
        return transactionRepository.saveTransaction(transaction);
    }

    @GraphQLMutation
    @Override
    public Transaction createTransaction(String clientId, TradeType tradeType, String cryptoId, double amount, double totalValue) {
        Client client = clientRepository.getById(clientId);
        Crypto crypto = cryptoRepository.getById(cryptoId);
        Transaction transaction = new Transaction(client, tradeType, crypto, amount, totalValue);
        String transactionId = transactionRepository.saveTransaction(transaction);
        transaction.setTransactionId(transactionId);
        return transaction;
    }

    @GraphQLMutation
    @Override
    public Optional<Transaction> deleteTransaction(String id) {
        transactionRepository.deleteById(id);
        return getTransaction(id);
    }
}
