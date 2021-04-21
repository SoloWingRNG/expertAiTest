package com.ai.expert.test;



import com.ai.expert.test.service.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@ComponentScan(basePackages = {"com.ai.expert.test.*"})
@EntityScan("com.ai.expert.test.*")
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("classpath:expertAITester.properties")
public class TestApplication implements CommandLineRunner {

    @Autowired
    FileService fileService;

    private static final Logger log = LoggerFactory.getLogger(TestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fileService.storeFileDb();



        log.info("                                                                                  \n" +
                "         /\\    \\                 /\\    \\                 /\\    \\                /::\\    \\        |\\    \\         \n" +
                "        /::\\    \\               /::\\____\\               /::\\    \\              /::::\\    \\       |:\\____\\        \n" +
                "       /::::\\    \\             /::::|   |               \\:::\\    \\            /::::::\\    \\      |::|   |        \n" +
                "      /::::::\\    \\           /:::::|   |                \\:::\\    \\          /::::::::\\    \\     |::|   |        \n" +
                "     /:::/\\:::\\    \\         /::::::|   |                 \\:::\\    \\        /:::/~~\\:::\\    \\    |::|   |        \n" +
                "    /:::/__\\:::\\    \\       /:::/|::|   |                  \\:::\\    \\      /:::/    \\:::\\    \\   |::|   |        \n" +
                "   /::::\\   \\:::\\    \\     /:::/ |::|   |                  /::::\\    \\    /:::/    / \\:::\\    \\  |::|   |        \n" +
                "  /::::::\\   \\:::\\    \\   /:::/  |::|   | _____   _____   /::::::\\    \\  /:::/____/   \\:::\\____\\ |::|___|______  \n" +
                " /:::/\\:::\\   \\:::\\    \\ /:::/   |::|   |/\\    \\ /\\    \\ /:::/\\:::\\    \\|:::|    |     |:::|    |/::::::::\\    \\ \n" +
                "/:::/__\\:::\\   \\:::\\____/:: /    |::|   /::\\____/::\\    /:::/  \\:::\\____|:::|____|     |:::|    /::::::::::\\____\\\n" +
                "\\:::\\   \\:::\\   \\::/    \\::/    /|::|  /:::/    \\:::\\  /:::/    \\::/    /\\:::\\    \\   /:::/    /:::/~~~~/~~      \n" +
                " \\:::\\   \\:::\\   \\/____/ \\/____/ |::| /:::/    / \\:::\\/:::/    / \\/____/  \\:::\\    \\ /:::/    /:::/    /         \n" +
                "  \\:::\\   \\:::\\    \\             |::|/:::/    /   \\::::::/    /            \\:::\\    /:::/    /:::/    /          \n" +
                "   \\:::\\   \\:::\\____\\            |::::::/    /     \\::::/    /              \\:::\\__/:::/    /:::/    /           \n" +
                "    \\:::\\   \\::/    /            |:::::/    /       \\::/    /                \\::::::::/    /\\::/    /            \n" +
                "     \\:::\\   \\/____/             |::::/    /         \\/____/                  \\::::::/    /  \\/____/             \n" +
                "      \\:::\\    \\                 /:::/    /                                    \\::::/    /                       \n" +
                "       \\:::\\____\\               /:::/    /                                      \\::/____/                        \n" +
                "        \\::/    /               \\::/    /                                        ~~                              \n" +
                "         \\/____/                 \\/____/                                                                         \n" +
                "                                                                                       ");
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }



}
