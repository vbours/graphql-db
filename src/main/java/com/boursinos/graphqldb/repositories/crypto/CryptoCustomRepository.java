package com.boursinos.graphqldb.repositories.crypto;

import com.boursinos.graphqldb.model.crypto.Crypto;

public interface CryptoCustomRepository {

    String saveCrypto(Crypto crypto);

}
