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
package de.codesourcery.jasm16.compiler;

import de.codesourcery.jasm16.ast.ASTNode;
import de.codesourcery.jasm16.utils.ITextRange;
import de.codesourcery.jasm16.utils.TextRange;

/**
 * A compilation error that includes a source-code reference.
 * 
 * @author tobias.gierke@code-sourcery.de
 */
public class CompilationError extends GenericCompilationError 
{
	public CompilationError(String message, ICompilationUnit unit , ASTNode node) 
	{
	    super(IMarker.TYPE_COMPILATION_ERROR, message,unit);
		setNodeAndLocation(node,null);
	}
	
	private void setNodeAndLocation(ASTNode node,ITextRange range) 
	{
	    if ( node != null ) 
	    {
	        setAttribute(IMarker.ATTR_AST_NODE , node );
	        if ( range == null && node.getTextRange() != null ) 
	        {
	            setLocation( new TextRange( node.getTextRange() ) );
	            if ( ! hasAttribute( IMarker.ATTR_SRC_OFFSET ) ) {
	                setErrorOffset( node.getTextRange().getStartingOffset() );
	            }	            
	        }
	    } 
	    
	    if ( range != null ) 
	    {
	        setLocation( new TextRange( range ) );
            if ( ! hasAttribute( IMarker.ATTR_SRC_OFFSET ) ) {
                setErrorOffset( range.getStartingOffset() );
            }   	        
	    }
	}
	
	public CompilationError(String message, ICompilationUnit unit , ASTNode node,Throwable cause) 
	{
        super(IMarker.TYPE_COMPILATION_ERROR, message,unit,cause);
        setNodeAndLocation(node,null);	    
	}	
	
	public CompilationError(String message, ICompilationUnit unit ,ITextRange location) 
	{
        super(IMarker.TYPE_COMPILATION_ERROR, message,unit);
        setNodeAndLocation(null,location);
	}
	
	public CompilationError(String message, ICompilationUnit unit , ITextRange location,Throwable cause) 
	{
        super(IMarker.TYPE_COMPILATION_ERROR, message,unit,cause);
        setNodeAndLocation(null,location);
	}	
		
}
