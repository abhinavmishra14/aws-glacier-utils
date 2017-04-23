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
package com.github.abhinavmishra14.aws.glacier.service.impl;

import static com.github.abhinavmishra14.aws.util.AWSUtilConstants.DISABLE_CERT_PARAM;
import static com.github.abhinavmishra14.aws.util.AWSUtilConstants.ERR_MSG_ACCESSKEY;
import static com.github.abhinavmishra14.aws.util.AWSUtilConstants.ERR_MSG_SECRETKEY;
import static com.github.abhinavmishra14.aws.util.AWSUtilConstants.TRUE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.glacier.transfer.UploadResult;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.github.abhinavmishra14.aws.glacier.service.GlacierArchiveService;
import com.github.abhinavmishra14.aws.util.AWSUtil;

/**
 * The Class GlacierArchiveServiceImpl.
 * 
 * @author Abhinav Kumar Mishra
 * @since 2017
 */
public class GlacierArchiveServiceImpl implements GlacierArchiveService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GlacierArchiveServiceImpl.class);

	/** The glacier client. */
	private final AmazonGlacierClient glacierClient;

	/** The sqs client. */
	private final AmazonSQSClient sqsClient;
	
	/** The sns client. */
	private final AmazonSNSClient snsClient;

	/**
	 * The Constructor.<b/>
	 * This Constructor will return glacier client if IAM role is enabled.<br/>
	 * Additionally it will set the given endPoint for performing upload operation over vault.<br/>
	 * Default endpoint will be always: "https://glacier.us-east-1.amazonaws.com/"
	 * SSL Certificate checking will be disabled based on provided flag.
	 *
	 * @param disableCertCheck the disable cert check
	 * @param endpoint the endpoint
	 */
	public GlacierArchiveServiceImpl(final boolean disableCertCheck,
			final String endpoint) {
		super();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GlacierArchiveServiceImpl is initializing using IAM Role..");
		}
		glacierClient = new AmazonGlacierClient(); //Get IAM Based glacier client
		sqsClient = new AmazonSQSClient();
		snsClient = new AmazonSNSClient();
		if(disableCertCheck) {
			System.setProperty(DISABLE_CERT_PARAM, TRUE);//Disable cert check
		}
		if(StringUtils.isNotBlank(endpoint)) {
			glacierClient.setEndpoint(endpoint);
		}
	}
	
	/**
	 * The Constructor.<b/>
	 * This Constructor will return glacier client if IAM role is enabled.<br/>
	 * SSL Certificate checking will be disabled based on provided flag.
	 *
	 * @param disableCertCheck the disable cert check
	 */
	public GlacierArchiveServiceImpl(final boolean disableCertCheck) {
		this(disableCertCheck, null);
	}
	

	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client if IAM role is enabled.<br/>
	 * Additionally it will set the given endPoint for performing upload operation over vault.<br/>
	 * Default endpoint will be always: "https://glacier.us-east-1.amazonaws.com/"
	 * SSL Certificate checking is by default disabled. 
	 *
	 * @param endpoint the end point
	 */
	public GlacierArchiveServiceImpl(final String endpoint) {
		this(true, endpoint);
	}
	
	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client if IAM role is enabled.<br/>
	 * SSL Certificate checking is by default disabled. 
	 */
	public GlacierArchiveServiceImpl() {
		this(true, null);
	}
	
	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client using accessKey and secretKey.<br/>
	 * Additionally it will set the given endPoint for performing upload operation over vault.<br/>
	 * Default endpoint will be always: "https://glacier.us-east-1.amazonaws.com/"
	 * SSL Certificate checking will be disabled based on provided flag.
	 *
	 * @param accessKey the access key
	 * @param secretKey the secret key
	 * @param disableCertCheck the disable cert check
	 * @param endpoint the endpoint
	 */
	public GlacierArchiveServiceImpl(final String accessKey,
			final String secretKey, final boolean disableCertCheck,
			final String endpoint) {
		super();
		AWSUtil.notNull(accessKey, ERR_MSG_ACCESSKEY);
		AWSUtil.notNull(secretKey, ERR_MSG_SECRETKEY);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GlacierArchiveServiceImpl is initializing using keys..");
		}
		final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		final AWSCredentialsProvider credentialsProvider = new StaticCredentialsProvider(credentials);
		glacierClient = new AmazonGlacierClient(credentialsProvider);
		sqsClient = new AmazonSQSClient(credentialsProvider);
		snsClient = new AmazonSNSClient(credentialsProvider);
		if(disableCertCheck) {
			System.setProperty(DISABLE_CERT_PARAM, TRUE);//Disable cert check
		}
		if(StringUtils.isNotBlank(endpoint)) {
			glacierClient.setEndpoint(endpoint);
		}
	}
	
	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client using accessKey and secretKey.<br/>
	 * SSL Certificate checking will be disabled based on provided flag.
	 *
	 * @param accessKey the access key
	 * @param secretKey the secret key
	 * @param disableCertCheck the disable cert check
	 */
	public GlacierArchiveServiceImpl(final String accessKey,
			final String secretKey, final boolean disableCertCheck) {
		this(accessKey, secretKey, disableCertCheck, null);
	}
		
	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client using accessKey and secretKey.<br/>
	 * Additionally it will set the given endPoint for performing upload operation over vault.<br/>
	 * Default endpoint will be always: "https://glacier.us-east-1.amazonaws.com/"<br/>
	 * SSL Certificate checking is by default disabled.
	 *
	 * @param accessKey the access key
	 * @param secretKey the secret key
	 * @param endpoint the endpoint
	 */
	public GlacierArchiveServiceImpl(final String accessKey,
			final String secretKey, final String endpoint) {
		this(accessKey, secretKey, true, endpoint);
	}
	
	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client using accessKey and secretKey.<br/>
	 * SSL Certificate checking is by default disabled.
	 *
	 * @param accessKey the access key
	 * @param secretKey the secret key
	 */
	public GlacierArchiveServiceImpl(final String accessKey,
			final String secretKey) {
		this(accessKey, secretKey, true, null);
	}
	
	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.aws.glacier.service.GlacierArchiveService#upload(java.lang.String, java.io.InputStream, java.lang.String)
	 */
	@Override
	public UploadResult archive(final String vaultName, final InputStream inputStream,
			final String archiveDescription) throws AmazonServiceException,
			AmazonClientException, IOException {
		LOGGER.info("Uploading archive to vault: {} with archiveDescription: {}", vaultName, archiveDescription);
		File tempFile = null;
		UploadResult uploadResult = null;
        try {
			// Create temporary file from stream to avoid 'out of memory' exception
			tempFile = AWSUtil.createTempFileFromStream(inputStream);
	        final ArchiveTransferManager archiveTrMgr = new ArchiveTransferManager(glacierClient, sqsClient, snsClient);
			uploadResult = archiveTrMgr.upload(vaultName, archiveDescription, tempFile);
		} finally {
			AWSUtil.deleteTempFile(tempFile); // Delete the temporary file once uploaded
		}
		return uploadResult;
	}

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.aws.glacier.service.GlacierArchiveService#upload(java.lang.String, java.io.File, java.lang.String)
	 */
	@Override
	public UploadResult archive(final String vaultName, final File inputFile,
			final String archiveDescription) throws AmazonServiceException,
			AmazonClientException, FileNotFoundException {
		LOGGER.info("Uploading archive file: {} to vault: {} with archiveDescription: {}", inputFile.getAbsolutePath(),
				vaultName, archiveDescription);
		final ArchiveTransferManager archiveTrMgr = new ArchiveTransferManager(glacierClient, sqsClient, snsClient);
		return archiveTrMgr.upload(vaultName, archiveDescription, inputFile);
	}
}
