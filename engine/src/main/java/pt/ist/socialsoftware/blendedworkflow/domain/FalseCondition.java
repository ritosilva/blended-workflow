package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import pt.ist.socialsoftware.blendedworkflow.shared.TripleStateBool;

public class FalseCondition extends FalseCondition_Base {

    public FalseCondition() {
        super();
    }

    @Override
    Condition cloneCondition(GoalModelInstance goalModelInstance) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    Condition cloneCondition(TaskModelInstance taskModelInstance) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void assignAttributeInstances(GoalWorkItem goalWorkItem,
            ConditionType conditionType) {
        // TODO Auto-generated method stub

    }

    @Override
    void assignAttributeInstances(TaskWorkItem taskWorkItem,
            ConditionType conditionType) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<BWEntity> getEntities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<BWAttribute> getAttributes() {
        return new HashSet<BWAttribute>();
    }

    @Override
    public HashMap<BWAttribute, String> getcompareConditionValues() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRdrUndefinedCondition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRdrSkippedCondition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRdrTrueCondition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRdrFalseCondition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean existExistEntity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean existCompareAttributeToValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean existTrue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TripleStateBool evaluate(GoalWorkItem goalWorkItem,
            ConditionType conditionType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TripleStateBool evaluateWithWorkItem(GoalWorkItem goalWorkItem,
            ConditionType conditionType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TripleStateBool evaluateWithDataModel(EntityInstance entityInstance,
            GoalWorkItem goalWorkItem, ConditionType conditionType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSubPath() {
        return "false";
    }

}
