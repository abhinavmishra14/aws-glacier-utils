/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2017. Abhinav Kumar Mishra. 
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.abhinavmishra14.aws.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * The Class AWSUtil.
 */
public final class AWSUtil {

	/**
	 * Instantiates a new AWS glacier util.
	 */
	private AWSUtil(){
		super();
	}
	
	/**
	 * Creates the temp file from stream.
	 *
	 * @param inputStream the in stream
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static File createTempFileFromStream(final InputStream inputStream)
			throws IOException {
		final File tempFile = File.createTempFile(AWSUtilConstants.TEMP_FILE_PREFIX, AWSUtilConstants.TEMP_FILE_SUFFIX);
		FileUtils.copyInputStreamToFile(inputStream, tempFile);    	
		return tempFile;
	}
	
	/**
	 * Delete temp file.
	 *
	 * @param tempFile the temp file
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean deleteTempFile(final File tempFile) throws IOException {
		boolean isDeleted = false;
		if (tempFile != null && tempFile.exists()) {
			isDeleted = tempFile.delete();
		}
		return isDeleted;
	}
	
	/**
	 * Not null.
	 *
	 * @param object the object
	 * @param message the message
	 */
	public static void notNull(final Object object, final String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		} else if (object instanceof String && StringUtils.isBlank(object.toString())) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Gets the expiry date.<br/>
	 * Expire by parameter takes input as Calendar.Month, Calendar.Hour, Calendar.Minute etc. 
	 *
	 * @param expireBy the expire by 
	 * @param expireByValue the expire by value
	 * @return the expiry date
	 * @see Calendar
	 */
	public static Date getExpiryDate(final int expireBy,
			final int expireByValue) {
		final Date currentDate = new Date();
	    final Calendar cal = Calendar.getInstance();
	    cal.setTime(currentDate);
	    cal.add(expireBy, expireByValue);
		return cal.getTime();
	}
}