package pt.ist.socialsoftware.blendedworkflow.engines.domain;

public class NotCondition extends NotCondition_Base {

	public NotCondition(Condition condition) {
		setCondition(condition);
	}

	@Override
	Condition cloneCondition(GoalModelInstance goalModelInstance) {
		return new NotCondition(getCondition().cloneCondition(goalModelInstance));
	}
	
	@Override
	void assignAttributeInstances(GoalWorkItem goalWorkItem) {
		getCondition().assignAttributeInstances(goalWorkItem);
	}


}
