

import java.util.*;

import javax.persistence.*;

import openstats.client.les.Labels;
import openstats.client.openstates.*;
import openstats.dbmodel.*;
import openstats.facades.AssemblyFacade;
import openstats.osmodel.OSAssembly;

public class JPAReadAssembly {

	EntityManagerFactory emf;
	EntityManager em;
	AssemblyFacade assemblyFacade;

	public static void main(String[] args) throws Exception {
		/*		
//		initJpa();

		ReadAction[] testActions = new ReadAction[] {
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
		for( ReadAction testAction: testActions) {
			OSAssembly osAssembly = buildAssembly(testAction);
			writeCsv(osAssembly);
		}
*/
		new JPAReadAssembly().run();
	}
	
	private void run() throws Exception {
		initJpa();
		
		DBGroup dbGroup = DBGroupHandler.getDBGroup(Labels.LESGROUPNAME, em);
		OSAssembly osAssembly = assemblyFacade.buildOSAssembly(dbGroup, "GA", "2013");

		System.out.println(osAssembly.getOSDistricts().getAggregateGroupInfo().getGroupLabels());

	}

	private void initJpa() throws Exception {
		emf = Persistence.createEntityManagerFactory("openstats");
		em = emf.createEntityManager();
		assemblyFacade = new AssemblyFacade(em);
	}

	private List<DBAssembly> listAssemblies() throws Exception {
		return em.createNamedQuery("Assembly.listAssemblies", DBAssembly.class)
			.getResultList();
	}

	private DBAssembly readJpa(ReadAction readAction) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("openstats");
		EntityManager em = emf.createEntityManager();
		return em.createNamedQuery("Assembly.getByStateAssembly", DBAssembly.class)
			.setParameter("state", readAction.getState())
			.setParameter("dbAssembly", readAction.getSession())
			.getSingleResult();
	}
	

}