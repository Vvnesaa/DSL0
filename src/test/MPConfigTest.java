package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import lab0.ACTION;
import lab0.MPConfig;
import lab0.Node;
import lab0.Rule;

import org.junit.Test;

public class MPConfigTest {

	@Test
	public void testMPConfig() {
		try {
			MPConfig mpconfig = new MPConfig("src/test/config.yaml", "bob");
			
			// test nodes
			Map<String, Node> nodes = mpconfig.getNodes();
			assertEquals(4, nodes.size());
			assertTrue(nodes.containsKey("alice"));
			assertEquals(12344, nodes.get("alice").getPort());
			assertEquals(1, nodes.get("alice").getIP()[2]);
			
			// test send rules
			List<Rule> rules = mpconfig.getSendRules();
			assertEquals(2, rules.size());
			Rule rule = rules.get(0);
			assertEquals(ACTION.DROP, rule.getAction());
			assertEquals("bob", rule.getSrc());
			assertEquals("alice", rule.getDest());
			assertEquals("Ack", rule.getKind());
			assertEquals(4, rule.getID());
			
			// test receive rules
            rules = mpconfig.getReceiveRules();
            assertEquals(1, rules.size());
             rule = rules.get(0);
            assertEquals(ACTION.DUPLICATE, rule.getAction());
            assertEquals("charlie", rule.getSrc());
            assertEquals(3, rule.getNth());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		    fail("error occurs");
		}

	}

}
