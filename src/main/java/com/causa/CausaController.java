package com.causa;

// external
import java.util.List;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class CausaController {
	
	@GetMapping("/hello")
	public String whatUp() {
		return "What Up from Causa API\n";
	}
	
	@PostMapping("/create_decision")
	public String createDecision(@RequestBody ApiDecision apiDecision) {
		// https://www.elastic.co/docs/reference/elasticsearch/clients/java/transport/rest-client/usage/requests
		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
		return "Testing";
	}
}
