package test;

import static org.junit.Assert.*;
import lab0.ACTION;
import lab0.Message;
import lab0.Rule;

import org.junit.Test;

public class RuleTest {

	@Test
	public void testExactMatches() {
		Rule rule = new Rule(ACTION.DROP);

		rule.setID(100);
		rule.setSrc("bob");
		rule.setDest("lee");
		rule.setKind("ack");
		rule.setNth(7);

		Message message = new Message(rule.getSrc(), rule.getDest(),
				rule.getKind(), "");
		message.setId(rule.getID());

		assertEquals(rule.getAction(), rule.matches(message, rule.getNth()));

		message = new Message("Un matched Src", rule.getDest(), rule.getKind(),
				"");
		assertFalse(rule.getAction() == rule.matches(message, rule.getNth()));
	}

	@Test
	public void testPartialMatches() {
		Rule rule = new Rule(ACTION.DROP);

		rule.setID(100);
		rule.setSrc("bob");
		rule.setDest("lee");
		// see, we remove the "kind" field here
		// rule.setKind("ack");
		rule.setNth(7);

		Message messageOfDifferentKind = new Message(rule.getSrc(),
				rule.getDest(), "kind never exist", "");
		messageOfDifferentKind.setId(rule.getID());

		assertEquals(rule.getAction(),
				rule.matches(messageOfDifferentKind, rule.getNth()));

		Message message = new Message("Un matched Src", rule.getDest(),
				rule.getKind(), "");
		assertFalse(rule.getAction() == rule.matches(message, rule.getNth()));
	}

	@Test
	public void testMatchesMethodOfAEmptyRule() {
		Rule emptyRule = new Rule(ACTION.DROP);

		Message messageOfDifferentKind = new Message("I don't know",
				"I don't care", "kind never exist", "");
		messageOfDifferentKind.setId(-123);

		assertEquals(emptyRule.getAction(),
				emptyRule.matches(messageOfDifferentKind, emptyRule.getNth()));
	}

}
