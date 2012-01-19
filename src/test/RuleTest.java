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

        rule.setCounter(rule.getNth());
        assertEquals(rule.getAction(), rule.matches(message));

        message = new Message("Un matched Src", rule.getDest(), rule.getKind(),
                "");
        rule.setCounter(rule.getNth());
        assertFalse(rule.getAction() == rule.matches(message));
    }

    @Test
    public void testPartialMatches() {
        Rule rule = new Rule(ACTION.DROP);

        rule.setID(100);
        rule.setSrc("bob");
        rule.setDest("lee");
        // see, we remove the "kind" field here
        // rule.setKind("ack");
        // rule.setNth(7);

        Message messageOfDifferentKind = new Message(rule.getSrc(),
                rule.getDest(), "kind never exist", "");
        messageOfDifferentKind.setId(rule.getID());

        assertEquals(rule.getAction(), rule.matches(messageOfDifferentKind));

        Message message = new Message("Un matched Src", rule.getDest(),
                rule.getKind(), "");
        assertFalse(rule.getAction() == rule.matches(message));
    }

    @Test
    public void testMatchesMethodOfAEmptyRule() {
        Rule emptyRule = new Rule(ACTION.DROP);

        Message messageOfDifferentKind = new Message("I don't know",
                "I don't care", "kind never exist", "");
        messageOfDifferentKind.setId(-123);

        assertEquals(emptyRule.getAction(),
                emptyRule.matches(messageOfDifferentKind));
    }

    @Test
    public void testRuleCounter() {
        Rule rule = new Rule(ACTION.DELAY);
        Message message = new Message("I don't know",
                "I don't care", "kind never exist", "");
        
        assertEquals(1, rule.getCounter());
        rule.matches(message);
        assertEquals(2, rule.getCounter());
        rule.matches(message);
        assertEquals(3, rule.getCounter());
    }
}
