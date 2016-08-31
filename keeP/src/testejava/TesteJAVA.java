package testejava;

import java.io.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ramon.silveira
 */
public class TesteJAVA {
	
	private static final Controller controller = new Controller();
	
	public static void main(String[] args) throws IOException {
		boolean first = controller.checkFirstKey();
		String senha = tryAccess(first);
		if (!senha.equals("")){
			Menu menu = new Menu(senha);
			menu.setVisible(true);
		}
	}
	
	public static boolean checkKey(String key) throws IOException {
		return (controller.checkChave(key) && (key.length() == 7));
	}
	
	private static String tryAccess(boolean first) throws IOException {
		String key = null;
		if (first) {
			boolean confirma = false;
			int tentativa = 0;
			while (!confirma && tentativa < 3) {
				JLabel label = new JLabel("Insira sua chave: ");
				JTextField password = new JPasswordField();
				Object[] ob = {label, password};
				int teste = JOptionPane.showConfirmDialog(null,ob,"Chave",JOptionPane.OK_CANCEL_OPTION);
				if (teste == JOptionPane.OK_OPTION) {
					key = password.getText();
				}
				if (key != null ) {
					confirma = checkKey(key);
					if (confirma) {
						tentativa = 3;
					}
				} else {
					tentativa = 3;
					return "";
				}
				tentativa++;
			}
			if (confirma == false) {
				return "";
			}
			return controller.userKey(key);
		} else {
			controller.insertNewKey();
			return "";
		}
		
	}
	
}
