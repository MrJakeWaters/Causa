package com.causa;

import java.net.URI;
import java.util.List;
import io.weaviate.client.base.Result;
import io.weaviate.client.base.WeaviateErrorMessage;
import org.springframework.web.bind.annotation.GetMapping;
import io.weaviate.client.v1.batch.model.ObjectGetResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@ResponseBody
@RequestMapping("/v1")
public class ControllerV1 {

	private final WeaviateAbstract db = new WeaviateAbstract("localhost:8081");

	@GetMapping("/hello")
	public String whatUp() {
		return "What Up from Causa API\n";
	}
	
	@PostMapping("/create_decision")
	public Result<ObjectGetResponse[]> createDecision(@RequestBody ApiDecision apiDecision) {
		Decision decision = new Decision(apiDecision.getProposition(), apiDecision.getEntity());
		Result<ObjectGetResponse[]> results = db.batchLoad(decision);
		if (results.hasErrors()) {
			results.toErrorResult();
			System.out.println("Batch import errors");
			for (WeaviateErrorMessage message: results.getError().getMessages()) {
				System.out.println(message.getMessage());
			}
		} else {
			System.out.println("Import Success");
		}
		return results;
	}
}
