package testejava;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	private byte[] palavra;

	public byte[] getPalavra() {
		return palavra;
	}

	public void encrypta(String palavra, String key, String path) {

		try {
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);

			this.palavra = cipher.doFinal(palavra.getBytes());
			insereSenhaNoArquivo(path, this.palavra);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] decrypta(byte[] palavraEncriptada, String key) {
		try {
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);

			return cipher.doFinal(palavraEncriptada);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insereSenhaNoArquivo(String path, byte[] palavra) throws IOException {
		if (path.contains("chave")) {
			encryptaChave(palavra, path, path);
		} else {
			FileOutputStream fos = new FileOutputStream(path + "-");
			fos.write(palavra);
			fos.close();
		}
	}

	public byte[] recuperaDoArquivo(String path, String key) throws FileNotFoundException, IOException {
		Path paths = Paths.get(path);
		byte[] data = Files.readAllBytes(paths);
		return decrypta(data, key);

	}

	void encryptaChave(byte[] palavra, String key, String path) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(palavra);
		fos.close();
	}

}
