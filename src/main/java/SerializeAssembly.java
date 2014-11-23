import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;

import openstats.client.les.ComputeAssembly;
import openstats.client.openstates.OpenState;
import openstats.client.openstates.OpenStateClasses;
import openstats.model.Assembly;


public class SerializeAssembly {
	
	public static void main(String... args) throws Exception {
		new SerializeAssembly().run();		
	}
	
	private void run() throws Exception {
		OpenState testAction = new OpenStateClasses.AKOpenState();
		Assembly assembly = new Assembly();
		new ComputeAssembly().computeAssemblyLES(testAction, assembly);
		JAXBContext ctx = JAXBContext.newInstance(Assembly.class);
		ctx.createMarshaller().marshal(assembly, Files.newOutputStream(Paths.get("/home/knicholas/AK-27.XML")));
		System.out.flush();
		
	}
}
