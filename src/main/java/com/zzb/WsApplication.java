package com.zzb;

import com.zzb.ws.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WsApplication implements CommandLineRunner {

	@Autowired
	WebSocketServer socketServer;

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		socketServer.run(8080);
	}
}

