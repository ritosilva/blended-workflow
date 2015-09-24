package pt.ist.socialsoftware.blendedworkflow.domain.taskmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import pt.ist.socialsoftware.blendedworkflow.TeardownRollbackTest;
import pt.ist.socialsoftware.blendedworkflow.domain.AttributeBasic;
import pt.ist.socialsoftware.blendedworkflow.domain.AttributeBasic.AttributeType;
import pt.ist.socialsoftware.blendedworkflow.domain.AttributeGroup;
import pt.ist.socialsoftware.blendedworkflow.domain.AttributeValueExpression;
import pt.ist.socialsoftware.blendedworkflow.domain.Comparison;
import pt.ist.socialsoftware.blendedworkflow.domain.Comparison.ComparisonOperator;
import pt.ist.socialsoftware.blendedworkflow.domain.DefAttributeCondition;
import pt.ist.socialsoftware.blendedworkflow.domain.DefEntityCondition;
import pt.ist.socialsoftware.blendedworkflow.domain.Dependence;
import pt.ist.socialsoftware.blendedworkflow.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.domain.MulCondition;
import pt.ist.socialsoftware.blendedworkflow.domain.RelationBW;
import pt.ist.socialsoftware.blendedworkflow.domain.RelationBW.Cardinality;
import pt.ist.socialsoftware.blendedworkflow.domain.Rule;
import pt.ist.socialsoftware.blendedworkflow.domain.Specification;
import pt.ist.socialsoftware.blendedworkflow.domain.Task;
import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;

public class CheckConsistencyMethodTest extends TeardownRollbackTest {
    private static final String TASK_ONE = "TaskOne";
    private static final String TASK_TWO = "TaskTwo";
    private static final String TASK_THREE = "TaskThree";
    private static final String ANOTHER_TASK = "anotherTask";
    private static final String ENTITY_ONE_NAME = "Entity one name";
    private static final String ENTITY_TWO_NAME = "Entity two name";
    private static final String ENTITY_THREE_NAME = "Entity three name";
    private static final String ATTRIBUTE_ONE_NAME = "att1";
    private static final String ATTRIBUTE_TWO_NAME = "att2";
    private static final String ATTRIBUTE_THREE_NAME = "att3";
    private static final String ATTRIBUTE_FOUR_NAME = "att4";
    private static final String ROLENAME_ONE = "theOne";
    private static final String ROLENAME_TWO = "theTwo";
    private static final String RULE_ONE_NAME = "ruleOne";
    private static final String RULE_TWO_NAME = "ruleTwo";

    Specification spec;
    Entity entityOne;
    Entity entityTwo;
    Entity entityThree;
    AttributeBasic attributeOne;
    AttributeBasic attributeTwo;
    AttributeBasic attributeThree;
    AttributeGroup attributeFour;
    AttributeBasic attributeFourOne;
    AttributeBasic attributeFourTwo;
    RelationBW relation;
    Rule ruleOne;
    Rule ruleTwo;

    Task taskOne;
    Task taskTwo;
    Task taskThree;

    @Override
    public void populate4Test() throws BWException {
        spec = new Specification("SpecId", "My spec", "author", "description",
                "version", "UID");

        entityOne = new Entity(spec.getDataModel(), ENTITY_ONE_NAME, false);
        attributeOne = new AttributeBasic(spec.getDataModel(), entityOne, null,
                ATTRIBUTE_ONE_NAME, AttributeType.NUMBER, true, false, false);
        attributeTwo = new AttributeBasic(spec.getDataModel(), entityOne, null,
                ATTRIBUTE_TWO_NAME, AttributeType.NUMBER, true, false, false);

        entityTwo = new Entity(spec.getDataModel(), ENTITY_TWO_NAME, false);
        attributeThree = new AttributeBasic(spec.getDataModel(), entityTwo,
                null, ATTRIBUTE_THREE_NAME, AttributeType.BOOLEAN, true, false,
                false);

        relation = new RelationBW(spec.getDataModel(), "name", entityOne,
                ROLENAME_ONE, Cardinality.ONE, false, entityTwo, ROLENAME_TWO,
                Cardinality.ZERO_MANY, false);

        entityThree = new Entity(spec.getDataModel(), ENTITY_THREE_NAME, false);
        attributeFour = new AttributeGroup(spec.getDataModel(), entityThree,
                ATTRIBUTE_FOUR_NAME, false);
        attributeFourOne = new AttributeBasic(spec.getDataModel(), entityThree,
                attributeFour, "att41", AttributeType.NUMBER, false, false,
                false);
        attributeFourTwo = new AttributeBasic(spec.getDataModel(), entityThree,
                attributeFour, "att42", AttributeType.NUMBER, false, false,
                false);

        new Dependence(spec.getDataModel(), attributeThree, ENTITY_TWO_NAME
                + "." + ROLENAME_ONE + "." + ATTRIBUTE_TWO_NAME);

        ruleOne = new Rule(spec.getDataModel(), RULE_ONE_NAME,
                new Comparison(new AttributeValueExpression(attributeOne),
                        new AttributeValueExpression(attributeTwo),
                        ComparisonOperator.EQUAL));

        ruleTwo = new Rule(spec.getDataModel(), RULE_TWO_NAME,
                new Comparison(new AttributeValueExpression(attributeFourOne),
                        new AttributeValueExpression(attributeFourTwo),
                        ComparisonOperator.EQUAL));

        spec.getConditionModel().generateConditions();

        taskOne = new Task(spec.getTaskModel(), TASK_ONE, "Description");
        taskOne.addPostCondition(DefEntityCondition.getDefEntity(entityOne));
        taskOne.addPostCondition(
                DefAttributeCondition.getDefAttribute(attributeOne));
        taskOne.addPostCondition(
                DefAttributeCondition.getDefAttribute(attributeTwo));
        taskOne.addRuleInvariant(ruleOne);

        taskTwo = new Task(spec.getTaskModel(), TASK_TWO, "Description");
        taskTwo.addPreCondition(DefEntityCondition.getDefEntity(entityOne));
        taskTwo.addPostCondition(DefEntityCondition.getDefEntity(entityTwo));
        taskTwo.addPostCondition(DefEntityCondition.getDefEntity(entityThree));
        taskTwo.addMultiplicityInvariant(MulCondition.getMulCondition(relation,
                relation.getRoleNameOne()));
        taskTwo.addMultiplicityInvariant(MulCondition.getMulCondition(relation,
                relation.getRoleNameTwo()));

        taskThree = new Task(spec.getTaskModel(), TASK_THREE, "Description");
        taskThree.addPreCondition(DefEntityCondition.getDefEntity(entityTwo));
        taskThree.addPreCondition(DefEntityCondition.getDefEntity(entityThree));
        taskThree.addPreCondition(
                DefAttributeCondition.getDefAttribute(attributeTwo));
        taskThree.addPostCondition(
                DefAttributeCondition.getDefAttribute(attributeThree));
        taskThree.addPostCondition(
                DefAttributeCondition.getDefAttribute(attributeFour));
        taskThree.addRuleInvariant(ruleTwo);

    }

    @Test
    public void taskWithoutDefsPost() {
        new Task(spec.getTaskModel(), ANOTHER_TASK, "Description");

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.NO_DEF_CONDITION_IN_POST, bwe.getError());
            assertEquals(ANOTHER_TASK, bwe.getMessage());
        }
    }

    @Test
    public void defConditionNotAppliedToPost() {
        taskOne.removePostCondition(
                DefAttributeCondition.getDefAttribute(attributeTwo));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.NOT_ALL_CONDITIONS_APPLIED,
                    bwe.getError());
            assertEquals("DEF(" + ATTRIBUTE_TWO_NAME + ")", bwe.getMessage());
        }
    }

    @Test
    public void defGroupConditionNotAppliedToPost() {
        taskThree.removePostCondition(
                DefAttributeCondition.getDefAttribute(attributeFour));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.NOT_ALL_CONDITIONS_APPLIED,
                    bwe.getError());
            assertEquals("DEF(" + ATTRIBUTE_FOUR_NAME + ")", bwe.getMessage());
        }
    }

    @Test
    public void mulConditionNotAppliedToPost() {
        taskTwo.removeMultiplicityInvariant(MulCondition
                .getMulCondition(relation, relation.getRoleNameTwo()));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.NOT_ALL_CONDITIONS_APPLIED,
                    bwe.getError());
            assertEquals(
                    MulCondition.getMulCondition(relation,
                            relation.getRoleNameTwo()).getSubPath(),
                    bwe.getMessage());
        }
    }

    @Test
    public void ruleConditionNotAppliedToPost() {
        taskOne.removeRuleInvariant(ruleOne);

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.NOT_ALL_CONDITIONS_APPLIED,
                    bwe.getError());
            assertEquals(RULE_ONE_NAME, bwe.getMessage());
        }
    }

    @Test
    public void defAttributeDoesNotDependOnDefEntity() {
        taskThree
                .removePreCondition(DefEntityCondition.getDefEntity(entityTwo));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.MISSING_DEF_IN_PRE, bwe.getError());
            assertEquals(TASK_THREE + ":" + ENTITY_TWO_NAME + "."
                    + ATTRIBUTE_THREE_NAME, bwe.getMessage());
        }
    }

    @Test
    public void defAttributeDoesNotEnforceDependenceConstraint() {
        taskThree.removePreCondition(
                DefAttributeCondition.getDefAttribute(attributeTwo));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.MISSING_DEF_IN_PRE, bwe.getError());
            assertEquals(TASK_THREE + ":" + ATTRIBUTE_THREE_NAME,
                    bwe.getMessage());
        }
    }

    @Test
    public void mulConditionIsWronglyLocated() {
        taskTwo.removeMultiplicityInvariant(MulCondition
                .getMulCondition(relation, relation.getRoleNameOne()));
        taskOne.addMultiplicityInvariant(MulCondition.getMulCondition(relation,
                relation.getRoleNameOne()));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.INCONSISTENT_MUL_CONDITION,
                    bwe.getError());
            assertEquals(TASK_ONE + ":" + ENTITY_TWO_NAME, bwe.getMessage());
        }
    }

    @Test
    public void mulConditionDoesNotHaveDefEntity() {
        taskTwo.removeMultiplicityInvariant(MulCondition
                .getMulCondition(relation, relation.getRoleNameOne()));
        taskThree.addMultiplicityInvariant(MulCondition
                .getMulCondition(relation, relation.getRoleNameOne()));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.INCONSISTENT_MUL_CONDITION,
                    bwe.getError());
            assertEquals(TASK_THREE + ":" + ENTITY_TWO_NAME, bwe.getMessage());
        }

    }

    @Test
    public void ruleConditionIsWronglyLocated() {
        taskOne.removeRuleInvariant(ruleOne);
        taskThree.addRuleInvariant(ruleOne);

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.INCONSISTENT_RULE_CONDITION,
                    bwe.getError());
            assertEquals(TASK_THREE + ":" + RULE_ONE_NAME, bwe.getMessage());
        }
    }

    @Test
    public void ruleConditionDoesNotHaveDefAttribute() {
        taskOne.removeRuleInvariant(ruleOne);
        taskTwo.addRuleInvariant(ruleOne);
        taskTwo.addPreCondition(
                DefAttributeCondition.getDefAttribute(attributeOne));
        taskTwo.addPreCondition(
                DefAttributeCondition.getDefAttribute(attributeTwo));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.INCONSISTENT_RULE_CONDITION,
                    bwe.getError());
            assertEquals(TASK_TWO + ":" + RULE_ONE_NAME, bwe.getMessage());
        }
    }

    @Test
    public void ruleConditionDoesNotHaveDefAttributeGroup() {
        taskThree.removeRuleInvariant(ruleTwo);
        taskTwo.addRuleInvariant(ruleTwo);
        taskTwo.addPreCondition(
                DefAttributeCondition.getDefAttribute(attributeFour));

        try {
            spec.getTaskModel().checkModel();
            fail();
        } catch (BWException bwe) {
            assertEquals(BWErrorType.INCONSISTENT_RULE_CONDITION,
                    bwe.getError());
            assertEquals(TASK_TWO + ":" + RULE_TWO_NAME, bwe.getMessage());
        }
    }

    @Test
    public void success() {
        boolean result = spec.getTaskModel().checkModel();

        assertTrue(result);
    }

}
