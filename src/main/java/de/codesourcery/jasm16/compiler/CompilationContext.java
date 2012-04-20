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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.codesourcery.jasm16.compiler.ICompiler.CompilerOption;
import de.codesourcery.jasm16.compiler.io.IObjectCodeWriter;
import de.codesourcery.jasm16.compiler.io.IObjectCodeWriterFactory;
import de.codesourcery.jasm16.compiler.io.IResource;
import de.codesourcery.jasm16.compiler.io.IResourceResolver;
import de.codesourcery.jasm16.exceptions.ResourceNotFoundException;

/**
 * Default {@link ICompilationContext} implementation.
 * 
 * @author tobias.gierke@code-sourcery.de
 */
public class CompilationContext implements ICompilationContext {

	private final ICompilationUnit currentUnit;
	private final ISymbolTable symbolTable;
	private final IObjectCodeWriterFactory writerFactory;
    private final List<ICompilationUnit> allCompilationUnits;
    private final IResourceResolver resourceResolver;
    private final Set<CompilerOption> options= new HashSet<CompilerOption>();
    
    public CompilationContext(ICompilationUnit unit,
    		ISymbolTable symbolTable,
    		IObjectCodeWriterFactory writerFactory,
    		IResourceResolver resourceResolver,
    		Set<CompilerOption> options) throws IOException
    {
        this( unit, Collections.singletonList( unit ) , symbolTable , writerFactory , resourceResolver , options );
    }
    
	public CompilationContext(ICompilationUnit unit,List<ICompilationUnit> allCompilationUnits,
			ISymbolTable symbolTable,IObjectCodeWriterFactory writerFactory,IResourceResolver resourceResolver,
			Set<CompilerOption> options) throws IOException
	{
		if (unit == null) {
			throw new IllegalArgumentException("unit must not be NULL");
		}
		if ( writerFactory == null ) {
            throw new IllegalArgumentException("writerFactory must not be NULL.");
        }
		if ( symbolTable == null ) {
            throw new IllegalArgumentException("symbolTable must not be NULL.");
        }
		if ( allCompilationUnits == null ) {
            throw new IllegalArgumentException("allCompilationUnits must not be NULL.");
        }
		if ( resourceResolver == null ) {
            throw new IllegalArgumentException("resourceResolver must not be NULL.");
        }
		if ( options == null ) {
			throw new IllegalArgumentException("options must not be NULL");
		}
		this.resourceResolver = resourceResolver;
		this.allCompilationUnits = new ArrayList<ICompilationUnit>( allCompilationUnits );
		this.symbolTable = symbolTable;
		this.writerFactory = writerFactory;
		this.currentUnit = unit;
		this.options.addAll( options );
	}
	
	@Override
	public ISymbolTable getSymbolTable() {
		return symbolTable;
	}

	@Override
	public ICompilationUnit getCurrentCompilationUnit() {
		return currentUnit;
	}

    protected IObjectCodeWriter createObjectCodeWriter()
    {
        return writerFactory.getWriter( this );
    }
    
    @Override
    public IObjectCodeWriterFactory getObjectCodeWriterFactory()
    {
        return writerFactory;
    }

    @Override
    public List<ICompilationUnit> getAllCompilationUnits()
    {
        return Collections.unmodifiableList( this.allCompilationUnits );
    }

    @Override
    public IResource resolve(String identifier) throws ResourceNotFoundException
    {
        return resourceResolver.resolve( identifier );
    }

    @Override
    public IResource resolveRelative(String identifier, IResource parent) throws ResourceNotFoundException
    {
        return resourceResolver.resolveRelative( identifier , parent );
    }

	@Override
	public boolean hasCompilerOption(CompilerOption option) 
	{
		if (option == null) {
			throw new IllegalArgumentException("option must not be NULL");
		}
		return options.contains( option );
	}
}
