

import openstats.client.les.ComputeAssembly;
import openstats.client.openstates.*;
import openstats.model.Assembly;

public class ComputeTest {
	

	public static void main(String[] args) throws Exception {


		ComputeAssembly computeAssembly = new ComputeAssembly();
//		AssemblyCsvHandler csvHandler = new AssemblyCsvHandler();
//		Writer writer = new OutputStreamWriter(System.out);
		for( OpenState testAction: OpenStateClasses.getOpenStates() ) {
			Assembly assembly = new Assembly();
			computeAssembly.computeAssemblyLES(testAction, assembly);
//			csvHandler.writeCsv(writer, assembly);
//			writer.flush();
		}


/*
		ComputeAssembly computeAssembly = new ComputeAssembly();
		AssemblyCsvHandler csvHandler = new AssemblyCsvHandler();
		OSAssembly assembly = computeAssembly.computeAssemblyLES(new OpenStateClasses.MAOpenState());
		Writer writer = new OutputStreamWriter(System.out);
		csvHandler.writeCsv(writer, assembly);
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
