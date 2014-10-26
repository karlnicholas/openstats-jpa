

import java.io.*;
import java.util.*;

import javax.persistence.*;

import openstats.client.openstates.*;
import openstats.dbmodel.*;

public class WriteAssemblies {

	private static TreeSet<String> currentTopics;
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) throws Exception {
		initJpa();

		TestAction[] testActions = new TestAction[] {
/*				
				new TestClasses.GATestAction(), 
				new TestClasses.ARTestAction(), 
				new TestClasses.OKTestAction(), 
				new TestClasses.MATestAction(), 
				new TestClasses.NCTestAction(), 
				new TestClasses.AZTestAction(),
*/				 
//				new TestClasses.MNTestAction(), 
				new TestClasses.HITestAction(), 
				new TestClasses.LATestAction(), 
				new TestClasses.TNTestAction(), 
				new TestClasses.VATestAction(), 
				new TestClasses.NJTestAction(), 
				new TestClasses.PATestAction(), 
				new TestClasses.MDTestAction(), 
				new TestClasses.MSTestAction(), 
				new TestClasses.MOTestAction(), 
				new TestClasses.TXTestAction(), 
				new TestClasses.NYTestAction(), 
				new TestClasses.CATestAction(), 
		};
		
		for( TestAction testAction: testActions) {
			DBAssembly assembly = buildAssembly(testAction);
			writeJpa(assembly);
		}
/*
		TestAction testAction = new GATestAction();
 		Assembly assembly = buildAssembly(testAction);
		writeJpa(assembly);
*/		
		
	}

	private static void initJpa() throws Exception {
		emf = Persistence.createEntityManagerFactory("openstats");
		em = emf.createEntityManager();
	}
	private static void writeJpa(DBAssembly assembly) throws Exception {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(assembly);
		tx.commit();
	}

	private static DBAssembly buildAssembly(TestAction testAction) throws Exception { 
		testAction.loadBulkData();

		buildcurrentTopics(testAction);
		
		DBAssembly assembly = new DBAssembly();
		assembly.setState(testAction.getState());
		assembly.setSession(testAction.getSession());
		DBDistricts districts = assembly.getDistricts();
		
		for ( org.openstates.data.Legislator legislator: org.openstates.model.Legislators.values()) {
			
			openstats.dbmodel.DBDistrict district = districts.findDistrict(legislator.chamber, legislator.district);
			if ( district == null ) {
				openstats.dbmodel.DBLegislator sLegislator = new openstats.dbmodel.DBLegislator();
				sLegislator.setName(legislator.full_name);
				sLegislator.setParty(legislator.party);
				district = new openstats.dbmodel.DBDistrict();
				district.setChamber(legislator.chamber);
				district.setDistrict(legislator.district);
				district.getLegislators().add(sLegislator); 
				districts.getDistrictList().add(district);
				
			}
		}
		return assembly;
	}	

	private static void buildcurrentTopics(TestAction testAction) throws Exception {
		currentTopics = new TreeSet<String>(); 
		InputStream is = WriteAssemblies.class.getResourceAsStream("/topics/" + testAction.getState() + "TopicBills2013.txt");
		InputStreamReader isr = new InputStreamReader(is, "ASCII");
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ( (line = br.readLine()) != null ) {
			currentTopics.add(line);
		}
		is.close();
//		System.out.println(currentTopics);
	}

	static class MyAction implements Comparable<MyAction> {
		public org.openstates.data.Bill.Action action; 
		public MyAction(org.openstates.data.Bill.Action action) {
			this.action = action;
		}
		@Override
		public int compareTo(MyAction o) {
			return action.date.compareTo(o.action.date);
		}
		
	}

}
