package com.exam.catering;

import com.exam.catering.controller.ClientController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CateringExamProjectApplicationTests {

    @Autowired
    ClientController clientController;

    @Test
    void contextLoads() {
        Assertions.assertThat(clientController).isNotNull();
    }
}

