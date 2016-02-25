package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import pt.ist.socialsoftware.blendedworkflow.service.dto.ExpressionDTO;
import pt.ist.socialsoftware.blendedworkflow.shared.TripleStateBool;

public class ForAllCondition extends ForAllCondition_Base {

	public ForAllCondition(RelationBW relation, Entity entity, Condition condition) {
		setForAllEntity(entity);
		setForAllRelation(relation);
		setCondition(condition);
	}

	@Override
	Condition cloneCondition(GoalModelInstance goalModelInstance) {
		DataModelInstance dataModelInstance = goalModelInstance.getBwInstance().getDataModelInstance();
		RelationBW relation = dataModelInstance.getRelation(getForAllRelation().getName());
		Entity entity = dataModelInstance.getEntity(getForAllEntity().getName()).get();
		return new ForAllCondition(relation, entity, getCondition().cloneCondition(goalModelInstance));
	}

	@Override
	Condition cloneCondition(TaskModelInstance taskModelInstance) {
		DataModelInstance dataModelInstance = taskModelInstance.getBwInstance().getDataModelInstance();
		RelationBW relation = dataModelInstance.getRelation(getForAllRelation().getName());
		Entity entity = dataModelInstance.getEntity(getForAllEntity().getName()).get();
		return new ForAllCondition(relation, entity, getCondition().cloneCondition(taskModelInstance));
	}

	@Override
	public void assignAttributeInstances(GoalWorkItem goalWorkItem, ConditionType conditionType) {
		// TODO:assignAttributeInstances
	}

	@Override
	void assignAttributeInstances(TaskWorkItem taskWorkItem, ConditionType conditionType) {
		// TODO:assignAttributeInstances
	}

	@Override
	public Set<Entity> getEntities() {
		Set<Entity> entity = new HashSet<Entity>();
		entity.add(getForAllEntity());
		return entity;
	}

	@Override
	public Set<AttributeBasic> getAttributeBasicSet() {
		return getCondition().getAttributeBasicSet();
	}

	@Override
	public Set<Path> getPathSet() {
		return getCondition().getPathSet();
	}

	@Override
	public HashMap<AttributeBasic, String> getcompareConditionValues() {
		return new HashMap<AttributeBasic, String>();
	}

	@Override
	public String toString() {
		return "ForAll[" + getForAllEntity().getName() + "." + getForAllRelation().getName() + "," + getCondition()
				+ "]";
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
		// TODO:Refactor
		return TripleStateBool.FALSE;
	}

	@Override
	public TripleStateBool evaluateWithWorkItem(GoalWorkItem goalWorkItem, ConditionType conditionType) {
		TripleStateBool result = TripleStateBool.TRUE;
		for (RelationInstance relationInstance : getForAllRelation().getRelationInstancesSet()) {
			EntityInstance entityInstance = relationInstance.getEntityInstance(getForAllEntity());
			result = result.AND(getCondition().evaluateWithDataModel(entityInstance, goalWorkItem, conditionType));
		}
		return result;
	}

	@Override
	public TripleStateBool evaluateWithDataModel(EntityInstance invalid, GoalWorkItem goalWorkItem,
			ConditionType conditionType) {
		TripleStateBool result = TripleStateBool.TRUE;
		for (RelationInstance relationInstance : getForAllRelation().getRelationInstancesSet()) {
			EntityInstance entityInstance = relationInstance.getEntityInstance(getForAllEntity());
			result = result.AND(getCondition().evaluateWithDataModel(entityInstance, goalWorkItem, conditionType));
		}
		return result;
	}

	@Override
	public Boolean existCompareAttributeToValue() {
		return false;
	}

	@Override
	public Boolean existTrue() {
		return false;
	}

	@Override
	public String getSubPath() {
		return ForAllCondition.class.getName();
	}

	@Override
	public ExpressionDTO getDTO(String specId) {
		// TODO Auto-generated method stub
		return null;
	}

}
