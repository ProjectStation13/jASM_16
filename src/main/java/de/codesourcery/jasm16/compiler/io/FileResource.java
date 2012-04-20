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
package de.codesourcery.jasm16.compiler.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.codesourcery.jasm16.utils.ITextRange;
import de.codesourcery.jasm16.utils.Misc;

/**
 * {@link IResource} implementation that wraps a string.
 * 
 * @author tobias.gierke@code-sourcery.de
 */
public class FileResource implements IResource
{
    private final File file;
    
    private String contents;
    
    public FileResource(File file)
    {
        if (file == null) {
            throw new IllegalArgumentException("file must not be NULL.");
        }
        this.file = file;
    }

    @Override
    public InputStream createInputStream() throws IOException
    {
        loadContents();
        return new ByteArrayInputStream( contents.getBytes() );
    }


    @Override
    public OutputStream createOutputStream(boolean append) throws IOException
    {
        return new FileOutputStream( file , append);
    }

    private String loadContents() throws IOException {
        if ( contents == null ) {
            final FileInputStream inputStream = new FileInputStream( file );
            try {
                contents = Misc.readSource( inputStream );
            } finally {
                try { inputStream.close(); } catch (Exception e) { /* ok */ }
            }
        }
        return contents;
    }
    
    @Override
    public String readText(ITextRange range) throws IOException
    {
        loadContents();
        return range.apply( contents );
    }
    
    @Override
    public String toString()
    {
        return file.getAbsolutePath();
    }

    @Override
    public long getAvailableBytes() throws IOException
    {
        return file.length();
    }

    public File getFile()
    {
        return file;
    }

}
