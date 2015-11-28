package pa.iscde.tasks.model.parser;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import pa.iscde.tasks.model.Task;
import pa.iscde.tasks.model.Task.PRIORITY;
import pa.iscde.tasks.model.Task.TYPE;

public class TestTaskExtractor {

	@Test
	public void whenThereArentTasksAnEmptyListShouldBeReturned() {
		final TaskExtractor ext = new TaskExtractor("// singleton instance");
		ext.process();
		Assert.assertEquals(new ArrayList<>(), ext.getTaskList());
	}

	@Test
	public void extractingASimpleTaskaSimpleTask() {
		final Task expected = new Task(PRIORITY.LOW, TYPE.BUG, "Not that important");
		final TaskExtractor ext = new TaskExtractor("//#BUG,LOW (Not that important)");
		ext.process();

		Assert.assertEquals(expected, ext.getTaskList().get(0));
		Assert.assertEquals(1, ext.getTaskList().size());
		Assert.assertEquals(new Integer(2), ext.getTaksRelativePostion().get(0));

	}
}
