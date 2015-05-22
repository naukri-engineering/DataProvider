Data Provider
================

It is a library that helps write data driven unit test cases in Android applications. The data source is a .xls file in which input parameter values and expected results are defined.

**Sample Usage**

```Java	
import com.naukri.provider.DataProviderTestCase;
import com.naukri.provider.DataProvider;
import com.naukri.provider.DataProviderHelper.TestCaseIO;
import com.naukri.provider.Source;

@Source(fileName = "testcases.xls")
public class SampleTest extends DataProviderTestCase {

	@DataProvider(label = "test1", inputType = { String.class,String.class }, outPutType = String.class)
    public void testConcatenate(TestCaseIO testCaseIO) {
        MyUnit myUnit = new MyUnit();
        String result = myUnit.concatenate((String) testCaseIO.input.get(0), (String) testCaseIO.input.get(1));
        assertEquals(testCaseIO.output, result);
    }
}
```
**Excel Structure**

 |  |  |  |
--- | --- | --- | ---
test1| one | two |onetwo
test1| two | one |twoone
test1| three | two |threetwo
test1| four | two |fourtwo

Contact Us
===========
engineering@naukri.com

Contributors
=============
Gaurav Kumar
Minni Arora

LICENSE
========
Copyright(c) 2015 Naukri.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

ACKNOWLEDGEMENT
===============
This product includes software developed by the Apache Software Foundation (http://www.apache.org/)
The included software is Apache POI downloaded from https://poi.apache.org/ which is distributed under The Apache Software License.
