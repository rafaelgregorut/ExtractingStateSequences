import java.nio.file.Path;



public class Main {

	public static void main(String[] args) {
		System.out.println("So criando o git");
		char mode = args[0].charAt(0);
		String filePath = args[1];
		
		FileHandler file = new FileHandler(filePath);
		String rawSequence = file.fileToString();
		System.out.println(rawSequence);
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
