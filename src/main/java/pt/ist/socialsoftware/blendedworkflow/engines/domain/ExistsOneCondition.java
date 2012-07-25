package pt.ist.socialsoftware.blendedworkflow.engines.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import pt.ist.socialsoftware.blendedworkflow.shared.TripleStateBool;

public class ExistsOneCondition extends ExistsOneCondition_Base {
    
    public ExistsOneCondition(Relation relation, Entity entity, Condition condition) {
    	setExistsOneEntity(entity);
    	setExistsOneRelation(relation);
    	setCondition(condition);
    }
	
	@Override
	Condition cloneCondition(GoalModelInstance goalModelInstance) {
		DataModelInstance dataModelInstance = goalModelInstance.getBwInstance().getDataModelInstance();
		Relation relation = dataModelInstance.getRelation(getExistsOneRelation().getName());
		Entity entity = dataModelInstance.getEntity(getExistsOneEntity().getName());
		return new ExistsOneCondition(relation, entity, getCondition().cloneCondition(goalModelInstance));
	}

	@Override
	Condition cloneCondition(TaskModelInstance taskModelInstance) {
		DataModelInstance dataModelInstance = taskModelInstance.getBwInstance().getDataModelInstance();
		Relation relation = dataModelInstance.getRelation(getExistsOneRelation().getName());
		Entity entity = dataModelInstance.getEntity(getExistsOneEntity().getName());
		return new ForAllCondition(relation, entity, getCondition().cloneCondition(taskModelInstance));
	}

	@Override
	public
	void assignAttributeInstances(GoalWorkItem goalWorkItem, ConditionType conditionType) {
		//TODO:assignAttributeInstances
	}

	@Override
	void assignAttributeInstances(TaskWorkItem taskWorkItem, ConditionType conditionType) {
		//TODO:assignAttributeInstances
	}

	@Override
	public Set<Entity> getEntities() {
		Set<Entity> entity = new HashSet<Entity>();
		entity.add(getExistsOneEntity());
		return entity;
	}

	@Override
	public Set<Attribute> getAttributes() {
		return new HashSet<Attribute>();
	}

	@Override
	public HashMap<Attribute, String> getcompareConditionValues() {
		return new HashMap<Attribute, String>();
	}
	
	@Override
	public String toString() {
		return "existsOne[" + getExistsOneEntity().getName() + "." + getExistsOneRelation().getName() + "," + getCondition() + "]";
	}
    
	@Override
	public Boolean existExistEntity() {
		return false;
	}
	
	@Override
	public String getRdrUndefinedCondition() {
		return null;
	}

	@Override
	public String getRdrSkippedCondition() {
		return null;
	}

	@Override
	public String getRdrTrueCondition() {
		return null;
	}

	@Override
	public String getRdrFalseCondition() {
		return null;
	}

	/******************************
	 * Evaluate
	 ******************************/
	@Override
	public TripleStateBool evaluate(GoalWorkItem goalWorkItem, ConditionType conditionType) {
		//TODO:Refactor
		return TripleStateBool.FALSE;
	}
	
	@Override
	public TripleStateBool evaluateWithWorkItem(GoalWorkItem goalWorkItem, ConditionType conditionType) {
		TripleStateBool result = TripleStateBool.FALSE;
		for (RelationInstance relationInstance : getExistsOneRelation().getRelationInstances()) {
			EntityInstance entityInstance = relationInstance.getEntityInstance(getExistsOneEntity());
			result = result.OR(getCondition().evaluateWithDataModel(entityInstance, goalWorkItem, conditionType));
		}
		return result;
	}
	
	@Override
	public TripleStateBool evaluateWithDataModel(EntityInstance invalid, GoalWorkItem goalWorkItem, ConditionType conditionType) {
		TripleStateBool result = TripleStateBool.FALSE;
		for (RelationInstance relationInstance : getExistsOneRelation().getRelationInstances()) {
			EntityInstance entityInstance = relationInstance.getEntityInstance(getExistsOneEntity());
			result = result.OR(getCondition().evaluateWithDataModel(entityInstance, goalWorkItem, conditionType));
		}
		return result;
	}
	
	@Override
	public Boolean existCompareAttributeToValue() {
		return false;
	}
	
	@Override
	public Boolean existTrue(){
		return false;
	}
}