package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.service.dto.DependenceDTO;

public class Dependence extends Dependence_Base {
	private static Logger logger = LoggerFactory.getLogger(Dependence.class);

	@Override
	public void setPath(Path path) {
		checkPathPrefixLocal(path);
		super.setPath(path);
	}

	public Dependence(DataModel dataModel, Product product, String value) {
		setDataModel(dataModel);
		setProduct(product);
		Path path = new Path(dataModel, value);
		setPath(path);
	}

	private void checkPathPrefixLocal(Path path) {
		List<String> elements = Arrays.stream(path.getValue().split("\\.")).collect(Collectors.toList());
		if (elements.size() == 1) {
			throw new BWException(BWErrorType.INVALID_PATH, path.getValue());
		}

		if (!elements.get(0).equals(getProduct().getEntity().getName())) {
			throw new BWException(BWErrorType.INVALID_PATH, path.getValue()
					+ " requires to have the Entity name as prefix: " + getProduct().getEntity().getName());
		}
	}

	private void checkEntityDependsOnOwnAttribute() {
		if (getProduct() == getTarget().getEntity()) {
			throw new BWException(BWErrorType.DEPENDENCE_CIRCULARITY,
					getPath().getValue() + " entity depends on its attribute: " + getProduct().getName());
		}
	}

	public void checkPathPrefix() {
		checkPathPrefixLocal(getPath());
	}

	public boolean checkPath() {
		checkPathPrefix();
		checkEntityDependsOnOwnAttribute();
		return getPath().check();
	}

	public void delete() {
		setDataModel(null);
		setProduct(null);
		getPath().delete();

		deleteDomainObject();
	}

	public DependenceDTO getDTO() {
		DependenceDTO depDTO = new DependenceDTO();
		depDTO.setSpecId(getDataModel().getSpecification().getSpecId());
		depDTO.setExtId(getExternalId());
		depDTO.setProduct(getProduct().getFullPath());
		depDTO.setProductExtId(getProduct().getExternalId());
		depDTO.setPath(getPath().getValue());

		return depDTO;
	}

	public Product getTarget() {
		return getPath().getTarget();
	}

	public Set<Dependence> getDependenceTransitiveClosure(Set<Dependence> visited) {
		visited.addAll(getPath().getTarget().getDependenceSet());

		for (Dependence next : getPath().getTarget().getDependenceSet()) {
			if (!visited.contains(next)) {
				visited.addAll(next.getDependenceTransitiveClosure(visited));
			}
		}

		return visited;
	}

	public void checkDependenceCycle() {
		Product product = getProduct();
		Set<Product> visited = new HashSet<Product>();

		checkDependenceCycle(product, visited);
	}

	private void checkDependenceCycle(Product source, Set<Product> visited) {
		Product target = getTarget();
		if (target.cannotBeDefinedBefore(source)) {
			throw new BWException(BWErrorType.DEPENDENCE_CIRCULARITY, getPath().getValue());
		}

		if (!visited.contains(target)) {
			visited.add(target);
			for (Dependence next : target.getDependenceSet()) {
				next.checkDependenceCycle(source, visited);
			}
		}
	}

}
