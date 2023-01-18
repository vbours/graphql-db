package com.boursinos.graphqldb.services.client;

import com.boursinos.graphqldb.model.client.Client;
import com.boursinos.graphqldb.repositories.client.ClientRepository;
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
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients(){
       return clientRepository.findAll();
    }

    @GraphQLQuery
    @Override
    public List<Client> getAllClients(int count) {
        return this.clientRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @GraphQLQuery
    @Override
    public Optional<Client> getClient(String id){
        return clientRepository.findById(id);
    }

    @Transactional
    public String saveClient(Client client){
        return clientRepository.saveClient(client);
    }

    @GraphQLMutation
    public Client createClient(String firstname, String lastname) {
        Client client = new Client();
        client.setFirstname(firstname);
        client.setLastname(lastname);
        String clientId = clientRepository.saveClient(client);
        client.setClientId(clientId);
        return client;
    }

    @Override
    public void deleteClient(String id){
        clientRepository.deleteById(id);
    }

    @Override
    public Client updateClient(String id, Client client){
        Client oldClient = clientRepository.getById(id);
        client.setClientId(id);
        client.setUpdatedAt(new Date());
        client.setCreatedAt(oldClient.getCreatedAt());
        clientRepository.save(client);
        return client;
    }

}
