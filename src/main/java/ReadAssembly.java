

import java.util.*;

import javax.persistence.*;

import openstats.model.*;

public class ReadAssembly {

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
			Assembly assembly = buildAssembly(testAction);
			writeCsv(assembly);
		}
*/		
		ReadAction testAction = new GATestAction();
//		List<Assembly> assemblies = listAssemblies();
		Assembly assembly = readJpa(testAction);
		WriteCsv.writeCsv(assembly);
	}

	private static List<Assembly> listAssemblies() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("openstats");
		EntityManager em = emf.createEntityManager();
		return em.createNamedQuery("Assembly.listAssemblies", Assembly.class)
			.getResultList();
	}

	private static Assembly readJpa(ReadAction readAction) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("openstats");
		EntityManager em = emf.createEntityManager();
		return em.createNamedQuery("Assembly.getByStateAssembly", Assembly.class)
			.setParameter("state", readAction.getState())
			.setParameter("assembly", readAction.getAssembly())
			.getSingleResult();
	}
	
	static class GATestAction implements ReadAction {
		@Override
		public String getState() {
			return "GA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class ARTestAction implements ReadAction {
		@Override
		public String getState() {
			return "AR";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class OKTestAction implements ReadAction {
		@Override
		public String getState() {
			return "OK";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class MATestAction implements ReadAction {
		@Override
		public String getState() {
			return "MA";
		}
		@Override
		public String getAssembly() {
			return "187th";
		}
	}

	static class NCTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NC";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class AZTestAction implements ReadAction {
		@Override
		public String getState() {
			return "AZ";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class MNTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MN";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class HITestAction implements ReadAction {
		@Override
		public String getState() {
			return "HI";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class LATestAction implements ReadAction {
		@Override
		public String getState() {
			return "LA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class TNTestAction implements ReadAction {
		@Override
		public String getState() {
			return "TN";
		}
		@Override
		public String getAssembly() {
			return "TN";
		}
	}


	static class VATestAction implements ReadAction {
		@Override
		public String getState() {
			return "VA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class NJTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NJ";
		}
		@Override
		public String getAssembly() {
			return "215";
		}
	}

	static class PATestAction implements ReadAction {
		@Override
		public String getState() {
			return "PA";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class MDTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MD";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	static class MSTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MS";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}

	static class MOTestAction implements ReadAction {
		@Override
		public String getState() {
			return "MO";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	static class TXTestAction implements ReadAction {

		@Override
		public String getState() {
			return "TX";
		}
		@Override
		public String getAssembly() {
			return "83";
		}
	}
	static class NYTestAction implements ReadAction {
		@Override
		public String getState() {
			return "NY";
		}
		@Override
		public String getAssembly() {
			return "2013";
		}
	}
	static class CATestAction implements ReadAction {
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