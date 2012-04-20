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

import de.codesourcery.jasm16.Address;
import de.codesourcery.jasm16.parser.Identifier;
import de.codesourcery.jasm16.utils.ITextRange;
import de.codesourcery.jasm16.utils.TextRange;

/**
 * A label that identifies a specific location in the
 * generated object code.
 * 
 * @author tobias.gierke@code-sourcery.de
 */
public class Label implements ISymbol {

	private final ICompilationUnit unit;
	private final Identifier identifier;
	private final ITextRange location;
	
	private Address address;
	
	public Label(ICompilationUnit unit , ITextRange location , Identifier identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException("identifier must not be NULL");
		}
		if ( location == null ) {
            throw new IllegalArgumentException("location must not be NULL.");
        }
		this.location = new TextRange( location );
		this.unit = unit;
		this.identifier = identifier;
	}
	
	public Identifier getIdentifier() {
		return identifier;
	}
	
	/**
	 * Returns the address associated with this label.
	 * 
	 * @return address or <code>null</code> if this label's address
	 * has not been resolved (yet).
	 */
	public Address getAddress()
    {
        return address;
    }
	
	/**
	 * Sets the address of this label.
	 * 
	 * @param address
	 */
	public void setAddress(Address address)
    {
        this.address = address;
    }
	
	@Override
	public ICompilationUnit getCompilationUnit() {
		return unit;
	}
	
	@Override
	public String toString() 
	{
	    if ( address != null ) {
	        return identifier+"("+address+" , "+unit+")";	        
	    }
		return identifier.toString();
	}

    @Override
    public ITextRange getLocation()
    {
        return location;
    }
	
}
