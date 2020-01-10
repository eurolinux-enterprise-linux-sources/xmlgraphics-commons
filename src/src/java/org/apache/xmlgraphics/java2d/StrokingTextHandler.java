/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: $ */

package org.apache.xmlgraphics.java2d;

import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

/**
 * Default TextHandler implementation which paints text using graphics primitives (shapes). 
 */
public class StrokingTextHandler implements TextHandler {

    private AbstractGraphics2D g2d;
    
    /**
     * Main constructor
     * @param g2d a graphics 2d implementation
     */
    public StrokingTextHandler(AbstractGraphics2D g2d) {
        this.g2d = g2d;
    }
    
    /** {@inheritDoc} */
    public void drawString(String text, float x, float y) {
        java.awt.Font awtFont = g2d.getFont();
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = awtFont.createGlyphVector(frc, text);
        Shape glyphOutline = gv.getOutline(x, y);
        g2d.fill(glyphOutline);
    }
}
