

import java.util.*;

import javax.persistence.*;

import openstats.client.les.Labels;
import openstats.data.DBGroupFacade;
import openstats.dbmodel.*;
import openstats.osmodel.OSAssembly;

public class ReadAssembly {

	EntityManagerFactory emf;
	EntityManager em;
	DBGroupFacade dbGroupFacade;

	public static void main(String[] args) throws Exception {
/*		
		TestAction[] testActions = new TestAction[] {
				new GATestAction(), 
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
		};
		
		for( TestAction testAction: testActions) {
			Assembly dbAssembly = buildAssembly(testAction);
			writeCsv(dbAssembly);
		}
*/
		new ReadAssembly().run();
	}
	
	private void run() throws Exception {
		initJpa();
		
		DBGroup dbGroup = DBGroupHandler.getDBGroup(Labels.LESGROUPNAME, em);
		OSAssembly osAssembly = dbGroupFacade.buildOSAssembly(dbGroup, "GA", "2013");

	}

	private void initJpa() throws Exception {
		emf = Persistence.createEntityManagerFactory("openstats");
		em = emf.createEntityManager();
		dbGroupFacade = new DBGroupFacade(em);
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
	
	class GATestAction implements ReadAction {
		@Override
		public String getState() {
			return "GA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class ARTestAction implements ReadAction {
		@Override
		public String getState() {
			return "AR";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class OKTestAction implements ReadAction {
		@Override
		public String getState() {
			return "OK";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class MATestAction implements ReadAction {
		@Override
		public String getState() {
			return "MA";
		}
		@Override
		public String getAssembly() {
			return "187th";
		}
	}

	class NCTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NC";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class AZTestAction implements ReadAction {
		@Override
		public String getState() {
			return "AZ";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class MNTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MN";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class HITestAction implements ReadAction {
		@Override
		public String getState() {
			return "HI";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class LATestAction implements ReadAction {
		@Override
		public String getState() {
			return "LA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class TNTestAction implements ReadAction {
		@Override
		public String getState() {
			return "TN";
		}
		@Override
		public String getAssembly() {
			return "TN";
		}
	}


	class VATestAction implements ReadAction {
		@Override
		public String getState() {
			return "VA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class NJTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NJ";
		}
		@Override
		public String getAssembly() {
			return "215";
		}
	}

	class PATestAction implements ReadAction {
		@Override
		public String getState() {
			return "PA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class MDTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MD";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	class MSTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MS";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	class MOTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MO";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	class TXTestAction implements ReadAction {

		@Override
		public String getState() {
			return "TX";
		}
		@Override
		public String getAssembly() {
			return "83";
		}
	}
	class NYTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NY";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	class CATestAction implements ReadAction {
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