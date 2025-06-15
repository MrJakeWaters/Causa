package com.causa;

import io.weaviate.client.Config;
import io.weaviate.client.base.Result;
import io.weaviate.client.WeaviateClient;
import io.weaviate.client.v1.batch.Batch;
import io.weaviate.client.v1.batch.api.ObjectsBatcher;
import io.weaviate.client.v1.data.model.WeaviateObject;
import io.weaviate.client.v1.batch.model.ObjectGetResponse;

public class WeaviateAbstract {
	// object to load to and receive from Weaviate database
	private String endpoint;
	private Config endpointConfig;
	private WeaviateClient client;

	public WeaviateAbstract(String endpoint) {
		this.endpoint = endpoint;
		this.endpointConfig = new Config("http", this.endpoint);
		this.client = new WeaviateClient(this.endpointConfig);
	}

	public WeaviateClient getClient() {
		return this.client;
	}

	public String getEndpoint() {
		return this.endpoint;
	}
	
	public Result<ObjectGetResponse[]> batchLoad(Decision decision) {
		Abstract objAbstract = new Abstract(decision);
		WeaviateObject obj = WeaviateObject.builder()
			.className(objAbstract.getName())
			.properties(objAbstract.getMap())
			.build();			
		ObjectsBatcher batch = this.client.batch().objectsBatcher();
		batch.withObjects(obj);
		Result<ObjectGetResponse[]> batchResult = batch.run();
		return batchResult;
	}

	public Result<ObjectGetResponse[]> batchLoad(Rationale rationale) {
		Abstract objAbstract = new Abstract(decision);
		WeaviateObject obj = WeaviateObject.builder()
			.className(objAbstract.getName())
			.properties(objAbstract.getMap())
			.build();			
		ObjectsBatcher batch = client.batch().objectsBatcher();
		batch.withObjects(obj);
		Result<ObjectGetResponse[]> batchResult = batch.run();
		return batchResult;
	}
}
