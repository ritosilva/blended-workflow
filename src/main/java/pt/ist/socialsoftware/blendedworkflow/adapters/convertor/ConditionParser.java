package pt.ist.socialsoftware.blendedworkflow.adapters.convertor;

import pt.ist.socialsoftware.blendedworkflow.engines.domain.AndCondition;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Attribute;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Attribute.AttributeType;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.CompareAttributeToValueCondition;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Condition;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.DataModel;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.ExistsAttributeCondition;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.ExistsEntityCondition;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.NotCondition;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.OrCondition;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException.BlendedWorkflowError;

public class ConditionParser {

	private final int STR_LENGHT;

	private static String _cond;

	private DataModel dataModel;
	private int _token;

	public ConditionParser(DataModel dataModel, String condition) throws BlendedWorkflowException {
		if(condition == null || condition.equals("")) {
			throw new BlendedWorkflowException(BlendedWorkflowError.EMPTY_CONDITION_STRING);
		}
		this.dataModel = dataModel;
		_cond = condition;
		STR_LENGHT = condition.length();
		_token = 0;
	}

	public Condition parseCondition() throws BlendedWorkflowException {
		Condition finalCondition = null;
		if(_cond.startsWith("existsAttribute(") || _cond.startsWith("existsEntity(") || _cond.startsWith("compareAttributeTo(")) {
			finalCondition = parseConditionType();
			//		} else if(_cond.startsWith("true")){
			//			return new TrueCondition();
		} else {
			throw new BlendedWorkflowException(BlendedWorkflowError.INVALID_CONDITION_STRING, _cond);
		}

		return continueParseCondition(finalCondition);
	}

	protected Condition continueParseCondition(Condition parsedCondition) throws BlendedWorkflowException {
		while(_token < STR_LENGHT) {
			if(_cond.startsWith(" and ", _token) || _cond.startsWith(" or ", _token)) {
				parsedCondition = parseConditionJoiner(parsedCondition);
			} else {
				throw new BlendedWorkflowException(BlendedWorkflowError.INVALID_CONDITION_STRING, _cond);
			}
		}
		return parsedCondition;
	}

	////// PARSE RULES /////
	protected Condition parseConditionType() throws BlendedWorkflowException {
		Condition parsedCondition;
		if(_cond.startsWith("existsAttribute(", _token)) {
			parsedCondition = parseExistsAttributeCondition();
		} else if (_cond.startsWith("existsEntity(", _token)) {
			parsedCondition = parseExistsEntityCondition();
		} else if(_cond.startsWith("compareAttributeTo(", _token)) {
			parsedCondition = parseCompareAttributeToCondition();
		} else {
			return null;
		}

		// see if there is a .not() next
		if(_cond.startsWith(".not()", _token)) {
			parsedCondition = parseNotCondition(parsedCondition);
		}
		return parsedCondition;
	}

	protected Condition parseExistsAttributeCondition() throws BlendedWorkflowException {
		int endOfCondition = _cond.indexOf(')', _token);
		if(endOfCondition < _token) {
			throw new BlendedWorkflowException(BlendedWorkflowError.INVALID_CONDITION_STRING, _cond);
		}
		String existsAttributeString = _cond.substring(_token, endOfCondition+1);
		StringBuilder elementName  = new StringBuilder();
		int startArgs = "existsAttribute(".length();

		Attribute attribute = parseExistsAttributeConditionArgs(existsAttributeString, startArgs, existsAttributeString.length()-1, elementName);
		Condition existsAttributeCondition = new ExistsAttributeCondition(attribute);
		_token = endOfCondition+1;
		return existsAttributeCondition;
	}

	protected Attribute parseExistsAttributeConditionArgs(String existsAttributeCondition, int startArgs, int endArgs, StringBuilder elementName) throws BlendedWorkflowException {
		if(startArgs > endArgs) throw new BlendedWorkflowException(BlendedWorkflowError.INVALID_CONDITION_STRING, existsAttributeCondition);

		elementName.append(existsAttributeCondition.substring(startArgs, endArgs));

		String[] elementArr = elementName.toString().split("\\.");

		Entity entity = parseEntity(elementArr);
		return parseAttribute(elementArr, entity);

	}

	protected Condition parseExistsEntityCondition() throws BlendedWorkflowException {
		int endOfCondition = _cond.indexOf(')', _token);
		if(endOfCondition < _token) {
			throw new BlendedWorkflowException(BlendedWorkflowError.INVALID_CONDITION_STRING, _cond);
		}

		String existsEntityString = _cond.substring(_token, endOfCondition+1);
		StringBuilder elementName  = new StringBuilder();
		int startArgs = "existsEntity(".length();
		Entity entity = parseExistsEntityConditionArgs(existsEntityString, startArgs, existsEntityString.length()-1, elementName);

		Condition existsEntityCondition = new ExistsEntityCondition(entity); 
		_token = endOfCondition+1;
		return existsEntityCondition;
	}

	protected Entity parseExistsEntityConditionArgs(String existsEntityCondition, int startArgs, int endArgs, StringBuilder elementName) throws BlendedWorkflowException {
		if(startArgs > endArgs) throw new BlendedWorkflowException(BlendedWorkflowError.INVALID_CONDITION_STRING, existsEntityCondition);

		elementName.append(existsEntityCondition.substring(startArgs, endArgs));

		if (dataModel.getEntity(elementName.toString()) != null)
			return dataModel.getEntity(elementName.toString());
		else 
			return new Entity(dataModel, elementName.toString());
	}

	protected Condition parseCompareAttributeToCondition() throws BlendedWorkflowException {
		int endOfCondition = _cond.indexOf(')', _token);

		if(endOfCondition < _token) {
			throw new BlendedWorkflowException(BlendedWorkflowError.INVALID_CONDITION_STRING, _cond);
		}

		String compareAttributeToString = _cond.substring(_token, endOfCondition+1);
		StringBuilder elementName = new StringBuilder();
		StringBuilder elementTo = new StringBuilder();
		int startArgs = "compareAttributeTo(".length();

		Attribute attribute;
		String operator;
		String value;
		//parseCompareAttributeConditionArgs(compareAttributeToString, startArgs, compareAttributeToString.length()-1, elementName, elementTo, attribute, operator, value);

		// FIXME REFACTOR
		int endArgs = compareAttributeToString.length()-1;
		int subToken = compareAttributeToString.indexOf(',', startArgs);

		// Parse Entity and Attribute
		elementName.append(compareAttributeToString.substring(startArgs, subToken));
		String[] elementArr = elementName.toString().split("\\.");
		Entity entity = parseEntity(elementArr);
		attribute = parseAttribute(elementArr, entity);

		// Parse Operator and Value
		elementTo.append(compareAttributeToString.substring(subToken+1, endArgs).trim());
		String[] toArr = elementTo.toString().split("\\.");
		operator = toArr[0];
		value = toArr[1];
		// FIXME REFACTOR

		CompareAttributeToValueCondition compareAttributeToCondition = new CompareAttributeToValueCondition(attribute, operator, value);
		_token = endOfCondition+1;

		return compareAttributeToCondition;
	}

	protected void parseCompareAttributeConditionArgs(String compareAttributeToString, int startArgs, int endArgs, StringBuilder elementName, StringBuilder elementTo, Attribute attribute, String operator, String value) throws BlendedWorkflowException {
		if(startArgs > endArgs) return;

		int subToken = compareAttributeToString.indexOf(',', startArgs);

		// Parse Entity and Attribute
		elementName.append(compareAttributeToString.substring(startArgs, subToken));

		String[] elementArr = elementName.toString().split("\\.");
		Entity entity = parseEntity(elementArr);
		attribute = parseAttribute(elementArr, entity);

		// Parse Operator and Value
		elementTo.append(compareAttributeToString.substring(subToken+1, endArgs).trim());
		String[] toArr = elementTo.toString().split("\\.");

		operator = toArr[0];
		value = toArr[1];
	}

	protected Condition parseNotCondition(Condition typeCondition) {
		_token += ".not()".length();
		return new NotCondition(typeCondition);
	}

	protected Condition parseConditionJoiner(Condition parsedCondition) throws BlendedWorkflowException {
		if(_cond.startsWith(" and ", _token)) {
			return parseAndCondition(parsedCondition);
		} else if(_cond.startsWith(" or ", _token)) {
			return parseOrCondition(parsedCondition);
		} else {
			throw new BlendedWorkflowException(BlendedWorkflowError.INVALID_CONDITION_STRING, _cond);
		}
	}

	protected Condition parseAndCondition(Condition parsedCondition) throws BlendedWorkflowException {
		_token += " and ".length();
		return new AndCondition(parsedCondition, parseConditionType());
	}

	protected Condition parseOrCondition(Condition parsedCondition) throws BlendedWorkflowException {
		_token += " or ".length();
		return new OrCondition(parsedCondition, parseConditionType());
	}

	private Entity parseEntity(String[] elementArr) throws BlendedWorkflowException {
		Entity entity;
		if (dataModel.getEntity(elementArr[0]) != null)
			entity = dataModel.getEntity(elementArr[0]);
		else 
			entity = new Entity(dataModel, elementArr[0]);
		return entity;
	}

	private Attribute parseAttribute(String[] elementArr, Entity entity) throws BlendedWorkflowException {
		AttributeType type;
		boolean iskeyAttribute;
		if (entity.getAttribute(elementArr[1]) != null)
			return entity.getAttribute(elementArr[1]);
		else {
			if (elementArr[2].equals("BOOLEAN"))
				type = AttributeType.BOOLEAN;
			else if (elementArr[2].equals("NUMBER"))
				type = AttributeType.NUMBER;
			else
				type = AttributeType.STRING;

			if (elementArr[3].equals("true"))
				iskeyAttribute = true;
			else
				iskeyAttribute = false;

			return new Attribute(dataModel, elementArr[1], entity, type, iskeyAttribute); 
		}
	}

}