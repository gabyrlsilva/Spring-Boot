package io.github.gabyrlsilva;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@Development
public class AppConfiguration {

    @Bean
    public CommandLineRunner executar(){
        return args -> {
          System.out.println("RODANDO O AMBIENTE DE DESENVOLVIMENTO");
        };
    }

}
