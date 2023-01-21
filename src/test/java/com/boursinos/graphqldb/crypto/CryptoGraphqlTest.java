package com.boursinos.graphqldb.crypto;

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
class CryptoGraphqlTest {

    private static final String GRAPHQL_PATH = "/graphql";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetAllCryptos_thenValidResponseReturned() {
        String getAllCryptosQuery = "{allCryptos(count: 100){ cryptoId, createdAt }}";

        byte[] results = webTestClient.post()
                .uri(GRAPHQL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(toJSON(getAllCryptosQuery)), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult().getResponseBodyContent();

        String body = new String(results, StandardCharsets.UTF_8); // for UTF-8 encoding
        Assert.assertTrue(body != null);
    }

    @Test
    void whenGetCrypto_thenValidResponseReturned() {
        String getClientQuery = "{crypto(id: \"ff80818185ce7f690185ce8f0c4d0000\"){ cryptoId, name, value }}";

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
        assertThat(body, hasJsonPath("$.data.crypto.name"));
        assertThat(body, hasJsonPath("$.data.crypto.value"));
        assertThat(body, hasJsonPath("$.data.crypto.name", equalTo("btc")));
    }

    @Test
    void whenCreateCrypto_thenValidResponseReturned() {
        String createCrypto = "mutation { createCrypto ( name:\"sol\",value:300){cryptoId, name, value}}";


        byte[] results = webTestClient.post()
                .uri(GRAPHQL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(toJSON(createCrypto)), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult().getResponseBody();

        String body = new String(results, StandardCharsets.UTF_8);
        System.out.println(body);
        Assert.assertTrue(body != null);
        assertThat(body, hasJsonPath("$.data.createCrypto.cryptoId"));
        assertThat(body, hasJsonPath("$.data.createCrypto.name"));
        assertThat(body, hasJsonPath("$.data.createCrypto.name", equalTo("sol")));
    }

    private static String toJSON(String query) {
        try {
            return new JSONObject().put("query", query).toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}