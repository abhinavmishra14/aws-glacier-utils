## aws-glacier-utils
##This utility can be used to archive and retrieve files or folders to amazon glacier vault.

####Note: By default test cases will be skipped. 

####Use access keys to build in order to pass the test cases and use skip test parameter (-Dmaven.test.skip=false). To force run tests: e.g. mvn clean install -Dmaven.test.skip=false


####For using glacier services on EC2 instance which is already mapped with IAM role, use the default constructor call to create instance of GlacierVaultServiceImpl. 

You can use default constructor initialization if you have access keys in aws profile 
(generally created user use home directory e.g. 'C:\Users\AbhiKMishra\.aws') or in environment variables. Additionally you can keep a property file named as 'AwsCredentials.properties' in class path as well. You can find a sample under src/main/resources directory.

**Example:** *GlacierArchiveService arcService = new GlacierArchiveServiceImpl();*  
**Example:** *GlacierRetrievalService arcService = new GlacierRetrievalServiceImpl();*  
**Example:** *GlacierVaultService arcService = new GlacierVaultServiceImpl();*

####For using glacier services using keys, create the instance using parameterized constructor. 


**Example:** *GlacierArchiveService arcService = new GlacierArchiveServiceImpl("accessKey","secretKey");*  
**Example:** *GlacierRetrievalService arcService = new GlacierRetrievalServiceImpl("accessKey","secretKey");*  
**Example:** *GlacierVaultService arcService = new GlacierVaultServiceImpl("accessKey","secretKey");*