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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.glacier.transfer.UploadResult;

/**
 * The Interface GlacierArchiveService.
 * 
 * @author Abhinav Kumar Mishra
 * @since 2017
 */
public interface GlacierArchiveService {

	/**
	 * Archive.
	 *
	 * @param vaultName the vault name
	 * @param inputStream the input stream
	 * @param archiveDescription the archive description
	 * @return the upload result
	 * @throws AmazonServiceException the amazon service exception
	 * @throws AmazonClientException the amazon client exception
	 * @throws IOException the IO exception
	 */
	UploadResult archive(final String vaultName, final InputStream inputStream,
			final String archiveDescription) throws AmazonServiceException,
			AmazonClientException, IOException;

	/**
	 * Archive.
	 *
	 * @param vaultName the vault name
	 * @param inputFile the input file
	 * @param archiveDescription the archive description
	 * @return the upload result
	 * @throws AmazonServiceException the amazon service exception
	 * @throws AmazonClientException the amazon client exception
	 * @throws IOException the IO exception
	 */
	UploadResult archive(final String vaultName, final File inputFile,
			final String archiveDescription) throws AmazonServiceException,
			AmazonClientException, IOException;
}
