package pt.ist.socialsoftware.blendedworkflow.engines.domain;

import java.util.Set;

public class AndCondition extends AndCondition_Base {

	public AndCondition(Condition one, Condition two) {
		addConditions(one);
		addConditions(two);
	}
	
	public Condition getConditionOne(){
		return getConditions().get(0);
	}
	
	public Condition getConditionTwo(){
		return getConditions().get(1);
	}

	@Override
	Condition cloneCondition(GoalModelInstance goalModelInstance) {
		return new AndCondition(getConditionOne().cloneCondition(goalModelInstance), getConditionTwo().cloneCondition(goalModelInstance));
	}

	@Override
	Condition cloneCondition(TaskModelInstance taskModelInstance) {
		return new AndCondition(getConditionOne().cloneCondition(taskModelInstance), getConditionTwo().cloneCondition(taskModelInstance));
	}
	
	@Override
	void assignAttributeInstances(GoalWorkItem goalWorkItem) {
		getConditionOne().assignAttributeInstances(goalWorkItem);
		getConditionTwo().assignAttributeInstances(goalWorkItem);
	}
	
	@Override
	void assignAttributeInstances(TaskWorkItem taskWorkItem, String conditionType) {
		getConditionOne().assignAttributeInstances(taskWorkItem, conditionType);
		getConditionTwo().assignAttributeInstances(taskWorkItem, conditionType);
	}
	
	@Override
	String getData() {
		return getConditionOne().getData() +"." + getConditionTwo().getData();
	}
	
	@Override
	public String getString() {
		return getConditionOne().getString() + " and " + getConditionTwo().getString();
	}
	
	@Override
	public Set<Entity> getEntities() {
		Set<Entity> entitiesOne = getConditionOne().getEntities();
		Set<Entity> entitiesTwo = getConditionTwo().getEntities();
		entitiesOne.addAll(entitiesTwo);
		return entitiesOne;
	}
	
	@Override
	public Set<Attribute> getAttributes() {
		Set<Attribute> attributesOne = getConditionOne().getAttributes();
		Set<Attribute> attributesTwo = getConditionTwo().getAttributes();
		attributesOne.addAll(attributesTwo);
		return attributesOne;
	}
	
}
