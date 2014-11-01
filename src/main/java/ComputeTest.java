

import openstats.client.les.ComputeAssembly;
import openstats.client.openstates.*;
import openstats.osmodel.OSAssembly;
import openstats.util.AssemblyCsvHandler;

public class ComputeTest {
	

	public static void main(String[] args) throws Exception {


		ComputeAssembly computeAssembly = new ComputeAssembly();
		AssemblyCsvHandler csvHandler = new AssemblyCsvHandler();
//		Writer writer = new OutputStreamWriter(System.out);
		for( OpenState testAction: OpenStateClasses.getOpenStates() ) {
			OSAssembly osAssembly = computeAssembly.computeAssemblyLES(testAction);
//			csvHandler.writeCsv(writer, osAssembly);
//			writer.flush();
		}


/*
		ComputeAssembly computeAssembly = new ComputeAssembly();
		AssemblyCsvHandler csvHandler = new AssemblyCsvHandler();
		OSAssembly osAssembly = computeAssembly.computeAssemblyLES(new OpenStateClasses.MAOpenState());
		Writer writer = new OutputStreamWriter(System.out);
		csvHandler.writeCsv(writer, osAssembly);
		writer.flush();		
*/
		
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
