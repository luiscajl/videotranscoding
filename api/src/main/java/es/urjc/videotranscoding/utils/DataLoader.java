package es.urjc.videotranscoding.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * To charge the users at started the service
 * 
 * @author luisca
 *
 */
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

	public void run(String... strings) throws Exception {
		log.info("CommandLineRunner");
	}
}
