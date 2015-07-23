package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.List;

import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;

public class BWAttribute extends BWAttribute_Base {

    public enum AttributeType {
        BOOLEAN, NUMBER, STRING, DATE
    };

    @Override
    public void setName(String name) {
        checkName(name);
        super.setName(name);
    }

    public BWAttribute(BWDataModel dataModel, BWEntity entity,
            BWAttributeGroup group, String name, AttributeType type,
            boolean isKeyAttribute, boolean isSystem) {
        setDataModel(dataModel);
        setEntity(entity);
        setAttributeGroup(group);
        setName(name);
        setType(type);
        setIsKeyAttribute(isKeyAttribute);
        setIsSystem(isSystem);
    }

    private void checkName(String name) {
        if ((name == null) || name.equals(""))
            throw new BWException(BWErrorType.INVALID_ATTRIBUTE_NAME, name);

        checkUniqueAttributeName(name);
    }

    private void checkUniqueAttributeName(String name) throws BWException {
        // Optional<Attribute> res = getEntity().getAttributesSet().stream()
        // .filter(att -> ((att != this) && att.getName().equals(name)))
        // .findFirst();
        //
        // if (res.isPresent()) {
        // new BWException(BlendedWorkflowError.INVALID_ATTRIBUTE_NAME, name);
        // }

        for (BWAttribute attribute : getEntity().getAttributesSet()) {
            if ((attribute != this) && attribute.getName().equals(name)) {
                throw new BWException(BWErrorType.INVALID_ATTRIBUTE_NAME, name);
            }
        }
    }

    public void cloneAttribute(DataModelInstance dataModelInstance,
            BWEntity entity) throws BWException {
        new BWAttribute(dataModelInstance, entity, getAttributeGroup(),
                getName(), getType(), getIsKeyAttribute(), getIsSystem());
    }

    /**
     * FIXME: Double/Boolean
     */
    public String getYAWLAttributeType() {
        return "string";
    }

    public String getYAWLAttributeValue() {
        if (this.getType().equals(AttributeType.NUMBER)) {
            return "0";
        } else {
            return "string";
        }
    }

    @Override
    public void delete() {
        setDataModel(null);
        setEntity(null);
        setAttributeGroup(null);
        getAttValueExpressionSet().stream()
                .forEach(exp -> exp.setAttribute(null));

        super.delete();
    }

    @Override
    public BWProduct getNext(List<String> pathLeft, String path) {
        if (pathLeft.size() == 0)
            return this;
        else
            throw new BWException(BWErrorType.INVALID_PATH,
                    path + ":" + pathLeft);
    }

}