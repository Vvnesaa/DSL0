
public class Rule {
	private String Action = null;
	private String Src = null;
	private String Dest = null;
	private String Kind = null;
	private int ID = -1;
	private int Nth = -1;
	
	public String getAction() {
		return Action;
	}
	
	public void setAction(String Action) {
		this.Action = Action;
	}
	
	public String getSrc() {
		return Src;
	}
	
	public void setSrc(String Src) {
		this.Src = Src;
	}
	
	public String getDest() {
		return Dest;
	}
	
	public void setDest(String Dest) {
		this.Dest = Dest;
	}
	
	public String getKind() {
		return Kind;
	}
	
	public void setKind(String Kind) {
		this.Kind = Kind;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getNth() {
		return Nth;
	}
	
	public void setNth(int Nth) {
		this.Nth = Nth;
	}
}
