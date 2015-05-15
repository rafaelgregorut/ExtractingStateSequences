import java.io.BufferedReader;
import java.io.FileReader;


public class FileHandler {

	private String filePath;
	
	FileHandler(String path) {
		filePath = path;
	}
	
	public String fileToString() {
		String fileContent = "";
		try {
				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				String line;
				while ((line = reader.readLine()) != null) {
					//records.add(line);
					fileContent += line + "\n";
				}
				reader.close();
				return fileContent;
		} catch (Exception e) {
		    System.err.format("Exception occurred trying to read '%s'.", filePath);
		    e.printStackTrace();
		    return null;
		}
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
