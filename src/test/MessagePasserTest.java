package test;

import java.io.FileNotFoundException;

import lab0.Message;
import lab0.MessagePasser;

// very simple test. I just make different situations with config & msg header & msg content
public class MessagePasserTest {
	public static void main(String[] args) {
		try {
			MessagePasser MP1 = new MessagePasser("src/test/config.yaml", "alice");
			String data1 = "MessageFromAliceToBob";
			Message m1 = new Message("alice", "bob", "Test1", data1);
			
			MessagePasser MP2 = new MessagePasser("src/test/config.yaml", "bob");
			String data2 = "MessageFromBobToAlice";
			Message m2 = new Message("bob", "alice", "Ack", data2);
			Message m3 = new Message("bob", "alice", "Other", data2);
			
			MP2.send(m2);
			MP2.send(m2);
			MP2.send(m3);
			
			Message test2 = MP1.receive();
			Message test3 = MP1.receive();
			Message test4 = MP1.receive();
			
			System.out.println(test2.toString());
			System.out.println(test3.toString());
			System.out.println(test4.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
