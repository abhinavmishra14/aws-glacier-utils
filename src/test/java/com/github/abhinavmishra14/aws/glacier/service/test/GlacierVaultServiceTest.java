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
package com.github.abhinavmishra14.aws.glacier.service.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.glacier.model.CreateVaultResult;
import com.amazonaws.services.glacier.model.DescribeVaultOutput;
import com.amazonaws.services.glacier.model.DescribeVaultResult;
import com.github.abhinavmishra14.aws.glacier.service.GlacierVaultService;
import com.github.abhinavmishra14.aws.glacier.service.impl.GlacierVaultServiceImpl;


/**
 * The GlacierVaultServiceTest.
 * 
 * @author Abhinav Kumar Mishra
 * @since 2017
 */
public class GlacierVaultServiceTest {

	/** The Constant VAULT_NAME. */
	private static final String VAULT_NAME = "testVault";

	/* For testing services on EC2 instance which is already mapped with
	  IAM role, use the default constructor call to create instance of
	  GlacierVaultServiceImpl. */
	private static GlacierVaultService vaultService = new GlacierVaultServiceImpl();  
	//private static GlacierVaultService vaultService = new GlacierVaultServiceImpl("accessKey","secretKey");

	/**
	 * Test create vault.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCreateVault() throws Exception {
		final CreateVaultResult createResult = vaultService.createVault("testVault");
		System.out.println("Location: "+createResult.getLocation());
		assertEquals("/303148936849/vaults/testVault", createResult.getLocation());
	}

	/**
	 * Test get vault description.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetVaultDescription() throws Exception {
		//Setup vault for test
		vaultService.createVault(VAULT_NAME);
		final DescribeVaultResult description = vaultService.getVaultDescription(VAULT_NAME);
		System.out.println("VaultNameFrmResponse: "+description.getVaultName());
		System.out.println("No.Of archives: "+description.getNumberOfArchives());
		System.out.println("Inventory lastUpdated: "+description.getLastInventoryDate());
		System.out.println("SizeInBytes: "+FileUtils.byteCountToDisplaySize(description.getSizeInBytes()));
		System.out.println("VaultARN: "+description.getVaultARN());
		assertEquals(VAULT_NAME, description.getVaultName());
	}

	/**
	 * Test list all vaults.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testListAllVaults() throws Exception {
		//Setup vault for test
		vaultService.createVault(VAULT_NAME);
		final List<DescribeVaultOutput> allVaults = vaultService.listAllVaults();
		for (final DescribeVaultOutput describeVaultOutput : allVaults) {
			System.out.println("VaultName: "+describeVaultOutput.getVaultName());
		}
		assertEquals("myVault", allVaults.get(0).getVaultName());
	}

	/**
	 * Tear down.
	 */
	@After
	public void tearDown(){
		System.out.println("Tearing down..");
		try {
			vaultService.deleteVault(VAULT_NAME);
		} catch (AmazonClientException ex){
			ex.printStackTrace();
		}
	}
}
