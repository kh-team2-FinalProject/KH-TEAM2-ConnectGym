package com.khteam2.connectgym;

import com.khteam2.connectgym.member.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    exclude = {
        org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
        org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
        org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
    }
)
public class ConnectgymApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectgymApplication.class, args);
        System.out.println("server start");

	}

}
