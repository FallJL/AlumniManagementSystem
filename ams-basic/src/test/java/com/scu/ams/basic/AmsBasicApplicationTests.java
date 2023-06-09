package com.scu.ams.basic;

//import com.aliyun.oss.*;
//import com.aliyun.oss.model.PutObjectRequest;
//import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
class AmsBasicApplicationTests {

	@Autowired
	AlumnusBasicService alumnusBasicService;

//	@Autowired
//	OSSClient ossClient;
//
//	/**
//	 * 测试给阿里云oss上传(快捷方式)
//	 */
//	@Test
//	public void testUpload2() throws FileNotFoundException {
//		// 上传文件流
//		InputStream inputStream = new FileInputStream("C:\\Users\\HY\\Pictures\\scu.jpg");
//
//		ossClient.putObject("ams-scu", "portrait/scu.jpg", inputStream);
//
//		// 关闭OSSClient
//		ossClient.shutdown();
//
//		System.out.println("上传完成...");
//	}
//
//	/**
//	 * 测试给阿里云oss上传(原生方式)
//	 */
//	@Test
//	public void testUpload() {
//		// Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//		String endpoint = "oss-cn-chengdu.aliyuncs.com";
//		// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//		String accessKeyId = "<youraccessKeyId>";
//		String accessKeySecret = "<youraccessKeySecret>";
//		// 填写Bucket名称，例如examplebucket。
//		String bucketName = "ams-scu";
//		// 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//		String objectName = "portrait/scu.jpg";
//		// 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//		// 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
//		String filePath= "C:\\Users\\HY\\Pictures\\scu.jpg";
//
//		// 创建OSSClient实例。
//		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//		try {
//			// 创建PutObjectRequest对象。
//			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(filePath));
//			// 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//			// ObjectMetadata metadata = new ObjectMetadata();
//			// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//			// metadata.setObjectAcl(CannedAccessControlList.Private);
//			// putObjectRequest.setMetadata(metadata);
//
//			// 上传文件。
//			PutObjectResult result = ossClient.putObject(putObjectRequest);
//		} catch (OSSException oe) {
//			System.out.println("Caught an OSSException, which means your request made it to OSS, "
//					+ "but was rejected with an error response for some reason.");
//			System.out.println("Error Message:" + oe.getErrorMessage());
//			System.out.println("Error Code:" + oe.getErrorCode());
//			System.out.println("Request ID:" + oe.getRequestId());
//			System.out.println("Host ID:" + oe.getHostId());
//		} catch (ClientException ce) {
//			System.out.println("Caught an ClientException, which means the client encountered "
//					+ "a serious internal problem while trying to communicate with OSS, "
//					+ "such as not being able to access the network.");
//			System.out.println("Error Message:" + ce.getMessage());
//		} finally {
//			if (ossClient != null) {
//				ossClient.shutdown();
//			}
//		}
//		System.out.println("上传完成...");
//	}

	@Test
	public void contextLoads() {
		AlumnusBasicEntity alumnusBasicEntity = new AlumnusBasicEntity();
		//测试插入功能
//		alumnusBasicEntity.setAluId("1");
//		alumnusBasicEntity.setAluName("wanghuan");
//		alumnusBasicEntity.setCity("成都");
//		alumnusBasicService.save(alumnusBasicEntity);
//		System.out.println("保存成功");

//		测试修改功能
//		alumnusBasicEntity.setId(1L);
//		alumnusBasicEntity.setAluFormerName("李四");
//		alumnusBasicService.updateById(alumnusBasicEntity);

		//测试查询功能
		List<AlumnusBasicEntity> list = alumnusBasicService.list(new QueryWrapper<AlumnusBasicEntity>().eq("alu_id", "1"));
		list.forEach((item)->{
			System.out.println(item);
		});
	}

}
