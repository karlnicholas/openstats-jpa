
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import openstats.client.les.Labels;
import openstats.client.openstates.*;
import openstats.osmodel.OSAssembly;

public class RestReadAssembly {

	public static void main(String[] args) throws Exception {
		new RestReadAssembly().run();
		
	}
	
	public void run() throws Exception {
        
		OpenState openState = new OpenStateClasses.AROpenState();

		Client client = ClientBuilder.newClient();

		WebTarget myResource = client.target("http://localhost:8080/openstats/rest/BILLPROGESS/"+openState.getState()+"/"+openState.getSession());
		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		OSAssembly osAssembly = builder.get(OSAssembly.class);
/*		
		WebTarget myResource = client.target("http://localhost:8080/openstats/rest");
		myResource.path("{group}/{state}/{session}");
		myResource.resolveTemplate("group", Labels.LESGROUPNAME);
		myResource.resolveTemplate("state", openState.getState());
		myResource.resolveTemplate("session", openState.getSession());

		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		OSAssembly osAssembly = builder.get(OSAssembly.class);
*/		
		Response response = builder.head();
		
		if (builder.head().getStatus() != Status.OK.getStatusCode() ) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
	 
		System.out.println(osAssembly.toString());

	}


/*

		
		OpenState openState = new OpenStateClasses.AROpenState();

		Client client = ClientBuilder.newClient();

		WebTarget myResource = client.target("http://localhost:8080/openstats/rest")
				.path("{group}/{state}/{session}")
				.resolveTemplate("group", Labels.LESGROUPNAME)
				.resolveTemplate("state", openState.getState())
				.resolveTemplate("session", openState.getSession());

		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		OSAssembly osAssembly = builder.get(OSAssembly.class);
		Response response = builder.head();
		
		if (builder.head().getStatus() != Status.OK.getStatusCode() ) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
	 
		System.out.println(osAssembly.toString());
		response.getLocation();
		
 		OpenState testAction = new GAOpenState();
 		Assembly assembly = buildAssembly(testAction, em);
		writeJpa(assembly);

	
	private static void printAllActions(Bill bill) {
		for ( Bill.Action action: bill.actions ) {
			System.out.println(action);
		}
	}
	private static TreeMap<org.openstates.data.Legislator, AuthorStats> readLegislators() throws Exception {
		TreeMap<org.openstates.data.Legislator, AuthorStats> legislators = new TreeMap<>();
		for ( org.openstates.data.Legislator legislator: org.openstates.model.Legislators.values()) {
			legislators.put(legislator, new AuthorStats());
		}
		return legislators;
	}
*/
	
}
