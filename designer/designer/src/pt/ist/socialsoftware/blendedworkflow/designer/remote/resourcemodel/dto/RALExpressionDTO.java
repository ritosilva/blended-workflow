package pt.ist.socialsoftware.blendedworkflow.designer.remote.resourcemodel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.AnyoneExpr;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.HasPositionExpr;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.IsPersonDataObject;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.IsPersonID;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.IsPersonInDuty;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.RALExpression;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.resourcemodel.dto.ResourceRuleDTO.ResourceRuleType;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "profileType", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = RALExprAnyoneDTO.class, name = "RALExprAnyoneDTO"),
    @JsonSubTypes.Type(value = RALExprIsPersonDTO.class, name = "RALExprIsPersonDTO"),
    @JsonSubTypes.Type(value = RALExprIsPersonDataObjectDTO.class, name = "RALExprIsPersonDataObjectDTO"),
    @JsonSubTypes.Type(value = RALExprIsPersonInTaskDutyDTO.class, name = "RALExprIsPersonInTaskDutyDTO"),
    @JsonSubTypes.Type(value = RALExprIsPersonInTaskDutyDTO.class, name = "RALExprHasPositionDTO"),
})
public class RALExpressionDTO {
	public static RALExpressionDTO buildRALExpressionDTO(String specId, RALExpression expression) {
		RALExpressionDTO ralExpressionDTO = null;
		if (expression instanceof IsPersonID) {
			IsPersonID isPersonExpr = (IsPersonID) expression;
			ralExpressionDTO = new RALExprIsPersonDTO(isPersonExpr.getPerson());
		} else if (expression instanceof IsPersonDataObject) {
			IsPersonDataObject isDataObjExpr = (IsPersonDataObject) expression;
			ralExpressionDTO = new RALExprIsPersonDataObjectDTO(isDataObjExpr.getDataField());
		} else if (expression instanceof IsPersonInDuty) {
			IsPersonInDuty isPersonInDutyExpr = (IsPersonInDuty) expression;
			ralExpressionDTO = new RALExprIsPersonInTaskDutyDTO(ResourceRuleType.fromAsgmtString(isPersonInDutyExpr.getTaskDuty()), isPersonInDutyExpr.getDataField());
		} else if (expression instanceof AnyoneExpr) {
			ralExpressionDTO = new RALExprAnyoneDTO();
		} else if (expression instanceof HasPositionExpr) {
			HasPositionExpr hasPositionExpr = (HasPositionExpr) expression;
			ralExpressionDTO = new RALExprHasPositionDTO(hasPositionExpr.getPosition().getName());
		}
		return ralExpressionDTO;
	}
}
