

import java.io.*;

import javax.persistence.*;

import openstats.client.les.Labels;
import openstats.dbmodel.*;
import openstats.facades.AssemblyFacade;
import openstats.osmodel.OSAssembly;
import openstats.util.AssemblyCsvHandler;

public class JpaReadAssembly {

	EntityManagerFactory emf;
	EntityManager em;
	AssemblyFacade assemblyFacade;

	public static void main(String[] args) throws Exception {
		/*		
//		initJpa();

		for( ReadAction testAction: OpenStateClasses.getTestActions() ) {
			OSAssembly osAssembly = buildAssembly(testAction);
			writeCsv(osAssembly);
		}
*/
		new JpaReadAssembly().run();
	}
	
	private void run() throws Exception {
		initJpa();
		
		DBGroup dbGroup = DBGroupHandler.getDBGroup(Labels.LESGROUPNAME, em);
		OSAssembly osAssembly = assemblyFacade.buildOSAssembly(dbGroup, "GA", "2013");
		Writer writer = new OutputStreamWriter(System.out);
		
		AssemblyCsvHandler csvHandler = new AssemblyCsvHandler();
		csvHandler.writeCsv(writer, osAssembly);
		
		writer.flush();

	}

	private void initJpa() throws Exception {
		emf = Persistence.createEntityManagerFactory("openstats");
		em = emf.createEntityManager();
		assemblyFacade = new AssemblyFacade(em);
	}

}