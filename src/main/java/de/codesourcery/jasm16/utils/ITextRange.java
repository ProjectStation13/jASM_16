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
package de.codesourcery.jasm16.utils;

import java.util.List;

/**
 * A block of text, identified by it's starting offset and length.
 * 
 * <p>Implementations of this interface are heavily used
 * throughout the compiler to associate AST nodes with
 * the source code they came from.</p>
 * 
 * @author tobias.gierke@code-sourcery.de
 */
public interface ITextRange
{
	/**
	 * Returns the starting offset of this text block.
	 * @return
	 */
    public int getStartingOffset(); 
    
    /**
     * Returns the end offset of this text block.
     * 
     * @return end offset ( starting offset + length )
     */
    public int getEndOffset(); 
    
    /**
     * Returns the length of this text block in characters.
     * 
     * @return
     */
    public int getLength(); 
    
    /**
     * Calculates the union of this text block with another.
     * 
     * @param other
     */
    public void merge(ITextRange other); 
    
    /**
     * Calculates the union of this text block with several others.
     * @param ranges
     */
    public void merge(List<? extends ITextRange> ranges);
    
    /**
     * Calculates the intersection of this text block with another.
     * 
     * @param other text block to calculate intersection with
     * @throws IllegalArgumentException if <code>other</code> is <code>null</code>
     * or does not overlap with this text range at all.
     */
    public void intersect(ITextRange other) throws IllegalArgumentException;
    
    /**
     * Subtracts another text range from this one.
     * 
     * <p>
     * Note that this method (intentionally) does <b>not</b> handle
     * intersections where the result would actually be two non-adjactent
     * regions of text.</p>     
     * @param other
     * @throws UnsupportedOperationException
     */
    public void subtract(ITextRange other) throws UnsupportedOperationException;  
    
    /**
     * Check whether this text block is the same as another.
     * 
     * @param other
     * @return <code>true</code> if this block as the same length and starting offset
     * as the argument
     */
    public boolean isSame(ITextRange other); 
    
    /**
     * Check whether this text block fully contains another region.
     * @param other
     * @return
     */
    public boolean contains(ITextRange other); 
    
    /**
     * Check whether this text block overlaps with another.
     * 
     * @param other
     * @return
     */
    public boolean overlaps(ITextRange other);
    
    /**
     * Check whether this text block covers a given offset.
     * @param offset
     * @return
     */
    public boolean contains(int offset);
    
    /**
     * Extract the region denoted by this text block from a string.
     * 
     * @param string
     * @return
     */
    public String apply(String string); 
}
