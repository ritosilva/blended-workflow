package pt.ist.socialsoftware.blendedworkflow.resources.service;

import pt.ist.socialsoftware.blendedworkflow.core.service.BWErrorType;

public enum RMErrorType {
    INVALID_RESOURCE_NAME, MISSING_POSITION_UNIT, INVALID_UNIT_NAME, INVALID_POSITION_NAME, INVALID_ROLE_NAME, INVALID_PERSON_NAME, INVALID_CAPABILITY_NAME, INVALID_RAL_EXPRESSION_TYPE, INVALID_RAL_EXPRESSION_DTO_TYPE, INVALID_ENTITY_NAME, INVALID_DATA_FIELD, ENTITY_IN_EXPRESSION_IS_NOT_PERSON, INVALID_RAL_EXPRESSION_CAST, EXPORT_INVALID, FILE_ERROR, INVALID_RESOURCE_RULE_TYPE, INVALID_USER_NAME, NO_LOGIN, USER_DOES_NOT_HAVE_PERSON_IN_SPEC, PERSON_IS_NOT_ELIGIBLE, NO_WORKITEMS_AVAILABLE, INVALID_WORKITEM, INVALID_MERGE_TYPE, INVALID_MERGE, NO_ACTIVITIES_AVAILABLE, INCONSISTENT_RAL_EXPRESSION, ACTIVITY_NOT_FOUND_IN_EXTRACT, NO_GOALS_AVAILABLE, RAL_EXPRESSION_IS_NOT_CONSISTENT, NO_ENTITY_BY_NAME;
}
