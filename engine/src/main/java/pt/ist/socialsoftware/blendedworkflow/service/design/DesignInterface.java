package pt.ist.socialsoftware.blendedworkflow.service.design;

public class DesignInterface {
    public static DesignInterface instance = null;

    public static DesignInterface getInstance() {
        if (instance == null) {
            instance = new DesignInterface();
        }
        return instance;
    }

    private DesignInterface() {
        // Bootstrap.init();
    }

    public void createSpecification(String name) {
        new CreateSpecificationService(name).execute();
    }

    public void createEntity(String specName, String entityName) {
        new CreateEntityService(specName, entityName).execute();
    }

    public void createAttribute(String specName, String entityName,
            String attributeName, String attributeType) {
        new CreateAttributeService(specName, entityName, attributeName,
                attributeType).execute();
    }

    public void createRelation(String specName, String entityOneName,
            String roleNameOne, String cardinalityOne, String entityTwoName,
            String roleNameTwo, String cardinalityTwo) {
        new CreateRelationService(specName, entityOneName, roleNameOne,
                cardinalityOne, entityTwoName, roleNameTwo, cardinalityTwo);
    }

}
