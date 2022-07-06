package API_Tests;


import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class API_Test01 extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://reqres.in/api/users");
    private ScenarioBuilder users = scenario("Get API Request Demo").exec(
            http("Get Single User")
                    .get("/2").check(
                            status().is(200)).
                    check(jsonPath("$.data.first_name").is("Janet"))
    ).pause(1);

    {
        setUp(
                users.injectOpen(atOnceUsers(10))).protocols(httpProtocol);
    }
}