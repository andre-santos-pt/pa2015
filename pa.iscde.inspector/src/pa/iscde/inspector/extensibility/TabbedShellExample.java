package pa.iscde.inspector.extensibility;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class TabbedShellExample {

  Display d;

  Shell s;

  TabbedShellExample() {
    d = new Display();
    s = new Shell(d);

    s.setSize(250, 200);
    
    s.setText("A Tabbed Shell Example");
    s.setLayout(new FillLayout());

    TabFolder tf = new TabFolder(s, SWT.BORDER);

    TabItem ti1 = new TabItem(tf, SWT.BORDER);
    ti1.setText("Option Group");
    ti1.setControl(new GroupExample(tf, SWT.SHADOW_ETCHED_IN));

    TabItem ti2 = new TabItem(tf, SWT.BORDER);
    ti2.setText("Grid");
    ti2.setControl(new GridComposite(tf));

    TabItem ti3 = new TabItem(tf, SWT.BORDER);
    ti3.setText("Text");
    Composite c1 = new Composite(tf, SWT.BORDER);
    c1.setLayout(new FillLayout());
    Text t = new Text(c1, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
    ti3.setControl(c1);

    TabItem ti4 = new TabItem(tf, SWT.BORDER);
    ti4.setText("Settings");
    Composite c2 = new Composite(tf, SWT.BORDER);
    c2.setLayout(new RowLayout());
    Text t2 = new Text(c2, SWT.BORDER | SWT.SINGLE | SWT.WRAP
        | SWT.V_SCROLL);
    Button b = new Button(c2, SWT.PUSH | SWT.BORDER);
    b.setText("Save");
    ti4.setControl(c2);

    s.open();
    while (!s.isDisposed()) {
      if (!d.readAndDispatch())
        d.sleep();
    }
    d.dispose();
  }
}

class GroupExample extends Composite {

  final Button b1;

  final Button b2;

  final Button b3;

  public GroupExample(Composite c, int style) {
    super(c, SWT.NO_BACKGROUND);
    this.setSize(110, 75);
    this.setLayout(new FillLayout());
    final Group g = new Group(this, style);
    g.setSize(110, 75);
    g.setText("Options Group");
    b1 = new Button(g, SWT.RADIO);
    b1.setBounds(10, 20, 75, 15);
    b1.setText("Option One");
    b2 = new Button(g, SWT.RADIO);
    b2.setBounds(10, 35, 75, 15);
    b2.setText("Option Two");
    b3 = new Button(g, SWT.RADIO);
    b3.setBounds(10, 50, 80, 15);
    b3.setText("Option Three");
  }

  public String getSelected() {
    if (b1.getSelection())
      return "Option One";
    if (b2.getSelection())
      return "Option Two";
    if (b3.getSelection())
      return "Option Three";
    return "None Selected";
  }

}

class GridComposite extends Composite {

  public GridComposite(Composite c) {
    super(c, SWT.BORDER);
    GridLayout gl = new GridLayout();
    gl.numColumns = 3;
    this.setLayout(gl);
    final Label l1 = new Label(this, SWT.BORDER);
    l1.setText("Column One");
    final Label l2 = new Label(this, SWT.BORDER);
    l2.setText("Column Two");
    final Label l3 = new Label(this, SWT.BORDER);
    l3.setText("Column Three");
    final Text t1 = new Text(this, SWT.SINGLE | SWT.BORDER);
    final Text t2 = new Text(this, SWT.SINGLE | SWT.BORDER);
    final Text t3 = new Text(this, SWT.SINGLE | SWT.BORDER);
    final Text t4 = new Text(this, SWT.SINGLE | SWT.BORDER);
    final Text t5 = new Text(this, SWT.SINGLE | SWT.BORDER);
    final Text t6 = new Text(this, SWT.SINGLE | SWT.BORDER);

    GridData gd = new GridData();
    gd.horizontalAlignment = GridData.CENTER;
    l1.setLayoutData(gd);

    gd = new GridData();
    gd.horizontalAlignment = GridData.CENTER;
    l2.setLayoutData(gd);

    gd = new GridData();
    gd.horizontalAlignment = GridData.CENTER;
    l3.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    t1.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    t2.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    t3.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    t4.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    t5.setLayoutData(gd);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    t6.setLayoutData(gd);
  }

  public static void main(String[] argv) {
    new TabbedShellExample();
  }
}