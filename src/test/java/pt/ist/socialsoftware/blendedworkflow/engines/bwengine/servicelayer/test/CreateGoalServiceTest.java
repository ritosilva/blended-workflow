package pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.GoalModelInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CreateBWInstanceService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CreateGoalService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.LoadBWSpecificationService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.parser.PrintBWSpecification;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.parser.StringUtils;

public class CreateGoalServiceTest {
	
	private static String BWSPECIFICATION_FILENAME = "src/test/xml/MedicalEpisode/MedicalEpisode.xml";
	private static String CREATE_BWINSTANCE_XML = "src/test/xml/MedicalEpisode/CreateBWInstanceInput.xml";
	private static String CREATE_GOAL_SECONDOPINION_XML = "src/test/xml/MedicalEpisode/CreateGoalSecondOpinion.xml";
	
	private static String BWSPECIFICATION_NAME = "Medical Appointment";
	private static String BWINSTANCE_ID = "Medical Appointment.1";
	private static String SECONDOPINION_ID = "Second Opinion.4";
	private static String SECONDOPINION_NAME = "Second Opinion";

	static {
		if(FenixFramework.getConfig()==null) {
			FenixFramework.initialize(new Config() {{
				dbAlias="test-db";
				domainModelPath="src/main/dml/blendedworkflow.dml";
				repositoryType=RepositoryType.BERKELEYDB;
				rootClass=BlendedWorkflow.class;
			}});
		}
	}

	@Before
	public void setUp() {
		String dataModelString = StringUtils.fileToString(BWSPECIFICATION_FILENAME);
		String createBWInstanceInputString = StringUtils.fileToString(CREATE_BWINSTANCE_XML);

		LoadBWSpecificationService loadBWSpecificationService = new LoadBWSpecificationService(dataModelString);
		CreateBWInstanceService createBWInstanceService = new CreateBWInstanceService(createBWInstanceInputString);
		try {
			loadBWSpecificationService.execute();
			createBWInstanceService.execute();
		} catch(BlendedWorkflowException e) {		
			fail(e.getMessage());
		}
	}

	@After
	public void tearDown() {
		boolean committed = false;
		try {
			Transaction.begin();
			BlendedWorkflow blendedWorkflow = BlendedWorkflow.getInstance();
			Set<BWSpecification> allBWSpecifications = blendedWorkflow.getBwSpecificationsSet();
			allBWSpecifications.clear();
			Transaction.commit();
			committed = true;
		} finally {
			if (!committed) {
				Transaction.abort();
				fail("CreateGoalServiceTest failed @TearDown.");
			}
		}
	}

	@Test
	public void createGoalSecondOpinion() {
		String createGoalInputString = StringUtils.fileToString(CREATE_GOAL_SECONDOPINION_XML);
		CreateGoalService createGoalService = new CreateGoalService(createGoalInputString);
		try {
			createGoalService.execute();
		} catch(BlendedWorkflowException e) {		
			fail(e.getMessage());
		}
		boolean committed = false;
		try {
			Transaction.begin();

			BlendedWorkflow blendedWorkflow = BlendedWorkflow.getInstance();
			BWInstance bwInstance = blendedWorkflow.getBWInstance(BWINSTANCE_ID);
			GoalModelInstance goalModelInstance = bwInstance.getGoalModelInstance();

			assertEquals(7, goalModelInstance.getGoalsCount()); // Created 6 Goals on Load +1
			assertEquals(SECONDOPINION_NAME, goalModelInstance.getGoal(SECONDOPINION_NAME).getName());
			assertEquals(SECONDOPINION_ID, bwInstance.getWorkItem(SECONDOPINION_ID).getId());
			
//			PrintBWSpecification.all(BWSPECIFICATION_NAME);

			Transaction.commit();
			committed = true;
		} catch (BlendedWorkflowException e) {
			fail(e.getMessage());
		} finally {
			if (!committed) {
				Transaction.abort();
			}
		}
	}

}