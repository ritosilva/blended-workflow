/*
 * generated by Xtext
 */
grammar InternalGoal;

options {
	superClass=AbstractInternalAntlrParser;
	
}

@lexer::header {
package org.blended.goal.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.blended.goal.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.blended.goal.services.GoalGrammarAccess;

}

@parser::members {

 	private GoalGrammarAccess grammarAccess;
 	
    public InternalGoalParser(TokenStream input, GoalGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }
    
    @Override
    protected String getFirstRuleName() {
    	return "GoalModel";	
   	}
   	
   	@Override
   	protected GoalGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}
}

@rulecatch { 
    catch (RecognitionException re) { 
        recover(input,re); 
        appendSkippedTokens();
    } 
}




// Entry rule entryRuleGoalModel
entryRuleGoalModel returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getGoalModelRule()); }
	 iv_ruleGoalModel=ruleGoalModel 
	 { $current=$iv_ruleGoalModel.current; } 
	 EOF 
;

// Rule GoalModel
ruleGoalModel returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		{ 
	        newCompositeNode(grammarAccess.getGoalModelAccess().getGoalsGoalParserRuleCall_0()); 
	    }
		lv_goals_0_0=ruleGoal		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalModelRule());
	        }
       		add(
       			$current, 
       			"goals",
        		lv_goals_0_0, 
        		"Goal");
	        afterParserOrEnumRuleCall();
	    }

)
)+
;





// Entry rule entryRuleEntityAchieveCondition
entryRuleEntityAchieveCondition returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEntityAchieveConditionRule()); }
	 iv_ruleEntityAchieveCondition=ruleEntityAchieveCondition 
	 { $current=$iv_ruleEntityAchieveCondition.current; } 
	 EOF 
;

// Rule EntityAchieveCondition
ruleEntityAchieveCondition returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='DEFF' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getEntityAchieveConditionAccess().getDEFFKeyword_0());
    }
	otherlv_1='(' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getEntityAchieveConditionAccess().getLeftParenthesisKeyword_1());
    }
(
(
		lv_name_2_0=RULE_ID
		{
			newLeafNode(lv_name_2_0, grammarAccess.getEntityAchieveConditionAccess().getNameIDTerminalRuleCall_2_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getEntityAchieveConditionRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_2_0, 
        		"ID");
	    }

)
)	otherlv_3=')' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getEntityAchieveConditionAccess().getRightParenthesisKeyword_3());
    }
)
;







// Entry rule entryRuleEntityInvariantCondition
entryRuleEntityInvariantCondition returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEntityInvariantConditionRule()); }
	 iv_ruleEntityInvariantCondition=ruleEntityInvariantCondition 
	 { $current=$iv_ruleEntityInvariantCondition.current; } 
	 EOF 
;

// Rule EntityInvariantCondition
ruleEntityInvariantCondition returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='MUL' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getEntityInvariantConditionAccess().getMULKeyword_0());
    }
	otherlv_1='(' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getEntityInvariantConditionAccess().getLeftParenthesisKeyword_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getEntityInvariantConditionAccess().getNameAttributeParserRuleCall_2_0()); 
	    }
		lv_name_2_0=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getEntityInvariantConditionRule());
	        }
       		set(
       			$current, 
       			"name",
        		lv_name_2_0, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_3=',' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getEntityInvariantConditionAccess().getCommaKeyword_3());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getEntityInvariantConditionAccess().getCardinalityCardinalityParserRuleCall_4_0()); 
	    }
		lv_cardinality_4_0=ruleCardinality		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getEntityInvariantConditionRule());
	        }
       		set(
       			$current, 
       			"cardinality",
        		lv_cardinality_4_0, 
        		"Cardinality");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_5=')' 
    {
    	newLeafNode(otherlv_5, grammarAccess.getEntityInvariantConditionAccess().getRightParenthesisKeyword_5());
    }
)
;







// Entry rule entryRuleAttributeAchieveCondition
entryRuleAttributeAchieveCondition returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAttributeAchieveConditionRule()); }
	 iv_ruleAttributeAchieveCondition=ruleAttributeAchieveCondition 
	 { $current=$iv_ruleAttributeAchieveCondition.current; } 
	 EOF 
;

// Rule AttributeAchieveCondition
ruleAttributeAchieveCondition returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getAttributeAchieveConditionAccess().getMandatoryAttributeAchieveConditionParserRuleCall_0()); 
    }
    this_MandatoryAttributeAchieveCondition_0=ruleMandatoryAttributeAchieveCondition
    { 
        $current = $this_MandatoryAttributeAchieveCondition_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getAttributeAchieveConditionAccess().getNotMandatoryAttributeAchieveConditionParserRuleCall_1()); 
    }
    this_NotMandatoryAttributeAchieveCondition_1=ruleNotMandatoryAttributeAchieveCondition
    { 
        $current = $this_NotMandatoryAttributeAchieveCondition_1.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleNotMandatoryAttributeAchieveCondition
entryRuleNotMandatoryAttributeAchieveCondition returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getNotMandatoryAttributeAchieveConditionRule()); }
	 iv_ruleNotMandatoryAttributeAchieveCondition=ruleNotMandatoryAttributeAchieveCondition 
	 { $current=$iv_ruleNotMandatoryAttributeAchieveCondition.current; } 
	 EOF 
;

// Rule NotMandatoryAttributeAchieveCondition
ruleNotMandatoryAttributeAchieveCondition returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='DEF' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getNotMandatoryAttributeAchieveConditionAccess().getDEFKeyword_0());
    }
	otherlv_1='(' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getNotMandatoryAttributeAchieveConditionAccess().getLeftParenthesisKeyword_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getNotMandatoryAttributeAchieveConditionAccess().getConditionsAttributeParserRuleCall_2_0()); 
	    }
		lv_conditions_2_0=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getNotMandatoryAttributeAchieveConditionRule());
	        }
       		add(
       			$current, 
       			"conditions",
        		lv_conditions_2_0, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)
)(	otherlv_3=',' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getNotMandatoryAttributeAchieveConditionAccess().getCommaKeyword_3_0());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getNotMandatoryAttributeAchieveConditionAccess().getConditionsAttributeParserRuleCall_3_1_0()); 
	    }
		lv_conditions_4_0=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getNotMandatoryAttributeAchieveConditionRule());
	        }
       		add(
       			$current, 
       			"conditions",
        		lv_conditions_4_0, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)
))*	otherlv_5=')' 
    {
    	newLeafNode(otherlv_5, grammarAccess.getNotMandatoryAttributeAchieveConditionAccess().getRightParenthesisKeyword_4());
    }
)
;





// Entry rule entryRuleMandatoryAttributeAchieveCondition
entryRuleMandatoryAttributeAchieveCondition returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getMandatoryAttributeAchieveConditionRule()); }
	 iv_ruleMandatoryAttributeAchieveCondition=ruleMandatoryAttributeAchieveCondition 
	 { $current=$iv_ruleMandatoryAttributeAchieveCondition.current; } 
	 EOF 
;

// Rule MandatoryAttributeAchieveCondition
ruleMandatoryAttributeAchieveCondition returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='MAN' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getMandatoryAttributeAchieveConditionAccess().getMANKeyword_0());
    }
	otherlv_1='(' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getMandatoryAttributeAchieveConditionAccess().getLeftParenthesisKeyword_1());
    }
	otherlv_2='DEF' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getMandatoryAttributeAchieveConditionAccess().getDEFKeyword_2());
    }
	otherlv_3='(' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getMandatoryAttributeAchieveConditionAccess().getLeftParenthesisKeyword_3());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getMandatoryAttributeAchieveConditionAccess().getConditionsAttributeParserRuleCall_4_0()); 
	    }
		lv_conditions_4_0=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getMandatoryAttributeAchieveConditionRule());
	        }
       		add(
       			$current, 
       			"conditions",
        		lv_conditions_4_0, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)
)(	otherlv_5=',' 
    {
    	newLeafNode(otherlv_5, grammarAccess.getMandatoryAttributeAchieveConditionAccess().getCommaKeyword_5_0());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getMandatoryAttributeAchieveConditionAccess().getConditionsAttributeParserRuleCall_5_1_0()); 
	    }
		lv_conditions_6_0=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getMandatoryAttributeAchieveConditionRule());
	        }
       		add(
       			$current, 
       			"conditions",
        		lv_conditions_6_0, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)
))*	otherlv_7=')' 
    {
    	newLeafNode(otherlv_7, grammarAccess.getMandatoryAttributeAchieveConditionAccess().getRightParenthesisKeyword_6());
    }
	otherlv_8=')' 
    {
    	newLeafNode(otherlv_8, grammarAccess.getMandatoryAttributeAchieveConditionAccess().getRightParenthesisKeyword_7());
    }
)
;





// Entry rule entryRuleAttributeInvariantCondition
entryRuleAttributeInvariantCondition returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAttributeInvariantConditionRule()); }
	 iv_ruleAttributeInvariantCondition=ruleAttributeInvariantCondition 
	 { $current=$iv_ruleAttributeInvariantCondition.current; } 
	 EOF 
;

// Rule AttributeInvariantCondition
ruleAttributeInvariantCondition returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='RUL' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getAttributeInvariantConditionAccess().getRULKeyword_0());
    }
	otherlv_1='(' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getAttributeInvariantConditionAccess().getLeftParenthesisKeyword_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAttributeInvariantConditionAccess().getExpressionExpressionParserRuleCall_2_0()); 
	    }
		lv_expression_2_0=ruleExpression		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAttributeInvariantConditionRule());
	        }
       		set(
       			$current, 
       			"expression",
        		lv_expression_2_0, 
        		"Expression");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_3=')' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getAttributeInvariantConditionAccess().getRightParenthesisKeyword_3());
    }
)
;







// Entry rule entryRuleCardinality
entryRuleCardinality returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getCardinalityRule()); } 
	 iv_ruleCardinality=ruleCardinality 
	 { $current=$iv_ruleCardinality.current.getText(); }  
	 EOF 
;

// Rule Cardinality
ruleCardinality returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(    this_INT_0=RULE_INT    {
		$current.merge(this_INT_0);
    }

    { 
    newLeafNode(this_INT_0, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_0()); 
    }

    |(    this_INT_1=RULE_INT    {
		$current.merge(this_INT_1);
    }

    { 
    newLeafNode(this_INT_1, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_1_0()); 
    }

	kw='..' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getFullStopFullStopKeyword_1_1()); 
    }
    this_INT_3=RULE_INT    {
		$current.merge(this_INT_3);
    }

    { 
    newLeafNode(this_INT_3, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_1_2()); 
    }
)
    |(    this_INT_4=RULE_INT    {
		$current.merge(this_INT_4);
    }

    { 
    newLeafNode(this_INT_4, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_2_0()); 
    }

	kw='..' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getFullStopFullStopKeyword_2_1()); 
    }

	kw='*' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getAsteriskKeyword_2_2()); 
    }
)
    |(    this_INT_7=RULE_INT    {
		$current.merge(this_INT_7);
    }

    { 
    newLeafNode(this_INT_7, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_3_0()); 
    }

	kw='..' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getFullStopFullStopKeyword_3_1()); 
    }

	kw='+' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getPlusSignKeyword_3_2()); 
    }
))
    ;





// Entry rule entryRuleAttribute
entryRuleAttribute returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getAttributeRule()); } 
	 iv_ruleAttribute=ruleAttribute 
	 { $current=$iv_ruleAttribute.current.getText(); }  
	 EOF 
;

// Rule Attribute
ruleAttribute returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(    this_ID_0=RULE_ID    {
		$current.merge(this_ID_0);
    }

    { 
    newLeafNode(this_ID_0, grammarAccess.getAttributeAccess().getIDTerminalRuleCall_0()); 
    }

	kw='.' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getAttributeAccess().getFullStopKeyword_1()); 
    }
    this_ID_2=RULE_ID    {
		$current.merge(this_ID_2);
    }

    { 
    newLeafNode(this_ID_2, grammarAccess.getAttributeAccess().getIDTerminalRuleCall_2()); 
    }
(
	kw='.' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getAttributeAccess().getFullStopKeyword_3_0()); 
    }
    this_ID_4=RULE_ID    {
		$current.merge(this_ID_4);
    }

    { 
    newLeafNode(this_ID_4, grammarAccess.getAttributeAccess().getIDTerminalRuleCall_3_1()); 
    }
)*)
    ;





// Entry rule entryRuleNothing
entryRuleNothing returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getNothingRule()); }
	 iv_ruleNothing=ruleNothing 
	 { $current=$iv_ruleNothing.current; } 
	 EOF 
;

// Rule Nothing
ruleNothing returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_name_0_0=	'nothing' 
    {
        newLeafNode(lv_name_0_0, grammarAccess.getNothingAccess().getNameNothingKeyword_0());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getNothingRule());
	        }
       		setWithLastConsumed($current, "name", lv_name_0_0, "nothing");
	    }

)
)
;





// Entry rule entryRuleExpression
entryRuleExpression returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getExpressionRule()); }
	 iv_ruleExpression=ruleExpression 
	 { $current=$iv_ruleExpression.current; } 
	 EOF 
;

// Rule Expression
ruleExpression returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:

    { 
        newCompositeNode(grammarAccess.getExpressionAccess().getOrParserRuleCall()); 
    }
    this_Or_0=ruleOr
    { 
        $current = $this_Or_0.current; 
        afterParserOrEnumRuleCall();
    }

;





// Entry rule entryRuleOr
entryRuleOr returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getOrRule()); }
	 iv_ruleOr=ruleOr 
	 { $current=$iv_ruleOr.current; } 
	 EOF 
;

// Rule Or
ruleOr returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 
    }
    this_And_0=ruleAnd
    { 
        $current = $this_And_0.current; 
        afterParserOrEnumRuleCall();
    }
((
    {
        $current = forceCreateModelElementAndSet(
            grammarAccess.getOrAccess().getOrLeftAction_1_0(),
            $current);
    }
)	otherlv_2='OR' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getOrAccess().getORKeyword_1_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 
	    }
		lv_right_3_0=ruleAnd		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getOrRule());
	        }
       		set(
       			$current, 
       			"right",
        		lv_right_3_0, 
        		"And");
	        afterParserOrEnumRuleCall();
	    }

)
))*)
;





// Entry rule entryRuleAnd
entryRuleAnd returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAndRule()); }
	 iv_ruleAnd=ruleAnd 
	 { $current=$iv_ruleAnd.current; } 
	 EOF 
;

// Rule And
ruleAnd returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getAndAccess().getPrimaryParserRuleCall_0()); 
    }
    this_Primary_0=rulePrimary
    { 
        $current = $this_Primary_0.current; 
        afterParserOrEnumRuleCall();
    }
((
    {
        $current = forceCreateModelElementAndSet(
            grammarAccess.getAndAccess().getAndLeftAction_1_0(),
            $current);
    }
)	otherlv_2='AND' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getAndAccess().getANDKeyword_1_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAndAccess().getRightPrimaryParserRuleCall_1_2_0()); 
	    }
		lv_right_3_0=rulePrimary		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAndRule());
	        }
       		set(
       			$current, 
       			"right",
        		lv_right_3_0, 
        		"Primary");
	        afterParserOrEnumRuleCall();
	    }

)
))*)
;





// Entry rule entryRulePrimary
entryRulePrimary returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getPrimaryRule()); }
	 iv_rulePrimary=rulePrimary 
	 { $current=$iv_rulePrimary.current; } 
	 EOF 
;

// Rule Primary
rulePrimary returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((	otherlv_0='(' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
    }

    { 
        newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionParserRuleCall_0_1()); 
    }
    this_Expression_1=ruleExpression
    { 
        $current = $this_Expression_1.current; 
        afterParserOrEnumRuleCall();
    }
	otherlv_2=')' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_0_2());
    }
)
    |((
    {
        $current = forceCreateModelElement(
            grammarAccess.getPrimaryAccess().getNotAction_1_0(),
            $current);
    }
)	otherlv_4='NOT' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getNOTKeyword_1_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionPrimaryParserRuleCall_1_2_0()); 
	    }
		lv_expression_5_0=rulePrimary		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getPrimaryRule());
	        }
       		set(
       			$current, 
       			"expression",
        		lv_expression_5_0, 
        		"Primary");
	        afterParserOrEnumRuleCall();
	    }

)
))
    |
    { 
        newCompositeNode(grammarAccess.getPrimaryAccess().getAtomicParserRuleCall_2()); 
    }
    this_Atomic_6=ruleAtomic
    { 
        $current = $this_Atomic_6.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleAtomic
entryRuleAtomic returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAtomicRule()); }
	 iv_ruleAtomic=ruleAtomic 
	 { $current=$iv_ruleAtomic.current; } 
	 EOF 
;

// Rule Atomic
ruleAtomic returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(((
    {
        $current = forceCreateModelElement(
            grammarAccess.getAtomicAccess().getAttributeDefinitionAction_0_0(),
            $current);
    }
)	otherlv_1='DEF' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getAtomicAccess().getDEFKeyword_0_1());
    }
	otherlv_2='(' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getAtomicAccess().getLeftParenthesisKeyword_0_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAtomicAccess().getNameAttributeParserRuleCall_0_3_0()); 
	    }
		lv_name_3_0=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAtomicRule());
	        }
       		set(
       			$current, 
       			"name",
        		lv_name_3_0, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_4=')' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getAtomicAccess().getRightParenthesisKeyword_0_4());
    }
)
    |((
    {
        $current = forceCreateModelElement(
            grammarAccess.getAtomicAccess().getAttributeValueAction_1_0(),
            $current);
    }
)(
(
		{ 
	        newCompositeNode(grammarAccess.getAtomicAccess().getNameAttributeParserRuleCall_1_1_0()); 
	    }
		lv_name_6_0=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAtomicRule());
	        }
       		set(
       			$current, 
       			"name",
        		lv_name_6_0, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)
)))
;





// Entry rule entryRuleGoal
entryRuleGoal returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getGoalRule()); }
	 iv_ruleGoal=ruleGoal 
	 { $current=$iv_ruleGoal.current; } 
	 EOF 
;

// Rule Goal
ruleGoal returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		lv_name_0_0=RULE_ID
		{
			newLeafNode(lv_name_0_0, grammarAccess.getGoalAccess().getNameIDTerminalRuleCall_0_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getGoalRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_0_0, 
        		"ID");
	    }

)
)	otherlv_1=':' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getGoalAccess().getColonKeyword_1());
    }
	otherlv_2='SUC' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getGoalAccess().getSUCKeyword_2());
    }
	otherlv_3='(' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getGoalAccess().getLeftParenthesisKeyword_3());
    }
(
(
(
		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getSucessConditionEntityAchieveConditionParserRuleCall_4_0_0()); 
	    }
		lv_sucessCondition_4_1=ruleEntityAchieveCondition		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		set(
       			$current, 
       			"sucessCondition",
        		lv_sucessCondition_4_1, 
        		"EntityAchieveCondition");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getSucessConditionAttributeAchieveConditionParserRuleCall_4_0_1()); 
	    }
		lv_sucessCondition_4_2=ruleAttributeAchieveCondition		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		set(
       			$current, 
       			"sucessCondition",
        		lv_sucessCondition_4_2, 
        		"AttributeAchieveCondition");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getSucessConditionNothingParserRuleCall_4_0_2()); 
	    }
		lv_sucessCondition_4_3=ruleNothing		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		set(
       			$current, 
       			"sucessCondition",
        		lv_sucessCondition_4_3, 
        		"Nothing");
	        afterParserOrEnumRuleCall();
	    }

)

)
)	otherlv_5=')' 
    {
    	newLeafNode(otherlv_5, grammarAccess.getGoalAccess().getRightParenthesisKeyword_5());
    }
(	otherlv_6=',' 
    {
    	newLeafNode(otherlv_6, grammarAccess.getGoalAccess().getCommaKeyword_6_0());
    }
	otherlv_7='ACT' 
    {
    	newLeafNode(otherlv_7, grammarAccess.getGoalAccess().getACTKeyword_6_1());
    }
	otherlv_8='(' 
    {
    	newLeafNode(otherlv_8, grammarAccess.getGoalAccess().getLeftParenthesisKeyword_6_2());
    }
(
(
(
		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getActivationConditionEntityAchieveConditionParserRuleCall_6_3_0_0()); 
	    }
		lv_activationCondition_9_1=ruleEntityAchieveCondition		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		set(
       			$current, 
       			"activationCondition",
        		lv_activationCondition_9_1, 
        		"EntityAchieveCondition");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getActivationConditionAttributeAchieveConditionParserRuleCall_6_3_0_1()); 
	    }
		lv_activationCondition_9_2=ruleAttributeAchieveCondition		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		set(
       			$current, 
       			"activationCondition",
        		lv_activationCondition_9_2, 
        		"AttributeAchieveCondition");
	        afterParserOrEnumRuleCall();
	    }

)

)
)	otherlv_10=')' 
    {
    	newLeafNode(otherlv_10, grammarAccess.getGoalAccess().getRightParenthesisKeyword_6_4());
    }
)?(	otherlv_11=',' 
    {
    	newLeafNode(otherlv_11, grammarAccess.getGoalAccess().getCommaKeyword_7_0());
    }
	otherlv_12='INV' 
    {
    	newLeafNode(otherlv_12, grammarAccess.getGoalAccess().getINVKeyword_7_1());
    }
	otherlv_13='(' 
    {
    	newLeafNode(otherlv_13, grammarAccess.getGoalAccess().getLeftParenthesisKeyword_7_2());
    }
(
(
(
		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getInvariantConditionsEntityInvariantConditionParserRuleCall_7_3_0_0()); 
	    }
		lv_invariantConditions_14_1=ruleEntityInvariantCondition		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		add(
       			$current, 
       			"invariantConditions",
        		lv_invariantConditions_14_1, 
        		"EntityInvariantCondition");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getInvariantConditionsAttributeInvariantConditionParserRuleCall_7_3_0_1()); 
	    }
		lv_invariantConditions_14_2=ruleAttributeInvariantCondition		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		add(
       			$current, 
       			"invariantConditions",
        		lv_invariantConditions_14_2, 
        		"AttributeInvariantCondition");
	        afterParserOrEnumRuleCall();
	    }

)

)
)(	otherlv_15=',' 
    {
    	newLeafNode(otherlv_15, grammarAccess.getGoalAccess().getCommaKeyword_7_4_0());
    }
(
(
(
		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getInvariantConditionsEntityInvariantConditionParserRuleCall_7_4_1_0_0()); 
	    }
		lv_invariantConditions_16_1=ruleEntityInvariantCondition		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		add(
       			$current, 
       			"invariantConditions",
        		lv_invariantConditions_16_1, 
        		"EntityInvariantCondition");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getGoalAccess().getInvariantConditionsAttributeInvariantConditionParserRuleCall_7_4_1_0_1()); 
	    }
		lv_invariantConditions_16_2=ruleAttributeInvariantCondition		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getGoalRule());
	        }
       		add(
       			$current, 
       			"invariantConditions",
        		lv_invariantConditions_16_2, 
        		"AttributeInvariantCondition");
	        afterParserOrEnumRuleCall();
	    }

)

)
))*	otherlv_17=')' 
    {
    	newLeafNode(otherlv_17, grammarAccess.getGoalAccess().getRightParenthesisKeyword_7_5());
    }
)?(	otherlv_18=',' 
    {
    	newLeafNode(otherlv_18, grammarAccess.getGoalAccess().getCommaKeyword_8_0());
    }
	otherlv_19='SUB' 
    {
    	newLeafNode(otherlv_19, grammarAccess.getGoalAccess().getSUBKeyword_8_1());
    }
	otherlv_20='(' 
    {
    	newLeafNode(otherlv_20, grammarAccess.getGoalAccess().getLeftParenthesisKeyword_8_2());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getGoalRule());
	        }
        }
	otherlv_21=RULE_ID
	{
		newLeafNode(otherlv_21, grammarAccess.getGoalAccess().getChildrenGoalsGoalCrossReference_8_3_0()); 
	}

)
)(	otherlv_22=',' 
    {
    	newLeafNode(otherlv_22, grammarAccess.getGoalAccess().getCommaKeyword_8_4_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getGoalRule());
	        }
        }
	otherlv_23=RULE_ID
	{
		newLeafNode(otherlv_23, grammarAccess.getGoalAccess().getChildrenGoalsGoalCrossReference_8_4_1_0()); 
	}

)
))*	otherlv_24=')' 
    {
    	newLeafNode(otherlv_24, grammarAccess.getGoalAccess().getRightParenthesisKeyword_8_5());
    }
)?)
;





RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;

