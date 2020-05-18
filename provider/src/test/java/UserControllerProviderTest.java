import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("provider")
@PactFolder("$rootDir/Pacts")
@Disabled
public class UserControllerProviderTest {
    private static final String FULL_NAME = "Alex_First";
    private static final String USER_NAME = "user_Alex";

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8080, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Create a new Client")
    public void toCreateUserState() {
        var client = new Client(FULL_NAME, USER_NAME);

        // nothing to do, real service is used

    }

    @State("Get a list of usernames from DB")
    public void userIsPresent() {
        // nothing to do, real service is used
    }


}
