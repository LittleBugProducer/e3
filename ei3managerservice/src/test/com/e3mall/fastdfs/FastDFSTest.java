package com.e3mall.fastdfs;

import com.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;


public class FastDFSTest {
	@Test
	public void testFileUpload()throws Exception{
		ClientGlobal.init("E:\\ideaWorkFile\\ei3\\ei3managerservice\\src\\main\\resources\\conf\\client.conf");
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		String[] strings = storageClient.upload_file("C:\\Users\\lC\\Downloads\\1524795083382.jpg","jpg",null);
		for(String string:strings){
			System.out.println(string);
		}
	}

	@Test
	public void testFastDfsClient()throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("E:\\ideaWorkFile\\ei3\\ei3managerservice\\src\\main\\resources\\conf\\client.conf");
		String string = fastDFSClient.uploadFile("C:\\Users\\lC\\Downloads\\1524795083382.jpg");
		System.out.println(string);
	}
}
