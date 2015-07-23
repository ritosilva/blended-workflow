/*
 * generated by Xtext
 */
package org.blended.data.validation

import org.blended.common.common.Attribute
import org.blended.common.common.CommonPackage
import org.blended.data.data.DataModel
import org.blended.data.data.DataPackage
import org.blended.data.repinterface.DataInterface
import org.eclipse.xtext.validation.Check

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.eclipse.xtext.validation.CheckType

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class DataValidator extends AbstractDataValidator {
	public static val INVALID_NAME = 'invalidName'

	@Check
	def checkModel(Attribute att) {
		if ((!att.type.equals("String")) && (!att.type.equals("Boolean")) && (!att.type.equals("Number")) &&
			(!att.type.equals("Date"))) {
			error("Invalid Data Type", CommonPackage.Literals.ATTRIBUTE__TYPE)
		}
	}

	@Check(CheckType.NORMAL)
	def checkModel(DataModel model) {
		var instance = DataInterface.getInstance
		var specId = model.eResource.normalizedURI.lastSegment.split("\\.").get(0)
		var notification = instance.loadDataModel(specId, model)
		if (notification.hasErrors)
			for (error : notification.error)
				error(error.type.toString + "-" + error.value, DataPackage.Literals.DATA_MODEL__SPECIFICATION,
					INVALID_NAME)
		else
			info('everything OK 2', DataPackage.Literals.DATA_MODEL__SPECIFICATION)
	}

}
