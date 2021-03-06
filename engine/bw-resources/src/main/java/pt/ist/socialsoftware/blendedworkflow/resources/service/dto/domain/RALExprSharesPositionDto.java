package pt.ist.socialsoftware.blendedworkflow.resources.service.dto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pt.ist.socialsoftware.blendedworkflow.core.service.design.DesignInterface;
import pt.ist.socialsoftware.blendedworkflow.resources.domain.*;
import pt.ist.socialsoftware.blendedworkflow.resources.service.RMErrorType;
import pt.ist.socialsoftware.blendedworkflow.resources.service.RMException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RALExprSharesPositionDto extends RALExprCommonalityDto {
	public RALExprSharesPositionDto() {
		
	}
	
	public RALExprSharesPositionDto(AmountDTO amountDTO, RALExpressionDto personExpr) {
		super(amountDTO, personExpr);
	}

	@Override
	public RALExpression getRALExpresion(ResourceModel resourceModel, DesignInterface designer, String specId) throws RMException {
		// log.debug("RALExpression Type: SHARES POSITION");

		RALExprCommonality.Amount amount = RALExprCommonality.Amount
				.fromString(getAmountDTO().toString());

		RALExpression expression = getPersonExpr().getRALExpresion(resourceModel, designer, specId);

		if (!(expression instanceof RALPersonExpression)) {
			throw new RMException(RMErrorType.INVALID_RAL_EXPRESSION_CAST,
					"Invalid RALExpression cast to " + RALPersonExpression.class.getName());
		}

		return new RALExprSharesPosition(resourceModel, amount, (RALPersonExpression) expression);
	}
}