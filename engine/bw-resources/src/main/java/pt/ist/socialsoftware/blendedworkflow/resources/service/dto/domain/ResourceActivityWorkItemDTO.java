package pt.ist.socialsoftware.blendedworkflow.resources.service.dto.domain;

import pt.ist.socialsoftware.blendedworkflow.core.domain.*;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.ActivityWorkItemDto;
import pt.ist.socialsoftware.blendedworkflow.resources.domain.Person;
import pt.ist.socialsoftware.blendedworkflow.resources.domain.ResourceModel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceActivityWorkItemDTO extends ActivityWorkItemDto implements ResourceWorkItemDTO {
    private Set<EntityIsPersonDto> _entityIsPersonDtoSet;
    private UserDto executionUser;

    public static ResourceActivityWorkItemDTO createActivityWorkItemDTO(WorkflowInstance workflowInstance, Activity activity) {
        ResourceActivityWorkItemDTO resourceActivityWorkItemDTO = new ResourceActivityWorkItemDTO(
                ActivityWorkItemDto.createActivityWorkItemDTO(workflowInstance, activity));
        ResourceModel resourceModel = activity.getActivityModel().getSpecification().getResourceModel();

        Set<EntityIsPersonDto> entityIsPersonDtoSet = new HashSet<>();
        Set<PersonDto> personContext = resourceModel.getPersonSet().stream().map(Person::getDTO).collect(Collectors.toSet());

        activity.getPostEntities().stream()
                .filter(entity -> resourceModel.checkEntityIsPerson(entity))
                .forEach(entity -> entityIsPersonDtoSet.add(new EntityIsPersonDto(entity.getDTO(), personContext)));
        resourceActivityWorkItemDTO.setEntityIsPersonDTOSet(entityIsPersonDtoSet);

        return resourceActivityWorkItemDTO;
    }

    public static ResourceActivityWorkItemDTO fillActivityWorkItemDTO(ActivityWorkItemDto activityWorkItemDTO, ActivityWorkItem activityWorkItem) {
        ResourceActivityWorkItemDTO resourceActivityWorkItemDTO = new ResourceActivityWorkItemDTO(activityWorkItemDTO);

        Set<EntityIsPersonDto> entityIsPersonDtoSet = new HashSet<>();

        activityWorkItem.getPostConditionSet().stream()
                .flatMap(postWorkItemArgument -> postWorkItemArgument.getProductInstanceSet().stream())
                .filter(EntityInstance.class::isInstance)
                .map(EntityInstance.class::cast)
                .filter(entityInstance -> entityInstance.getPerson() != null)
                .forEach(entityInstance -> entityIsPersonDtoSet.add(new EntityIsPersonDto(entityInstance.getDTO(), entityInstance.getPerson().getDTO())));

        resourceActivityWorkItemDTO.setEntityIsPersonDTOSet(entityIsPersonDtoSet);

        resourceActivityWorkItemDTO.setExecutionUser(activityWorkItem.getExecutionUser().getDTO());

        return resourceActivityWorkItemDTO;
    }

    public ResourceActivityWorkItemDTO(ActivityWorkItemDto activityWorkItemDTO) {
        super();
        setSpecId(activityWorkItemDTO.getSpecId());
        setSpecName(activityWorkItemDTO.getSpecName());
        setWorkflowInstanceName(activityWorkItemDTO.getWorkflowInstanceName());
        setDefinitionGroupSet(activityWorkItemDTO.getDefinitionGroupSet());
        setName(activityWorkItemDTO.getName());
        setTimestamp(activityWorkItemDTO.getTimestamp());
        setPreArguments(activityWorkItemDTO.getPreArguments());
        setPostArguments(activityWorkItemDTO.getPostArguments());

        _entityIsPersonDtoSet = new HashSet<>();
    }



    @Override
    public Set<EntityIsPersonDto> getEntityIsPersonDTOSet() {
        return _entityIsPersonDtoSet;
    }

    @Override
    public void setEntityIsPersonDTOSet(Set<EntityIsPersonDto> entityIsPersonDtoSet) {
        this._entityIsPersonDtoSet = entityIsPersonDtoSet;
    }

    @Override
    public UserDto getExecutionUser() {
        return executionUser;
    }

    @Override
    public void setExecutionUser(UserDto executionUser) {
        this.executionUser = executionUser;
    }

    @Override
    public ActivityWorkItem executeActivity(WorkflowInstance workflowInstance, Activity activity) {
        ActivityWorkItem activityWorkItem = super.executeActivity(workflowInstance, activity);

        for (EntityIsPersonDto entityIsPersonDto : getEntityIsPersonDTOSet()) {
            activityWorkItem.getPostConditionSet().stream()
                    .map(PostWorkItemArgument::getProductInstanceSet)
                    .flatMap(Collection::stream)
                    .filter(productInstance -> productInstance instanceof EntityInstance)
                    .filter(productInstance -> entityIsPersonDto.getEntity().getName().equals(productInstance.getEntity().getName()))
                    .filter(productInstance -> productInstance.getEntity().getResourceModel().checkEntityIsPerson(productInstance.getProduct()))
                    .map(productInstance -> (EntityInstance) productInstance)
                    .forEach(entityInstance -> {
                        if (entityIsPersonDto.getPersonChosen() != null) {
                            Person person = entityInstance.getEntity().getResourceModel().getPerson(entityIsPersonDto.getPersonChosen().getName());
                            entityInstance.setPerson(person);
                        } else {
                            entityInstance.setPerson(new Person(
                                    entityInstance.getEntity().getResourceModel(),
                                    entityInstance.getExternalId(),
                                    entityInstance.getExternalId()
                            ));
                        }
                    });
        }

        return activityWorkItem;
    }

    @Override
    public String print() {
        String result = super.print();
        for (EntityIsPersonDto entityIsPersonDto : getEntityIsPersonDTOSet()) {
            result = result + "EIP ENTITY: " + ((entityIsPersonDto.getEntity() != null) ? entityIsPersonDto.getEntity().getName() : "") + "\r\n";
            result = result + "EIP PERSON CONTEXT: "
                    + entityIsPersonDto.getPersonContext().stream()
                    .map(PersonDto::getName)
                    .collect(Collectors.joining(";"))
                    + "\r\n";
            result = result + "EIP PERSON CHOSEN: " + ((entityIsPersonDto.getPersonChosen() != null) ? entityIsPersonDto.getPersonChosen().getName() : "") + "\r\n";
        }
        return result;
    }
}
