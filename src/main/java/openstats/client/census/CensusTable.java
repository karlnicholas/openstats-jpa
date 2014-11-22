package openstats.client.census;

import java.util.ArrayList;
import java.util.List;

public class CensusTable {
	
	public static class StringPair {
		public String label;
		public String descr;
		public StringPair(String label, String descr) {
			super();
			this.label = label;
			this.descr = descr;
		}		
	}
	public String tableId;
	public String tableDescr;
	
	public List<StringPair> cells = new ArrayList<StringPair>();
	

}
