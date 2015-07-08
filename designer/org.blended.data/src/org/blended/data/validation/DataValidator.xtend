/*
 * generated by Xtext
 */
package org.blended.data.validation

import org.blended.data.data.DataPackage
import org.blended.data.data.Entity
import org.blended.data.data.Specification
import org.eclipse.xtext.validation.Check
import pt.ist.socialsoftware.blendedworkflow.service.BWException
import pt.ist.socialsoftware.blendedworkflow.service.design.DesignInterface

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class DataValidator extends AbstractDataValidator {
//UUID for entity Patient2: 5cb85a9e-bb36-4154-96fe-2e15cacb4033
//UUID for entity Episode: f310f08f-f539-4247-a03d-ad3f6adf0473
//UUID for entity Prescription: 3995c35c-74b8-4e83-b994-8081541b1da4
//UUID for entity Data: c5e920ae-bcc3-45ef-9bc2-c3842b022261
//UUID for entity Report: 88260bd8-197d-487d-b44b-eaa5c59f2b4c
//UUID for entity Medication: d85acf7a-dd49-4667-b44b-f5de1dad4472


  	public static val INVALID_NAME = 'invalidName'
  	
  	@Check
  	def String checkTest(Entity entity) {
  		//if (entity.uid == null) {
  		//	entity.uid = entity.hashCode().toString
  		//	System.out.println("UUID for entity " + entity.name + ": " + entity.uid)
  		//}
  		//else System.out.println("UUID for entity " + entity.name + "is already assigned with value: " + entity.uid)
  		System.out.println("UUID for entity " + entity.name + ": " + entity.hashCode)
  		return "helloo"
  	}

	@Check
	def check(Specification specification) {
		warning('AAAA', DataPackage.Literals.SPECIFICATION__NAME, INVALID_NAME)

		var instance = DesignInterface.getInstance
		
		try {
			instance.createSpecification(specification.name)
			
		}
		catch (BWException ex) {
			if (ex.error.equals(BWException.BlendedWorkflowError.INVALID_SPECIFICATION_NAME)) {
				error('Specification with the same name already exists', DataPackage.Literals.SPECIFICATION__NAME, INVALID_NAME)
			}
		}
	}
}
