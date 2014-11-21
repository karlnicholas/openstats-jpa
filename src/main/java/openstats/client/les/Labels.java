package openstats.client.les;

import java.util.*;

public class Labels {

	public static final String LESGROUPNAME = new String("BILLPROGRESS");
	public static final String LESGROUPDESCR = new String("Legislative Effiveness Scores based on Bill Type and Progress.\n(Volden, C., & Wiseman, A. E. (2009). Legislative effectiveness in Congress. Manuscript, The Ohio State University.)");
	public static ArrayList<String> DISTRICTSAGGREGATELABELS = new ArrayList<String>( Arrays.asList(new String[] {
		"ResInt", "ResAdopted", 
		"BillsInt", "BillsOC", "BillsPassed", "BillsChap", 
		"TopicsInt", "TopicsOC", "TopicsPassed", "TopicsChap"
	}));
	public static ArrayList<String> DISTRICTSAGGREGATEDESC = new ArrayList<String>( Arrays.asList(new String[] {
			"Resolutions Introduced", "Resolutions Adopted", 
			"Bills Introduced", "Bills to Other Chamber", "Bills Passed", "Bills Chaptered", 
			"Topical Bills Introduced", "Topical Bills to Other Chamber", "Topical Bills Passed", "Topical Bills Chaptered"
		}));
	public static ArrayList<String> DISTRICTCOMPUTATIONLABEL = new ArrayList<String>(Arrays.asList(new String[] {"LES"}));
	public static ArrayList<String> DISTRICTCOMPUTATIONDESC = new ArrayList<String>(Arrays.asList(new String[] {"Legislator Effectiveness Score"}));
	public static ArrayList<String> ASSEMBLYCOMPUTATIONLABEL = new ArrayList<String>(Arrays.asList(new String[] {"Skewness"}));
	public static ArrayList<String> ASSEMBLYCOMPUTATIONDESC = new ArrayList<String>(Arrays.asList(new String[] {"Skewness of distribution of LES scores for all districts"}));
		

}
