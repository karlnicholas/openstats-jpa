package openstats.client.les;

import java.util.*;

public class Labels {

	public static final String LESGROUPNAME = new String("BILLPROGRESS");
	public static final String LESGROUPDESCR = new String("Legislative Effiveness Scores based on Bill Type and Progress.\n(Volden, C., & Wiseman, A. E. (2009). Legislative effectiveness in Congress. Manuscript, The Ohio State University.)");
	public static ArrayList<String> DISTRICTSAGGREGATELABELS = new ArrayList<String>( Arrays.asList(new String[] {
		"ResInt", "ResAdopted", 
		"BillsInt", "BillsOC", "BillsPassed", "BillsChap" 
	}));
	public static ArrayList<String> DISTRICTSAGGREGATEDESC = new ArrayList<String>( Arrays.asList(new String[] {
			"Resolutions Introduced", "Resolutions Adopted", 
			"Bills Introduced", "Bills to Other Chamber", "Bills Passed", "Bills Chaptered"
		}));
	public static final String DISTRICTCOMPUTATIONLABEL = "LES";
	public static final String DISTRICTCOMPUTATIONDESC = "Legislator Effectiveness Score";
	public static final String ASSEMBLYCOMPUTATIONLABEL = "Skewness";
	public static final String ASSEMBLYCOMPUTATIONDESC = "Skewness of distribution of LES scores for all districts";
		

}
