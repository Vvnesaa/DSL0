package lab0;

public class Rule {
    public ACTION matches(Message message) {
        ACTION result = ACTION.NOTHING;
        if (itemMatches(this.src, message.getSrc())
                && itemMatches(this.id, message.getId())
                && itemMatches(this.kind, message.getKind())
                && itemMatches(this.dest, message.getDest())) {
            if (itemMatches(this.nth, counter)) {
                result = this.action;
            }
            counter += 1;
        }

        return result;
    }

    // -- Constructors
    public Rule(ACTION action) {
        this.action = action;
    }

    // -- Accessors
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

    public void setNth(int nth) {
        this.nth = nth;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    // --- Utilities
    private boolean itemMatches(String ruleField, String messageField) {
        // if expected is not set, then it should be automatically be true
        return ruleField == null || messageField.equals(ruleField);
    }

    private boolean itemMatches(int ruleField, int messageField) {
        // if expected is not set, then it should be automatically be true
        return ruleField == -1 || messageField == ruleField;
    }

    // --- Data
    private ACTION action = ACTION.NOTHING;
    private String src = null;
    private String dest = null;
    private String kind = null;
    private int id = -1;
    private int nth = -1;
    private int counter = 1;
}
