package pt.ist.socialsoftware.blendedworkflow.designer.remote.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.PersonDTO;
import pt.ist.socialsoftware.blendedworkflow.designer.remote.dto.RelationDTO;

public class ResourceModelInterface {
	private static Logger logger = LoggerFactory.getLogger(ResourceModelInterface.class);

	final static String BASE_URL = "http://localhost:8080";

	private static ResourceModelInterface instance = null;

	public static ResourceModelInterface getInstance() {
		if (instance == null) {
			instance = new ResourceModelInterface();
		}
		return instance;
	}
	
	public static void cleanResourceModel(String specId) {
		
	}
	
	public PersonDTO createPerson (PersonDTO person, BWNotification notification) {
		logger.debug("createPerson: {}, {}", person.getName(), person.getBody());

		final String uri = BASE_URL + "/specs/{specId}/resourcemodel/persons";

		Map<String, String> params = new HashMap<>();
		params.put("specId", person.getSpecId());

		RestTemplate restTemplate = RestUtil.getRestTemplate();
		PersonDTO result = null;
		try {
			result = restTemplate.postForObject(uri, person, PersonDTO.class, params);
		} catch (RestClientException rce) {
			notification.addError(new BWError("REST connection", rce.getMessage()));
		} catch (Exception e) {
			notification.addError(new BWError("HTTP Error", "There was an error in the HTTP connection."));
		}
		
		return result;
	}
	

}
