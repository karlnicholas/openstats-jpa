

import javax.persistence.*;

import openstats.client.les.ComputeAssembly;
import openstats.client.openstates.*;
import openstats.facades.AssemblyFacade;
import openstats.osmodel.*;

public class WriteAssemblyGroups {

	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) throws Exception {
		initJpa();

		TestAction[] testActions = new TestAction[] {
				new TestClasses.GATestAction(), 
/*				
				new ARTestAction(), 
				new OKTestAction(), 
				new MATestAction(), 
				new NCTestAction(), 
				new AZTestAction(),
//				new MNTestAction(), 
				new HITestAction(), 
				new LATestAction(), 
				new TNTestAction(), 
				new VATestAction(), 
				new NJTestAction(), 
				new PATestAction(), 
				new MDTestAction(), 
				new MSTestAction(), 
				new MOTestAction(), 
				new TXTestAction(), 
				new NYTestAction(), 
				new CATestAction(),
*/				 
		};
		
		ComputeAssembly computeAssembly = new ComputeAssembly(); 
		AssemblyFacade assemblyFacade = new AssemblyFacade(em); 
		
		for( TestAction testAction: testActions) {
			OSAssembly osAssembly = computeAssembly.computeAssemblyLES(testAction);
			assemblyFacade.writeOSAssembly(osAssembly);
		}

/*
 		TestAction testAction = new GATestAction();
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
