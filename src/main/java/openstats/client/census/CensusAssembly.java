package openstats.client.census;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import openstats.client.census.CensusTable.StringPair;
import openstats.client.openstates.OpenState;
import openstats.model.*;
import openstats.model.District.CHAMBER;

public class CensusAssembly {

	public Assembly censusAssembly(OpenState openState, CensusTable censusTable, Assembly assembly) throws Exception {			
		
//		System.out.println("Determine count = " + determineCount);
		assembly.setGroup(new Group(censusTable.tableId, censusTable.tableDescr));

// http://api.census.gov/data/2012/acs5?
		//get=B19301_001E,B19301_001M,B19301A_001E,B19301A_001M
		//&for=state+legislative+district+(upper+chamber):*
		//&in=state:02
		Map<String, String> argMap = new TreeMap<String, String>();

		Districts districts = assembly.getDistricts();
		List<InfoItem> infoItems = new ArrayList<InfoItem>();

		StringBuilder sb = new StringBuilder();
		for (StringPair pair: censusTable.cells) {
			sb.append(pair.label);
			sb.append(',');
			infoItems.add( new InfoItem( pair.label, pair.descr) );
		}

		districts.setAggregateGroupInfo(new GroupInfo(infoItems));

		sb.deleteCharAt(sb.length()-1);
		argMap.put("get", sb.toString());
		argMap.put("for", "state+legislative+district+(upper+chamber):*");
		argMap.put("in", "state:" + openState.getCensusCode());
		// skipping descriptions for the moment
		
		CensusApi censusApi = new CensusApi();
		List<List<String>> results = censusApi.query(argMap);
		buildDistricts(censusTable, districts, results, CHAMBER.UPPER);

		if ( !openState.getState().equals("NE")) {
			argMap.clear();
			argMap.put("get", sb.toString());
			argMap.put("for", "state+legislative+district+(lower+chamber):*");
			argMap.put("in", "state:" + openState.getCensusCode());
			// skipping descriptions for the moment
			
			results = censusApi.query(argMap);
			buildDistricts(censusTable, districts, results, CHAMBER.LOWER);
		}
		return assembly;
	}
	
	private void buildDistricts(CensusTable censusTable, Districts districts, List<List<String>> results, CHAMBER chamber) {
		results.remove(0);
		for(List<String> row: results) {
			District district = districts.findDistrict(chamber, row.get(21));
			if ( district == null ) continue;
//			if ( district == null ) throw new RuntimeException("District not found:"+row.get(21));
			
			List<Long> valueList = new ArrayList<Long>();
			for( int idx = 0, idxE = censusTable.cells.size(); idx<idxE; ++idx ) {
				long value;
				try {
					value = Long.parseLong(row.get(idx));
				} catch (NumberFormatException ignored) {
					value = -1;
				}
				valueList.add(value);
				
			}
			district.setAggregateValues(valueList);
		}
		
	}
	
}
