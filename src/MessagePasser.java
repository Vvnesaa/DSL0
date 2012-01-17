import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MessagePasser {
	private static final int EmptyInRcvSleepTime = 500;
	
	private MPConfig currentConfig;
	private LinkedList<Message> outputMessage = new LinkedList<Message>();
	private LinkedList<Message> inputMessage = new LinkedList<Message>();
	private Lock outputQueueLock = new ReentrantLock();
	private Lock inputQueueLock = new ReentrantLock();
	private int newID = 0;
	
	public MessagePasser(String configFilename, String localName) throws FileNotFoundException {
		currentConfig = new MPConfig(configFilename, localName);
		
		Thread tSend = new Thread(new SendRunnable());
		Thread tReceive = new Thread(new ReceiveRunnable());
		tSend.start();
		tReceive.start();
	}
	
	// user send message to tail of outputMessage queue
	public void send(Message message) {
		message.set_id(newID++);
		outputQueueLock.lock();
		try {
			outputMessage.add(message);
		} finally {
			outputQueueLock.unlock();
		}
	}
	
	// user get message from head of inputMessage queue
	// block while inputMessage queue is empty
	public Message receive() {
		Message result;
		while (true) {
			inputQueueLock.lock();
			try {
				if (!inputMessage.isEmpty()) {
					result = inputMessage.removeLast();
					break;
				}
			} finally {
				inputQueueLock.unlock();
			}
			try {
				Thread.sleep(EmptyInRcvSleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// MP send message from head of outputMessage queue
	private class SendRunnable implements Runnable {
		@Override
		public void run() {
		}
	}
	
	// MP receive message to tail of inputMessage queue
	private class ReceiveRunnable implements Runnable {
		@Override
		public void run() {
		}
	}
	
}
