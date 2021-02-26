package javvernaut.alfabank;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static javvernaut.alfabank.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    protected static final WireMockServer wireMockServer = new WireMockServer(options().port(8888).notifier(new ConsoleNotifier(true)));

    @BeforeAll
    private static void start() {
        wireMockServer.start();
        giphyStubs();
        oerStubs();
    }

    @AfterAll
    private static void stop() {
        wireMockServer.stop();
    }

    private static void oerStubs() {
        wireMockServer.stubFor(get(urlEqualTo(OER_CURRENT_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Etag", "W/\"333\"")
                        .withHeader("Date", "Thu, 22 Feb 2021 13:13:29 GMT")
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/openexchangerates/responseSingleCurrent.json")));
        wireMockServer.stubFor(get(urlEqualTo(OER_PREVIOUS_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Etag", "W/\"444\"")
                        .withHeader("Date", "Thu, 22 Feb 2021 13:13:29 GMT")
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/openexchangerates/responseSinglePrevious.json")));
    }

    private static void giphyStubs() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(GIPHY_URL_GROWTH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/giphy/growth.json")));
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(IMAGE_URL_GROWTH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "image/gif")
                        .withBody(IMAGE_DATA_GROWTH)));
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(GIPHY_URL_FALL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/giphy/fall.json")));
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(IMAGE_URL_FALL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "image/gif")
                        .withBody(IMAGE_DATA_FALL)));
    }
}
