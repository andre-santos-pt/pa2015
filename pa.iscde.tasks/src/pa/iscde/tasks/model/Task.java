package pa.iscde.tasks.model;

public class Task {

	public enum PRIORITY {
		HIGH, MEDIUM, LOW;
	}

	public enum TYPE {
		BUG, REFACTOR, ANALISYS;
	}

	private final PRIORITY priority;
	private final TYPE type;
	private final String msg;

	public PRIORITY getPriority() {
		return priority;
	}

	public TYPE getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}

	// Constructors...
	public Task(String filename, String absPath, Integer linePos, PRIORITY priority, TYPE type, String msg) {
		this.priority = priority;
		this.type = type;
		this.msg = msg;
	}

	// Constructors...
	public Task(PRIORITY priority, TYPE type, String msg) {
		this.priority = priority;
		this.type = type;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "prioraty:" + priority + ",Type:" + type + " Msg: " + msg;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Task))
			return false;

		final Task other = (Task) obj;

		return priority.equals(other.priority) && type.equals(other.type) && msg.equals(other.msg);
	}

}
