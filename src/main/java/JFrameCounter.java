import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFrameCounter extends JFrame {
	private JButton jButtonIncrement = new JButton();
	private JButton jButtondecrement = new JButton();
	private JButton jButtonundo = new JButton();
	private JButton jButtonredo = new JButton();
	private HistoryList historyList = new HistoryList();

	private Counter counter;
	private CounterHandler counterHandler=new CounterHandler();;

	public JFrameCounter() {
		try {
			jbInit();
			counter = new Counter();
			counter.setCounterState(new Level1(counter));
			GreenHandler greenHandler = new GreenHandler(null);
			RedHandler redHandler =new RedHandler(greenHandler);
			BlueHandler blueHandler= new BlueHandler(redHandler);
			OrangeHandler orangeHandler =new OrangeHandler(blueHandler);
			counterHandler.setChainOfHandlers(orangeHandler);
			counterHandler.handlePackage(new
					Counter(counter.getCount()));
			TextFrame textframe = new TextFrame();
			textframe.setVisible(true);
			RectFrame rectframe = new RectFrame();
			rectframe.setVisible(true);
			OvalFrame ovalframe = new OvalFrame();
			ovalframe.setVisible(true);
			counter.addObserver(textframe);
			counter.addObserver(rectframe);
			counter.addObserver(ovalframe);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JFrameCounter frame = new JFrameCounter();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(297, 169));
		jButtonIncrement.setText("+");
		jButtonIncrement.setBounds(new Rectangle(30, 25, 73, 22));
		jButtonIncrement.setActionCommand("increment");
		jButtonIncrement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonIncrement_actionPerformed(e);
			}
		});
		jButtondecrement.setText("-");
		jButtondecrement.setBounds(new Rectangle(155, 25, 73, 22));
		jButtondecrement.setActionCommand("decrement");
		jButtondecrement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtondecrement_actionPerformed(e);
			}
		});
		jButtonundo.setText("undo");
		jButtonundo.setBounds(new Rectangle(30, 80, 73, 22));
		jButtonundo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonundo_actionPerformed(e);
			}
		});
		jButtonredo.setText("redo");
		jButtonredo.setBounds(new Rectangle(155, 80, 73, 22));
		jButtonredo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonredo_actionPerformed(e);
			}
		});
		this.getContentPane().add(jButtonredo, null);
		this.getContentPane().add(jButtonundo, null);
		this.getContentPane().add(jButtondecrement, null);
		this.getContentPane().add(jButtonIncrement, null);
	}

	private void jButtonIncrement_actionPerformed(ActionEvent e) {
		IncrementCommand incrementcommand = new IncrementCommand(counter);
		incrementcommand.execute();
		historyList.addCommand(incrementcommand);
		counterHandler.handlePackage(new
				Counter(counter.getCount()));
	}

	private void jButtondecrement_actionPerformed(ActionEvent e) {
		DecrementCommand decrementcommand = new DecrementCommand(counter);
		decrementcommand.execute();
		historyList.addCommand(decrementcommand);
		counterHandler.handlePackage(new
				Counter(counter.getCount()));
	}

	private void jButtonundo_actionPerformed(ActionEvent e) {
		historyList.undo();
		counterHandler.handlePackage(new
				Counter(counter.getCount()));
	}

	private void jButtonredo_actionPerformed(ActionEvent e) {

		historyList.redo();
		counterHandler.handlePackage(new
				Counter(counter.getCount()));
	}
}
