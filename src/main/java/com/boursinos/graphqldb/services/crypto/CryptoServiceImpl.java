package com.boursinos.graphqldb.services.crypto;

import com.boursinos.graphqldb.model.crypto.Crypto;
import com.boursinos.graphqldb.repositories.crypto.CryptoRepository;
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
public class CryptoServiceImpl implements CryptoService {

    @Autowired
    private CryptoRepository cryptoRepository;

    @Override
    public List<Crypto> getAllCryptos() {
        return cryptoRepository.findAll();
    }

    @GraphQLQuery
    @Override
    public List<Crypto> getAllCryptos(int count) {
        return this.cryptoRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @GraphQLQuery
    @Override
    public Optional<Crypto> getCrypto(String id) {
        return cryptoRepository.findById(id);
    }

    @Transactional
    public String saveCrypto(Crypto crypto) {
        return cryptoRepository.saveCrypto(crypto);
    }

    @GraphQLMutation
    @Override
    public Crypto createCrypto(String name, double value) {
        Crypto crypto = new Crypto(name, value);
        String clientId = cryptoRepository.saveCrypto(crypto);
        crypto.setCryptoId(clientId);
        return crypto;
    }

    @GraphQLMutation
    @Override
    public Optional<Crypto> deleteCrypto(String id) {
        cryptoRepository.deleteById(id);
        return getCrypto(id);
    }

    @GraphQLMutation
    @Override
    public Crypto updateCrypto(String id, String name, double value) {
        Crypto oldCrypto = cryptoRepository.getById(id);
        Crypto crypto = new Crypto(name, value);
        crypto.setCryptoId(id);
        crypto.setUpdatedAt(new Date());
        crypto.setCreatedAt(oldCrypto.getCreatedAt());
        cryptoRepository.save(crypto);
        return crypto;
    }

}
