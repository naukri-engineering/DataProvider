package com.naukri.dataprovider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.naukri.dataprovider.DataProviderHelper.TestCaseIO;

/**
 * 
 * @author gaurav.kumar
 *
 */
public class DataProviderTestCase extends AndroidTestCase {

	/**
	 * from the current class read the Source of file specified by Source
	 * annotation and invoke all the methods in current class, throws
	 * RunTimeException if current class have not specified the fileName via
	 * Source annotation
	 * 
	 * @throws JSONException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public void testAllCases() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		Class<? extends DataProviderTestCase> class1 = this.getClass();
		if (class1.isAnnotationPresent(Source.class)) {
			Source source = (Source) class1.getAnnotation(Source.class);
			String fileName = source.fileName();
			invokeAllMethod(fileName, class1);
		} else {
			throw new RuntimeException("Can not find Source");
		}
	}

	/**
	 * invoke all the method of the class1 for all the test cases in file
	 * specified by fileName,throws RunTimeException if executing method have
	 * not specified the input, output via DataProvider annotation
	 * 
	 * @param fileName
	 * @param class1
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private void invokeAllMethod(String fileName, Class class1)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		for (Method method : class1.getDeclaredMethods()) {
			Class[] parameterTypes = method.getParameterTypes();
			if (parameterTypes.length == 2
					&& parameterTypes[0] == TestCaseIO.class) {
				if (method.isAnnotationPresent(DataProvider.class)) {
					DataProvider parameter = (DataProvider) method
							.getAnnotation(DataProvider.class);
					invokeTestCase(fileName, parameter, method, class1);
				} else {
					throw new RuntimeException("Can not find DataProvider");
				}

			}
		}
	}

	/**
	 * invoke all the test cases on Method method passing a instance of
	 * TestCaseIO
	 * 
	 * @param fileName
	 * @param parameter
	 * @param method
	 * @param class1
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private void invokeTestCase(String fileName, DataProvider parameter,
			Method method, Class class1) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			InstantiationException {
		DataProviderHelper dataProviderHelper = new DataProviderHelper(this);
		List<TestCaseIO> testCaseIOs = dataProviderHelper.getTestCases(
				parameter.label(), fileName, parameter.outPutType(),
				parameter.inputType());
		for (TestCaseIO testCaseIO : testCaseIOs) {
			method.invoke(class1.newInstance(), testCaseIO, getContext());
		}
	}

}
