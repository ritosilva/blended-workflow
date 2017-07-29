package pt.ist.socialsoftware.blendedworkflow.designer.remote;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.Association;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.Attribute;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.Constraint;
import pt.ist.socialsoftware.blendedworkflow.designer.blendedWorkflow.Entity;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.AttributeDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.DependenceDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.EntityDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.ExpressionDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.ProductDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.RelationDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.RuleDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.SpecDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.repository.BWNotification;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.repository.RepositoryException;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.repository.RepositoryInterface;

public class WriteBlendedWorkflowService {
	private static Logger logger = LoggerFactory.getLogger(WriteBlendedWorkflowService.class);

	private static WriteBlendedWorkflowService instance = null;

	public static WriteBlendedWorkflowService getInstance() {
		logger.debug("getInstance");
		if (instance == null) {
			instance = new WriteBlendedWorkflowService();
		}
		return instance;
	}

	// to be invoked by tests only
	public void deleteSpecification(String specId) {
		// logger.debug("deleteSpecification {}", specId);
		this.repository.deleteSpecification(specId);
	}

	private RepositoryInterface repository = null;

	private WriteBlendedWorkflowService() {
		this.repository = RepositoryInterface.getInstance();
	}

	public BWNotification write(BWSpecification eBWSpecification) {
		String specId = eBWSpecification.getSpecification().getName().replaceAll("\\s+", "");
		// logger.debug("eBWSpecification: {}", specId);

		BWNotification notification = new BWNotification();

		try {
			this.repository.getSpecBySpecId(specId);
			this.repository.cleanBlendedWorkflowModel(specId);
		} catch (RepositoryException re) {
			// logger.debug("getSpec: {}", re.getMessage());
			try {
				this.repository.createSpec(new SpecDTO(specId, eBWSpecification.getSpecification().getName()));
			} catch (RepositoryException ree) {
				notification.addError(ree.getError());
				// logger.debug("createSpec: {}", re.getMessage());
				return notification;
			}
		}

		Set<RuleDTO> rulesToCreate = new HashSet<>();

		for (Entity eEnt : eBWSpecification.getEntities()) {
			String entityExtId = null;
			try {
				EntityDTO entityDTO = this.repository
						.createEntity(new EntityDTO(specId, eEnt.getName(), eEnt.isExists()));
				entityExtId = entityDTO.getExtId();
				// logger.debug("createdEntity: {}, {}, {}",
				// entityDTO.getExtId(), entityDTO.getName(),
				// entityDTO.getExists());
			} catch (RepositoryException re) {
				notification.addError(re.getError());
				// logger.debug("createEntity: {}", re.getMessage());
				continue;
			}
			// create entity dependences
			for (String eDep : eEnt.getDependsOn()) {
				try {
					this.repository.createDependence(new DependenceDTO(specId, eEnt.getName(), eDep));
				} catch (RepositoryException re) {
					notification.addError(re.getError());
					// logger.debug("Error: {}", re.getMessage());
				}
			}

			for (EObject eObj : eEnt.getAttributes()) {
				if (eObj instanceof Attribute) {
					AttributeDTO attributeDTO;
					Attribute eAtt = (Attribute) eObj;
					try {
						attributeDTO = this.repository.createAttribute(
								new AttributeDTO(specId, ProductDTO.ProductType.ATTRIBUTE.name(), entityExtId,
										eEnt.getName(), eAtt.getName(), eAtt.getType(), eAtt.isMandatory()));
					} catch (RepositoryException re) {
						notification.addError(re.getError());
						// logger.debug("Error: {}", re.getMessage());
						continue;
					}

					// create attribute dependences
					for (String eDep : eAtt.getDependsOn()) {
						try {
							this.repository.createDependence(new DependenceDTO(specId,
									attributeDTO.getEntityName() + "." + attributeDTO.getName(), eDep));
						} catch (RepositoryException re) {
							notification.addError(re.getError());
							// logger.debug("Error: {}", re.getMessage());
						}
					}
				}
			}

			for (Constraint constraint : eEnt.getConstraint()) {
				ExpressionDTO expression = ExpressionDTO.buildExpressionDTO(specId, constraint.getConstraint());
				rulesToCreate.add(new RuleDTO(specId, eEnt.getName(), constraint.getName(), expression));
			}

		}

		for (Association assoc : eBWSpecification.getAssociations()) {
			try {
				this.repository.createRelation(new RelationDTO(specId, assoc.getName(), assoc.getEntity1().getName(),
						assoc.getName1(), assoc.getCardinality1(), assoc.getEntity2().getName(), assoc.getName2(),
						assoc.getCardinality2()));
			} catch (RepositoryException re) {
				notification.addError(re.getError());
				// logger.debug("Error: {}", re.getMessage());
			}
		}

		for (RuleDTO ruleDTO : rulesToCreate) {
			try {
				this.repository.createRule(ruleDTO);
			} catch (RepositoryException re) {
				notification.addError(re.getError());
				// logger.debug("Error: {}", re.getMessage());
			}
		}

		Set<DependenceDTO> deps = this.repository.getDependencies(specId);
		for (DependenceDTO dep : deps) {
			// logger.debug("dependence extId:{}, path:{}, product:{}",
			// dep.getExtId(), dep.getPath(), dep.getProduct());
			try {
				this.repository.checkDependence(specId, dep.getExtId());
			} catch (RepositoryException re) {
				notification.addError(re.getError());
				// logger.debug("Error: {}", re.getMessage());

				this.repository.deleteDependence(specId, dep.getExtId());
			}
		}

		try {
			this.repository.checkBlendedWorkflowModel(specId);
		} catch (RepositoryException re) {
			notification.addError(re.getError());
		}

		this.repository.printSpecificationModels(specId);

		if (!notification.hasErrors()) {
			this.repository.generateConditionModel(specId);

			this.repository.generateGoalModel(specId);

			this.repository.generateActivityModel(specId);
		}

		return notification;
	}
}
