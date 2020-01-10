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

/* $Id: ImageProcessingHints.java 641214 2008-03-26 09:12:09Z jeremias $ */
 
package org.apache.xmlgraphics.image.loader;

/**
 * This interface defines some standard hints to be used for image processing in this package.
 * They are provided for convenience. You can define your own hints as you like.
 */
public interface ImageProcessingHints {

    /** Used to send a hint about the source resolution for pixel to unit conversions. */
    Object SOURCE_RESOLUTION = "SOURCE_RESOLUTION"; //Value: Number (unit dpi)
    /** Used to send a hint about the target resolution (of the final output format). */
    Object TARGET_RESOLUTION = "TARGET_RESOLUTION"; //Value: Number (unit dpi)
    
    /** Used to tell the image loader to ignore any color profile in the image. */
    Object IGNORE_COLOR_PROFILE = "IGNORE_COLOR_PROFILE"; //Value: Boolean
    
}
