package com.boursinos.graphqldb.services.crypto;

import com.boursinos.graphqldb.model.crypto.Crypto;

import java.util.List;
import java.util.Optional;

public interface CryptoService {

    List<Crypto> getAllCryptos();

    List<Crypto> getAllCryptos(int count);

    public Optional<Crypto> getCrypto(String id);

    String saveCrypto(Crypto crypto);

    Optional<Crypto> deleteCrypto(String id);

    Crypto updateCrypto(String id, String name, double value);

    Crypto createCrypto(final String name, final double value);

}
