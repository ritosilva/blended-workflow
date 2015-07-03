/*
 * generated by Xtext
 */
package org.blended.data.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.IFileSystemAccess
/*import org.blended.condition.ConditionStandaloneSetup
import org.eclipse.xtext.util.StringInputStream
import org.blended.condition.condition.ConditionModel
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.SaveOptions
import static extension org.eclipse.xtext.EcoreUtil2.**/

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class DataGenerator implements IGenerator {
	
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		var conditionModel = new DataGeneratorConditionModel(resource, fsa)
		conditionModel.doGenerate	
		
	/* 	new org.eclipse.emf.mwe.utils.StandaloneSetup()
		var file = resource.normalizedURI.toString.replace(".dm", ".cm")
		
		//file = resource.normalizedURI.lastSegment.replace(".dm", ".cm")
		var injector = new ConditionStandaloneSetup().createInjectorAndDoEMFRegistration()
		var resourceSet = injector.getInstance(typeof(XtextResourceSet))
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE)
		var r = resourceSet.getResource(URI.createURI(file), true)

		var builder = SaveOptions.newBuilder()
		builder.noValidation
		builder.format
		r.save(builder.options.toOptionsMap)
		//var model = (Model) resource.getContents().get(0)
		//System.out.println(r.getClass().toString())*/
	}
}