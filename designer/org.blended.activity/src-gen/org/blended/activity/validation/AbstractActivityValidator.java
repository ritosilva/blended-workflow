/*
 * generated by Xtext
 */
package org.blended.activity.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;

public class AbstractActivityValidator extends org.eclipse.xtext.validation.AbstractDeclarativeValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<EPackage>();
	    result.add(org.blended.activity.activity.ActivityPackage.eINSTANCE);
		return result;
	}
}