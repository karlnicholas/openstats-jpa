package openstats.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import openstats.client.openstates.OpenState;
import openstats.client.openstates.OpenStateClasses;
import openstats.dbmodel.DBAssemblyHandler;
import openstats.model.Assembly;
import openstats.model.District;
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
		initJpa();
		try {
			EntityTransaction et = em.getTransaction();
			et.begin();
			
			for ( OpenState openState: OpenStateClasses.getOpenStates() ) {
				FileInputStream file = new FileInputStream(new File("/home/knicholas/censusdata/"+openState.getState().toLowerCase()+".xls"));
		        		     
			    //Get the workbook instance for XLS file
			    HSSFWorkbook workbook = new HSSFWorkbook(file);
			 
			    //Get first sheet from the workbook
			    HSSFSheet sheet = workbook.getSheetAt(0);
			     
				Assembly assembly = new Assembly(openState.getState(), openState.getSession());
				List<District> districtList = assembly.getDistricts().getDistrictList();
	
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
				DBAssemblyHandler.createAssembly(assembly, em);
			}
			et.commit();
		} finally {
			emf.close();
		}
		     
	}
	
}
