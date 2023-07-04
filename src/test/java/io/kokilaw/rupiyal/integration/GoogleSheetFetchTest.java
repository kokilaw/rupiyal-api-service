package io.kokilaw.rupiyal.integration;

import io.kokilaw.rupiyal.RupiyalApiServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Created by kokilaw on 2023-07-04
 */

@SpringBootTest(classes = RupiyalApiServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleSheetFetchTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

}
