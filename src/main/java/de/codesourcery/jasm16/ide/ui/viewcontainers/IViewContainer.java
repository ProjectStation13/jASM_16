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
package de.codesourcery.jasm16.ide.ui.viewcontainers;

import java.util.List;

import de.codesourcery.jasm16.ide.ui.views.IView;

/**
 * A view container is a top-level component that may contain
 * one or multiple {@link IView}s.
 * 
 * @author tobias.gierke@code-sourcery.de
 */
public interface IViewContainer {

	/**
	 * Dispose this container with all view's 
	 * that are currently part of it.
	 * 
	 * <p>Invoking this method invokes
	 * <code>dispose()</code> on all child views.
	 * </p>
	 * @see IView#dispose()
	 */
	public void dispose();
	
	/**
	 * Adds a view to this container.
	 * 
	 * @param view
	 */
	public void addView(IView view);
	
	/**
	 * Removes a view from this container.
	 * 
	 * @param view
	 */
	public void removeView(IView view);

	/**
	 * Returns all views that are currently
	 * children of this container.
	 * 
	 * @return
	 */
	public List<IView> getViews();
}
