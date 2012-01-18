package lab0;

public class Node {
	private String Name;
	private byte[] IP = new byte[4];
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
		// TODO MATIC NUMBER
		for (int i = 0; i < 4; ++i) {
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
