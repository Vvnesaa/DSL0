enum ACTION {
	NOTHING, DROP, DELAY, DUPLICATE,
}

public class Rule {
	// TODO: nth parameter is really ugly here, but
	// in some rules we need to know the "nth" info.
	// I think the "nth" goes with rules, so maybe we
	// should keep track of count in rules (not outside).
	public ACTION matches(Message message, int nth) {
		if (itemMatches(this.src, message.getDest())
				&& itemMatches(this.id, message.getId())
				&& itemMatches(this.nth, nth)
				&& itemMatches(this.kind, message.getKind())
				&& itemMatches(this.dest, message.getDest())) {
			return this.action;
		}
		return ACTION.NOTHING;
	}

	private boolean itemMatches(String expected, String actual) {
		// if expected is not set, then it should be automatically be true
		return expected == null || expected == actual;
	}
	private boolean itemMatches(int expected, int actual) {
		// if expected is not set, then it should be automatically be true
		return expected == -1 || expected == actual;
	}

	public ACTION getAction() {
		return action;
	}

	public void setAction(ACTION Action) {
		this.action = Action;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String Src) {
		this.src = Src;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String Dest) {
		this.dest = Dest;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String Kind) {
		this.kind = Kind;
	}

	public int getID() {
		return id;
	}

	public void setID(int ID) {
		this.id = ID;
	}

	public int getNth() {
		return nth;
	}

	public void setNth(int Nth) {
		this.nth = Nth;
	}

	// --- Data
	private ACTION action = ACTION.NOTHING;
	private String src = null;
	private String dest = null;
	private String kind = null;
	private int id = -1;
	private int nth = -1;

}
