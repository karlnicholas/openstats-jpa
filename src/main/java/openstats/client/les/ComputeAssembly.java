package openstats.client.les;

import java.io.*;
import java.util.*;

import openstats.client.openstates.*;
import openstats.client.openstates.OpenState.BILLACTION;
import openstats.client.openstates.OpenState.BILLTYPE;
import openstats.client.util.Statistics;
import openstats.model.*;
import openstats.model.District.CHAMBER;

public class ComputeAssembly {

	class BillAction implements Comparable<BillAction> {
		public org.openstates.data.Bill.Action action; 
		public BillAction(org.openstates.data.Bill.Action action) {
			this.action = action;
		}
		@Override
		public int compareTo(BillAction o) {
			return action.date.compareTo(o.action.date);
		}
		
	}

	private TreeSet<String> currentTopics;

	public void computeAssemblyLES(OpenState openState, Assembly assembly) throws Exception { 
		openState.loadBulkData();
		TreeMap<org.openstates.data.Legislator, AuthorStats> legislatorStats = readLegislators();
		buildcurrentTopics(openState);
		determineOfficeScores(legislatorStats);
		ArrayList<org.openstates.data.Bill.Sponsor> sponsors = new ArrayList<org.openstates.data.Bill.Sponsor>();
		int determineCount = 0;
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
				if ( sponsorStats != null ) {
					determineCount++;
					determineBillProgress(bill, sponsorStats, openState);
				}
	
			}
//			if ( sponsors.size() == 0 ) System.out.println("Principal Sponsor Not Found:" + bill.bill_id );
		}
//		System.out.println("Determine count = " + determineCount);
//		Group group = 
		assembly.setGroup(new Group(Labels.LESGROUPNAME, Labels.LESGROUPDESCR));
		
		Districts districts = assembly.getDistricts();
		List<InfoItem> osInfoItems = new ArrayList<InfoItem>();
		for ( int i=0, ie=Labels.DISTRICTSAGGREGATELABELS.size(); i<ie; ++i ) {
			osInfoItems.add( new InfoItem( Labels.DISTRICTSAGGREGATELABELS.get(i), Labels.DISTRICTSAGGREGATEDESC.get(i)) );
		}
		districts.setAggregateGroupInfo(new GroupInfo(osInfoItems));
		// skipping descriptions for the moment
		
		for ( org.openstates.data.Legislator legislator: legislatorStats.keySet() ) {
			AuthorStats sponsorStats = legislatorStats.get(legislator); 
			
			CHAMBER chamber;
			if ( legislator.chamber.equals("upper") ) chamber = CHAMBER.UPPER;
			else if ( legislator.chamber.equals("lower") ) chamber = CHAMBER.LOWER;
			else throw new RuntimeException("Chamber not found:" + legislator.chamber);
			District district = districts.findDistrict(chamber, legislator.district);
			if (district == null ) throw new RuntimeException("District not found:"+":"+legislator.chamber+":"+ legislator.district );
			
			if ( district.getAggregateValues() != null ) {
				List<Long> valueList = district.getAggregateValues();
				valueList.set(0, valueList.get(0) + sponsorStats.billData[0][0]);
				valueList.set(1, valueList.get(1) + sponsorStats.billData[0][3]);
				valueList.set(2, valueList.get(2) + sponsorStats.billData[1][0]);
				valueList.set(3, valueList.get(3) + sponsorStats.billData[1][1]);
				valueList.set(4, valueList.get(4) + sponsorStats.billData[1][2]);
				valueList.set(5, valueList.get(5) + sponsorStats.billData[1][3]);
				valueList.set(6, valueList.get(6) + sponsorStats.billData[2][0]);
				valueList.set(7, valueList.get(7) + sponsorStats.billData[2][1]);
				valueList.set(8, valueList.get(8) + sponsorStats.billData[2][2]);
				valueList.set(9, valueList.get(9) + sponsorStats.billData[2][3]);
			} else {
				List<Long> valueList = new ArrayList<Long>();
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
				district.setAggregateValues(valueList);
			}
		}
		computeLES(districts);
		System.out.println(openState.getState()+":"+computeSkewness(assembly));
	}	

	
	public double computeSkewness(Assembly assembly) {
		Districts districts = assembly.getDistricts();
		double[] stats = new double[districts.getDistrictList().size()];
		int idx=0;
		for ( District district: districts.getDistrictList() ) {
			List<Double> valueList = district.getComputationValues();
			stats[idx++] = valueList.get(0);
		}
		Statistics statistics = new Statistics(stats);
		List<InfoItem> osInfoItems = new ArrayList<InfoItem>();
		for ( int i=0, ie=Labels.ASSEMBLYCOMPUTATIONLABEL.size(); i<ie; ++i ) {
			osInfoItems.add( new InfoItem( Labels.ASSEMBLYCOMPUTATIONLABEL.get(i), Labels.ASSEMBLYCOMPUTATIONDESC.get(i)) );
		}
		assembly.setComputationGroupInfo(new GroupInfo(osInfoItems));
		List<Double> valueList = new ArrayList<Double>();

		double mean = statistics.getMean();
		double variance = statistics.getVariance();
		
		double thirdmoment = 0;
		for ( double v: stats )
			thirdmoment += Math.pow((v-mean), 3.0);
		thirdmoment /= stats.length;

		// same a skew.p() in excel.
		double skewness = thirdmoment / Math.pow(variance, (3.0/2.0));

//		double skewness = (3.0*(statistics.getMean() - statistics.getMedian()))/statistics.getStdDev();
		valueList.add(skewness); 
		assembly.setComputationValues(valueList);
		return skewness;
	}


	private void determinePrincipalSponsors(
		org.openstates.data.Bill bill, 
		ArrayList<org.openstates.data.Bill.Sponsor> sponsors
	) {
		for ( org.openstates.data.Bill.Sponsor sponsor: bill.sponsors ) {
// if ( !sponsor.type.toLowerCase().equals("primary") ) System.out.print(".");
			if ( sponsor.type.toLowerCase().equals("primary") ) sponsors.add(sponsor);
		}
	}
	
	private void determineBillProgress(
		org.openstates.data.Bill bill, 
		AuthorStats sponsorStats, OpenState openState
	) {
		BILLTYPE billType = openState.getBillType(bill, currentTopics);	// default resolution
		if ( billType == BILLTYPE.UNKNOWN ) throw new RuntimeException("Cannot determine bill type:" + bill.bill_id+":" + bill.title);
		
		List<BillAction> actions = new ArrayList<BillAction>();
		for ( org.openstates.data.Bill.Action action: bill.actions ) {
			actions.add(new BillAction(action));
		}
		Collections.sort(actions);

/*
if ( bill.chamber.toLowerCase().equals("upper") && billType == BILLTYPE.RESOLUTION) {
	for ( BillAction action: actions ) {
		System.out.println(bill.bill_id+":"+action.action.action);
	}
}
*/
		
		int progress = 0;
		for ( BillAction myAction: actions ) {
			String act = myAction.action.action.toLowerCase();
//			if ( bill.bill_id.contains("SR") ) System.out.println(bill.bill_id + ":" + bill.chamber+":"+act);
			BILLACTION billProgress = openState.getBillAction(bill.chamber, act, billType);
			if ( billProgress != BILLACTION.OTHER && billProgress.ordinal() > progress ) progress = billProgress.ordinal();
		}
		sponsorStats.billData[billType.ordinal()][progress]++;

	}

	private TreeMap<org.openstates.data.Legislator, AuthorStats> readLegislators() throws Exception {
		TreeMap<org.openstates.data.Legislator, AuthorStats> legislators = new TreeMap<org.openstates.data.Legislator, AuthorStats>();
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
	private void determineOfficeScores(
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
	
	public void computeLES(Districts districts) {
				
//		ArrayList<Long> lidsAll = makeRList();
		
		List<InfoItem> osInfoItems = new ArrayList<InfoItem>();
		for ( int i=0, ie=Labels.DISTRICTCOMPUTATIONLABEL.size(); i<ie; ++i ) {
			osInfoItems.add( new InfoItem( Labels.DISTRICTCOMPUTATIONLABEL.get(i), Labels.DISTRICTCOMPUTATIONDESC.get(i)) );
		}
		districts.setComputationGroupInfo(new GroupInfo(osInfoItems));
	
		double LESMult = new Double(districts.getDistrictList().size()/4.0);

		double[][] denomArray = new double[3][4];

		denomArray[0][0] = totalFrom(districts, 0);
		denomArray[0][1] = 0.0;
		denomArray[0][2] = 0.0;
		denomArray[0][3] = totalFrom(districts, 1); 
		
		denomArray[1][0] = totalFrom(districts, 2);
		denomArray[1][1] = totalFrom(districts, 3); 
		denomArray[1][2] = totalFrom(districts, 4); 
		denomArray[1][3] = totalFrom(districts, 5); 

		denomArray[2][0] = totalFrom(districts, 6);
		denomArray[2][1] = totalFrom(districts, 7); 
		denomArray[2][2] = totalFrom(districts, 8); 
		denomArray[2][3] = totalFrom(districts, 9);
		
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

		for ( District dist: districts.getDistrictList()) {

			List<Long> valueList = dist.getAggregateValues();

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
			List<Double> comps = new ArrayList<Double>();
			comps.add(LES);
			dist.setComputationValues(comps);
		}
	}
	
	private double totalFrom(Districts districts, int index) {
		double ret = 0.0;
		for ( District dist: districts.getDistrictList()) {
			Long iVal = dist.getAggregateValues().get(index);
			ret = ret + iVal;
		}
		return ret;
	}

	private void buildcurrentTopics(OpenState openState) throws Exception {
		currentTopics = new TreeSet<String>(); 
		InputStream is = ComputeAssembly.class.getResourceAsStream("/topics/" + openState.getState() + "TopicBills2013.txt");
		InputStreamReader isr = new InputStreamReader(is, "ASCII");
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ( (line = br.readLine()) != null ) {
			currentTopics.add(line);
		}
		is.close();
//		System.out.println(currentTopics);
	}

}
