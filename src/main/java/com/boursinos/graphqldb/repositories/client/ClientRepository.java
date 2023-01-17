package com.boursinos.graphqldb.repositories.client;

import com.boursinos.graphqldb.model.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>, ClientCustomRepository {

}
