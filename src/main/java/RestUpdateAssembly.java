

import java.net.URI;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import openstats.client.les.ComputeAssembly;
import openstats.client.openstates.*;
import openstats.osmodel.*;

public class RestUpdateAssembly {

	public static void main(String[] args) throws Exception {
		/*				

		OpenState[] testActions = new OpenState[] {
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
		
		ComputeAssembly computeAssembly = new ComputeAssembly(); 
		
		OSAssembly osAssembly = computeAssembly.computeAssemblyLES(new OpenStateClasses.NCOpenState());
//		ObjectMapper objectMapper = new ObjectMapper();
//		String json = objectMapper.writeValueAsString(osAssembly);
//		System.out.println(json);
		
		Client client = ClientBuilder.newClient();
		WebTarget myResource = client.target("http://localhost:8080/openstats/rest");
		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		Response response = builder.post(Entity.json(osAssembly), Response.class);
		
		if (response.getStatus() != Status.CREATED.getStatusCode() ) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
	 
		URI location = response.getLocation();
		System.out.println(location.toString());
	 

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
