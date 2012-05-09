/**
 * Copyright 2012 Tobias Gierke <tobias.gierke@code-sourcery.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.codesourcery.jasm16.ast;

import java.io.IOException;

import de.codesourcery.jasm16.compiler.CompilationError;
import de.codesourcery.jasm16.compiler.io.IResource;
import de.codesourcery.jasm16.compiler.io.IResource.ResourceType;
import de.codesourcery.jasm16.exceptions.ParseException;
import de.codesourcery.jasm16.exceptions.ResourceNotFoundException;
import de.codesourcery.jasm16.lexer.IToken;
import de.codesourcery.jasm16.lexer.TokenType;
import de.codesourcery.jasm16.parser.IParseContext;
import de.codesourcery.jasm16.parser.IParser.ParserOption;

/**
 * The '.include' AST node.
 * 
 * @author tobias.gierke@code-sourcery.de
 */
public class IncludeSourceFileNode extends ASTNode {

	private String resourceIdentifier; 
	private IResource resource;
	
	@Override
	public ASTNode copySingleNode() 
	{
		final IncludeSourceFileNode result = new IncludeSourceFileNode();
		result.resource = resource;
		result.resourceIdentifier = resourceIdentifier;
		return result;
	}
	
	@Override
	public ASTNode createCopy(boolean shallow) {
		return super.createCopy(true);
	}

	public IResource getResource() {
		return resource;
	}
	
	@Override
	protected ASTNode parseInternal(IParseContext context) throws ParseException 
	{
		mergeWithAllTokensTextRegion( context.read( TokenType.INCLUDE_SOURCE ) );
		if ( context.peek().isWhitespace() ) {
			mergeWithAllTokensTextRegion( context.read( TokenType.WHITESPACE ) );
		}
		if ( ! context.peek().hasType(TokenType.STRING_DELIMITER ) ) {
			throw new ParseException("Expected a file name" , context.currentParseIndex() , 0 );
		}
		
		mergeWithAllTokensTextRegion( context.read( TokenType.STRING_DELIMITER ) );
		if ( ! context.peek().hasType(TokenType.CHARACTERS) ) {
			throw new ParseException("Expected a file name" , context.currentParseIndex() , 0 );
		}		
		final IToken tok = context.read( TokenType.CHARACTERS );
		this.resourceIdentifier = tok.getContents();
		
		mergeWithAllTokensTextRegion( tok );
		try {
			resource = context.resolveRelative( tok.getContents() , context.getCompilationUnit().getResource(), ResourceType.SOURCE_CODE );
		} 
		catch (ResourceNotFoundException e) 
		{
			context.getCompilationUnit().addMarker(
					new CompilationError("File '"+tok.getContents()+"' does not exist", 
							context.getCompilationUnit() , tok )
			);
		}
		
		if ( ! context.hasParserOption( ParserOption.NO_SOURCE_INCLUDE_PROCESSING ) ) 
		{
			try {
				final IParseContext subContext = context.createParseContextForInclude( resource );
				
				final AST ast = (AST) new AST().parse( subContext );
				subContext.getCompilationUnit().setAST( ast );
				addChild( ast , context );
			} 
			catch (IOException e) 
			{
				context.getCompilationUnit().addMarker(
						new CompilationError("I/O error while including '"+tok.getContents()+"' : "+e.getMessage(), 
								context.getCompilationUnit() , tok )
				);			
			}
		}
		
		mergeWithAllTokensTextRegion( context.read( TokenType.STRING_DELIMITER ) );
		
		return this;
	}

	@Override
	public boolean supportsChildNodes() {
		return true;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}
}