package com.boursinos.graphqldb.transaction;

import com.boursinos.graphqldb.GraphqlDbApplication;
import graphql.Assert;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = GraphqlDbApplication.class)
class TransactionGraphqlTest {

    private static final String GRAPHQL_PATH = "/graphql";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetAllTransactions_thenValidResponseReturned() {
        String getAllTransactionsQuery = "{allTransactions(count: 100){ transactionId }}";

        byte[] results = webTestClient.post()
                .uri(GRAPHQL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(toJSON(getAllTransactionsQuery)), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult().getResponseBodyContent();

        String body = new String(results, StandardCharsets.UTF_8); // for UTF-8 encoding
        Assert.assertTrue(body != null);
    }

    @Test
    void whenGetTransaction_thenValidResponseReturned() {
        String getTransactionQuery = "{transaction(id: \"ff80818185cef1910185cef2bf9e0001\"){ amount, totalValue }}";

        byte[] results = webTestClient.post()
                .uri(GRAPHQL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(toJSON(getTransactionQuery)), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult().getResponseBodyContent();

        String body = new String(results, StandardCharsets.UTF_8); // for UTF-8 encoding
        Assert.assertTrue(body != null);
        assertThat(body, hasJsonPath("$.data.transaction.amount"));
        assertThat(body, hasJsonPath("$.data.transaction.totalValue"));
        assertThat(body, hasJsonPath("$.data.transaction.amount", equalTo(1.0)));
    }

    @Test
    void whenCreateTransaction_thenValidResponseReturned() {
        String createTransaction = "mutation{createTransaction(tradeType:SELL,amount:3,totalValue:1000,clientId:\"ff80818185c6dec50185c6df69ee0000\",cryptoId:\"ff80818185ce7f690185ce8f0c4d0000\"){transactionId, client{firstname}}}";


        byte[] results = webTestClient.post()
                .uri(GRAPHQL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(toJSON(createTransaction)), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult().getResponseBody();

        String body = new String(results, StandardCharsets.UTF_8);
        System.out.println(body);
        Assert.assertTrue(body != null);
        assertThat(body, hasJsonPath("$.data.createTransaction.transactionId"));
        assertThat(body, hasJsonPath("$.data.createTransaction.client.firstname"));
        assertThat(body, hasJsonPath("$.data.createTransaction.client.firstname", equalTo("vasilis")));
    }

    private static String toJSON(String query) {
        try {
            return new JSONObject().put("query", query).toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}