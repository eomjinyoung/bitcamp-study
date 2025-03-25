package bitcamp.myapp.cloud;

import java.io.InputStream;
import java.io.OutputStream;

public interface StorageService {

  void upload(String filePath, InputStream fileIn);
  void download(String filePath, OutputStream fileOut);
  void delete(String filePath);

}
