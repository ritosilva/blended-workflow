package pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.fenixframework.pstm.Transaction;

import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Goal;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.GoalWorkItem;

public class CreateGoalInstanceService implements Callable<String> {

	private static Logger log = Logger.getLogger("CreateGoalInstanceService");
	
	private BWInstance bwInstance;
	private Goal goal;

	public CreateGoalInstanceService (long bwInstanceOID, long goalOID) {
		this.bwInstance = AbstractDomainObject.fromOID(bwInstanceOID);
		this.goal = AbstractDomainObject.fromOID(goalOID);
	}
	
	@Override
	public String call() throws Exception {
		log.info("Start");
		Transaction.begin();
		generateGoalWorkItems(this.bwInstance, this.goal);
		Transaction.commit();
		log.info("END");
		return "CreateGoalInstanceService:Sucess";
	}
	
	private void generateGoalWorkItems(BWInstance bwInstance, Goal goal) {
		if (goal.getGoalWorkItem() == null) {
			new GoalWorkItem(bwInstance, goal);
		}
		
		for (Goal subGoal : goal.getSubGoals()) {
			generateGoalWorkItems(bwInstance, subGoal);
		}		
	}

}
