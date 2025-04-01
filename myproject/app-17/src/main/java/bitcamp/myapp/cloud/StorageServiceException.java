package bitcamp.myapp.cloud;

public class StorageServiceException extends RuntimeException {
  public StorageServiceException(String message) {
    super(message);
  }

  public StorageServiceException(Throwable cause) {
    super(cause);
  }

  public StorageServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
