package pt.ist.socialsoftware.blendedworkflow.engines.domain;

public class ExistsAttributeCondition extends ExistsAttributeCondition_Base {

	public ExistsAttributeCondition(Attribute attribute) {
		setAttribute(attribute);
	}

	@Override
	Condition cloneCondition(GoalModelInstance goalModelInstance) {
		DataModelInstance dataModelInstance = goalModelInstance.getBwInstance().getDataModelInstance();
		Entity entity = dataModelInstance.getEntity(getAttribute().getEntity().getName());
		Attribute attribute = entity.getAttribute(getAttribute().getName());
		return new ExistsAttributeCondition(attribute) ;
	}

	@Override
	void assignAttributeInstances(GoalWorkItem goalWorkItem) {
		getAttribute().getEntity().assignAttributeInstances(goalWorkItem,getAttribute());
	}

}
