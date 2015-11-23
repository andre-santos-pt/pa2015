package pa.iscde.tasks.model.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pa.iscde.tasks.model.Task;
import pa.iscde.tasks.model.Task.PRIORITY;
import pa.iscde.tasks.model.Task.TYPE;

public class TaskExtractor {

	private final Pattern taskRegex = Pattern.compile("#(BUG|REFACTOR|ANALISYS),(HIGH|MEDIUM|LOW) \\((.+)\\)");

	private final String text;

	// There should be a smarter way to return this relationship....
	private final List<Integer> postions;
	private final List<Task> tasks;

	public TaskExtractor(String text) {
		this.text = text;
		postions = new ArrayList<>();
		tasks = new ArrayList<>();
	}

	public void process() {
		final Matcher m = taskRegex.matcher(text);
		while (m.find()) {

			final TYPE type = TYPE.valueOf(m.group(1));
			final PRIORITY priority = PRIORITY.valueOf(m.group(2));
			final String msg = m.group(3);

			if (type != null && priority != null) {
				tasks.add(new Task(priority, type, msg));
				postions.add(m.start());
			}
		}
	}

	public List<Integer> getTaksRelativePostion() {
		return postions;
	}

	public List<Task> getTaskList() {
		return tasks;
	}

}
