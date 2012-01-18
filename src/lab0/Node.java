package lab0;

public class Node {
	private final int IP_PARTS_COUNT = 4;
	private String Name;
	private byte[] IP = new byte[IP_PARTS_COUNT];
	private int Port = -1;

	public String getName() {
		return Name;
	}
	
	public void setName(String Name) {
		this.Name = Name;
	}
	
	public byte[] getIP() {
		return IP;
	}
	
	public void setIP(String IP) {
		String[] parts = IP.split("\\.");
		for (int i = 0; i < IP_PARTS_COUNT; ++i) {
			byte number = (byte)Integer.parseInt(parts[i]);
			this.IP[i] = number;
		}
	}
	
	public int getPort() {
		return Port; 
	}
	
	public void setPort(int Port) {
		this.Port = Port;
	}
}
