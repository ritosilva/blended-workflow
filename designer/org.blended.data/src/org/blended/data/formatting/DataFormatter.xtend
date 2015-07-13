/*
 * generated by Xtext
 */
package org.blended.data.formatting

import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter
import org.eclipse.xtext.formatting.impl.FormattingConfig
import org.blended.data.services.DataGrammarAccess
import org.eclipse.xtext.Keyword

/**
 * This class contains custom formatting declarations.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#formatting
 * on how and when to use it.
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
class DataFormatter extends AbstractDeclarativeFormatter {

//	@Inject extension DataGrammarAccess
	
	override protected void configureFormatting(FormattingConfig c) {
		var DataGrammarAccess f = getGrammarAccess() as DataGrammarAccess;
		c.autoLinewrap = 130
			
		for (Keyword key : f.findKeywords("{")) {
			c.setLinewrap().after(key)
		}
		
		for (Keyword key : f.findKeywords("}")) {
			c.setLinewrap().before(key)
			c.setLinewrap(2).after(key)
		}
		
		for (Keyword key : f.findKeywords("(")) {
			c.setNoSpace().after(key)
		}
		
		
		for (Keyword key : f.findKeywords(")")) {
			c.setNoSpace().before(key)
			c.setLinewrap().after(key)
		}
		
		f.findKeywordPairs("{","}").forEach[
			c.setIndentation(first, second)
		]
		
		//c.setIndentationIncrement().before(f.attributeRule)
		//c.setIndentationDecrement().after(f.attributeRule)
		c.setLinewrap().before(f.attributeRule)
	
		//c.setIndentationIncrement().before(f.associationRule)
		//c.setIndentationDecrement().after(f.associationRule)
		c.setLinewrap().before(f.associationRule)
		
		c.setLinewrap(2).after(f.specificationRule)
	}
}
