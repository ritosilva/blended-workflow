package pt.ist.socialsoftware.blendedworkflow.bwmanager;

import java.util.HashMap;

import org.apache.log4j.Logger;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CreateBWInstanceService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.LoadBWSpecificationService;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.DataModelInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.EntityInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Relation;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.RelationInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException.BlendedWorkflowError;
import pt.ist.socialsoftware.blendedworkflow.presentation.BWPresentation;
import pt.ist.socialsoftware.blendedworkflow.shared.BWExecutorService;

import com.vaadin.ui.Window.Notification;

public class BWManager {

	private final Logger log = Logger.getLogger("BWManager");
	protected BWPresentation bwPresentation = null;

	public BWPresentation getBwPresentation() {
		return bwPresentation;
	}

	public void setBwPresentation(BWPresentation bwPresentation) {
		this.bwPresentation = bwPresentation;
	}

	/**
	 * Notify the BWPresentation of loaded BWSpecifications.
	 * 
	 * @param bwSpecification
	 *            The loaded BWSpecification.
	 */
	public void notifyLoadedBWSpecification(BWSpecification bwSpecification) {
		log.info("BWSpecification " + bwSpecification.getName() + " created.");
		getBwPresentation().addBWSpecification(bwSpecification.getExternalId(),
				bwSpecification.getName());
	}

	/**
	 * Notify the BWPresentation of created BWInstances.
	 * 
	 * @param bwInstance
	 *            The created BWInstance.
	 */
	public void notifyCreatedBWInstance(BWInstance bwInstance) {
		log.info("BWInstance " + bwInstance.getID() + " created.");
		getBwPresentation().addBWInstance(bwInstance.getExternalId(),
				bwInstance.getName());
	}

	/**
	 * Update the BWPresentation with all BWSpecifications and BWInstances
	 * created.
	 */
	public void updateBWPresentation() {
		for (BWSpecification bwSpecification : BlendedWorkflow.getInstance()
				.getBwSpecificationsSet()) {
			notifyLoadedBWSpecification(bwSpecification);
			for (BWInstance bwInstance : bwSpecification.getBwInstancesSet()) {
				notifyCreatedBWInstance(bwInstance);
			}
		}
	}

	/**
	 * Show Exception.
	 */
	public void notifyException(BlendedWorkflowError bwe) {
		getBwPresentation().getMainWindow().showNotification(bwe.toString(),
				Notification.TYPE_ERROR_MESSAGE);
	}

	/**
	 * LoadSpecification Service.
	 */
	public void loadBWSpecification(String bwXML) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance()
				.getBWExecutorService();
		LoadBWSpecificationService service = new LoadBWSpecificationService(
				bwXML);
		bwExecutorService.runTask(service);
	}

	/**
	 * Create BWInstance Service.
	 */
	public void createBWInstance(String bwSpecificationOID, String name,
			String userID) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance()
				.getBWExecutorService();
		CreateBWInstanceService service = new CreateBWInstanceService(
				bwSpecificationOID, name, userID);
		bwExecutorService.runTask(service);
	}

	/**
	 * TODO: Test/Refactor. Create a RelationInstance.
	 */
	public void addRelationInstance(String bwInstanceOID, String e1OID,
			String e2OID) {
		BWInstance bwInstance = FenixFramework.getDomainObject(bwInstanceOID);
		DataModelInstance dataModelInstance = bwInstance.getDataModelInstance();

		EntityInstance e1 = FenixFramework.getDomainObject(e1OID);
		EntityInstance e2 = FenixFramework.getDomainObject(e2OID);
		Entity entity1 = e1.getEntity();
		Entity entity2 = e2.getEntity();
		boolean exists = false;

		// Check if relation instance already exists
		for (Relation relation : dataModelInstance.getRelationsSet()) {
			for (RelationInstance relationInstance : relation
					.getRelationInstancesSet()) {
				EntityInstance one = relationInstance.getEntityInstanceOne();
				EntityInstance two = relationInstance.getEntityInstanceTwo();
				if (one.equals(e1) && two.equals(e2) || one.equals(e2)
						&& two.equals(e1)) {
					exists = true;
					break;
				}
			}
		}

		// Create only if no previous RelationInstance exists
		if (!exists) {
			for (Relation relation : dataModelInstance.getRelationsSet()) {
				Entity one = relation.getEntityOne();
				Entity two = relation.getEntityTwo();
				if ((one.equals(entity1) && two.equals(entity2))) {
					new RelationInstance(relation, e1, e2,
							e1.getNewRelationInstanceID());
				} else if (one.equals(entity2) && two.equals(entity1)) {
					new RelationInstance(relation, e2, e1,
							e2.getNewRelationInstanceID());
				}
			}
		}
	}

	public void notifyNeededEntityInstances(EntityInstance entityContext,
			HashMap<Entity, Relation> neededEntityInstances) {
		getBwPresentation().generateAddSubGoalsContextWindow(entityContext,
				neededEntityInstances);
	}

}