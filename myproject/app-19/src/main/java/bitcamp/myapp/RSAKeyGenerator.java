package bitcamp.myapp;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RSAKeyGenerator {
  public static void main(String[] args) throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);

    // private/public 키 쌍을 생성한다.
    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    // private/public 키를 byte[] 로 변환한다.
    byte[] privateKey = keyPair.getPrivate().getEncoded();
    byte[] publicKey = keyPair.getPublic().getEncoded();

    // byte[] 형식의 RSA 키를 PEM 형식으로 변환
    String privateKeyPem = convertToPemFormat("PRIVATE KEY", privateKey);
    String publicKeyPem = convertToPemFormat("PUBLIC KEY", publicKey);

    System.out.println(privateKeyPem);
    System.out.println(publicKeyPem);

//    System.out.println(Paths.get("private.pem").toAbsolutePath());
//    System.out.println(Paths.get("public.pem").toAbsolutePath());

    Files.write(Paths.get("private.pem"), privateKeyPem.getBytes());
    Files.write(Paths.get("public.pem"), publicKeyPem.getBytes());
  }

  private static String convertToPemFormat(String type, byte[] keyBytes) {
    String base64Encoded = Base64.getEncoder().encodeToString(keyBytes);
    return "-----BEGIN " + type + "-----\n" +
            base64Encoded.replaceAll("(.{64})", "$1\n") +  // 64자마다 줄 바꿈
            "\n-----END " + type + "-----";
  }
}
