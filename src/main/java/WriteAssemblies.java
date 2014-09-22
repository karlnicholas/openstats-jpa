
import java.io.*;
import java.util.*;

import javax.persistence.*;

import openstats.model.*;

import org.openstates.bulkdata.LoadBulkData;

public class WriteAssemblies {

	static class AuthorStats {
		public AuthorStats() {
			billData = new long[3][];
			for ( int i=0; i<3; ++i ) {
				billData[i] = new long[4];
				for ( int j=0;j<4;++j) {
					billData[i][j] = 0;
				}
			}
		}
		long billData[][];
		int cmember = 0;
		int cvchair = 0;
		int cchair = 0;
		int leader = 0;
		int officeScore = -1;
		double les = 0.0;
	}

	private static TreeSet<String> currentTopics;
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) throws Exception {
		initJpa();

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
	private static void writeJpa(Assembly assembly) throws Exception {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(assembly);
		tx.commit();
	}

	private static Assembly buildAssembly(TestAction testAction) throws Exception { 
		testAction.loadBulkData();
		TreeMap<org.openstates.data.Legislator, AuthorStats> legislatorStats = readLegislators();
		buildcurrentTopics(testAction);
		determineOfficeScores(legislatorStats);
		ArrayList<org.openstates.data.Bill.Sponsor> sponsors = new ArrayList<org.openstates.data.Bill.Sponsor>();
		Collection<org.openstates.data.Bill> bills = org.openstates.model.Bills.values();
		for ( org.openstates.data.Bill bill:  bills ) {
	//		System.out.println(bill.bill_id+"---------------------------------------");
			sponsors.clear();
			determinePrincipalSponsors(bill, sponsors);
			for ( org.openstates.data.Bill.Sponsor sponsor: sponsors ) {
				org.openstates.data.Legislator legislator = null;
				AuthorStats sponsorStats = null;
				if ( sponsor != null && sponsor.leg_id != null ) {
					legislator = org.openstates.model.Legislators.get(sponsor.leg_id);
					if ( legislator != null ) sponsorStats = legislatorStats.get(legislator);
				}
				if ( sponsorStats != null ) determineBillProgress(bill, sponsorStats, testAction);
	
			}
			if ( sponsors.size() == 0 ) System.out.println("Principal Sponsor Not Found:" + bill.bill_id );
		}
		
		Assembly assembly = new Assembly();
		assembly.setState(testAction.getState());
		assembly.setAssembly(testAction.getAssembly());
		Districts districts = assembly.getDistricts();
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.getGroupLabels().addAll(Labels.AGGLABELS);
		districts.getAggregateGroupMap().put(Labels.GROUPLABEL, groupInfo);
//		Aggregate aggregate = districts.getUserData().createAggregate(Labels.GROUPLABEL, AGGLABELS);
		
		for ( org.openstates.data.Legislator legislator: legislatorStats.keySet() ) {
			AuthorStats sponsorStats = legislatorStats.get(legislator); 
			
			openstats.model.District district = districts.findDistrict(legislator.chamber, legislator.district);
			if ( district != null ) {
				ArrayList<Long> valueList = district.getAggregates().get(Labels.GROUPLABEL);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[0][0]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[0][3]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[1][0]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[1][1]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[1][2]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[1][3]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[2][0]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[2][1]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[2][2]);
				valueList.set(0, valueList.get(0) + sponsorStats.billData[2][3]);
				district.getAggregates().put(Labels.GROUPLABEL, valueList);
			} else {
				openstats.model.Legislator sLegislator = new openstats.model.Legislator();
				sLegislator.setName(legislator.full_name);
				sLegislator.setParty(legislator.party);
				district = new openstats.model.District();
				district.setChamber(legislator.chamber);
				district.setDistrict(legislator.district);
				district.getLegislators().add(sLegislator); 
				districts.getDistrictList().add(district);
				
				ArrayList<Long> valueList = new ArrayList<Long>(Labels.AGGLABELS.size());
				valueList.add(sponsorStats.billData[0][0]);
				valueList.add(sponsorStats.billData[0][3]);
				valueList.add(sponsorStats.billData[1][0]);
				valueList.add(sponsorStats.billData[1][1]);
				valueList.add(sponsorStats.billData[1][2]);
				valueList.add(sponsorStats.billData[1][3]);
				valueList.add(sponsorStats.billData[2][0]);
				valueList.add(sponsorStats.billData[2][1]);
				valueList.add(sponsorStats.billData[2][2]);
				valueList.add(sponsorStats.billData[2][3]);
				district.getAggregates().put(Labels.GROUPLABEL, valueList);
			}
		}
		computeLES(districts);
		computeSkewness(assembly);
		return assembly;
	}	

	
	public static void computeSkewness(Assembly assembly) throws OpenStatsException {
		Districts districts = assembly.getDistricts();
		double[] stats = new double[districts.getDistrictList().size()];
		int i=0;
		for ( District district: districts.getDistrictList() ) {
			ArrayList<Double> valueList = district.getComputations().get(Labels.GROUPLABEL);
			stats[i++] = valueList.get(0);
		}
		Statistics statistics = new Statistics(stats);
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.getGroupLabels().addAll(Labels.SKEWLABEL);
		assembly.getComputationGroupMap().put(Labels.GROUPLABEL, groupInfo);
		ArrayList<Double> valueList = new ArrayList<Double>(); 
		valueList.add((3.0*(statistics.getMean() - statistics.getMedian()))/statistics.getStdDev()); 
		assembly.getComputations().put(Labels.GROUPLABEL, valueList);		
	}

	/**
			legAgg.setName(legislator.full_name);
			legAgg.setChamber(legislator.chamber);
			legAgg.setDistrict(legislator.district);
			legAgg.setParty(legislator.party);
			Map<String, Integer> aggregates = legAgg.getValueAggregates(); 
			aggregates.put(RESINT.toString(), sponsorStats.billData[0][0]);
			aggregates.put(RESADOPTED.toString(), sponsorStats.billData[0][3]);
			aggregates.put(BILLSINT.toString(), sponsorStats.billData[1][0]);
			aggregates.put(BILLSOC.toString(), sponsorStats.billData[1][1]);
			aggregates.put(BILLSPASSED.toString(), sponsorStats.billData[1][2]);
			aggregates.put(BILLSCHAP.toString(), sponsorStats.billData[1][3]);
			aggregates.put(TOPICSINT.toString(), sponsorStats.billData[2][0]);
			aggregates.put(TOPICSOC.toString(), sponsorStats.billData[2][1]);
			aggregates.put(TOPICSPASSED.toString(), sponsorStats.billData[2][2]);
			aggregates.put(TOPICSCHAP.toString(), sponsorStats.billData[2][3]);
			Map<String, Double> computations = legAgg.getValueComputations();
			computations.put("LES", sponsorStats.les);
	 */

	private static void determinePrincipalSponsors(
		org.openstates.data.Bill bill, 
		ArrayList<org.openstates.data.Bill.Sponsor> sponsors
	) {
		for ( org.openstates.data.Bill.Sponsor sponsor: bill.sponsors ) {
			if ( sponsor.type.toLowerCase().equals("primary") ) sponsors.add(sponsor);
		}
	}
	
	private static void determineBillProgress(
		org.openstates.data.Bill bill, 
		AuthorStats sponsorStats, TestAction testAction
	) {
		int cat;	// default resolution
		if ( testAction.testId(bill.bill_id) == true ) {
			if ( currentTopics.contains(bill.bill_id) ) {
//				System.out.println("Topic: " + bill.bill_id);
				cat = 2;
			}
			else cat = 1;
		}
		else cat = 0;
		
		List<MyAction> actions = new ArrayList<MyAction>();
		for ( org.openstates.data.Bill.Action action: bill.actions ) {
			actions.add(new MyAction(action));
		}
		Collections.sort(actions);
		
		int progress = 0;
		for ( MyAction myAction: actions ) {
			String act = myAction.action.action.toLowerCase();
//			if ( bill.bill_id.contains("SR") ) System.out.println(bill.bill_id + ":" + bill.chamber+":"+act);
			int tprog = testAction.testAction(bill.chamber, act);
			if ( tprog >= 0 ) progress = tprog;
		}
		sponsorStats.billData[cat][progress]++;

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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
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
		public String getAssembly() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ca-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}
/*
	private static void printAllActions(Bill bill) {
		for ( Bill.Action action: bill.actions ) {
			System.out.println(action);
		}
	}
*/
	private static TreeMap<org.openstates.data.Legislator, AuthorStats> readLegislators() throws Exception {
		TreeMap<org.openstates.data.Legislator, AuthorStats> legislators = new TreeMap<>();
		for ( org.openstates.data.Legislator legislator: org.openstates.model.Legislators.values()) {
			legislators.put(legislator, new AuthorStats());
		}
		return legislators;
	}
	
	/**
	 * 
	 * Legislative Influence: Toward Theory Development through Causal Analysis
	 * Author(s): Katherine Meyer
	 * Source: Legislative Studies Quarterly, Vol. 5, No. 4 (Nov., 1980), pp. 563-585
	 * Published
	 * 
	 * It assigned the following valueList to positions: Party Leader
	 * or Whip = 5; Committee Chair and Vice Chair simultaneously on different
	 * committees = 4; Committee Chair only = 3; two or more Committee Vice
	 * Chairs = 2; Committee Vice Chair only = 1; and Member only = 0.
	 * 
	 * Added -1 if no office held
	 * 
	 */
	private static void determineOfficeScores(
		TreeMap<org.openstates.data.Legislator, AuthorStats> authorSuccess
	) {
		for ( org.openstates.data.Committee committee: org.openstates.model.Committees.values() ) {
			for ( org.openstates.data.Committee.Member member: committee.members ) {
				org.openstates.data.Legislator legislator = null;
				if ( member.leg_id != null ) legislator = org.openstates.model.Legislators.get(member.leg_id);
				if ( legislator != null ) {
					AuthorStats successStat = authorSuccess.get(legislator);
					String role = member.role.toLowerCase();
					if ( role.contains("member")) {
						successStat.cmember++;
					}
					else if ( role.contains("vice")) {
						successStat.cvchair++;
					}
					else if ( role.contains("chair") ) {
						successStat.cchair++;
//					} else { 
						// assume it's a leadership position?
//						System.out.println("Leader Role???:" + legislator + ":" + role);
//						successStat.leader++;
					}
				}
			}
		}
		// check 
		for (org.openstates.data.Legislator legislator: authorSuccess.keySet() ) {
			AuthorStats successStat = authorSuccess.get(legislator); 
			if ( successStat.cmember > 0 ) successStat.officeScore = 0;
			if ( successStat.cvchair == 1 ) successStat.officeScore = 1;
			if ( successStat.cvchair > 1 ) successStat.officeScore = 2;
			if ( successStat.cchair == 1 ) successStat.officeScore = 3;
			if ( successStat.cchair > 0 && successStat.cvchair > 0 ) successStat.officeScore = 4;
			if ( successStat.leader > 0 ) successStat.officeScore = 5;
/*
			for ( Legislator.Role role: legislator.roles ) {
				String type = role.type.toLowerCase();
				if ( !(type.contains("member") || type.contains("vice chair") || type.contains("chair")) ) {
					System.out.println("Presumed leadership?:" + role);
					successStat.officeScore = 5;
				}
			}
*/			
		}
	}
	
	public static void computeLES(Districts districts) throws OpenStatsException {
				
//		ArrayList<Long> lidsAll = makeRList();
//		Computation computation = districts.getUserData().createComputation(Labels.GROUPLABEL, Labels.LESLABEL);
		
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.getGroupLabels().addAll(Labels.LESLABEL);
		districts.getComputationGroupMap().put(Labels.GROUPLABEL, groupInfo);
	
		double LESMult = new Double(districts.getDistrictList().size()/4.0);

		double[][] denomArray = new double[3][4];

		denomArray[0][0] = totalFrom(districts, Labels.AGGLABELS.get(0));
		denomArray[0][1] = 0.0;
		denomArray[0][2] = 0.0;
		denomArray[0][3] = totalFrom(districts, Labels.AGGLABELS.get(1)); 
		
		denomArray[1][0] = totalFrom(districts, Labels.AGGLABELS.get(2));
		denomArray[1][1] = totalFrom(districts, Labels.AGGLABELS.get(3)); 
		denomArray[1][2] = totalFrom(districts, Labels.AGGLABELS.get(4)); 
		denomArray[1][3] = totalFrom(districts, Labels.AGGLABELS.get(5)); 

		denomArray[2][0] = totalFrom(districts, Labels.AGGLABELS.get(6));
		denomArray[2][1] = totalFrom(districts, Labels.AGGLABELS.get(7)); 
		denomArray[2][2] = totalFrom(districts, Labels.AGGLABELS.get(8)); 
		denomArray[2][3] = totalFrom(districts, Labels.AGGLABELS.get(9));
		
		// make the array inverse cumulative across rows 
		for ( int j=0; j < 3; ++j ) {
			for ( int i=0; i < 4; ++i ) {
				double sum = 0.0;
				for ( int i2=i; i2 < 4; ++i2 ) {
					sum += denomArray[j][i2]; 
				}
				denomArray[j][i] = sum;
			}
		}

		double billsMult = 5.0;
		double topicMult = 10.0;
		
		
		double[] denom = new double[4];
		denom[0] = denomArray[0][0]
				+ (billsMult * denomArray[1][0])  
				+ (topicMult * denomArray[2][0]); 

		denom[1] = denomArray[0][1]
				+ (billsMult * denomArray[1][1])  
				+ (topicMult * denomArray[2][1]); 

		denom[2] = denomArray[0][2]
				+ (billsMult * denomArray[1][2])  
				+ (topicMult * denomArray[2][2]); 
	
		denom[3] = denomArray[0][3]
				+ (billsMult * denomArray[1][3])  
				+ (topicMult * denomArray[2][3]); 

		double[][] distArray = new double[3][4];

		for ( openstats.model.District dist: districts.getDistrictList()) {

			ArrayList<Long> valueList = dist.getAggregates().get(Labels.GROUPLABEL);

			distArray[0][0] = valueList.get(0);
			distArray[0][1] = 0.0;
			distArray[0][2] = 0.0;
			distArray[0][3] = valueList.get(1); 
			
			distArray[1][0] = valueList.get(2);
			distArray[1][1] = valueList.get(3); 
			distArray[1][2] = valueList.get(4); 
			distArray[1][3] = valueList.get(5); 

			distArray[2][0] = valueList.get(6);
			distArray[2][1] = valueList.get(7); 
			distArray[2][2] = valueList.get(8); 
			distArray[2][3] = valueList.get(9);
				
			// make the array inverse cumulative across rows 
			for ( int j=0; j < 3; ++j ) {
				for ( int i=0; i < 4; ++i ) {
					double sum = 0.0;
					for ( int i2=i; i2 < 4; ++i2 ) {
						sum += distArray[j][i2]; 
					}
					distArray[j][i] = sum;
				}
			}
	
			double[] num = new double[4];
			num[0] = distArray[0][0]
					+ (billsMult * distArray[1][0])  
					+ (topicMult * distArray[2][0]); 

			num[1] = distArray[0][1]
					+ (billsMult * distArray[1][1])  
					+ (topicMult * distArray[2][1]); 

			num[2] = distArray[0][2]
					+ (billsMult * distArray[1][2])  
					+ (topicMult * distArray[2][2]); 

			num[3] = distArray[0][3]
					+ (billsMult * distArray[1][3])  
					+ (topicMult * distArray[2][3]); 

			double partIntroduced = num[0] / denom[0];			
			double partOtherChamber = num[1] / denom[1];
			double partPassed = num[2] / denom[2];
			double partChaptered = num[3] / denom[3]; 

			double LES = (partIntroduced + partOtherChamber + partPassed + partChaptered) * LESMult;
			ArrayList<Double> comps = new ArrayList<Double>(Labels.LESLABEL.size());
			comps.add(LES);
			dist.getComputations().put(Labels.GROUPLABEL, comps);
		}
	}
	
	private static double totalFrom(Districts districts, String label) throws OpenStatsException {
		double ret = 0.0;
		int index = districts.getAggregateGroupMap().get(Labels.GROUPLABEL).getGroupLabels().indexOf(label);
		for ( openstats.model.District dist: districts.getDistrictList()) {
			Long iVal = dist.getAggregates().get(Labels.GROUPLABEL).get(index);
			ret = ret + iVal;
		}
		return ret;
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

	interface TestAction {
		public String getState();
		public String getAssembly();
		public void loadBulkData() throws Exception;
		public boolean testId(String bill_id);
		public int testAction(String chamber, String act);
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
