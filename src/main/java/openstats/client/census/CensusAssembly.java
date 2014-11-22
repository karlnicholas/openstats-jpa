package openstats.client.census;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import openstats.client.census.CensusTable.StringPair;
import openstats.client.openstates.OpenState;
import openstats.model.*;

public class CensusAssembly {

	public Assembly censusAssembly() throws Exception {
		
		CSVParser parser = CSVFormat.RFC4180.parse(Files.newBufferedReader(Paths.get("/home/knicholas/censusdata/ACS_12_5YR_DP03_with_ann.csv"), Charset.forName("utf-8")));
		Iterator<CSVRecord> rowIt = parser.iterator();
		CSVRecord labels = rowIt.next();
		CSVRecord descrs = rowIt.next();
		
		
//		System.out.println("Determine count = " + determineCount);
		Group group = new Group("ACS_12_5YR_DP03", "SELECTED ECONOMIC CHARACTERISTICS: U.S. Census Bureau, 2008-2012 American Community Survey");
		Assembly assembly = new Assembly("CA", "2013", group);
		Districts districts = assembly.getOSDistricts();
		List<InfoItem> infoItems = new ArrayList<InfoItem>();
		for ( int i=0, ie=labels.size(); i<ie; ++i ) {
			infoItems.add( new InfoItem( labels.get(i), descrs.get(i)) );
		}
		districts.setAggregateGroupInfo(new GroupInfo(infoItems));
		// skipping descriptions for the moment
		
		while ( rowIt.hasNext() ) {
			CSVRecord record = rowIt.next();
			String chamber = record.get(0).substring(3,4);
			if ( chamber.equals("U")) chamber = "upper";
			else if (chamber.equals("L")) chamber = "lower";
			else throw new RuntimeException("Invalid Chamber: " + record.get(0));
			String dNum = record.get(0).substring(12);
			dNum = Integer.toString(Integer.parseInt(dNum));
			District district = new District(chamber, dNum);
			districts.getOSDistrictList().add(district);
			
			List<Long> valueList = new ArrayList<Long>();
			Iterator<String> rIt = record.iterator();
			long value;
			while ( rIt.hasNext() ) {
				try {
					value = Long.parseLong(rIt.next());
				} catch (NumberFormatException ignored) {
					value = -1;
				}
				valueList.add(value);
			}
			district.setAggregateValues(valueList);
		}
		return assembly;
	}	

/*
001 Alabama 
002 Alaska
003 Not Used
004 Arizona
005 Arkansas
006 California
007 Not Used
008 Colorado
009 Connecticut
010 Delaware
011 District of Columbia
012 Florida
013 Georgia
014 Not Used
015 Hawaii
016 Idaho
017 Illinois
018 Indiana
019 Iowa
020 Kansas
021 Kentucky
022 Louisiana
023 Maine
024 Maryland
025 Massachusetts
026 Michigan
027 Minnesota
028 Mississippi
029 Missouri
030 Montana
031 Nebraska
032 Nevada
033 New Hampshire
034 New Jersey
035 New Mexico
036 New York
037 North Carolina
038 North Dakota
039 Ohio
040 Oklahoma
041 Oregon
042 Pennsylvania
043 Not Used
044 Rhode Island
045 South Carolina
046 South Dakota
047 Tennessee
048 Texas
049 Utah
050 Vermont
051 Virginia
052 Not Used
053 Washington
054 West Virginia
055 Wisconsin
056 Wyoming	
*/

	public Assembly censusAssembly(CensusTable censusTable) throws Exception {			
		
//		System.out.println("Determine count = " + determineCount);
		Group group = new Group(censusTable.tableId, censusTable.tableDescr);
		Assembly assembly = new Assembly("AL", "2013", group);
// http://api.census.gov/data/2012/acs5?
		//get=B19301_001E,B19301_001M,B19301A_001E,B19301A_001M
		//&for=state+legislative+district+(upper+chamber):*
		//&in=state:02
		Map<String, String> argMap = new TreeMap<String, String>();

		Districts districts = assembly.getOSDistricts();
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
		argMap.put("in", "state:" + "01");
		// skipping descriptions for the moment
		
		CensusApi censusApi = new CensusApi();
		List<List<String>> results = censusApi.query(argMap);
		buildDistricts(censusTable, districts, results, "upper");

		argMap.clear();
		argMap.put("get", sb.toString());
		argMap.put("for", "state+legislative+district+(lower+chamber):*");
		argMap.put("in", "state:" + "01");
		// skipping descriptions for the moment
		
		results = censusApi.query(argMap);
		buildDistricts(censusTable, districts, results, "lower");
		return assembly;
	}
	
	private void buildDistricts(CensusTable censusTable, Districts districts, List<List<String>> results, String chamber) {
		int rowNum = 1;
		results.remove(0);
		for(List<String> row: results) {
			String dNum = Integer.toString(rowNum++);
			District district = new District(chamber, dNum);
			districts.getOSDistrictList().add(district);
			
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
