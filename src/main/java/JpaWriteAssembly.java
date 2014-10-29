

import javax.persistence.*;

import openstats.client.les.ComputeAssembly;
import openstats.client.openstates.*;
import openstats.facades.AssemblyFacade;
import openstats.osmodel.*;

public class JpaWriteAssembly {

	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) throws Exception {
		initJpa();

		OpenState[] testActions = new OpenState[] {
				new OpenStateClasses.GAOpenState(), 
/*				
				new OpenStateClasses.AROpenState(), 
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
*/				 
		};
		
		ComputeAssembly computeAssembly = new ComputeAssembly(); 
		AssemblyFacade assemblyFacade = new AssemblyFacade(em); 
		
		for( OpenState testAction: testActions) {
			OSAssembly osAssembly = computeAssembly.computeAssemblyLES(testAction);
			assemblyFacade.writeOSAssembly(osAssembly);
		}

/*
 		OpenState testAction = new GAOpenState();
 		Assembly assembly = buildAssembly(testAction, em);
		writeJpa(assembly);
*/		
		
	}

	private static void initJpa() throws Exception {
		emf = Persistence.createEntityManagerFactory("openstats");
		em = emf.createEntityManager();
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
