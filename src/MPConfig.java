 import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


public class MPConfig {
	public MPConfig(String configFilename, String localName) throws FileNotFoundException {
		InputStream input = new FileInputStream(new File(configFilename));
		Yaml yaml = new Yaml();
		Object sections = yaml.load(input);
		sections.equals(1);
	}
	
	public Map<String, Node> getNodes() {
		return null;
	}
	public List<Rule> getSendRule() {
		return null;
	}
	public List<Rule> getReceiveRule() {
		return null;
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
			MPConfig config = new MPConfig("config.yaml", "none");
			
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
