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
package com.github.abhinavmishra14.aws.glacier.service;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.glacier.model.CreateVaultResult;
import com.amazonaws.services.glacier.model.DescribeVaultOutput;
import com.amazonaws.services.glacier.model.DescribeVaultResult;

/**
 * The Interface GlacierVaultService.
 * 
 * @author Abhinav Kumar Mishra
 * @since 2017
 */
public interface GlacierVaultService {

	/**
	 * Creates the vault.
	 *
	 * @param vaultName the vault name
	 * @return the creates the vault result
	 * @throws AmazonServiceException the amazon service exception
	 * @throws AmazonClientException the amazon client exception
	 */
	CreateVaultResult createVault(final String vaultName)
			throws AmazonServiceException, AmazonClientException;

	/**
	 * Delete vault.
	 *
	 * @param vaultName the vault name
	 * @throws AmazonServiceException the amazon service exception
	 * @throws AmazonClientException the amazon client exception
	 */
	void deleteVault(final String vaultName) throws AmazonServiceException,
			AmazonClientException;

	/**
	 * List all vaults.
	 *
	 * @return the list< describe vault output>
	 * @throws AmazonServiceException the amazon service exception
	 * @throws AmazonClientException the amazon client exception
	 */
	List<DescribeVaultOutput> listAllVaults() throws AmazonServiceException,
			AmazonClientException;

	/**
	 * Gets the vault description.
	 *
	 * @param vaultName the vault name
	 * @return the vault description
	 * @throws AmazonServiceException the amazon service exception
	 * @throws AmazonClientException the amazon client exception
	 */
	DescribeVaultResult getVaultDescription(final String vaultName)
			throws AmazonServiceException, AmazonClientException;

	/**
	 * Delete archived object.
	 *
	 * @param vaultName the vault name
	 * @param archiveId the archive id
	 * @throws AmazonServiceException the amazon service exception
	 * @throws AmazonClientException the amazon client exception
	 */
	void deleteArchivedObject(final String vaultName, final String archiveId)
			throws AmazonServiceException, AmazonClientException;
}
