package pa.iscde.tasks.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import pa.iscde.tasks.control.TasksActivator;
import pa.iscde.tasks.model.parser.CommentExtractor;
import pa.iscde.tasks.model.parser.FileToString;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public enum ModelProvider {

	INSTANCE;

	private final List<TaskOccurence> taskList;

	private ModelProvider() {
		taskList = new ArrayList<>();
	}

	public List<TaskOccurence> getTasksList() {
		taskList.clear();

		try {
			handleSources(TasksActivator.getBrowserServices().getRootPackage().getChildren(), taskList);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return taskList;
	}

	private void handleSources(SortedSet<SourceElement> sources, List<TaskOccurence> tasks) throws IOException {
		// TODO - Convert to Visitor?
		for (SourceElement e : sources) {
			if (e.isPackage())
				handleSources(((PackageElement) e).getChildren(), tasks);
			else
				taskList.addAll(new CommentExtractor(new FileToString(e.getFile()).parse(), e.getName(),
						e.getFile().getAbsolutePath()).getCommentDetails());

		}
	}
	/*
	 * // taskViewer.setInput(TEST_ARRAY);
	 * 
	 * // Tasks #OLA "I don't like this"
	 * 
	 * ProjectBrowserServices serv = TasksActivator.getBrowserServices();
	 * 
	 * serv.getRootPackage().traverse(new Visitor() {
	 * 
	 * @Override public boolean visitPackage(PackageElement packageElement) {
	 * System.out.println("pkg: " + packageElement.getName());
	 * 
	 * return true; }
	 * 
	 * @Override public void visitClass(ClassElement classElement) {
	 * 
	 * System.out.println(classElement.getFile().getPath());
	 * 
	 * /* if (classElement.getName().equals("PidescoActivator.java")) { try {
	 * final Scanner s = new Scanner(classElement.getFile()); while
	 * (s.hasNextLine()) { System.out.println(s.nextLine());
	 * 
	 * } } catch (FileNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 *
	 * } });
	 * 
	 * System.out.println("In TableView: ");
	 * 
	 * 
	 */
}
