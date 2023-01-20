package com.boursinos.graphqldb.repositories.crypto;

import com.boursinos.graphqldb.model.crypto.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, String>, CryptoCustomRepository {

}
