import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class MPConfig {
	public MPConfig(String configFilename, String localName)
			throws FileNotFoundException {
		Yaml yaml = new Yaml();

		InputStream input = new FileInputStream(new File(configFilename));
		Object rawConfig = yaml.load(input);

		nodes = extractNodes(rawConfig);
		sendRules = extractRules(rawConfig, "SendRules");
		receiveRules = extractRules(rawConfig, "ReceiveRules");
	}

	// TODO: TO REFACTOR - Really really disgusting implementation.
	@SuppressWarnings("rawtypes")
	private HashMap<String, Node> extractNodes(Object rawConfig) {
		ArrayList list = (ArrayList) ((LinkedHashMap) rawConfig)
				.get("Configuration");

		HashMap<String, Node> nodes = new HashMap<String, Node>();
		for (Object item : list) {
			LinkedHashMap info = (LinkedHashMap) item;

			Node node = new Node();

			node.setName(info.get("Name").toString());
			node.setIP(info.get("IP").toString());
			node.setPort(((Integer) info.get("Port")).intValue());

			nodes.put(node.getName(), node);
		}
		return nodes;
	}

	// TODO TO REFACTOR: YET another ugly code.
	private List<Rule> extractRules(Object rawConfig, String sectionName) {
		ArrayList list = (ArrayList) ((LinkedHashMap) rawConfig)
				.get(sectionName);

		List<Rule> rules = new ArrayList<Rule>();
		for (Object item : list) {
			LinkedHashMap info = (LinkedHashMap) item;
			Rule rule = new Rule();

            // TODO: REMEMBER to remove these ugly duplications.
			rule.setAction(info.get("Action").toString());
			if (info.containsKey("Src"))
				rule.setSrc(info.get("Src").toString());
			if (info.containsKey("Dest"))
                rule.setDest(info.get("Dest").toString());
			if (info.containsKey("Kind"))
                rule.setKind(info.get("Kind").toString());

			if (info.containsKey("ID"))
                rule.setID(((Integer) info.get("ID")).intValue());
			if (info.containsKey("Nth"))
                rule.setNth(((Integer) info.get("Nth")).intValue());

			rules.add(rule);
		}
		return rules;
	}

	// --- Accessers
	public List<Rule> getSendRules() {
		return sendRules;
	}

	public List<Rule> getReceiveRules() {
		return receiveRules;
	}

	public Map<String, Node> getNodes() {
		return nodes;
	}

	// -- Data
	private List<Rule> receiveRules;
	private List<Rule> sendRules;
	private Map<String, Node> nodes;

	// -- test method
	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * Constructor constructor = new Constructor(Node.class); Yaml yaml =
		 * new Yaml(constructor); InputStream input = new FileInputStream(new
		 * File("config.yaml")); Node rule = (Node) yaml.load(input);
		 */

		// System.out.println(rule.getAction() + " " + rule.getSrc() + " " +
		// rule.getDest() + " " + rule.getKind() + " " + rule.getID() + " " +
		// rule.getNth());

		InputStream input;
		try {
			MPConfig config = new MPConfig("config.yaml", "none");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
