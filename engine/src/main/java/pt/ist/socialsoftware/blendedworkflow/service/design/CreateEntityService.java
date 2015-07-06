package pt.ist.socialsoftware.blendedworkflow.service.design;

import pt.ist.socialsoftware.blendedworkflow.domain.Specification;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.service.BWException.BlendedWorkflowError;
import pt.ist.socialsoftware.blendedworkflow.service.BWService;

public class CreateEntityService extends BWService {
    private final String specName;
    private final String entityName;

    public CreateEntityService(String specName, String entityName) {
        this.specName = specName;
        this.entityName = entityName;
    }

    @Override
    protected void dispatch() throws BWException {
        Specification spec = getBlendedWorkflow()
                .getSpecification(this.specName)
                .orElseThrow(() -> new BWException(
                        BlendedWorkflowError.INVALID_SPECIFICATION_NAME));

        spec.getDataModel().createEntity(entityName);
    }

}