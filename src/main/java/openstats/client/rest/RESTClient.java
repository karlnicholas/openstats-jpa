package openstats.client.rest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

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
			System.out.println("OK = " + Status.OK.getStatusCode());
			if ( response.getStatus() != Status.OK.getStatusCode() ) {
				System.out.println(response.getHeaderString("error"));
				throw new RuntimeException(response.getHeaderString("error"));
			}
		} catch ( BadRequestException e ) {
			System.out.print("BadRequest : " + e.getMessage()+":");
			System.out.println(builder.head().getHeaderString("error"));
			throw new RuntimeException(e);
		} finally {
			if ( response != null ) response.close();
		}

	}
	
}
