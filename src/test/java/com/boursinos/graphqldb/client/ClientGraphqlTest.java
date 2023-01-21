package com.boursinos.graphqldb.client;

import com.boursinos.graphqldb.GraphqlDbApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = GraphqlDbApplication.class)
class ClientGraphqlTest {

    private static final String GRAPHQL_PATH = "/graphql";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetAllClients_thenValidResponseReturned() {
        String getAllClientsQuery = "{allClientsQuery{ clientId, createdAt }}";

        byte[] results = webTestClient.post()
                .uri(GRAPHQL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(toJSON(getAllClientsQuery)), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult().getResponseBodyContent();

        String body = new String(results, StandardCharsets.UTF_8); // for UTF-8 encoding
        Assert.assertTrue(body != null);
    }

    @Test
    void whenGetClient_thenValidResponseReturned() {
        String getClientQuery = "{client(id: \"ff80818185c6dec50185c6df69ee0000\"){ clientId, firstname, createdAt }}";

        byte[] results = webTestClient.post()
                .uri(GRAPHQL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(toJSON(getClientQuery)), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult().getResponseBodyContent();

        String body = new String(results, StandardCharsets.UTF_8); // for UTF-8 encoding
        Assert.assertTrue(body != null);
        assertThat(body, hasJsonPath("$.data.client.firstname"));
        assertThat(body, hasJsonPath("$.data.client.createdAt"));
        assertThat(body, hasJsonPath("$.data.client.firstname", equalTo("vasilis")));
    }

    @Test
    void whenCreateClient_thenValidResponseReturned() {
        String createClient = "mutation { createClient(firstname:\"rebecca\",lastname:\"marta\"){clientId,firstname,lastname,createdAt}}";


        byte[] results = webTestClient.post()
                .uri(GRAPHQL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(toJSON(createClient)), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult().getResponseBody();

        String body = new String(results, StandardCharsets.UTF_8);
        System.out.println(body);
        Assert.assertTrue(body != null);
        assertThat(body, hasJsonPath("$.data.createClient.firstname"));
        assertThat(body, hasJsonPath("$.data.createClient.createdAt"));
        assertThat(body, hasJsonPath("$.data.createClient.firstname", equalTo("rebecca")));
    }

    private static String toJSON(String query) {
        try {
            return new JSONObject().put("query", query).toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}