package com.ebc.ecard;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EbcApplicationTests {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("test your code");
    }
}