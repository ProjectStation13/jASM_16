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
package de.codesourcery.jasm16.ide.ui.views;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.codesourcery.jasm16.emulator.IEmulationOptionsProvider;
import de.codesourcery.jasm16.emulator.IEmulator;
import de.codesourcery.jasm16.emulator.devices.impl.DefaultKeyboard;
import de.codesourcery.jasm16.emulator.devices.impl.DefaultScreen;

public class ScreenView extends AbstractView
{
    public static final String VIEW_ID="screen-view";
    
    private volatile JPanel panel;

    private volatile DefaultScreen screen = null;
    private final IEmulator emulator;
    private final DefaultKeyboard keyboard;
    private volatile boolean debugCustomFonts = false;
    private final IEmulationOptionsProvider optionsProvider;
    
    public ScreenView(IEmulationOptionsProvider optionsProvider, IEmulator emulator) 
    {
        if (emulator == null) {
            throw new IllegalArgumentException("emulator must not be NULL.");
        }
        this.emulator = emulator;
        this.optionsProvider = optionsProvider;
        this.keyboard = optionsProvider.getEmulationOptions().getKeyboard( emulator );
    }
    
    @Override
    public void disposeHook()
    {
    	if ( screen != null ) {
    		this.emulator.removeDevice( screen );
    		this.screen = null;
    	}
        this.emulator.removeDevice( keyboard );
    }

    public void setDebugCustomFonts(boolean debugCustomFonts)
    {
        this.debugCustomFonts = debugCustomFonts;
        if ( panel != null ) {
            panel.repaint();
        }
    }
    
    @Override
    public void refreshDisplay()
    {
    	if ( panel != null ) {
    		panel.repaint();
    	}
    }

    @Override
    public String getTitle()
    {
        return "Screen";
    }

    @Override
    public String getID()
    {
        return VIEW_ID;
    }
    
    @Override
    protected JPanel getPanel()
    {
    	if ( panel == null ) 
    	{
    		panel=createPanel();
    		screen = optionsProvider.getEmulationOptions().getScreen(emulator);
    		screen.attach( panel );
    		keyboard.setInputComponent( panel );
    	}
    	return panel;
    }

    protected JPanel createPanel()
    {
        panel = new JPanel() 
        {
        	public void paint(java.awt.Graphics g) 
             {
        		super.paint(g);
        		
        		final int height;
        		if ( debugCustomFonts ) {
        		    height = getHeight() / 2;
        		} else {
        		    height = getHeight();
        		}
        		
        		final BufferedImage original1 = screen.getScreenImage();
        		
                final Graphics2D g2 = (Graphics2D) g;
                
        		final Image scaled = original1.getScaledInstance( getWidth() , height  , Image.SCALE_FAST );
                g2.drawImage(  scaled , 0,0, null );
        		
                if ( debugCustomFonts ) {
                    BufferedImage original2 = screen.getFontImage();
                    final Image scaled2 = original2.getScaledInstance( getWidth() , height , Image.SCALE_FAST );
                    ((Graphics2D) g).drawImage(  scaled2 , 0, height , null );
                }
             }        	
        };
        
        panel.setDoubleBuffered( true );
        panel.setPreferredSize( new Dimension(400,200 ) );
        panel.setSize( new Dimension(400,200 ) );       
        panel.setFocusable( true );
        setColors( panel );
        return panel;
    }
}