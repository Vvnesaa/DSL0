import java.io.Serializable;


public class Message implements Serializable {
	
	private int id;
	private String src;
	private String dest;
	private String kind;
	private Object data;

	public Message(String src, String dest, String kind, Object data) {
		this.id = 0;
		this.src = src;
		this.dest = dest;
		this.kind = kind;
		this.data = data;
	}
	
	public void set_id(int id) {
		this.id = id;
	}
}
