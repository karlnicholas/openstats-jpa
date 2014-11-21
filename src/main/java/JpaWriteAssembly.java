

import javax.persistence.*;

import openstats.client.les.ComputeAssembly;
import openstats.client.openstates.*;
import openstats.facades.AssemblyFacade;
import openstats.osmodel.*;

public class JpaWriteAssembly {

	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) throws Exception {
		new JpaWriteAssembly().run();
	}

	private void initJpa() throws Exception {
		emf = Persistence.createEntityManagerFactory("openstats");
		em = emf.createEntityManager();
	}
	
	private void run() throws Exception {
		initJpa();
		
		ComputeAssembly computeAssembly = new ComputeAssembly();
		AssemblyFacade assemblyFacade = new AssemblyFacade(em);
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		for( OpenState testAction: OpenStateClasses.getOpenStates()) {
			OSAssembly osAssembly = computeAssembly.computeAssemblyLES(testAction);
			assemblyFacade.writeOSAssembly(osAssembly);
		}

		et.commit();

/*
 		OpenState testAction = new GAOpenState();
 		Assembly assembly = buildAssembly(testAction, em);
		writeJpa(assembly);
*/		
		
		emf.close();
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
