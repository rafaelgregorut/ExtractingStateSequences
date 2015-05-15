import java.nio.file.Path;



public class Main {

	public static void main(String[] args) {
		System.out.println("SÃ³ criando o git");
		char mode = args[1].charAt(0);
		String filePath = args[2];
		
		FileHandler file = new FileHandler(filePath);
		String rawSequence = file.fileToString();
		
		switch (mode) {
			case 'd':
				break;
			case 'u':
				break;
			default:
				System.out.println("Modo não é válido");
		}
	}

}
