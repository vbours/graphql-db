package com.boursinos.graphqldb.repositories.client;

import com.boursinos.graphqldb.model.client.Client;

public interface ClientCustomRepository {

    String saveClient(Client client);

}
