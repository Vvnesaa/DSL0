package lab0;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessagePasser {
	private static final int EmptyInRcvSleepTime = 500;
	private static final int EmptyOutSdSleepTime = 500;

	private MPConfig currentConfig;
	private LinkedList<Message> outputMessage = new LinkedList<Message>();
	private LinkedList<Message> inputMessage = new LinkedList<Message>();
	private LinkedList<Message> delayInput = new LinkedList<Message>();
	private LinkedList<Message> delayOutput = new LinkedList<Message>();
	private Lock outputQueueLock = new ReentrantLock();
	private Lock inputQueueLock = new ReentrantLock();
	private Lock delayInputLock = new ReentrantLock();
	private int newID = 0;
	private String localName;

	public MessagePasser(String configFilename, String localName)
			throws FileNotFoundException {
		this.localName = localName;
		currentConfig = new MPConfig(configFilename, localName);

		Thread tSend = new Thread(new SendRunnable());
		Thread tReceive = new Thread(new ReceiveRunnable());
		tSend.start();
		tReceive.start();
	}

	// only match the first applied rule
	// but still need to check all rules for "Nth"
	private ACTION checkSendRules(Message m) {
		List<Rule> rules = currentConfig.getSendRules();
		ACTION result = ACTION.NOTHING;
		for (Rule r : rules) {
			ACTION temp = r.matches(m); // HAS PROBLEM!!!
			if (result == ACTION.NOTHING)
				result = temp; 
		}
		return result;
	}

	// user send message to tail of outputMessage queue
	// m1(delay) -> m2(duplicate) : m2, m2, m1
	// m1(delay) -> m2(delay) -> m3(nothing) : m3, m1, m2
	// first check config file change
	public void send(Message message) {

		// Check Config File Change => Problem!!! What if my port change?
		// interrupt receive thread? How to handle?

		message.setId(newID++);
		ACTION action = checkSendRules(message);
		if (action == ACTION.DROP)
			return;
		if (action == ACTION.DELAY) {
			delayOutput.add(message);
			return;
		}
		outputQueueLock.lock();
		try {
			if (action == ACTION.DUPLICATE)
				outputMessage.add(message);
			outputMessage.add(message);
			while (!delayOutput.isEmpty())
				outputMessage.add(delayOutput.removeFirst());
		} finally {
			outputQueueLock.unlock();
		}
	}

	// user get message from head of inputMessage queue
	// block while inputMessage queue is empty
	// first check config file change
	public Message receive() {

		// Check Config File Change => Problem!!! What if my port change?
		// interrupt receive thread? How to handle?

		Message result;
		while (true) {
			inputQueueLock.lock();
			if (!inputMessage.isEmpty()) {
				result = inputMessage.removeLast();
				inputQueueLock.unlock();
				break;
			}
			inputQueueLock.unlock();
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
			Message m;
			while (true) {
				outputQueueLock.lock();
				if (outputMessage.isEmpty()) {
					outputQueueLock.unlock();
					try {
						Thread.sleep(EmptyOutSdSleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				} else {
					m = outputMessage.removeFirst();
					outputQueueLock.unlock();
					Node dest = currentConfig.getNodes().get(m.getDest());
					Socket s = null;
					try {
						s = new Socket(InetAddress.getByAddress(dest.getIP()),
								dest.getPort());
						OutputStream outStream = s.getOutputStream();
						ObjectOutputStream out = new ObjectOutputStream(
								outStream);
						out.writeObject(m);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (s != null)
							try {
								s.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			}
		}
	}

	// MP receive message to tail of inputMessage queue
	private class ReceiveRunnable implements Runnable {
		@Override
		public void run() {
			try {
				int port = currentConfig.getNodes().get(localName).getPort();
				ServerSocket s = new ServerSocket(port);
				while (true) {
					Socket incoming = s.accept();
					Thread t = new Thread(new ReceiveRunnableHandler(incoming));
					t.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// only match the first applied rule
	// but still need to check all rules for "Nth"
	private ACTION checkReceiveRules(Message m) {
		List<Rule> rules = currentConfig.getReceiveRules();
		ACTION result = ACTION.NOTHING;
		for (Rule r : rules) {
			ACTION temp = r.matches(m); // HAS PROBLEM!!!
			if (result == ACTION.NOTHING)
				result = temp; 
		}
		return result;
	}

	// Same assumptions for ACTION as send()
	private class ReceiveRunnableHandler implements Runnable {
		private Socket incoming;

		public ReceiveRunnableHandler(Socket i) {
			incoming = i;
		}

		@Override
		public void run() {
			try {
				try {
					InputStream inStream = incoming.getInputStream();
					ObjectInputStream in = new ObjectInputStream(inStream);
					Message m;
					try {
						m = (Message) in.readObject();
						ACTION action = checkReceiveRules(m);
						if (action == ACTION.DROP) {
							// Do Nothing
						} else if (action == ACTION.DELAY) {
							delayInputLock.lock();
							delayInput.add(m);
							delayInputLock.unlock();
						} else {
							inputQueueLock.lock();
							if (action == ACTION.DUPLICATE)
								inputMessage.add(m);
							inputMessage.add(m);
							delayInputLock.lock();
							while (!delayInput.isEmpty())
								inputMessage.add(delayInput.removeFirst());
							delayInputLock.unlock();
							inputQueueLock.unlock();
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} finally {
					incoming.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
