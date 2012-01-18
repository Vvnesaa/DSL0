import java.io.Serializable;


public class Message implements Serializable {
	
	private int id;
	private String src;
	private String dest;
	private String kind;

	private Serializable data;

	public Message(String src, String dest, String kind, Serializable data) {
		this.id = 0;
		this.src = src;
		this.dest = dest;
		this.kind = kind;
		this.data = data;
	}
		
	public String getSrc() {
		return src;
	}
	
	public String getDest() {
		return dest;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getKind() {
		return this.kind;
	}
}
