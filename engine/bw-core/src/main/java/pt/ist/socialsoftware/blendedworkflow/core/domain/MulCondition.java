package pt.ist.socialsoftware.blendedworkflow.core.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ist.socialsoftware.blendedworkflow.core.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.EntityDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.ExpressionDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.MulConditionDto;

public class MulCondition extends MulCondition_Base {

	// returns the mul condition that has that role in the path expression, e.
	// g. given role name a returns for MUL(B.a,n)
	public static MulCondition getMulCondition(RelationBW relation, String rolename) {
		for (MulCondition mulCondition : relation.getMulConditionSet()) {
			if (mulCondition.getRolename().equals(rolename)) {
				return mulCondition;
			}
		}

		if (relation.getMulConditionSet().size() >= 2) {
			throw new BWException(BWErrorType.INVALID_ROLE_NAME, rolename);
		}

		if (relation.getRolenameOne().equals(rolename)) {
			return new MulCondition(relation, 2);
		}

		if (relation.getRolenameTwo().equals(rolename)) {
			return new MulCondition(relation, 1);
		}

		assert false;
		return null;
	}

	public static void createMulConditions(RelationBW relation) {
		getMulCondition(relation, relation.getRolenameOne());
		getMulCondition(relation, relation.getRolenameTwo());
	}

	private MulCondition(RelationBW relation, int side) {
		relation.getDataModel().getSpecification().getConditionModel().addEntityInvariantCondition(this);
		setRelationBW(relation);
		setSide(side);
	}

	public Entity getSourceEntity() {
		if (getSide() == 1) {
			return getRelationBW().getEntityOne();
		} else {
			return getRelationBW().getEntityTwo();
		}
	}

	public Entity getTargetEntity() {
		if (getSide() == 1) {
			return getRelationBW().getEntityTwo();
		} else {
			return getRelationBW().getEntityOne();
		}
	}

	public String getRolename() {
		if (getSide() == 1) {
			return getRelationBW().getRolenameTwo();
		} else {
			return getRelationBW().getRolenameOne();
		}
	}

	public Cardinality getCardinality() {
		if (getSide() == 1) {
			return getRelationBW().getCardinalityTwo();
		} else {
			return getRelationBW().getCardinalityOne();
		}
	}

	public String getPath() {
		return getSourceEntity().getName() + "." + getRolename();
	}

	public MulCondition getSymmetricMulCondition() {
		String rolename;

		if (getSide() == 1) {
			rolename = getRelationBW().getRolenameOne();
		} else {
			rolename = getRelationBW().getRolenameTwo();
		}

		return MulCondition.getMulCondition(getRelationBW(), rolename);
	}

	public String getExpression() {
		return "MUL(" + getSourceEntity().getName() + "." + getRolename() + "," + getCardinality().getExp() + ")";
	}

	@Override
	public void delete() {
		setConditionModel(null);
		setRelationBW(null);
		setInvariantConditionGoal(null);
		setActivityWithMultiplicity(null);

		deleteDomainObject();
	}

	@Override
	public String getSubPath() {
		return "MUL(" + getSourceEntity().getName() + "." + getRolename() + "," + getCardinality().getExp() + ")";
	}

	@Override
	public Set<Entity> getEntities() {
		return getRelationBW().getEntitySet();
	}

	@Override
	public Set<Attribute> getAttributeSet() {
		return new HashSet<>();
	}

	@Override
	public Set<Path> getPathSet() {
		return new HashSet<>();

	}

	public MulConditionDto getDto() {
		MulConditionDto mulConditionDTO = new MulConditionDto();
		mulConditionDTO.setExternalId(getExternalId());
		mulConditionDTO.setRolename(getRolename());
		mulConditionDTO.setRolePath(getSourceEntity().getName() + "." + getRolename());
		mulConditionDTO.setCardinality(getCardinality().getExp());
		mulConditionDTO.setMin(getCardinality().getMinValue());
		mulConditionDTO.setMax(getCardinality().getMaxValue());
		mulConditionDTO.setSourceMin(getSymmetricMulCondition().getCardinality().getMinValue());
		mulConditionDTO.setSourceMax(getSymmetricMulCondition().getCardinality().getMaxValue());
		mulConditionDTO.setSourceEntity(new EntityDto(getSourceEntity()));
		mulConditionDTO.setTargetEntity(new EntityDto(getTargetEntity()));

		return mulConditionDTO;
	}

	@Override
	public ExpressionDto getExpressionDTO(String specId) {
		// TODO Auto-generated method stub
		return null;
	}

}
