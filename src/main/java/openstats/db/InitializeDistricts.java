package openstats.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.openstates.data.Legislator.Role;

import openstats.client.openstates.OpenState;
import openstats.client.openstates.OpenStateClasses;
import openstats.dbmodel.DBAssemblyHandler;
import openstats.model.*;
import openstats.model.District.CHAMBER;

public class InitializeDistricts {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public static void main(String... args) throws Exception {
		new InitializeDistricts().run();
	}
	
	private void initJpa() throws Exception {
		emf = Persistence.createEntityManagerFactory("openstats");
		em = emf.createEntityManager();
	}
	
	private void run() throws Exception {
//		initJpa();
		try {
//			EntityTransaction et = em.getTransaction();
//			et.begin();
			
//			for ( OpenState openState: OpenStateClasses.getOpenStates() ) {
				OpenState openState = new OpenStateClasses.CAOpenState();
				
				FileInputStream file = new FileInputStream(new File("c:/users/karl/censusdata/"+openState.getState().toLowerCase()+".xls"));
		        		     
			    //Get the workbook instance for XLS file
			    HSSFWorkbook workbook = new HSSFWorkbook(file);
			 
			    //Get first sheet from the workbook
			    HSSFSheet sheet = workbook.getSheetAt(0);
			     
				Assembly assembly = new Assembly(openState.getState(), openState.getSession());
				List<District> districtList = assembly.getDistrictList();
	
	    		//Iterate through each rows from first sheet
			    Iterator<Row> rowIterator = sheet.iterator();
			    while(rowIterator.hasNext()) {
			        Row row = rowIterator.next();
			         
			        //For each row, iterate through each columns
			        String geoid = row.getCell(2).getStringCellValue();
			        String name = null;
			        CHAMBER chamber = null;
			        if ( geoid.startsWith("61000US") ) {
			        	name = geoid.substring(9);
			        	chamber = CHAMBER.UPPER;
			        } else if (geoid.startsWith("62000US")) {
			        	name = geoid.substring(9);
			        	chamber = CHAMBER.LOWER;
			        }
			        if ( name != null ) {
						District district = new District(name, chamber );
						district.setDescription(row.getCell(3).getStringCellValue());
						districtList.add(district);
					}
		
			    }
			    file.close();
			    setLegislators(openState, assembly);
//				DBAssemblyHandler.createAssembly(assembly, em);
//			}
//			et.commit();
		} finally {
//			emf.close();
		}
		     
	}
	
	public void setLegislators(OpenState openState, Assembly assembly) throws Exception { 
		openState.loadBulkData();
		
		Map<String, org.openstates.data.Legislator> mapLegs = new TreeMap<String, org.openstates.data.Legislator>();
		
		String session = openState.getSession();
		System.out.println("Session = " + session);
		// assume current or less term
		for ( org.openstates.data.Legislator legislator: org.openstates.model.Legislators.values()) {

			String district = null;
			String chamber = null;
			for ( Role role: legislator.roles) {
				if ( role.term != null && role.district != null && role.chamber != null) {
					String key = role.term +'\t'+role.district+'\t'+role.chamber+'\t'+legislator.leg_id;
					if ( mapLegs.get(key) != null ) System.out.println("Dup1:"+key+":"+legislator.full_name+":"+mapLegs.get(key).full_name);
					mapLegs.put(key, legislator);
				}
			}
			if ( legislator.old_roles != null ) {
				for ( String roleKey: legislator.old_roles.keySet() ) {
					for ( Role role: legislator.old_roles.get(roleKey)) {
						if ( role.term != null && role.district != null && role.chamber != null) {
							String key = role.term +'\t'+role.district+'\t'+role.chamber+'\t'+legislator.leg_id;
							if ( mapLegs.get(key) != null ) System.out.println("Dup2:"+key+":"+legislator.full_name+":"+mapLegs.get(key).full_name);
							else mapLegs.put(key, legislator);
						}
					}
				}
			}
		}
		for ( String key: mapLegs.keySet() ) {
			org.openstates.data.Legislator legislator = mapLegs.get(key);
			System.out.println(key+'\t'+legislator.full_name);
		}

		/*		
		for ( org.openstates.data.Legislator legislator: org.openstates.model.Legislators.values()) {
			CHAMBER chamber;
			if ( legislator.chamber.equals("upper") ) chamber = CHAMBER.UPPER;
			else if ( legislator.chamber.equals("lower") ) chamber = CHAMBER.LOWER;
			else throw new RuntimeException("Chamber not found:" + legislator.chamber);
			String dNum = legislator.district;
			if ( dNum.length() != 3 ) {
				try {
					dNum = String.format("%03d", Integer.parseInt(dNum));
				} catch (Exception ignored ) {
					dNum = String.format("%3s", dNum);
				}
			}
			District district = assembly.findDistrict(chamber, dNum);
			if (district == null ) {
				continue;
			}
			Legislator aLegislator = new Legislator();
			aLegislator.setName(legislator.full_name);
			aLegislator.setParty(legislator.party);
			district.getLegislators().add(aLegislator);
		}
*/		
	}	
}
