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

/**
 * The Class AWSUtilConstants.
 */
public final class AWSUtilConstants {
	
	/** The Constant TEMP_FILE_PREFIX. */
	public static final String TEMP_FILE_PREFIX = "tempFile";
	
	/** The Constant TEMP_FILE_SUFFIX. */
	public static final String TEMP_FILE_SUFFIX = "glacierObject";
	
	/** The Constant SEPARATOR. */
	public static final String SEPARATOR = "/";
	
	/** The Constant ERR_MSG_ACCESSKEY. */
	public static final String ERR_MSG_ACCESSKEY = "AccessKey is null!";
	
	/** The Constant ERR_MSG_SECRETKEY. */
	public static final String ERR_MSG_SECRETKEY = "SecretKey is null!";
	
	/** The Constant ERR_MSG_PROP_FILE. */
	public static final String ERR_MSG_PROP_FILE = "Property file can not be null!";

	
	/** The Constant DISABLE_CERT_PARAM. */
	//-Dcom.amazonaws.sdk.disableCertChecking=true
	public static final String DISABLE_CERT_PARAM = "com.amazonaws.sdk.disableCertChecking";
	
	/** The Constant TRUE. */
	public static final String TRUE = "true";
	
	/** The Constant FALSE. */
	public static final String FALSE = "false";
	
	/** The Constant CLASSPATH_CREDENTIAL_PATH. */
	public static final String CLASSPATH_CREDENTIAL_PATH = "AwsCredentials.properties";

	/**
	 * Instantiates a new AWS Util Constants.
	 */
	private AWSUtilConstants(){
		super();
	}
}