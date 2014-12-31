package openstats.client.rest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import openstats.model.Assembly;

public class RESTClient {
	
	private Client client;
			
	public RESTClient() {
		client = ClientBuilder.newClient();
	}
	
	public void close() {
		client.close();
	}

	public Assembly getTemplateAssembly(String state, String session) {
		WebTarget myResource = client.target("http://localhost:8080/openstats/rest")
			.path("/template/{state}/{session}")
			.resolveTemplate("state", state)
			.resolveTemplate("session", session);
		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		try {
			return builder.get(Assembly.class);
		} catch ( BadRequestException e ) {
			throw new RuntimeException(builder.head().getHeaderString("error"), e);
		}
	}
	
	public void updateAssembly(Assembly assembly) {
		WebTarget myResource = client.target("http://localhost:8080/openstats/rest");
		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		Response response=null;
		try {
			response = builder.put(Entity.json(assembly));
		} catch ( BadRequestException e ) {
			System.out.print("BadRequest : " + e.getMessage()+":");
			System.out.println(builder.head().getHeaderString("error"));
		} finally {
			if ( response != null ) response.close();
		}

	}
	
}
