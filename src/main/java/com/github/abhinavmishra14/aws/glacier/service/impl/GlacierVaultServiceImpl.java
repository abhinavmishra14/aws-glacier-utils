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

import java.util.List;

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
import com.amazonaws.services.glacier.model.CreateVaultRequest;
import com.amazonaws.services.glacier.model.CreateVaultResult;
import com.amazonaws.services.glacier.model.DeleteArchiveRequest;
import com.amazonaws.services.glacier.model.DeleteVaultRequest;
import com.amazonaws.services.glacier.model.DescribeVaultOutput;
import com.amazonaws.services.glacier.model.DescribeVaultRequest;
import com.amazonaws.services.glacier.model.DescribeVaultResult;
import com.amazonaws.services.glacier.model.ListVaultsRequest;
import com.amazonaws.services.glacier.model.ListVaultsResult;
import com.github.abhinavmishra14.aws.glacier.service.GlacierVaultService;
import com.github.abhinavmishra14.aws.util.AWSUtil;

/**
 * The Class GlacierVaultServiceImpl.
 * 
 * @author Abhinav Kumar Mishra
 * @since 2017
 */
public class GlacierVaultServiceImpl implements GlacierVaultService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GlacierVaultServiceImpl.class);

	/** The glacier client. */
	private final AmazonGlacierClient glacierClient;

	/**
	 * The Constructor.<b/>
	 * This Constructor will return glacier client if IAM role is enabled.<br/>
	 * Additionally it will set the given endPoint for performing operations over vault.<br/>
	 * Default endpoint will be always: "https://glacier.us-east-1.amazonaws.com/"
	 * SSL Certificate checking will be disabled based on provided flag.
	 *
	 * @param disableCertCheck the disable cert check
	 * @param endpoint the endpoint
	 */
	public GlacierVaultServiceImpl(final boolean disableCertCheck,
			final String endpoint) {
		super();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GlacierVaultServiceImpl is initializing using IAM Role..");
		}
		glacierClient = new AmazonGlacierClient(); //Get IAM Based glacier client
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
	public GlacierVaultServiceImpl(final boolean disableCertCheck) {
		this(disableCertCheck, null);
	}
	
	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client if IAM role is enabled.<br/>
	 * Additionally it will set the given endPoint for performing operations over vault.<br/>
	 * Default endpoint will be always: "https://glacier.us-east-1.amazonaws.com/"
	 * SSL Certificate checking is by default disabled. 
	 *
	 * @param endpoint the end point
	 */
	public GlacierVaultServiceImpl(final String endpoint) {
		this(true, endpoint);
	}
	
	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client if IAM role is enabled.<br/>
	 * SSL Certificate checking is by default disabled. 
	 */
	public GlacierVaultServiceImpl() {
		this(true, null);
	}
	
	/**
	 * The Constructor.<br/>
	 * This Constructor will return glacier client using accessKey and secretKey.<br/>
	 * Additionally it will set the given endPoint for performing operations over vault.<br/>
	 * Default endpoint will be always: "https://glacier.us-east-1.amazonaws.com/"
	 * SSL Certificate checking will be disabled based on provided flag.
	 *
	 * @param accessKey the access key
	 * @param secretKey the secret key
	 * @param disableCertCheck the disable cert check
	 * @param endpoint the endpoint
	 */
	public GlacierVaultServiceImpl(final String accessKey,
			final String secretKey, final boolean disableCertCheck,
			final String endpoint) {
		super();
		AWSUtil.notNull(accessKey, ERR_MSG_ACCESSKEY);
		AWSUtil.notNull(secretKey, ERR_MSG_SECRETKEY);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GlacierVaultServiceImpl is initializing using keys..");
		}
		final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		final AWSCredentialsProvider credentialsProvider = new StaticCredentialsProvider(credentials);
		glacierClient = new AmazonGlacierClient(credentialsProvider);
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
	public GlacierVaultServiceImpl(final String accessKey,
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
	public GlacierVaultServiceImpl(final String accessKey,
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
	public GlacierVaultServiceImpl(final String accessKey,
			final String secretKey) {
		this(accessKey, secretKey, true, null);
	}

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.aws.glacier.service.GlacierArchiveService#createArchiveVault(java.lang.String)
	 */
	@Override
	public CreateVaultResult createVault(final String vaultName)
			throws AmazonServiceException, AmazonClientException {
		LOGGER.info("Creating vault: {}", vaultName);
		final CreateVaultRequest createVaultRequest = new CreateVaultRequest().withAccountId("-").withVaultName(vaultName);
		return glacierClient.createVault(createVaultRequest);
	}

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.aws.glacier.service.GlacierVaultService#deleteVault(java.lang.String)
	 */
	@Override
	public void deleteVault(final String vaultName)
			throws AmazonServiceException, AmazonClientException {
		LOGGER.info("Deleting vault: {}", vaultName);
		final DeleteVaultRequest deleteVaultRequest = new DeleteVaultRequest(vaultName);
		glacierClient.deleteVault(deleteVaultRequest);
	}

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.aws.glacier.service.GlacierVaultService#listAllVaults()
	 */
	@Override
	public List<DescribeVaultOutput> listAllVaults()
			throws AmazonServiceException, AmazonClientException {
		LOGGER.info("Getting all available vaults in the current region..");
		final ListVaultsRequest listVaultsRequest = new ListVaultsRequest();
		final ListVaultsResult listVaultsResult = glacierClient.listVaults(listVaultsRequest);
		return listVaultsResult.getVaultList();
	}

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.aws.glacier.service.GlacierVaultService#getVaultDescription(java.lang.String)
	 */
	@Override
	public DescribeVaultResult getVaultDescription(final String vaultName)
			throws AmazonServiceException, AmazonClientException {
		LOGGER.info("Getting description of vault: {}", vaultName);
		final DescribeVaultRequest describeVaultRequest = new DescribeVaultRequest(vaultName);
		return glacierClient.describeVault(describeVaultRequest);
	}

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.aws.glacier.service.GlacierVaultService#deleteArchivedObject(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteArchivedObject(final String vaultName, final String archiveId)
			throws AmazonServiceException, AmazonClientException {
		LOGGER.info("Deleting archivedObject having archiveId: {} from vault: {}", archiveId, vaultName);
		final DeleteArchiveRequest deleteArchiveRequest = new DeleteArchiveRequest(vaultName, archiveId);
		glacierClient.deleteArchive(deleteArchiveRequest);
	}
}
