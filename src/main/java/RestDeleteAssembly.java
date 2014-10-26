

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import openstats.client.openstates.*;

public class RestDeleteAssembly {

	public static void main(String[] args) throws Exception {

/*
		ReadAction[] readActions = new ReadAction[] {
				new ReadClasses.GAReadAction(), 
				new ReadClasses.ARReadAction(), 
				new ReadClasses.OKReadAction(), 
				new ReadClasses.MAReadAction(), 
				new ReadClasses.NCReadAction(), 
				new ReadClasses.AZReadAction(), 
//				new ReadClasses.MNReadAction(), 
				new ReadClasses.HIReadAction(), 
				new ReadClasses.LAReadAction(), 
				new ReadClasses.TNReadAction(), 
				new ReadClasses.VAReadAction(), 
				new ReadClasses.NJReadAction(), 
				new ReadClasses.PAReadAction(), 
				new ReadClasses.MDReadAction(), 
				new ReadClasses.MSReadAction(), 
				new ReadClasses.MOReadAction(), 
				new ReadClasses.TXReadAction(), 
				new ReadClasses.NYReadAction(), 
				new ReadClasses.CAReadAction(),
		}; 
*/
		
		ReadAction readAction = new ReadClasses.NCReadAction();
		
		Client client = ClientBuilder.newClient();
		WebTarget myResource = client.target("http://localhost:8080/openstats/rest/BILLPROGESS/"+readAction.getState()+"/"+readAction.getSession());
		Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
		Response response = builder.delete(Response.class);
		
		if (response.getStatus() != Status.OK.getStatusCode() ) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		


/*
 		ReadAction testAction = new GAReadAction();
 		Assembly assembly = buildAssembly(testAction, em);
		writeJpa(assembly);
*/		
		
	}

	static class CAReadAction implements ReadAction {

		@Override
		public String getState() {
			return "CA";
		}

		@Override
		public String getSession() {
			return "2013";
		}

	}

}
