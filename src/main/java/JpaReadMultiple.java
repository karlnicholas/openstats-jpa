

import java.io.*;

import javax.persistence.*;

import openstats.client.les.Labels;
import openstats.dbmodel.*;
import openstats.facades.AssemblyFacade;
import openstats.model.Assembly;
import openstats.util.AssemblyCsvHandler;

public class JpaReadMultiple {

	EntityManagerFactory emf;
	EntityManager em;
	AssemblyFacade assemblyFacade;

	public static void main(String[] args) throws Exception {
		/*		
//		initJpa();

		for( ReadAction testAction: OpenStateClasses.getTestActions() ) {
			Assembly Assembly = buildAssembly(testAction);
			writeCsv(Assembly);
		}
*/
		new JpaReadMultiple().run();
	}
	
	private void run() throws Exception {
		initJpa();
		
		DBGroup dbGroup = DBGroupHandler.getDBGroup(Labels.LESGROUPNAME, em);
		Assembly Assembly = assemblyFacade.buildAssembly(dbGroup, "GA", "2013");
		Writer writer = new OutputStreamWriter(System.out);
		
		AssemblyCsvHandler csvHandler = new AssemblyCsvHandler();
		csvHandler.writeCsv(writer, Assembly);
		
		writer.flush();
		
		emf.close();

	}

	private void initJpa() throws Exception {
		emf = Persistence.createEntityManagerFactory("openstats");
		em = emf.createEntityManager();
		assemblyFacade = new AssemblyFacade(em);
	}

}