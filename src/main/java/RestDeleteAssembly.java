

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import openstats.client.openstates.*;

public class RestDeleteAssembly {

	public static void main(String[] args) throws Exception {
		
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
