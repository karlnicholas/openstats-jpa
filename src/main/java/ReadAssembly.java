

import java.util.*;

import javax.persistence.*;

import openstats.client.les.Labels;
import openstats.dbmodel.*;
import openstats.facades.AssemblyFacade;
import openstats.osmodel.OSAssembly;

public class ReadAssembly {

	EntityManagerFactory emf;
	EntityManager em;
	AssemblyFacade assemblyFacade;

	public static void main(String[] args) throws Exception {
/*		
		ReadAction[] testActions = new ReadAction[] {
				new GAReadAction(), 
				new ARReadAction(), 
				new OKReadAction(), 
				new MAReadAction(), 
				new NCReadAction(), 
				new AZReadAction(), 
//				new MNReadAction(), 
				new HIReadAction(), 
				new LAReadAction(), 
				new TNReadAction(), 
				new VAReadAction(), 
				new NJReadAction(), 
				new PAReadAction(), 
				new MDReadAction(), 
				new MSReadAction(), 
				new MOReadAction(), 
				new TXReadAction(), 
				new NYReadAction(), 
				new CAReadAction(), 
		};
		
		for( ReadAction testAction: testActions) {
			Assembly dbAssembly = buildAssembly(testAction);
			writeCsv(dbAssembly);
		}
*/
		new ReadAssembly().run();
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
			.setParameter("dbAssembly", readAction.getAssembly())
			.getSingleResult();
	}
	
	class GAReadAction implements ReadAction {
		@Override
		public String getState() {
			return "GA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class ARReadAction implements ReadAction {
		@Override
		public String getState() {
			return "AR";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class OKReadAction implements ReadAction {
		@Override
		public String getState() {
			return "OK";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class MAReadAction implements ReadAction {
		@Override
		public String getState() {
			return "MA";
		}
		@Override
		public String getAssembly() {
			return "187th";
		}
	}

	class NCReadAction implements ReadAction {
		@Override
		public String getState() {
			return "NC";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class AZReadAction implements ReadAction {
		@Override
		public String getState() {
			return "AZ";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class MNReadAction implements ReadAction {
		@Override
		public String getState() {
			return "MN";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class HIReadAction implements ReadAction {
		@Override
		public String getState() {
			return "HI";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class LAReadAction implements ReadAction {
		@Override
		public String getState() {
			return "LA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class TNReadAction implements ReadAction {
		@Override
		public String getState() {
			return "TN";
		}
		@Override
		public String getAssembly() {
			return "TN";
		}
	}


	class VAReadAction implements ReadAction {
		@Override
		public String getState() {
			return "VA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class NJReadAction implements ReadAction {
		@Override
		public String getState() {
			return "NJ";
		}
		@Override
		public String getAssembly() {
			return "215";
		}
	}

	class PAReadAction implements ReadAction {
		@Override
		public String getState() {
			return "PA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class MDReadAction implements ReadAction {
		@Override
		public String getState() {
			return "MD";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	class MSReadAction implements ReadAction {
		@Override
		public String getState() {
			return "MS";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class MOReadAction implements ReadAction {
		@Override
		public String getState() {
			return "MO";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	class TXReadAction implements ReadAction {

		@Override
		public String getState() {
			return "TX";
		}
		@Override
		public String getAssembly() {
			return "83";
		}
	}
	class NYReadAction implements ReadAction {
		@Override
		public String getState() {
			return "NY";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	class CAReadAction implements ReadAction {
		@Override
		public String getState() {
			return "CA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	interface ReadAction {
		public String getState();
		public String getAssembly();
	}
}