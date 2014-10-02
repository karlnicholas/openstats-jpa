
import java.io.*;
import java.util.*;

import openstats.model.*;

import org.supercsv.io.AbstractCsvWriter;
import org.supercsv.prefs.CsvPreference;


public class WriteCsv {
	
	public static void writeCsv(Assembly session) throws Exception {
        
    	class MyCsvWriter extends AbstractCsvWriter {

			public MyCsvWriter(Writer writer, CsvPreference preference) {
				super(writer, preference);
			}
			public void writeRow(String... columns ) throws IOException {
				super.writeRow(columns);
			}
		}
		MyCsvWriter writer = null;
        try {
        	
        	writer = new MyCsvWriter(new FileWriter("c:/users/karl/"+session.getState()+"-2013-les.csv"), CsvPreference.STANDARD_PREFERENCE);
	        Districts districts = session.getDistricts();
	        // the header elements are used to map the bean valueList to each column (names must match)
	        List<String> columns = new ArrayList<String>();

	        columns.add("district");
	        columns.add("chamber");
//			Aggregate aggregate = districts.getAggregate(GROUPLABEL);

	        for ( String label: districts.getAggregateGroupMap().get(Labels.GROUPLABEL).getGroupLabels()) {
	        	columns.add(label);
	        }
	        for ( String label: districts.getComputationGroupMap().get(Labels.GROUPLABEL).getGroupLabels()) {
	        	columns.add(label);
	        }

            // write the header
	        String[] sColumns = new String[columns.size()];
	        sColumns = columns.toArray(sColumns);
            writer.writeHeader(sColumns);
            class LESComparator implements Comparator<District> {
				int index;
            	public LESComparator(Districts districts) {
            		index = districts.getComputationGroupMap().get(Labels.GROUPLABEL).getGroupLabels().indexOf(Labels.LESLABEL.get(0));
            	}
				@Override
				public int compare(District o1, District o2) {
					return o2.getComputations().get(Labels.GROUPLABEL).get(index).compareTo(o1.getComputations().get(Labels.GROUPLABEL).get(index));
				}
            }
            Collections.sort(districts.getDistrictList(), new LESComparator(districts) );
            // write the customer lists
            for ( final District dist: districts.getDistrictList()) {
            	columns.clear();
    	        columns.add(dist.getDistrict());
    	        columns.add(dist.getChamber());
    	        List<Long> aggs = dist.getAggregates().get(Labels.GROUPLABEL);
    	        for ( Long agg: aggs ) {
    	        	columns.add(agg.toString());
    	        }
    	        List<Double> comps = dist.getComputations().get(Labels.GROUPLABEL);
    	        for ( Double comp: comps ) {
    	        	columns.add(comp.toString());
    	        }
                writer.writeRow(columns.toArray(sColumns));
            }

            writer.writeHeader(Labels.SKEWLABEL.get(0));
            writer.writeRow(session.getComputations().get(Labels.GROUPLABEL).get(0).toString());
        }
        finally {
            if( writer  != null ) {
            	writer.close();
            }
        }
	}

}
