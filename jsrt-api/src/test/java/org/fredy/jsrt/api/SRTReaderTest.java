/* 
 * Copyright 2012 Fredy Wijaya
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.fredy.jsrt.api;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;

import org.fredy.jsrt.api.InvalidSRTException;
import org.fredy.jsrt.api.SRT;
import org.fredy.jsrt.api.SRTInfo;
import org.fredy.jsrt.api.SRTReader;
import org.fredy.jsrt.api.SRTReaderException;
import org.fredy.jsrt.api.SRTTimeFormat;
import org.junit.Test;

/**
 * @author fredy
 */
public class SRTReaderTest {

   @Test
   public void testRead() throws Exception {
       SRTInfo info = SRTReader.read(new File("src/test/resources/good1.srt"));
       
       assertEquals(2, info.size());
       Iterator<SRT> iter = info.iterator();
       SRT srt = iter.next();
       assertEquals(1, srt.number);
       assertEquals("Hello World", srt.text.get(0));
       assertEquals("00:00:20,000", SRTTimeFormat.format(srt.startTime));
       assertEquals("00:00:24,400", SRTTimeFormat.format(srt.endTime));
       assertEquals("Bye World", srt.text.get(1));
       
       srt = iter.next();
       assertEquals(2, srt.number);
       assertEquals("00:00:24,600", SRTTimeFormat.format(srt.startTime));
       assertEquals("00:00:27,800", SRTTimeFormat.format(srt.endTime));
       assertEquals("Foo Bar", srt.text.get(0));
       assertEquals("Bar Foo", srt.text.get(1));
   }
   
   @Test(expected = SRTReaderException.class)
   public void testReadFileDoesntExist() {
       SRTReader.read(new File("foo.srt"));
   }
   
   @Test(expected = SRTReaderException.class)
   public void testReadIsNotAFile() {
       SRTReader.read(new File("."));
   }
   
   /*
    * Additional newline after subtitle number 1
    */
   @Test(expected = InvalidSRTException.class)
   public void testReadInvalidSRT1() {
       SRTReader.read(new File("src/test/resources/bad1.srt"));
   }
   
   /*
    * Missing start time and date time information
    */
   @Test(expected = InvalidSRTException.class)
   public void testReadInvalidSRT2() {
       SRTReader.read(new File("src/test/resources/bad2.srt"));
   }
   
   /*
    * Invalid start time and date time information
    */
   @Test(expected = InvalidSRTException.class)
   public void testReadInvalidSRT3() {
       SRTReader.read(new File("src/test/resources/bad3.srt"));
   }
   
   /*
    * Missing subtitle text
    */
   @Test(expected = InvalidSRTException.class)
   public void testReadInvalidSRT4() {
       SRTReader.read(new File("src/test/resources/bad4.srt"));
   }
}
