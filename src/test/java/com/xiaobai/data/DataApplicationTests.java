package com.xiaobai.data;

import com.xiaobai.data.service.DataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataApplicationTests {

    @Autowired
    private DataService dataService;

    @Test
    void contextLoads() {

        dataService.getData("");
    }

}
