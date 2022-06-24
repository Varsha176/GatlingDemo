package computerdatabase;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class demoblazeSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://api.demoblaze.com")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
  ;
  
  private String uri1 = "https://www.demoblaze.com";
  
  private String uri3 = "https://hls.demoblaze.com";

  private ScenarioBuilder scn = scenario("demoblazeSimulation")
    .exec(
      http("request_0")
        .options("/bycat")
              .resources(
          http("request_1")
            .post("/bycat").body(RawFileBody("computerdatabase/demoblazesimulation/0001_request.json"))
        )
    )
    .pause(1)
    .exec(
      http("request_2")
        .get(uri1 + "/prod.html?idp_=8")

        .resources(
          http("request_3")
            .get(uri1 + "/config.json")
           ,
          http("request_4")
            .get(uri3 + "/index.m3u8"),
          http("request_5")
            .get(uri3 + "/about_demo_hls_600k.m3u8"),
          http("request_6")
            .get(uri3 + "/about_demo_hls_600k00000.ts"),
          http("request_7")
            .options("/view")
            ,
          http("request_8")
            .post("/view")

            .body(RawFileBody("computerdatabase/demoblazesimulation/0008_request.json"))
        )
    )
    .pause(1)
    .exec(
      http("request_9")
        .options("/addtocart")

        .resources(
          http("request_10")
            .post("/addtocart")

            .body(RawFileBody("computerdatabase/demoblazesimulation/0010_request.html"))
        )
    )
    .pause(3)
    .exec(
      http("request_11")
        .get(uri1 + "/cart.html")

        .resources(
          http("request_12")
            .get(uri1 + "/config.json"),
          http("request_13")
            .get(uri3 + "/index.m3u8"),
          http("request_14")
            .get(uri3 + "/about_demo_hls_600k.m3u8"),
          http("request_15")
            .get(uri3 + "/about_demo_hls_600k00000.ts"),
          http("request_16")
            .options("/viewcart")
          ,
          http("request_17")
            .post("/viewcart")

            .body(RawFileBody("computerdatabase/demoblazesimulation/0017_request.json")),
          http("request_18")
            .options("/view")
           ,
          http("request_19")
            .post("/view")

            .body(RawFileBody("computerdatabase/demoblazesimulation/0019_request.json"))
        )
    )
    .pause(10)
    .exec(
      http("request_20")
        .options("/deleteitem")

        .resources(
          http("request_21")
            .post("/deleteitem")

            .body(RawFileBody("computerdatabase/demoblazesimulation/0021_request.json")),
          http("request_22")
            .get(uri1 + "/cart.html")

            .check(bodyBytes().is(RawFileBody("computerdatabase/demoblazesimulation/0022_response.bin"))),
          http("request_23")
            .get(uri1 + "/config.json")

            .check(bodyBytes().is(RawFileBody("computerdatabase/demoblazesimulation/0023_response.bin"))),
          http("request_24")
            .get(uri3 + "/index.m3u8")
            .check(bodyBytes().is(RawFileBody("computerdatabase/demoblazesimulation/0024_response.dat"))),
          http("request_25")
            .get(uri3 + "/about_demo_hls_600k.m3u8")
            .check(bodyBytes().is(RawFileBody("computerdatabase/demoblazesimulation/0025_response.dat"))),
          http("request_26")
            .get(uri3 + "/about_demo_hls_600k00000.ts")
            .check(bodyBytes().is(RawFileBody("computerdatabase/demoblazesimulation/0026_response.txt"))),
          http("request_27")
            .options("/viewcart")
          ,
          http("request_28")
            .post("/viewcart")

            .body(RawFileBody("computerdatabase/demoblazesimulation/0028_request.json"))
            .check(bodyBytes().is(RawFileBody("computerdatabase/demoblazesimulation/0028_response.json")))
        )
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
