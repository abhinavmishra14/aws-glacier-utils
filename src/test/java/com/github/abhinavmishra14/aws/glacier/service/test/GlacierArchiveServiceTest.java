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

import java.io.File;
import java.io.InputStream;

import org.junit.After;
import org.junit.Test;

import com.amazonaws.services.glacier.transfer.UploadResult;
import com.github.abhinavmishra14.aws.glacier.service.GlacierArchiveService;
import com.github.abhinavmishra14.aws.glacier.service.GlacierVaultService;
import com.github.abhinavmishra14.aws.glacier.service.impl.GlacierArchiveServiceImpl;
import com.github.abhinavmishra14.aws.glacier.service.impl.GlacierVaultServiceImpl;


/**
 * The Class GlacierArchiveServiceTest.
 */
public class GlacierArchiveServiceTest {

	/** The Constant VAULT_NAME. */
	private static final String VAULT_NAME = "testVault";

	/* For testing services on EC2 instance which is already mapped with
	  IAM role, use the default constructor call to create instance of
	  GlacierVaultServiceImpl. */
	private static GlacierVaultService vaultService = new GlacierVaultServiceImpl();  
	//private static GlacierVaultService vaultService = new GlacierVaultServiceImpl("accessKey","secretKey");

	/* For testing services on EC2 instance which is already mapped with
	  IAM role, use the default constructor call to create instance of
	  GlacierVaultServiceImpl. */
	private static GlacierArchiveService arcService = new GlacierArchiveServiceImpl();  
	//private static GlacierArchiveService arcService = new GlacierArchiveServiceImpl("accessKey","secretKey");

	/**
	 * Test upload file.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testUploadFile() throws Exception {
		//Setup vault for test
		vaultService.createVault(VAULT_NAME);
		final File file = new File("C:/EclipseWorspace/aws-glacier-utils/src/test/resources/samples/sampleArchiveFile.txt");
		final UploadResult result = arcService.archive(VAULT_NAME, file, "test upload");
		System.out.println("ArchiveId: "+result.getArchiveId());
	}

	/**
	 * Test upload stream.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testUploadStream() throws Exception {
		//Setup vault for test
		vaultService.createVault(VAULT_NAME);
		final InputStream inStream = GlacierArchiveServiceTest.class.getResourceAsStream("/samples/sampleArchiveFile.txt");
		final UploadResult result = arcService.archive(VAULT_NAME, inStream, "test upload");
		System.out.println("ArchiveId: "+result.getArchiveId());
	}

	/**
	 * Test delete archive.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDeleteArchive() throws Exception {
		//Setup vault for test
		vaultService.createVault(VAULT_NAME);
		final InputStream inStream = GlacierArchiveServiceTest.class.getResourceAsStream("/samples/sampleArchiveFile.txt");
		final UploadResult result = arcService.archive(VAULT_NAME, inStream, "test upload");
		vaultService.deleteArchivedObject(VAULT_NAME, result.getArchiveId());
	}
	
	/**
	 * Tear down.
	 */
	@After
	public void tearDown(){
		System.out.println("Tearing down..");
		//TODO:: Cleanup archived objects then delete vault.
		//vaultService.deleteVault(VAULT_NAME);
	}
}
