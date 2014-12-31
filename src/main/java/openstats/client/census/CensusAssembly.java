package openstats.client.census;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import openstats.client.census.CensusTable.StringPair;
import openstats.client.openstates.OpenState;
import openstats.dbmodel.AggregateResult;
import openstats.model.*;
import openstats.model.District.CHAMBER;

public class CensusAssembly {
	private static final Logger logger = Logger.getLogger(CensusAssembly.class.getName());
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
		List<List<String>> values = censusApi.query(argMap);
//		buildDistricts(censusTable, districts, results, CHAMBER.UPPER);

		if ( !openState.getState().equals("NE")) {
			argMap.clear();
			argMap.put("get", sb.toString());
			argMap.put("for", "state+legislative+district+(lower+chamber):*");
			argMap.put("in", "state:" + openState.getCensusCode());
			// skipping descriptions for the moment
			
			values = censusApi.query(argMap);
//			buildDistricts(censusTable, districts, results, CHAMBER.LOWER);
		}
		return assembly;
	}
	
	private void buildDistricts(CensusTable censusTable, Districts districts, List<List<String>> values, CHAMBER chamber) {
		values.remove(0);
		for(List<String> row: values) {
			District district = districts.findDistrict(chamber, row.get(21));
			if ( district == null ) {
				logger.warning("District not found for state " + row.get(20)+":"+chamber+":"+row.get(21));
				continue;
			}
//			if ( district == null ) throw new RuntimeException("District not found:"+row.get(21));
			
			List<AggregateResult> results = new ArrayList<AggregateResult>();
			for( int idx = 0, idxE = censusTable.cells.size(); idx<idxE; ++idx ) {
				long value;
				try {
					value = Long.parseLong(row.get(idx));
				} catch (NumberFormatException ignored) {
					value = -1;
				}
				results.add(new AggregateResult(value, 0) );
				
			}
			district.setAggregateResults(results);
		}
		
	}
	
}
