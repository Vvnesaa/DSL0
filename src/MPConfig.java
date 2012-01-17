 import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


public class MPConfig {
	public MPConfig(String configFilename, String localName) {
		readYAML(configFilename);
	}
	
	private void readYAML(String configFilename) {
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		/*
		Constructor constructor = new Constructor(Node.class);
		Yaml yaml = new Yaml(constructor);
		InputStream input = new FileInputStream(new File("config.yaml"));
		Node rule = (Node) yaml.load(input);
		*/
		
		//System.out.println(rule.getAction() + " " + rule.getSrc() + " " + rule.getDest() + " " + rule.getKind() + " "  + rule.getID() + " " + rule.getNth());
		
		
		
		InputStream input;
		try {
			input = new FileInputStream(new File("config.yaml"));
			Yaml yaml = new Yaml();
		    Object data = yaml.load(input);
		    Node node = (Node) data;
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
