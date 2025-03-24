package bitcamp.myapp.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

@Service
public class NCPObjectStorageService implements StorageService {

  @Value("${ncp.end-point}")
  private String endPoint;

  @Value("${ncp.region-name}")
  private String regionName;

  @Value("${ncp.access-key}")
  private String accessKey;

  @Value("${ncp.secret-key}")
  private String secretKey;

  @Value("${ncp.bucket-name}")
  private String bucketName;

  private AmazonS3 s3;

/* 스프링 프레임워크에서 객체를 생성하는 과정:

1) 객체 생성 -> 생성자 호출
NCPObjectStorageService obj = new NCPObjectStorageService();

2) @Value 애노테이션이 붙은 필드에 값 삽입
obj.endPoint = prop.getProperty("ncp.end-point");
...

3) @PostContruct 애노테이션이 붙은 메서드 호출
obj.init();
*/

  @PostConstruct
  public void init() {
    System.out.println("init() 호출됨! : " + endPoint);

    // AWS S3 API 버전 1에서 발생하는 경고 메시지를 출력하지 않게 설정
    // 버전 2로 변경하면 경고 메시지가 발생하지 않는다.
    // 단, 버전 2는 네이버 클라우드의 Object Storage 서비스와 연동을 지원하지 않는다.
    System.getProperties().setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");

    this.s3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
            .build();
  }

  public void upload(String filePath, InputStream fileIn) {

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType("application/x-directory");

    PutObjectRequest putObjectRequest = new PutObjectRequest(
            bucketName, // 버킷 이름
            filePath, // 업로드 파일의 이름 및 디렉토리 경로
            fileIn, // 업로드 할 파일의 InputStream
            objectMetadata // 업로드에 필요한 부가 정보
    );

    try {
      s3.putObject(putObjectRequest);

    } catch (Exception e) {
      throw new StorageServiceException(e);
    }
  }

  @Override
  public void download(String filePath, OutputStream fileOut) {
    try {
      S3Object s3Object = s3.getObject(bucketName, filePath);
      S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();


      byte[] bytesArray = new byte[4096];
      int bytesRead = -1;
      while ((bytesRead = s3ObjectInputStream.read(bytesArray)) != -1) {
        fileOut.write(bytesArray, 0, bytesRead);
      }

      s3ObjectInputStream.close();
      fileOut.close();

    } catch (Exception e) {
      throw new StorageServiceException(e);
    }
  }

  @Override
  public void delete(String filePath) {
    try {
      s3.deleteObject(bucketName, filePath);
    } catch (Exception e) {
      throw new StorageServiceException(e);
    }
  }
}
