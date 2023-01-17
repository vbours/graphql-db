package com.boursinos.graphqldb.services.client;

import com.boursinos.graphqldb.model.client.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getAllClients();

    List<Client> getAllClients(int count);

    public Optional<Client> getClient(String id);

    String saveClient(Client client);

    void deleteClient(String id);

    Client updateClient(String id, Client client);

    Client createClient(final String firstname, final String lastname);

}
