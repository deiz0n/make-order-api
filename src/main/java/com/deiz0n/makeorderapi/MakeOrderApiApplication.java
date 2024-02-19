package com.deiz0n.makeorderapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "make-order-api",
        version = "1.0",
        description = "API desenvolvida com o intuito de otimizar o gerenciamento de pedidos"
))
public class MakeOrderApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakeOrderApiApplication.class, args);
    }

}
