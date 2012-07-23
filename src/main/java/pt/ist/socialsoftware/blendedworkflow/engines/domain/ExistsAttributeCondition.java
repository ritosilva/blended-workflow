package pt.ist.socialsoftware.blendedworkflow.engines.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.ist.socialsoftware.blendedworkflow.engines.domain.Condition.ConditionType;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.DataModel.DataState;
import pt.ist.socialsoftware.blendedworkflow.shared.TripleStateBool;

public class ExistsAttributeCondition extends ExistsAttributeCondition_Base {

	public ExistsAttributeCondition(Attribute attribute) {
		setAttribute(attribute);
	}

	@Override
	Condition cloneCondition(GoalModelInstance goalModelInstance) {
		DataModelInstance dataModelInstance = goalModelInstance.getBwInstance().getDataModelInstance();
		Entity entity = dataModelInstance.getEntity(getAttribute().getEntity().getName());
		Attribute attribute = entity.getAttribute(getAttribute().getName());
		return new ExistsAttributeCondition(attribute);
	}

	@Override
	Condition cloneCondition(TaskModelInstance taskModelInstance) {
		DataModelInstance dataModelInstance = taskModelInstance.getBwInstance().getDataModelInstance();
		Entity entity = dataModelInstance.getEntity(getAttribute().getEntity().getName());
		Attribute attribute = entity.getAttribute(getAttribute().getName());
		return new ExistsAttributeCondition(attribute);
	}

	@Override
	public void assignAttributeInstances(GoalWorkItem goalWorkItem, ConditionType conditionType) {
		getAttribute().getEntity().assignAttributeInstances(goalWorkItem,getAttribute(), conditionType);
	}

	@Override
	public void assignAttributeInstances(TaskWorkItem taskWorkItem, ConditionType conditionType) {
		getAttribute().getEntity().assignAttributeInstances(taskWorkItem,getAttribute(), conditionType);
	}

	@Override
	public Set<Entity> getEntities() {
		return new HashSet<Entity>();
	}

	@Override
	public Set<Attribute> getAttributes() {
		Set<Attribute> attribute = new HashSet<Attribute>();
		attribute.add(getAttribute());
		return attribute;
	}

	@Override
	public HashMap<Attribute, String> getcompareConditionValues() {
		return new HashMap<Attribute, String>();
	}

	@Override
	public String getRdrUndefinedCondition() {
		String condition = "(";
		String attributeName = getAttribute().getName().replaceAll(" ", "");
		String entityName = getAttribute().getEntity().getName().replaceAll(" ", "");

		condition += entityName + "_" + attributeName + "_State = " + DataState.UNDEFINED + ")";
		return condition;
	}

	@Override
	public String getRdrSkippedCondition() {
		String condition = "(";
		String attributeName = getAttribute().getName().replaceAll(" ", "");
		String entityName = getAttribute().getEntity().getName().replaceAll(" ", "");

		condition += entityName + "_" + attributeName + "_State = " + DataState.SKIPPED + ")";
		return condition;
	}

	@Override
	public String getRdrTrueCondition() {
		String condition = "(";
		String attributeName = getAttribute().getName().replaceAll(" ", "");
		String entityName = getAttribute().getEntity().getName().replaceAll(" ", "");

		condition += entityName + "_" + attributeName + "_State = " + DataState.DEFINED + ")";
		return condition;
	}

	@Override
	public String getRdrFalseCondition() {
		return "(FALSE_NODE = FALSE)";
	}

	@Override
	public String toString() {
		return "existsAttribute(" + getAttribute().getEntity().getName() + "." +getAttribute().getName() +")";
	}

	@Override
	public Boolean existExistEntity() {
		return false;
	}

	@Override
	public Boolean existTrue(){
		return false;
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
		List<WorkItemArgument> arguments = null;
		if (conditionType.equals(ConditionType.ACTIVATE_CONDITION)) {
			arguments = goalWorkItem.getInputWorkItemArguments();
		} else if (conditionType.equals(ConditionType.SUCESS_CONDITION)) {
			arguments = goalWorkItem.getOutputWorkItemArguments();
		}

		if (arguments != null) {
			for (WorkItemArgument workItemArgument : arguments) {
				Attribute workItemAttribute = workItemArgument.getAttributeInstance().getAttribute();
				Attribute conditionAttribute = getAttribute();
				if (workItemAttribute == conditionAttribute) {
					if (workItemArgument.getState().equals(DataState.SKIPPED)) {
						return TripleStateBool.SKIPPED;
					} else if (workItemArgument.getState().equals(DataState.UNDEFINED)) {
						return TripleStateBool.FALSE;
					}
				}
			}
		}
		return TripleStateBool.TRUE;
	}

	@Override
	public TripleStateBool evaluateWithDataModel(EntityInstance entityInstance, GoalWorkItem goalWorkItem, ConditionType conditionType) {
		for (AttributeInstance attributeInstance : entityInstance.getAttributeInstances()) {
			Attribute attribute = attributeInstance.getAttribute();
			Attribute conditionAttribute = getAttribute();

			if (attribute == conditionAttribute) {
				DataState state = getWorkItemState(attributeInstance, goalWorkItem, conditionType);
				if (state == null) {
					state = attributeInstance.getState();
				}		

				if (state.equals(DataState.SKIPPED)) {
					return TripleStateBool.SKIPPED;
				} else if (attributeInstance.getState().equals(DataState.UNDEFINED)) {
					return TripleStateBool.FALSE;
				}
			}
		}
		return TripleStateBool.TRUE;
	}

	private DataState getWorkItemState(AttributeInstance attributeInstance, GoalWorkItem goalWorkItem, ConditionType conditionType) {
		//		List<WorkItemArgument> arguments = null;
		//		if (conditionType.equals(ConditionType.ACTIVATE)) {
		//			arguments = goalWorkItem.getInputWorkItemArguments();
		//		} else if (conditionType.equals(ConditionType.SUCESS)) {
		//			arguments = goalWorkItem.getOutputWorkItemArguments();
		//		}
		//		for (WorkItemArgument workItemArgument : arguments) {
		if (goalWorkItem != null) {
			for (WorkItemArgument workItemArgument : goalWorkItem.getOutputWorkItemArguments()) {
				if (workItemArgument.getAttributeInstance().equals(attributeInstance)) {
					return workItemArgument.getState();
				}
			}
		}
		return null;
	}

	@Override
	public Boolean existCompareAttributeToValue() {
		return false;
	}

}
