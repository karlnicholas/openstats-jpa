

import java.io.*;
import java.util.*;

import javax.persistence.*;

import openstats.model.*;

import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

public class ReadSession {

	private static final String GROUPLABEL = "BILLPROGRESS";
	private static ArrayList<String> LESLABEL = new ArrayList<String>(Arrays.asList(new String[] {"LES"}));
	private static ArrayList<String> SKEWLABEL = new ArrayList<String>(Arrays.asList(new String[] {"SKEWNESS"}));		

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
		writeCsv(session);
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

	private static void writeCsv(Session session) throws Exception {
        
    	class MyCsvWriter extends AbstractCsvWriter {

			public MyCsvWriter(Writer writer, CsvPreference preference) {
				super(writer, preference);
			}
			public void writeRow(String... columns ) throws IOException {
				super.writeRow(columns);
			}
		}
		MyCsvWriter writer = null;
        try {
        	
        	writer = new MyCsvWriter(new FileWriter("c:/users/karl/"+session.getState()+"-2013-les.csv"), CsvPreference.STANDARD_PREFERENCE);
	        Districts districts = session.getDistricts();
	        // the header elements are used to map the bean values to each column (names must match)
	        List<String> columns = new ArrayList<String>();

	        columns.add("district");
	        columns.add("chamber");
//			Aggregate aggregate = districts.getAggregate(GROUPLABEL);

	        for ( String head: districts.getUserData().getAggregates().getGroup(GROUPLABEL)) {
	        	columns.add(head);
	        }
	        for ( String head: districts.getUserData().getComputations().getGroup(GROUPLABEL)) {
	        	columns.add(head);
	        }

            // write the header
	        String[] sColumns = new String[columns.size()];
	        sColumns = columns.toArray(sColumns);
            writer.writeHeader(sColumns);
            class LESComparator implements Comparator<District> {
            	private Districts districts;
            	public LESComparator(Districts districts) {
            		this.districts = districts;
            	}
				@Override
				public int compare(District o1, District o2) {
					try {
						return districts.getUserData().getComputation(GROUPLABEL).getValue(o2, LESLABEL.get(0)).compareTo(districts.getUserData().getComputation(GROUPLABEL).getValue(o1, LESLABEL.get(0)));
					} catch (OpenStatsException e) {
						throw new RuntimeException(e);
					}
				}
            }
            
            Collections.sort(districts.getDistrictList(), new LESComparator(districts));
            // write the customer lists
            for ( final openstats.model.District dist: districts.getDistrictList()) {
            	columns.clear();
    	        columns.add(dist.getDistrict());
    	        columns.add(dist.getChamber());
    	        List<Long> aggs = districts.getUserData().getAggregate(GROUPLABEL).getValues(dist);
    	        for ( Long agg: aggs ) {
    	        	columns.add(agg.toString());
    	        }
    	        List<Double> comps = districts.getUserData().getComputation(GROUPLABEL).getValues(dist);
    	        for ( Double comp: comps ) {
    	        	columns.add(comp.toString());
    	        }
                writer.writeRow(columns.toArray(sColumns));
            }

            writer.writeHeader(SKEWLABEL.get(0));
            writer.writeRow(session.getUserData().getComputation(GROUPLABEL).getValue(session, SKEWLABEL.get(0)).toString());
                
        }
        finally {
            if( writer  != null ) {
            	writer.close();
            }
        }
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
