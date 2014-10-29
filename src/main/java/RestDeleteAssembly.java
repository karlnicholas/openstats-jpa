

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import openstats.client.openstates.*;

public class RestDeleteAssembly {

	public static void main(String[] args) throws Exception {

/*
		OpenState[] readActions = new OpenState[] {
				new OpenStatesClasses.GAOpenState(), 
				new OpenStatesClasses.AROpenState(), 
				new OpenStatesClasses.OKOpenState(), 
				new OpenStatesClasses.MAOpenState(), 
				new OpenStatesClasses.NCOpenState(), 
				new OpenStatesClasses.AZOpenState(), 
//				new OpenStatesClasses.MNOpenState(), 
				new OpenStatesClasses.HIOpenState(), 
				new OpenStatesClasses.LAOpenState(), 
				new OpenStatesClasses.TNOpenState(), 
				new OpenStatesClasses.VAOpenState(), 
				new OpenStatesClasses.NJOpenState(), 
				new OpenStatesClasses.PAOpenState(), 
				new OpenStatesClasses.MDOpenState(), 
				new OpenStatesClasses.MSOpenState(), 
				new OpenStatesClasses.MOOpenState(), 
				new OpenStatesClasses.TXOpenState(), 
				new OpenStatesClasses.NYOpenState(), 
				new OpenStatesClasses.CAOpenState(),
		}; 
*/
		
		OpenState readAction = new OpenStateClasses.NCOpenState();
		
		Client client = ClientBuilder.newClient();
		WebTarget myResource = client.target("http://localhost:8080/openstats/rest/BILLPROGESS/"+readAction.getState()+"/"+readAction.getSession());
		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		Response response = builder.delete(Response.class);
		
		if (response.getStatus() != Status.OK.getStatusCode() ) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

/*
 		OpenState testAction = new GAOpenState();
 		Assembly assembly = buildAssembly(testAction, em);
		writeJpa(assembly);
*/		
		
	}

}
