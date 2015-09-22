package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.Set;
import java.util.stream.Collectors;

import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.service.dto.ActivityDTO;

public class Task extends Task_Base {

    @Override
    public void setName(String name) {
        checkUniqueTaskName(name);
        super.setName(name);
    }

    public Task(TaskModel taskModel, String name, String description) {
        setTaskModel(taskModel);
        setName(name);
        setDescription(description);
    }

    public Task(TaskModel taskModel, String name, String description,
            Set<Condition> preConditions, Set<Condition> postCondition,
            String previous, String joinCode, String splitCode)
                    throws BWException {
        setTaskModel(taskModel);
        setName(name);
        getPreConditionSet().addAll(preConditions);
        getPostConditionSet().addAll(postCondition);
        setDescription(description);
        setPrevious(previous);
        setJoinCode(joinCode);
        setSplitCode(splitCode);
    }

    private void checkUniqueTaskName(String name) throws BWException {
        if (getTaskModel().getTasksSet().stream()
                .filter(t -> t != this && t.getName().equals(name)).findFirst()
                .isPresent())
            throw new BWException(BWErrorType.INVALID_TASK_NAME, name);
    }

    public void cloneTask(TaskModelInstance taskModelInstance)
            throws BWException {
        Set<Condition> newPreConditions = null;
        Set<Condition> preConditions = getPreConditionSet();
        Set<Condition> newPostCondition = null;
        Set<Condition> postCondition = getPostConditionSet();
        if (!preConditions.isEmpty() && !getPostConditionSet().isEmpty()) {
            newPreConditions = preConditions.stream()
                    .map((cond) -> cond.cloneCondition(taskModelInstance))
                    .collect(Collectors.toSet());
            newPostCondition = postCondition.stream()
                    .map((cond) -> cond.cloneCondition(taskModelInstance))
                    .collect(Collectors.toSet());
        }
        Task newTask = new Task(taskModelInstance, getName(), getDescription(),
                newPreConditions, newPostCondition, getPrevious(),
                getJoinCode(), getSplitCode());
        newTask.setUser(getUser());
        newTask.setRole(getRole());
    }

    /**
     * Get the Task condition data to use in the use interface.
     * 
     * @return a string with the condition data entities.
     */
    public String getConstraintData(Boolean isPreConstraint) {
        Set<BWEntity> entities;
        Set<BWAttribute> attributes;
        String dataString = "";

        // Get Condition Data
        if (isPreConstraint) {
            entities = getPreConditionSet().stream()
                    .flatMap((cond) -> cond.getEntities().stream())
                    .collect(Collectors.toSet());
            attributes = getPreConditionSet().stream()
                    .flatMap((cond) -> cond.getAttributes().stream())
                    .collect(Collectors.toSet());
        } else {
            entities = getPostConditionSet().stream()
                    .flatMap((cond) -> cond.getEntities().stream())
                    .collect(Collectors.toSet());
            attributes = getPostConditionSet().stream()
                    .flatMap((cond) -> cond.getAttributes().stream())
                    .collect(Collectors.toSet());
        }

        // Add Attribute entities
        for (BWAttribute attribute : attributes) {
            entities.add(attribute.getEntity());
        }

        // Create String
        int count = 0;
        for (BWEntity entity : entities) {
            if (entities.size() == 1) {
                dataString += entity.getName();
            } else if (count < entities.size() - 1) {
                dataString += entity.getName() + ", ";
                ;
            } else {
                dataString += entity.getName();
            }
            count++;
        }
        return dataString;
    }

    public void delete() {
        setTaskModel(null);
        getPreConditionSet().forEach(c -> removePreCondition(c));
        getPostConditionSet().forEach(c -> removePostCondition(c));
        getMultiplicityInvariantSet()
                .forEach(m -> removeMultiplicityInvariant(m));
        getRuleInvariantSet().forEach(r -> removeRuleInvariant(r));

        deleteDomainObject();
    }

    public ActivityDTO getDTO() {
        return new ActivityDTO(getTaskModel().getSpecification().getSpecId(),
                getName(), getDescription());
    }

}
