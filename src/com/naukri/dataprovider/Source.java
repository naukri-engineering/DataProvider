package com.naukri.dataprovider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author gaurav.kumar
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Source {

	String fileName();

}
