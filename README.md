# graphql-db project

### Technologies ###

* Java Spring Boot
* Graphql
* JPA - Hibernate
* Unit tests
* GraphQL SPQR

### Description ###

Java Spring Boot application (microservice) which is connected with
a postgres db in order to execute operations. In this project there are three tables, which can help us to
run some graphql queries, in order to understand better the advantages of this communication way.
We use GraphQL SPQR to support Graphql in this project.GraphQL SPQR (GraphQL Schema Publisher & Query Resolver) is
a simple-to-use library for rapid development of GraphQL APIs in Java.

**Advantages of GraphQL**

GraphQL offers many benefits over REST APIs.

* One of the main benefits is clients have the ability to dictate exactly
  what they need from the server, and receive that data in a predictable way.
* Another big benefit is the ability to retrieve many resources in a single request. Continuing with our example schema,
  imagine we want to also fetch the names of the clients and also crypto data (name, value) when we ask for a
  transaction query. For a traditional REST API, we would probably need to make an additional request to a /client?id=X
  endpoint and one more to /crypto?id=y, passing in our client‘s and crypto's ID.

### Specification ###

<li>Language : Java
<li>Framework : Spring boot, JPA, Hibernate
<li>Graphql
<li>Unit Tests
<li>Database storage : Postgres

### Installation ###

GraphQL SPQR is deployed to Maven Central.

```
<dependency>
    <groupId>io.leangen.graphql</groupId>
    <artifactId>spqr</artifactId>
    <version>0.12.0</version>
</dependency>
```

### Deploy external services ###

This project needs a postgres db. In order to deploy Postgres as a docker container:

* Go to root directory
* Run
  ```
  docker-compose up --build
  ```

### Important Links ###

The graphql ui link to run the queries is :
graphql: http://0.0.0.0:8091/gui

### Database schema ###

![alt text](https://user-images.githubusercontent.com/112252315/213863412-4b4720be-fc02-4b14-a912-86b4f7ee32c5.jpg)

### Queries and Mutations examples ###

We can open the graphql ui to run our queries.

#### Mutations (create) ####

1. When we want to create a new client and we ask to return clientId and createdAt only.

```
mutation {
  createClient(firstname: "vasilis", lastname: "boursinos")
  {
    clientId,
    createdAt
  }
}
```

2. When we want to create a new crypto record and we ask to return only the cryptoId.

```
mutation {
  createCrypto(name: "bnb", value: 300)
  {
    cryptoId
  }
}
```

3. When we want to create a new transaction record and we ask to return only the transactionId (clientId and cryptoId
   are
   foreign keys of the other tables).

```
mutation {
  createTransaction(tradeType: BUY, amount: 1, totalValue: 1000, clientId: "ff80818185c6dec50185c6df69ee0000", cryptoId: "ff80818185ce7f690185ce8f0c4d0000" )
  {
    transactionId
  }
}
```

#### Mutations (update) ####

1. When we want to update an existing record and we want as a response clientId, the updated firstname and the update
   lastname

```
mutation {
    updateClient(id: "ff80818185c5ea920185c5f765e40001", firstname: "Chris", lastname: "m")
    {
        clientId,
        firstname,
        lastname
    }
}
```

#### Mutations (delete) ####

1. When we want to delete an existing record and we want as a response the clientId

```
mutation {
  deleteClient(id: "ff80818185c5ea920185c5eb620d0000") 
  {
    clientId
  }
}
```

#### Queries (getAll) ####

1. We ask to retrieve the client records (first 100) and to show only clientId, firstname, lastname

```
query {
  allClients(count: 100) 
  {	
	clientId,
    firstname,
    lastname
	}
}
```

2. We ask to retrieve the crypto records (first 100) and to show only cryptoId, name, value and createdAt

```
query {
  allCryptos(count: 100) 
  {	
	cryptoId,
    name,
    value,
    createdAt
	}
}
```

3. We ask to retrieve the transaction records (first 100) and to show not only transactionId and amount (as we can do
   with rest), but also information related to crypto and client like clientId, firstname, lastname and cryptoId, name.

```
query {
  allTransactions(count: 100) 
  {	
	transactionId,
    amount
  	client{
      clientId,
      firstname,
      lastname
  	}
    crypto{
      cryptoId,
      name
    }
  }
}
```

#### Queries (get by Id) ####

As we can see, we can ask for a single record as rest can do but we can select what field we want to show. In our
example we select the firstname.

```
query {
    client(id: "ff80818185c5ea920185c5f765e40001")
    {
        firstname
    }
}
```