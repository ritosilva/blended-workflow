package pt.ist.socialsoftware.blendedworkflow.core.service;

public enum BWErrorType {
	EMPTY_CONDITION_STRING, INVALID_CARDINALITY, INVALID_CONDITION_STRING, INVALID_SPECIFICATION_NAME, INVALID_ENTITY_NAME, INVALID_ATTRIBUTE_NAME, INVALID_RELATION_NAME, INVALID_ACTIVITY_NAME, INVALID_GOAL_NAME, NON_EXISTENT_CASE_ID, NON_EXISTENT_ENTITY_INSTANCE, NON_EXISTENT_ACTIVITY_NAME, NON_EXISTENT_GOAL_NAME, NON_EXISTENT_WORKITEM_ID, FALSE_PRE_CONSTRAIN, YAWL_ADAPTER, WORKLET_ADAPTER, WORKLIST_MANAGER, BWPRESENTATION, YAWL_REGISTER_SERVICE, YAWL_REMOVE_SERVICE, YAWL_LOAD_SPECIFICATION, YAWL_REMOVE_CLIENT, YAWL_REGISTER_CLIENT, WORKLET_ADAPTER_ADDNODE, WORKLET_ADAPTER_EVALUATE, WORKLET_ADAPTER_PROCESS, INVALID_USER, INVALID_ROLE, INVALID_ATTRIBUTE_OPERATION, UMANAGED_RULE_TYPE, WORKLET_ADAPTER_EVALUATEPRECONDITION, INVALID_SPECIFICATION_ID, INVALID_ATTRIBUTE_TYPE, INVALID_ROLE_NAME, NON_EXISTENT_ENTITY, INVALID_PATH, INCONSISTENT_TYPE, INCONSISTENT_EXPRESSION, INVALID_RULE_NAME, INVALID_ENTITY, DEPENDENCE_NOT_EXISTS, INVALID_ATTRIBUTE_GROUP, INCONSISTENT_ATTRIBUTE_MANDATORY, NOT_FOUND, INVALID_RELATION, NO_DATA_MODEL, NO_CONDITION_MODEL, UNMERGEABLE_GOALS, CANNOT_EXTRACT_GOAL, NO_DEF_CONDITION_IN_POST, NOT_ALL_CONDITIONS_APPLIED, MISSING_DEF_IN_PRE, MISSING_MUL_CONDITION, INCONSISTENT_MUL_CONDITION, INCONSISTENT_RULE_CONDITION, ATTRIBUTE_BELONGS_TO_GROUP, CANNOT_ADD_TASK, DUPLICATE_NAME, DEPENDENCE_CIRCULARITY, ACTIVITY_UNKNOWN_POST_CONDITION_DEF, CANNOT_EXTRACT_ACTIVITY, SEQUENCE_CONDITION_INVALID, UNKNOWN_SEQUENCE_CONDITION, INCONSISTENT_GOALMODEL, WORKFLOWINSTANCE_CONSISTENCY, ENTITYINSTANCE_CONSISTENCY, ATTRIBUTEINSTANCE_CONSISTENCY, RELATIONINSTANCE_CONSISTENCY, WORK_ITEM_CONSISTENCY, WORK_ITEM_ARGUMENT_CONSISTENCY, UNKNOWN_INSTANCE, UNKNOWN_SPECIFICATION, NOT_ALL_PRODUCT_INSTANCES_DEFINED, PRE_WORK_ITEM_ARGUMENT, POST_WORK_ITEM_ARGUMENT, NOT_DEFINED, NOT_UNIQUE_MANDATORY_ENTITY, FILE_ERROR, INCONSISTENT_PRODUCT_GOAL, INCONSISTENT_ASSOCIATION_GOAL, NON_EXISTENT_ACTIVITY_BY_PRODUCT, NON_EXISTENT_GOAL_BY_PRODUCT
}
