
import java.util.List;

import javax.persistence.*;

import openstats.client.les.Labels;
import openstats.data.*;
import openstats.model.*;
import openstats.osmodel.*;


public class UpdateAssembly {

    /*
     * find the assembly in the database
     * error if not found.
     * find the group mentioned for any state-session in the database
     * if not found then create
     * if found then check consistency
     * if consistency fails this error
     * update aggregations and computations.
     * 
     * More thoughts:
     *   A group is referenced by name.
     *   A group, once named, applies to all stat holders ( eg, assembly, district, legislator ... )
     *   A group has a description and labels each with a description
     *   A group can be queried by name
     *   A group can be created by giving name, description, and labels with descriptions.
     *   A group will indicate which stat holders have stats for a group.
     *   For updating or creating groupstats for a holder, first
     *     get the group name, or create if necessary
     *     get the stats containers for the statholder
     *     update the stats containers and write to DB
     *     
     *     ok .. problem .. 
     *     the labels that exist for a group 
     *     will be different depending on the stat-holder.
     *     in other words, the labels for groupX are different for districts
     *     than for an assembly.
     *     also, the labels that exist for aggregation is different than for 
     *     computation.
     *     .. so, each group-holder has to define its own set of labels (GroupInfo)..
     *     .. The challenge therefor is going to be to create and interface that makes
     *     .. sense, is not too complicated, and gets the job done.
     *     
     *     Just do CRUD operations for 
     *     OSGroup, 
     *     AssemblyLabelsForAggregations,
     *     AssemblyLabelsForComputations,  
     *     DistrictsLabelsForAggregations, 
     *     DistrictsLabelsForComputations, 
     *     LegislatorLabelsForAggregations, 
     *     LegislatorLabelsForComputations, 
     *     
     *     and
     *     
     *     AssemblyAggregations, 
     *     AssemblyComputations, 
     *     DistrictsAggregations, 
     *     DistrictsComputations, 
     *     LegislatorAggregations, 
     *     LegislatorComputations, 
     *     
     */
	public static void main(String[] args) throws OpenStatsException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("openstats");
		EntityManager em = emf.createEntityManager();
		
		DBGroupFacade dbGroupFacade = new DBGroupFacade(em);
		AssemblyRepository assemblyRepository = new AssemblyRepository(em);
        
        DBAssembly assembly = assemblyRepository.findByStateSession("AR", "2013");
        
        DBGroup dbGroup = dbGroupFacade.getDBGroup(Labels.LESGROUPNAME);
        
        
	}
	
}
