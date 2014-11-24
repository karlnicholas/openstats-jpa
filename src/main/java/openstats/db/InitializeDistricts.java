package openstats.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import openstats.client.openstates.OpenState;
import openstats.client.openstates.OpenStateClasses;
import openstats.dbmodel.DBAssemblyHandler;
import openstats.model.Assembly;
import openstats.model.District;
import openstats.model.District.CHAMBER;
import openstats.model.Districts;

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
		EntityTransaction et = em.getTransaction();
		et.begin();
		for(OpenState openState: OpenStateClasses.getOpenStates()) {
			ZipFile zipFile = null;
			String entryName = null;
			Assembly assembly = new Assembly(openState.getState(), openState.getSession());
			try {
				URL url = InitializeDistricts.class.getResource("/districts/NAMES_ST"+openState.getCensusCode()+"_"+openState.getState()+".zip");
				File bulkDataFile = new File(url.getFile());
				zipFile = new ZipFile( bulkDataFile );
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
				while ( entries.hasMoreElements() ) {
					ZipEntry entry = entries.nextElement();
					if ( entry.isDirectory() ) continue;
					entryName = entry.getName();
					if ( entryName.contains("SLDU")  ) {
						buildDistricts(zipFile, entry, CHAMBER.UPPER, assembly.getDistricts());
					} else if ( entryName.contains("SLDL")  ) {
						buildDistricts(zipFile, entry, CHAMBER.LOWER, assembly.getDistricts());
					}
				}
			} finally {
				if ( zipFile != null ) {
					try {
						zipFile.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
			DBAssemblyHandler.createAssembly(assembly, em);
		}
		et.commit();
		emf.close();
	}
	
	private Districts buildDistricts(ZipFile zipFile, ZipEntry entry, CHAMBER chamber, Districts districts) throws Exception {
		List<District> districtList = districts.getDistrictList();
		BufferedReader reader = new BufferedReader( new InputStreamReader(zipFile.getInputStream(entry), "utf-8"));
		String line;
		while( (line=reader.readLine()) != null ) {
			String[] splits = line.split("\\|");
			if ( !splits[0].equals("STATEFP")) {
				District district = new District(splits[1], chamber);
				district.setName(splits[2]);
				district.setDescription(splits[3]);
				districtList.add(district);
			}
		}
		return districts;
	}
}
