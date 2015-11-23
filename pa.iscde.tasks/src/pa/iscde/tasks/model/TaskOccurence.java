package pa.iscde.tasks.model;

public class TaskOccurence {

	private final Task task;
	private final String filename;
	private final String absPath;
	private final Integer linePos;

	public TaskOccurence(Task task, String filename, String absPath, Integer linePos) {
		this.task = task;
		this.filename = filename;
		this.absPath = absPath;
		this.linePos = linePos;
	}

	public Task getTask() {
		return task;
	}

	public String getFilename() {
		return filename;
	}

	public String getAbsPath() {
		return absPath;
	}

	public Integer getLinePos() {
		return linePos;
	}

	@Override
	public String toString() {
		return "Filename:" + filename + ",AbsPath:" + absPath + ",LinePos:" + linePos + "Task: " + task.toString();
	}

}
