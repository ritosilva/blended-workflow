package pt.ist.socialsoftware.blendedworkflow.adapters.convertor;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Condition;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.DataModel;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.AchieveGoal;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.GoalModel;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Role;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Task;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.User;

import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException;
import pt.ist.socialsoftware.blendedworkflow.shared.StringUtils;

public class GoalModelFactory {

	public void parseXMLGoalModel(DataModel dataModel, GoalModel goalModel, String specificationXML) throws BlendedWorkflowException {
		User defaultUser = BlendedWorkflow.getInstance().getOrganizationalModel().getUser("BlendedWorkflow");
		Role defaultRole = BlendedWorkflow.getInstance().getOrganizationalModel().getRole("Admin");
		
		Document doc = StringUtils.stringToDoc(specificationXML);

		Element root = doc.getRootElement();
		Namespace bwNamespace = root.getNamespace();

		Element goalModelXML = root.getChild("GoalModel", bwNamespace);

		// Root Goal
		Element rootGoalXML = goalModelXML.getChild("RootGoal", bwNamespace);
		String rootGoalName = rootGoalXML.getChildText("Name", bwNamespace);
		String rootGoalDescription = rootGoalXML.getChildText("description", bwNamespace);
		
		String rootGoalConditionString = rootGoalXML.getChildText("SucessCondition", bwNamespace);
		rootGoalConditionString = ConditionFactory.getRelationDependencies(dataModel, rootGoalConditionString);
		Condition rootGoalCondition = ConditionFactory.createCondition(dataModel, rootGoalConditionString);
		
		AchieveGoal rootGoal = new AchieveGoal(goalModel, rootGoalName, rootGoalDescription, rootGoalCondition);
		rootGoal.setUser(defaultUser);
		rootGoal.setRole(defaultRole);
		
		// Activate Conditions
		int activateConditionCount = Integer.parseInt(rootGoalXML.getChildText("ActivateConditionCount", bwNamespace));
		for (int i = 0; i < activateConditionCount ; i++) {
			String activateConditionXML = "ActivateCondition" + (i+1);
			String activateConditionString = rootGoalXML.getChildText(activateConditionXML, bwNamespace);
			activateConditionString = ConditionFactory.getRelationDependencies(dataModel, activateConditionString);
			Condition activateCondition = ConditionFactory.createCondition(dataModel, activateConditionString);
			rootGoal.addActivateConditions(activateCondition);
		}
		

		List<?> goals = goalModelXML.getChildren("Goal", bwNamespace);
		for (Object goal : goals) {
			Element goalXML = (Element) goal;

			String goalName = goalXML.getChildText("Name", bwNamespace);
			String goalDescription = goalXML.getChildText("description", bwNamespace);
			
			String goalConditionString = goalXML.getChildText("SucessCondition", bwNamespace);
			goalConditionString =ConditionFactory.getRelationDependencies(dataModel, goalConditionString);
			Condition goalCondition = ConditionFactory.createCondition(dataModel, goalConditionString);
			
			AchieveGoal parentGoal = goalModel.getGoal(goalXML.getChildText("ParentName", bwNamespace)); 
			AchieveGoal newGoal = new AchieveGoal(goalModel, parentGoal, goalName, goalDescription, goalCondition);
			newGoal.setUser(defaultUser);
			newGoal.setRole(defaultRole);
			
			// Activate Conditions
			activateConditionCount = Integer.parseInt(goalXML.getChildText("ActivateConditionCount", bwNamespace));
			for (int i = 0; i < activateConditionCount ; i++) {
				String activateConditionXML = "ActivateCondition" + (i+1);
				String activateConditionString = goalXML.getChildText(activateConditionXML, bwNamespace);
				activateConditionString = ConditionFactory.getRelationDependencies(dataModel, activateConditionString);
				Condition activateCondition = ConditionFactory.createCondition(dataModel, activateConditionString);
				newGoal.addActivateConditions(activateCondition);
			}
			
		}
	}
}