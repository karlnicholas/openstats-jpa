

import java.net.URI;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import openstats.client.les.*;
import openstats.client.openstates.*;
import openstats.osmodel.*;

public class RestReadAssembly {

	public static void main(String[] args) throws Exception {
		/*				

		OpenState[] openStates = new OpenState[] {
				new OpenStateClasses.AROpenState(), 
				new OpenStateClasses.GAOpenState(), 
				new OpenStateClasses.OKOpenState(), 
				new OpenStateClasses.MAOpenState(), 
				new OpenStateClasses.NCOpenState(), 
				new OpenStateClasses.AZOpenState(),
//				new OpenStateClasses.MNOpenState(), 
				new OpenStateClasses.HIOpenState(), 
				new OpenStateClasses.LAOpenState(), 
				new OpenStateClasses.TNOpenState(), 
				new OpenStateClasses.VAOpenState(), 
				new OpenStateClasses.NJOpenState(), 
				new OpenStateClasses.PAOpenState(), 
				new OpenStateClasses.MDOpenState(), 
				new OpenStateClasses.MSOpenState(), 
				new OpenStateClasses.MOOpenState(), 
				new OpenStateClasses.TXOpenState(), 
				new OpenStateClasses.NYOpenState(), 
				new OpenStateClasses.CAOpenState(),
		};
*/				 
		
		OpenState openState = new OpenStateClasses.AROpenState();

		Client client = ClientBuilder.newClient();
		URI uri = new URI("http", null, "localhost", 8080, "/openstats/rest/"+Labels.LESGROUPNAME+"/"+openState.getState()+"/"+openState.getSession(), null, null);
		System.out.println(uri.toString());
		WebTarget myResource = client.target(uri);
		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		OSAssembly osAssembly = builder.get(OSAssembly.class);
		Response response = builder.head();
		
		if (builder.head().getStatus() != Status.OK.getStatusCode() ) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
	 
		System.out.println(osAssembly.toString());
	 

/*
 		OpenState testAction = new GAOpenState();
 		Assembly assembly = buildAssembly(testAction, em);
		writeJpa(assembly);
*/		
		
	}

/*
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
