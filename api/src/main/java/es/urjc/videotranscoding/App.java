package es.urjc.videotranscoding;

import org.apache.coyote.UpgradeProtocol;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

/**
 * Main APP and import xml config
 * 
 * @author luisca
 */
@SpringBootApplication
@Slf4j
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		log.info(" --------- App SpringBoot Started ------- ");
	}

	// @Bean
	// public TomcatConnectorCustomizer http2ProtocolCustomizer() {
	// 	return (connector) -> {
	// 		for (UpgradeProtocol upgradeProtocol : connector.findUpgradeProtocols()) {
	// 			if (upgradeProtocol instanceof Http2Protocol) {
	// 				Http2Protocol http2Protocol = (Http2Protocol) upgradeProtocol;
	// 				http2Protocol.setOverheadContinuationThreshold(0);
	// 				http2Protocol.setOverheadDataThreshold(0);
	// 				http2Protocol.setOverheadWindowUpdateThreshold(0);
	// 			}
	// 		}
	// 	};
	// }

}
