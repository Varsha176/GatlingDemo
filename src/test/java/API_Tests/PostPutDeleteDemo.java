package API_Tests;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PostPutDeleteDemo extends Simulation {

    //protocol
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://reqres.in/api");

    //sceanrio
    private ScenarioBuilder Createusers = scenario("Create User").exec(
            http("Create a new User")
                    .post("/users").header("content-type","application/json")
                    .asJson()
                    .body(RawFileBody("Data/user.json"))
//                    .body(StringBody("{\n" +
//                            "    \"name\": \"morpheus\",\n" +
//                            "    \"job\": \"leader\"\n" +
//                            "}")).asJson()
                    .check(status().is(201),jsonPath("$.name").is("qaautomationhub"))).pause(1);

    private ScenarioBuilder Upateusers = scenario("Update User").exec(
            http("Update the User")
                    .put("/users/2").header("content-type","application/json")
                    .asJson()
                    .body(RawFileBody("Data/user.json"))
//
                    .check(status().is(200),jsonPath("$.name").is("qaautomationhub")));

    private ScenarioBuilder Deleteusers = scenario("Delete User").exec(
            http("Delete the User")
                    .delete("/users/2").header("content-type","application/json")
                    .check(status().is(204)));
    private ScenarioBuilder FetchUsers = scenario("Get API Request Demo").exec(
            http("Get Single User")
                    .get("/users/2").check(
                            status().is(200)).
                    check(jsonPath("$.data.first_name").is("Janet"))
    ).pause(1);
    {
        setUp(
                Createusers.injectOpen(rampUsers(5).during(5)),
                Upateusers.injectOpen(rampUsers(10).during(7)),
                Deleteusers.injectOpen(rampUsers(10).during(7))).protocols(httpProtocol);
               FetchUsers.injectOpen(atOnceUsers(30)).protocols(httpProtocol);
    }
}
