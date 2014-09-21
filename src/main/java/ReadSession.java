

import java.util.*;

import javax.persistence.*;

import openstats.model.*;

public class ReadSession {

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
			Session session = buildSession(testAction);
			writeCsv(session);
		}
*/		
		ReadAction testAction = new GATestAction();
//		List<Session> sessions = listSessions();
		Session session = readJpa(testAction);
		WriteCsv.writeCsv(session);
	}

	private static List<Session> listSessions() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("openstats");
		EntityManager em = emf.createEntityManager();
		return em.createNamedQuery("Session.listSessions", Session.class)
			.getResultList();
	}

	private static Session readJpa(ReadAction readAction) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("openstats");
		EntityManager em = emf.createEntityManager();
		return em.createNamedQuery("Session.getByStateSession", Session.class)
			.setParameter("state", readAction.getState())
			.setParameter("session", readAction.getSession())
			.getSingleResult();
	}
	
	static class GATestAction implements ReadAction {
		@Override
		public String getState() {
			return "GA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class ARTestAction implements ReadAction {
		@Override
		public String getState() {
			return "AR";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class OKTestAction implements ReadAction {
		@Override
		public String getState() {
			return "OK";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class MATestAction implements ReadAction {
		@Override
		public String getState() {
			return "MA";
		}
		@Override
		public String getSession() {
			return "187th";
		}
	}

	static class NCTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NC";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class AZTestAction implements ReadAction {
		@Override
		public String getState() {
			return "AZ";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class MNTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MN";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class HITestAction implements ReadAction {
		@Override
		public String getState() {
			return "HI";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class LATestAction implements ReadAction {
		@Override
		public String getState() {
			return "LA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class TNTestAction implements ReadAction {
		@Override
		public String getState() {
			return "TN";
		}
		@Override
		public String getSession() {
			return "TN";
		}
	}


	static class VATestAction implements ReadAction {
		@Override
		public String getState() {
			return "VA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class NJTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NJ";
		}
		@Override
		public String getSession() {
			return "215";
		}
	}

	static class PATestAction implements ReadAction {
		@Override
		public String getState() {
			return "PA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class MDTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MD";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}
	static class MSTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MS";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	static class MOTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MO";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}
	static class TXTestAction implements ReadAction {

		@Override
		public String getState() {
			return "TX";
		}
		@Override
		public String getSession() {
			return "83";
		}
	}
	static class NYTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NY";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}
	static class CATestAction implements ReadAction {
		@Override
		public String getState() {
			return "CA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
	}

	interface ReadAction {
		public String getState();
		public String getSession();
	}
	}
