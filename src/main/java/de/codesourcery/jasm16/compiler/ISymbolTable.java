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

import java.util.List;

import de.codesourcery.jasm16.exceptions.DuplicateSymbolException;
import de.codesourcery.jasm16.parser.Identifier;

/**
 * A symbol table.
 * 
 * @author tobias.gierke@code-sourcery.de
 */
public interface ISymbolTable {

	/**
	 * Look up a symbol by identifier.
	 * 
	 * @param identifier
	 * @return
	 */
	public ISymbol getSymbol(Identifier identifier);
	
	/**
	 * Returns all known symbols from this table.
	 * @return
	 */
	public List<ISymbol> getSymbols();
	
	/**
	 * Define a new symbol.
	 * 
	 * @param symbol
	 * @throws DuplicateSymbolException if this symbol already exists.
	 */
	public void defineSymbol(ISymbol symbol) throws DuplicateSymbolException;		
	
	/**
	 * Check whether there is a symbol with a given identifier.
	 * 
	 * @param identifier
	 * @return
	 */
	public boolean containsSymbol(Identifier identifier);
	
	/**
	 * Removes all symbols from this symbol table.
	 * 
	 */
	public void clear();
}