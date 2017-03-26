/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.strategy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.border.EtchedBorder;

/**
 * @author cskim
 *
 */
public class GrimPanFrameMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GrimPanFrameMain thisClass = this;
	private JPanel contentPane;
	private DrawPanelView drawPanelView = null;
	private GrimPanModel model = null;

	public JCheckBoxMenuItem jcmiFill = null;
	private JFileChooser jFileChooser1 = null;
	private JFileChooser jFileChooser2 = null;
	private String defaultDir = "/home/cskim/temp/";
	
	
	JFrame frm = new JFrame();
	JPopupMenu popupMenu;
	JMenuItem deleteItem;
	
	JMenuBar panMenuBar;	
	JMenu fileMenu;
	JMenuItem jmiNew;
	JMenuItem jmiOpen;
	JMenuItem jmiSave;
	JMenuItem jmiSaveAs;
	JMenuItem jmiSaveAsSvg;
	JMenuItem jmiExit;
	JMenu shapeMenu;
	JRadioButtonMenuItem rdbtnmntmLine;
	JRadioButtonMenuItem rdbtnmntmPen;	
	JRadioButtonMenuItem rdbtnmntmPoly;
	JRadioButtonMenuItem rdbtnmntmRegular;
	JRadioButtonMenuItem rdbtnmntmOval;
	JMenu editMenu;	
	JMenuItem jmiRemove;
	JMenuItem jmiMove;
	JMenu settingMenu;
	JMenuItem jmiStroke;
	JMenuItem jmiStorkeColor;	
	JMenuItem jmiFillColor;

	JMenu helpMenu;	
	JMenuItem jmiAbout;

	JMenu rightClickMenu;
	JMenuItem jmiRmove;
	
	ButtonGroup btnGroup = new ButtonGroup();
	JPanel statusPanel;
	JLabel shapeLbl;
	JLabel sizeLbl;
	JLabel countLbl;
	JMenuItem rmiAdd;
	JLabel messageLbl;

	public JLabel modeLBL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrimPanFrameMain frame = new GrimPanFrameMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GrimPanFrameMain() {
		setTitle("그림");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);

		panMenuBar = new JMenuBar();
		setJMenuBar(panMenuBar);

		fileMenu = new JMenu("File  ");
		panMenuBar.add(fileMenu);

		jmiNew = new JMenuItem("New  ");
		jmiNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.shapeList.clear();
				model.curDrawShape = null;
				model.polygonPoints.clear();
				drawPanelView.repaint();
			}
		});
		fileMenu.add(jmiNew);

		jmiOpen = new JMenuItem("Open ");
		jmiOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAction();
			}
		});
		fileMenu.add(jmiOpen);

		jmiSave = new JMenuItem("Save ");
		jmiSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAction();
			}
		});
		fileMenu.add(jmiSave);

		jmiSaveAs = new JMenuItem("Save  As...");
		jmiSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAsAction();
			}
		});
		fileMenu.add(jmiSaveAs);

		jmiExit = new JMenuItem("Exit");
		jmiExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(jmiExit);

		shapeMenu = new JMenu("Shape  ");
		panMenuBar.add(shapeMenu);

		rdbtnmntmLine = new JRadioButtonMenuItem("선분");
		rdbtnmntmLine.setSelected(true);
		rdbtnmntmLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_LINE);
				}
			}
		});
		shapeMenu.add(rdbtnmntmLine);

		rdbtnmntmPen = new JRadioButtonMenuItem("연필");
		rdbtnmntmPen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_PENCIL);
				}
			}
		});
		shapeMenu.add(rdbtnmntmPen);

		rdbtnmntmPoly = new JRadioButtonMenuItem("다형");
		rdbtnmntmPoly.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_POLYGON);
				}
			}
		});
		shapeMenu.add(rdbtnmntmPoly);

		rdbtnmntmRegular = new JRadioButtonMenuItem("다각형");
		rdbtnmntmRegular.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_REGULAR);
					if (model.curDrawShape != null){
						model.shapeList
						.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
								model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
						model.curDrawShape = null;
					}
					Object[] possibleValues = { 
							"3", "4", "5", "6", "7",
							"8", "9", "10", "11", "12"
					};
					Object selectedValue = JOptionPane.showInputDialog(null,
							"Choose one", "Input",
							JOptionPane.INFORMATION_MESSAGE, null,
							possibleValues, possibleValues[0]);
					model.setNPolygon(Integer.parseInt((String)selectedValue));
					//drawPanelView.repaint();
				}
			}
		});
		shapeMenu.add(rdbtnmntmRegular);

		rdbtnmntmOval = new JRadioButtonMenuItem("타원형");
		rdbtnmntmOval.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(Util.SHAPE_OVAL);
					if (model.curDrawShape != null){
						model.shapeList
						.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
								model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
						model.curDrawShape = null;
					}
				}
			}
		});
		shapeMenu.add(rdbtnmntmOval);

		btnGroup.add(rdbtnmntmLine);
		btnGroup.add(rdbtnmntmPen);
		btnGroup.add(rdbtnmntmPoly);
		btnGroup.add(rdbtnmntmRegular);
		btnGroup.add(rdbtnmntmOval);		

		editMenu = new JMenu("Edit  ");
		panMenuBar.add(editMenu);

		rmiAdd = new JMenuItem("추가");
		editMenu.add(rmiAdd);

		jmiRemove = new JMenuItem("제거");
		editMenu.add(jmiRemove);

		jmiMove = new JMenuItem("이동");
		jmiMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setEditState(Util.EDIT_MOVE);
				if (model.curDrawShape != null){
					model.shapeList
					.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
							model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
					model.curDrawShape = null;
				}
				drawPanelView.repaint();
			}
		});
		editMenu.add(jmiMove);

		settingMenu = new JMenu("Setting");
		panMenuBar.add(settingMenu);

		jmiStroke = new JMenuItem("선두께");
		jmiStroke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputVal = JOptionPane.showInputDialog("선두께", "1");
				if (inputVal!=null){
					model.setShapeStrokeWidth(Float.parseFloat(inputVal));
				}
				else {
					model.setShapeStrokeWidth(1f);
				}
			}
		});
		settingMenu.add(jmiStroke);

		jmiStorkeColor = new JMenuItem("테두리색");
		jmiStorkeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = 
						JColorChooser.showDialog(thisClass, 
								"Choose a color",
								Color.black);					
				if (color!=null){
					model.setShapeStrokeColor(color);
				}
				else {
					model.setShapeStrokeColor(Color.black);
				}
				drawPanelView.repaint();
			}
		});
		settingMenu.add(jmiStorkeColor);

		jcmiFill = new JCheckBoxMenuItem("채움");
		jcmiFill.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				boolean fillState = jcmiFill.getState();
				model.setShapeFill(fillState);
				drawPanelView.repaint();
			}
		});
		settingMenu.add(jcmiFill);

		jmiFillColor = new JMenuItem("채움색");
		jmiFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = 
						JColorChooser.showDialog(thisClass, 
								"Choose a color",
								Color.black);					
				if (color!=null){
					model.setShapeFillColor(color);
				}
				else {
					model.setShapeFillColor(Color.black);
				}
				drawPanelView.repaint();
			}
		});
		settingMenu.add(jmiFillColor);

		helpMenu = new JMenu("Help  ");
		panMenuBar.add(helpMenu);

		jmiAbout = new JMenuItem("About");
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(thisClass,
						"GrimPan Ver0.2.2 \nProgrammed by cskim, cse, hufs.ac.kr", 
						"About", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		helpMenu.add(jmiAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		model = new GrimPanModel(this);

		drawPanelView = new DrawPanelView(model);
		drawPanelView.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				String sizeText = String.format("Size: %d x %d  ", 
						drawPanelView.getSize().width, drawPanelView.getSize().height);
				thisClass.sizeLbl.setText(sizeText);
			}
		});
		contentPane.add(drawPanelView, BorderLayout.CENTER);

		statusPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) statusPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		statusPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(statusPanel, BorderLayout.SOUTH);

		sizeLbl = new JLabel("Size:               ");
		statusPanel.add(sizeLbl);

		shapeLbl = new JLabel("Shape:              ");
		shapeLbl.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(shapeLbl);
		
		countLbl = new JLabel("Count:             ");
		countLbl.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(countLbl);
		
		modeLBL = new JLabel("Mode:               ");
		statusPanel.add(modeLBL);

		messageLbl = new JLabel("  ");
		statusPanel.add(messageLbl);

		jFileChooser1 = new JFileChooser(defaultDir);
		jFileChooser1.setDialogTitle("Open Saved GrimPan");

		jFileChooser2 = new JFileChooser(defaultDir);
		jFileChooser2.setDialogType(JFileChooser.SAVE_DIALOG);
		jFileChooser2.setDialogTitle("Save As ...");
		
		model.setEditState(Util.SHAPE_LINE);
	}
	private void openAction() {
		if (jFileChooser1.showOpenDialog(this) ==
				JFileChooser.APPROVE_OPTION) {
			File selFile = jFileChooser1.getSelectedFile();
			model.readShapeFromSaveFile(selFile);
			model.setSaveFile(selFile);
			drawPanelView.repaint();
		}
	}
	private void saveAction() {
		if (model.getSaveFile()==null){
			model.setSaveFile(new File(defaultDir+"noname.grm"));
		}
		File selFile = model.getSaveFile();
		model.saveGrimPanData(selFile);	
	}
	private void saveAsAction() {
		if (jFileChooser2.showSaveDialog(this) ==
				JFileChooser.APPROVE_OPTION) {
			File selFile = jFileChooser2.getSelectedFile();
			model.setSaveFile(selFile);
			model.saveGrimPanData(selFile);
		}
	}

}
