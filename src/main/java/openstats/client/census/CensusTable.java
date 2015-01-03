package openstats.client.census;

import java.util.ArrayList;
import java.util.List;

public class CensusTable {
	public enum AGGORCOMP { AGG, COMP };
	public String tableId;
	public String tableDescr;
	public AGGORCOMP aggOrComp;
	
	public List<StringPair> cells = new ArrayList<StringPair>();
	
	public CensusTable(String tableId, String tableDescr, AGGORCOMP aggOrComp) {
		this.tableId = tableId;
		this.tableDescr = tableDescr;
		this.aggOrComp = aggOrComp;
	}

	public static class StringPair {
		public String label;
		public String descr;
		public StringPair(String label, String descr) {
			this.label = label;
			this.descr = descr;
		}		
	}

}
