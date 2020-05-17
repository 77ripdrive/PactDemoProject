import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "provider", port = "8181")

public class BasePactTest {

    private static final String EXPECTED_SESSION_ID = "x-session-id: 22a0cb26-955b-4180-8631-b2965c1c01d4";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json.*";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";
    private static final String CHALLENGE_CLIENTS = "/challenge/clients";
    private static final String FULL_NAME = "Alex_First";
    private static final String USER_NAME = "user_Alex";
    private Utils utils = new Utils();

    private Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[]{
            CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8
    });


    @Pact(consumer = "client_consumer")
    public RequestResponsePact createClientPact(PactDslWithProvider builder) {
        return builder
                .given("Create a new Client")
                .uponReceiving("a request to POST a client")
                .path(CHALLENGE_CLIENTS)
                .method("POST")
                .body(new PactDslJsonBody()
                        .stringType("fullName", FULL_NAME)
                        .stringType("userName", USER_NAME))
                .willRespondWith()
                .status(200)
                .matchHeader(CONTENT_TYPE, APPLICATION_JSON)
                .body(new PactDslJsonBody()
                        .stringType("resultCode", "Ok"))
                .toPact();
    }

    @Pact(consumer = "client_consumer")
    public RequestResponsePact updateClientPact(PactDslWithProvider builder) {
        return builder
                .given("Get a list of usernames from DB")
                .uponReceiving(" a request to GET a clients ")
                .path(CHALLENGE_CLIENTS)
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(utils.readJsonFile("/getClients.json"))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createClientPact")
    void testPactAddClientPostResponse(MockServer mockServer) throws IOException {
        var restClient = new DummyMessageApp(mockServer.getUrl());
        var actualResult = restClient.
                postResponseAddNewUser(CHALLENGE_CLIENTS, FULL_NAME, USER_NAME).getStatusLine().getStatusCode();
        System.out.println(actualResult);
        assertThat(actualResult, is(200));
        assertThat(EntityUtils.toString(restClient.postResponseAddNewUser(CHALLENGE_CLIENTS, FULL_NAME, USER_NAME).getEntity()),
                is(equalTo(utils.readJsonFile("/postAddClientResult.json").toString())));

    }

    @Test
    @PactTestFor(pactMethod = "updateClientPact")
    void testPactClientExistGetResponse(MockServer mockServer) throws IOException {
        var restClient = new DummyMessageApp(mockServer.getUrl());
        var actualResult = restClient
                .getResponse(CHALLENGE_CLIENTS).getStatusLine().getStatusCode();
        assertThat(actualResult, is(200));
    }
}
