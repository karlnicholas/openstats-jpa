import java.io.*;

import openstats.client.les.ComputeAssembly;
import openstats.client.openstates.*;
import openstats.osmodel.OSAssembly;
import openstats.util.AssemblyCsvHandler;


public class ComputeTest {
	

	public static void main(String[] args) throws Exception {
/*
		OpenState[] testActions = new OpenState[] {
			new OpenStateClasses.MIOpenState(),
			new OpenStateClasses.MTOpenState(),
			new OpenStateClasses.INOpenState(),
//			new OpenStateClasses.IDOpenState(),		
			new OpenStateClasses.CTOpenState(), 
			new OpenStateClasses.VTOpenState(), 
			new OpenStateClasses.UTOpenState(), 
			new OpenStateClasses.SDOpenState(), 
			new OpenStateClasses.OROpenState(), 
			new OpenStateClasses.OHOpenState(), 
			new OpenStateClasses.WYOpenState(), 
//			new OpenStateClasses.SCOpenState(),
			new OpenStateClasses.RIOpenState(),
			new OpenStateClasses.NVOpenState(),
			new OpenStateClasses.NMOpenState(),
			new OpenStateClasses.NHOpenState(),	
			new OpenStateClasses.NEOpenState(),
			new OpenStateClasses.NDOpenState(),
			new OpenStateClasses.MEOpenState(),
//			new OpenStateClasses.KSOpenState(),	
			new OpenStateClasses.ILOpenState(),	
//			new OpenStateClasses.IAOpenState(),	
			new OpenStateClasses.FLOpenState(), 
			new OpenStateClasses.COOpenState(), 
			new OpenStateClasses.ALOpenState(), 
			new OpenStateClasses.AKOpenState(), 
			new OpenStateClasses.KYOpenState(), 
			new OpenStateClasses.GAOpenState(), 
			new OpenStateClasses.AROpenState(), 
			new OpenStateClasses.OKOpenState(), 
			new OpenStateClasses.MAOpenState(), 
			new OpenStateClasses.NCOpenState(), 
			new OpenStateClasses.AZOpenState(),
//				new OpenStateClasses.MNOpenState(), 
			new OpenStateClasses.HIOpenState(), 
			new OpenStateClasses.LAOpenState(), 
			new OpenStateClasses.TNOpenState(), 
			new OpenStateClasses.VAOpenState(), 
			new OpenStateClasses.NJOpenState(), 
			new OpenStateClasses.PAOpenState(), 
			new OpenStateClasses.MDOpenState(), 
			new OpenStateClasses.MSOpenState(), 
			new OpenStateClasses.MOOpenState(), 
			new OpenStateClasses.TXOpenState(), 
			new OpenStateClasses.NYOpenState(), 
			new OpenStateClasses.CAOpenState(),
		};

		ComputeAssembly computeAssembly = new ComputeAssembly();
		AssemblyCsvHandler csvHandler = new AssemblyCsvHandler();
//		Writer writer = new OutputStreamWriter(System.out);
		for( OpenState testAction: testActions) {
			OSAssembly osAssembly = computeAssembly.computeAssemblyLES(testAction);
//			csvHandler.writeCsv(writer, osAssembly);
//			writer.flush();
		}
		
*/
		
		ComputeAssembly computeAssembly = new ComputeAssembly();
		AssemblyCsvHandler csvHandler = new AssemblyCsvHandler();
		OSAssembly osAssembly = computeAssembly.computeAssemblyLES(new OpenStateClasses.NJOpenState());
		Writer writer = new OutputStreamWriter(System.out);
		csvHandler.writeCsv(writer, osAssembly);
		writer.flush();		

	}

/*
	new OpenStateClasses.GAOpenState(), 
	new OpenStateClasses.AROpenState(), 
	new OpenStateClasses.OKOpenState(), 
	new OpenStateClasses.MAOpenState(), 
	new OpenStateClasses.NCOpenState(), 
	new OpenStateClasses.AZOpenState(),
//			new OpenStateClasses.MNOpenState(), 
	new OpenStateClasses.HIOpenState(), 
	new OpenStateClasses.LAOpenState(), 
	new OpenStateClasses.TNOpenState(), 
	new OpenStateClasses.VAOpenState(), 
	new OpenStateClasses.NJOpenState(), 
	new OpenStateClasses.PAOpenState(), 
	new OpenStateClasses.MDOpenState(), 
	new OpenStateClasses.MSOpenState(), 
	new OpenStateClasses.MOOpenState(), 
	new OpenStateClasses.TXOpenState(), 
	new OpenStateClasses.NYOpenState(), 
	new OpenStateClasses.CAOpenState(),
*/
	
}
