package com.boursinos.graphqldb.services.client;

import com.boursinos.graphqldb.model.client.Client;
import com.boursinos.graphqldb.repositories.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients(){
       return clientRepository.findAll();
    }

    @Override
    public List<Client> getAllClients(int count) {
        return this.clientRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @Override
    public Optional<Client> getClient(String id){
        return clientRepository.findById(id);
    }

    @Transactional
    public String saveClient(Client client){
        return clientRepository.saveClient(client);
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

    @Override
    public Client createClient(String firstname, String lastname) {
        final Client client = new Client();
        client.setFirstname(firstname);
        client.setLastname(lastname);
        client.setCreatedAt(new Date());
        client.setUpdatedAt(new Date());
        return this.clientRepository.save(client);
    }


}
