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

/* $Id: XMPParserTest.java 606564 2007-12-23 15:42:28Z jeremias $ */

package org.apache.xmlgraphics.xmp;

import java.net.URL;

import junit.framework.TestCase;

import org.apache.xmlgraphics.xmp.schemas.DublinCoreAdapter;
import org.apache.xmlgraphics.xmp.schemas.DublinCoreSchema;
import org.apache.xmlgraphics.xmp.schemas.XMPBasicAdapter;
import org.apache.xmlgraphics.xmp.schemas.XMPBasicSchema;
import org.apache.xmlgraphics.xmp.schemas.pdf.AdobePDFAdapter;
import org.apache.xmlgraphics.xmp.schemas.pdf.AdobePDFSchema;

/**
 * Tests for the XMP parser.
 */
public class XMPParserTest extends TestCase {

    public void testParseBasics() throws Exception {
        URL url = getClass().getResource("test-basics.xmp");
        Metadata meta = XMPParser.parseXMP(url);
        
        DublinCoreAdapter dcAdapter = DublinCoreSchema.getAdapter(meta);
        XMPBasicAdapter basicAdapter = XMPBasicSchema.getAdapter(meta);
        AdobePDFAdapter pdfAdapter = AdobePDFSchema.getAdapter(meta);
        
        XMPProperty prop;
        prop = meta.getProperty(XMPConstants.DUBLIN_CORE_NAMESPACE, "creator");
        XMPArray array;
        array = prop.getArrayValue();
        assertEquals(1, array.getSize());
        assertEquals("John Doe", array.getValue(0).toString());
        assertEquals("John Doe", dcAdapter.getCreators()[0]);
               
        prop = meta.getProperty(XMPConstants.DUBLIN_CORE_NAMESPACE, "title");
        assertEquals("Example document", prop.getValue().toString());
        assertEquals("Example document", dcAdapter.getTitle());
        prop = meta.getProperty(XMPConstants.XMP_BASIC_NAMESPACE, "CreateDate");
        //System.out.println("Creation Date: " + prop.getValue() + " " + prop.getClass().getName());
        prop = meta.getProperty(XMPConstants.XMP_BASIC_NAMESPACE, "CreatorTool");
        assertEquals("An XML editor", prop.getValue().toString());
        assertEquals("An XML editor", basicAdapter.getCreatorTool());
        prop = meta.getProperty(XMPConstants.ADOBE_PDF_NAMESPACE, "Producer");
        assertEquals("Apache FOP Version SVN trunk", prop.getValue().toString());
        assertEquals("Apache FOP Version SVN trunk", pdfAdapter.getProducer());
        prop = meta.getProperty(XMPConstants.ADOBE_PDF_NAMESPACE, "PDFVersion");
        assertEquals("1.4", prop.getValue().toString());
        assertEquals("1.4", pdfAdapter.getPDFVersion());
    }
    
    public void testParse1() throws Exception {
        URL url = getClass().getResource("unknown-schema.xmp");
        Metadata meta = XMPParser.parseXMP(url);
        
        DublinCoreAdapter dcAdapter = DublinCoreSchema.getAdapter(meta);
        
        XMPProperty prop;
        //Access through the known schema as reference
        prop = meta.getProperty(XMPConstants.DUBLIN_CORE_NAMESPACE, "title");
        assertEquals("Unknown Schema", prop.getValue().toString());
        assertEquals("Unknown Schema", dcAdapter.getTitle());
        
        //Access through a schema unknown to the XMP framework
        prop = meta.getProperty("http://unknown.org/something", "dummy");
        assertEquals("Dummy!", prop.getValue().toString());
    }
    
    public void testParseStructures() throws Exception {
        URL url = getClass().getResource("test-structures.xmp");
        Metadata meta = XMPParser.parseXMP(url);
        
        XMPProperty prop;
        
        String testns = "http://foo.bar/test/";
        prop = meta.getProperty(testns, "something");
        assertEquals("blablah", prop.getValue().toString());
        
        prop = meta.getProperty(testns, "ingredients");
        XMPArray array = prop.getArrayValue();
        assertEquals(3, array.getSize());
        XMPStructure struct = array.getStructure(0);
        assertEquals(2, struct.getPropertyCount());
        prop = struct.getValueProperty();
        assertEquals("Apples", prop.getValue());
        prop = struct.getProperty(testns, "amount");
        assertEquals("4", prop.getValue());
        
        prop = meta.getProperty(testns, "villain");
        XMPProperty prop1;
        prop1 = prop.getStructureValue().getProperty(testns, "name");
        assertEquals("Darth Sidious", prop1.getValue());
        prop1 = prop.getStructureValue().getProperty(testns, "other-name");
        assertEquals("Palpatine", prop1.getValue());
        
    }
    
}
