

import java.io.*;
import java.util.*;

import javax.persistence.*;

import openstats.client.openstates.TestAction;
import openstats.model.*;

import org.openstates.bulkdata.LoadBulkData;

public class WriteAssemblies {

	private static TreeSet<String> currentTopics;
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) throws Exception {
		initJpa();

		TestAction[] testActions = new TestAction[] {
/*				
				new GATestAction(), 
				new ARTestAction(), 
				new OKTestAction(), 
				new MATestAction(), 
				new NCTestAction(), 
				new AZTestAction(),
*/				 
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
			
			openstats.model.DBDistrict district = districts.findDistrict(legislator.chamber, legislator.district);
			if ( district == null ) {
				openstats.model.DBLegislator sLegislator = new openstats.model.DBLegislator();
				sLegislator.setName(legislator.full_name);
				sLegislator.setParty(legislator.party);
				district = new openstats.model.DBDistrict();
				district.setChamber(legislator.chamber);
				district.setDistrict(legislator.district);
				district.getLegislators().add(sLegislator); 
				districts.getDistrictList().add(district);
				
			}
		}
		return assembly;
	}	

	
	static class GATestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("senate read and referred") ) return 1;
			else if (chamber.equals("upper") && act.contains("house first readers") ) return 1;
			else if (act.contains("house sent to governor") ) return 2;
			else if (chamber.equals("lower") && act.contains("read and adopted") ) return 3;
			else if (chamber.equals("upper") && act.contains("read and adopted") ) return 3;
			else if ( act.contains("signed by governor") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "GA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ga-json.zip", "2013", TimeZone.getTimeZone("GMT-06:00") );
		}
	}

	static class ARTestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("transmitted to the senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("transmitted  to the house") ) return 1;
			else if (chamber.equals("upper") && act.contains("transmitted to the house") ) return 1;
			else if (act.contains("correctly enrolled and ordered transmitted to the governor's office.") ) return 2;
			else if (chamber.equals("lower") && act.contains("read and adopted") ) return 3;
			else if (chamber.equals("upper") && act.contains("read the third time and adopted.") ) return 3;
			else if ( act.contains("is now act ") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "AR";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ar-json.zip", "2013", TimeZone.getTimeZone("GMT-06:00") );
		}
	}

	static class OKTestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("engrossed, signed, to senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("engrossed to house") ) return 1;
			else if (act.contains("sent to governor") ) return 2;
			else if (chamber.equals("lower") && act.contains("enrolled, signed, filed with secretary of state") ) return 3;
			else if (chamber.equals("upper") && act.contains("enrolled, filed with secretary of state") ) return 3;
			else if ( act.contains("approved by governor") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "OK";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-ok-json.zip", "2013", TimeZone.getTimeZone("GMT-06:00") );			
		}
	}

	static class MATestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("H ") || bill_id.contains("S ") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("senate concurred") ) return 1;
			else if (chamber.equals("upper") && act.contains("house concurred") ) return 1;
			else if (act.contains("enacted and laid before the governor") ) return 2;
			else if ( act.contains("signed by the governor") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "MA";
		}
		@Override
		public String getSession() {
			return "187th";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-ma-json.zip", "187th", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	static class NCTestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("rec from house") ) return 1;
			else if (chamber.equals("upper") && act.contains("rec from senate") ) return 1;
			else if (act.contains("ratified") ) return 2;
			else if (chamber.equals("lower") &&  act.contains("adopted") ) return 3;
			else if (chamber.equals("upper") &&  act.contains("adopted") ) return 3;
			else if ( act.contains("signed by gov.") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "NC";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-nc-json.zip", "2013", TimeZone.getTimeZone("GMT-07:00") );
		}
	}

	static class AZTestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("transmit to house") ) return 1;
			else if (chamber.equals("upper") && act.contains("transmitted to house") ) return 1;
			else if (act.contains("transmitted to governor") ) return 2;
			else if (act.contains("enrolled to governor") ) return 2;
			else if (act.contains("resolution adopted in final form") ) return 3;
			else if (act.contains("transmitted to secretary of state") ) return 3;
			else if ( act.equals("signed") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "AZ";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-az-json.zip", "51st-1st", TimeZone.getTimeZone("GMT-07:00") );
		}
	}

	static class MNTestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HF") || bill_id.contains("SF") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			return -1;
		}
		@Override
		public String getState() {
			return "MN";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-11-01-mn-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	static class HITestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("transmitted to senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("transmitted to house") ) return 1;
			else if (act.contains("transmitted to governor") ) return 2;
			else if (act.contains("enrolled to governor") ) return 2;
			else if (act.contains("resolution adopted in final form") ) return 3;
			else if (act.contains("certified copies of resolutions sent") ) return 3;
			else if ( act.contains("act ") ) return 3;
			return -1;
		}

		@Override
		public String getState() {
			return "HI";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-hi-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	static class LATestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB") ) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
//			if ( bill.bill_id.contains("SCR") ) System.out.println(bill.bill_id + ":" + bill.chamber+":"+act);
			if (chamber.equals("lower") && act.contains("received in the senate.") ) return 1;
			else if (chamber.equals("lower") && act.contains("enrolled and signed by the speaker of the house.") ) return 1;
			else if (chamber.equals("upper") && act.contains("received in the house from the senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("ordered sent to the house.") ) return 1;
			else if (act.contains("sent to the governor") ) return 2;
			else if (act.contains("sent to the secretary of state by the secretary") ) return 3;
			else if (act.contains("taken by the clerk of the house and presented to the secretary of state") ) return 3;
			else if ( act.contains("becomes act no.") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "LA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-la-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	static class TNTestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB")) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("ready for transmission to sen") ) return 1;
			else if (chamber.equals("upper") && act.contains("ready for transmission to house") ) return 1;
			else if (act.contains("transmitted to gov. for action") ) return 2;
			else if (act.contains("adopted as am.,  ayes ") ) return 3;
			else if (act.contains("adopted,  ayes ") ) return 3;
			else if ( act.contains("signed by governor") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "TN";
		}
		@Override
		public String getSession() {
			return "TN";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-tn-json.zip", "108", TimeZone.getTimeZone("GMT-05:00") );
		}
	}


	static class VATestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB")) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("passed house") ) return 1;
			else if (chamber.equals("upper") && act.contains("passed senate") ) return 1;
			else if (act.contains("enrolled") ) return 2;
			else if ( act.contains("enacted, chapter") ) return 3;
			else if (chamber.equals("lower") &&  act.contains("agreed to by house") ) return 3;
			else if (chamber.equals("upper") &&  act.contains("agreed to by senate") ) return 3;
			else if ( act.contains("approved by governor") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "VA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-va-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	static class NJTestAction implements TestAction {
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("A ") || bill_id.contains("S ")) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("received in the senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("received in the assembly") ) return 1;
			else if (act.contains("passed both houses") ) return 2;
			else if ( act.contains("approved p.") ) return 3;
			else if ( act.contains("filed with secretary of state") ) return 3;
			return -1;
		}
		@Override
		public String getState() {
			return "NJ";
		}
		@Override
		public String getSession() {
			return "215";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-11-01-nj-json.zip", "215", TimeZone.getTimeZone("GMT-05:00") );
		}
		
	}

	static class PATestAction implements TestAction {

		@Override
		public String getState() {
			return "PA";
		}

		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB")) return true;
			return false;
		}

		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("laid on the table") ) return 1;
			else if (chamber.equals("upper") && act.contains("laid on the table") ) return 1;
			else if (act.contains("presented to the governor") ) return 2;
			else if ( act.contains("approved by the governor") ) return 3;
			return -1;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-pa-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	static class MDTestAction implements TestAction {

		@Override
		public String getState() {
			return "MD";
		}

		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB")) return true;
			return false;
		}

		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("first reading senate rules") ) return 1;
			else if (chamber.equals("upper") && act.contains("first reading") && !act.contains("first reading senate rules")) return 1;
			else if (act.contains("passed enrolled") ) return 2;
			else if (act.contains("returned passed") ) return 2;
			else if ( act.contains("approved by the governor") ) return 3;
			return -1;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-md-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
		
	}
	static class MSTestAction implements TestAction {

		@Override
		public String getState() {
			return "MS";
		}

		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB")) return true;
			return false;
		}

		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("transmitted to senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("transmitted to house") ) return 1;
			else if (act.contains("enrolled bill signed") ) return 2;
			else if ( act.contains("approved by governor") ) return 3;
			return -1;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ms-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
		
	}

	static class MOTestAction implements TestAction {
		@Override
		public String getState() {
			return "MO";
		}
		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB")) return true;
			return false;
		}
		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("reported to the senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("reported to the assembly") ) return 1;
			else if (act.contains("truly agreed to and finally passed") ) return 2;
			else if ( act.contains("approved by governor") ) return 3;
			else if ( act.contains("signed by governor") ) return 3;
			return -1;
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-mo-json.zip", "2013", TimeZone.getTimeZone("GMT-06:00") );
		}
	}
	static class TXTestAction implements TestAction {

		@Override
		public String getState() {
			return "TX";
		}

		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("HB") || bill_id.contains("SB")) return true;
			return false;
		}

		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("received from the house") ) return 1;
			else if (chamber.equals("upper") && act.contains("received from the senate") ) return 1;
			else if (act.contains("sent to the governor") ) return 2;
			else if ( act.contains("signed by the governor") ) return 3;
			return -1;
		}

		@Override
		public String getSession() {
			return "83";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-tx-json.zip", "83", TimeZone.getTimeZone("GMT-06:00") );
		}

	}
	static class NYTestAction implements TestAction {

		@Override
		public String getState() {
			return "NY";
		}

		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("A ") || bill_id.contains("S ")) return true;
			return false;
		}

		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("delivered to senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("delivered to assembly") ) return 1;
			else if (act.contains("delivered to governor") ) return 2;
			else if ( act.contains("signed chap.") ) return 3;
			return -1;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-ny-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
		
	}
	static class CATestAction implements TestAction {

		@Override
		public String getState() {
			return "CA";
		}

		@Override
		public boolean testId(String bill_id) {
			if ( bill_id.contains("SB") || bill_id.contains("AB") || bill_id.contains("SBX1") || bill_id.contains("ABX1")) return true;
			return false;
		}

		@Override
		public int testAction(String chamber, String act) {
			if (chamber.equals("lower") && act.contains("to the senate") ) return 1;
			else if (chamber.equals("lower") && act.contains("in senate") ) return 1;
			else if (chamber.equals("upper") && act.contains("to the assembly") ) return 1;
			else if (chamber.equals("upper") && act.contains("in assembly") ) return 1;
			else if (act.contains("to engrossing and enrolling") ) return 2;
			else if (act.contains("enrolled and presented to the governor") ) return 2;
			else if ( act.contains("approved by the governor") ) return 3;
			else if ( act.contains("chaptered by secretary of state") ) return 3;
			return -1;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ca-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
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
