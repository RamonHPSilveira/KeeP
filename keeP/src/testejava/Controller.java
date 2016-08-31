package testejava;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ramon.silveira
 */
public class Controller {

	private static final String KEY = "as*jfe#JC752hdHm";
	private static final String adicionarKey = "sK*fd$cXl";
	AES aes;
	private static final String CHAVEPATH = "D:\\TR\\password\\chave.txt";
	private static final String GERALPATH = "D:\\TR\\password\\";

	public AES getAes() {
		return aes;
	}

	public static String getCHAVEPATH() {
		return CHAVEPATH;
	}

	public void setAes(AES aes) {
		this.aes = aes;

	}

	public Controller() {
		this.aes = new AES();
	}

	public void insereSenha(String palavra, String key, String path) {
		aes.encrypta(palavra, key, path);
	}

	public String recuperaSenha(String key, String path) throws IOException {
		return new String(aes.recuperaDoArquivo(path, key));
	}

	public boolean checkChave(String chave) throws IOException {
		String chaveRecuperada = new String(aes.recuperaDoArquivo(CHAVEPATH, KEY));
		return chaveRecuperada.equals(chave + adicionarKey);
	}

	public boolean checkFirstKey() throws IOException {
		Path paths = Paths.get(CHAVEPATH);
		if (Files.exists(paths)) {

			byte[] chave = Files.readAllBytes(paths);
			return chave.length != 0;
		} else {
			return false;
		}

	}

	public void insertNewKey() throws IOException {
		JTextField pass = new JPasswordField();
		JLabel label = new JLabel("Digite sua Chave com 7 digitos");
		Object[] ob = {label, pass};
		int option = JOptionPane.showConfirmDialog(null, ob, "Chave nova", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String key = pass.getText();

			if (key != null && key.length() == 7) {
				key = key + adicionarKey;
				insereChave(key, KEY, CHAVEPATH);

			}
		}
	}

	public void insereChave(String palavra, String key, String path) throws IOException {
		aes.encrypta(palavra, key, path);
	}

	public String userKey(String key) {
		return key + adicionarKey;
	}

	public void deletaSenha(String path) {
		File file = new File(path);
		file.delete();
	}

	public File[] listarAqruivos() {
		File diretorio = new File(GERALPATH);
		File fList[] = diretorio.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("-");
			}
		});

		return fList;
	}

}
