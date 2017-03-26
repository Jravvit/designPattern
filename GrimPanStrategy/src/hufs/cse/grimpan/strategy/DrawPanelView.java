/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.strategy;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class DrawPanelView 
	extends JPanel implements MouseInputListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GrimPanModel model = null;
	private JPanel frm = new JPanel();
	private JPopupMenu popupMenu = null;
	private JMenuItem deleteItem = null;
	private MouseEvent mEvent = null;
	
	public DrawPanelView(GrimPanModel m){
		this.model = m;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		popupMenu = new JPopupMenu();
		deleteItem = new JMenuItem("delete");
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tmpState = model.getEditState();
				model.setEditState(Util.SHAPE_DELETE);
				model.sb.performMouseRightClicked(mEvent);
				model.setEditState(tmpState);
				repaint();
			}
		});
		popupMenu.add(deleteItem);
		frm.add(popupMenu);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (GrimShape grimShape:model.shapeList){
			grimShape.draw(g2);
		}
		
		if (model.curDrawShape != null){
			g2.setColor(model.getShapeStrokeColor());
			g2.setStroke(new BasicStroke(model.getShapeStrokeWidth()));
			g2.draw(model.curDrawShape);
			if (model.isShapeFill() 
					&& model.getEditState()!=Util.SHAPE_PENCIL){
				g2.setColor(model.getShapeFillColor());
				g2.fill(model.curDrawShape);
			}
		}
		model.setShapeCount();
	}
	public void mouseClicked(MouseEvent ev) {
		
		
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}
	public void mouseMoved(MouseEvent ev) {

	}

	public void mousePressed(MouseEvent ev) {
		if (SwingUtilities.isLeftMouseButton(ev)){
			model.sb.performMousePressed(ev);
		} 
		repaint();
	}

	public void mouseReleased(MouseEvent ev) {
		if (SwingUtilities.isLeftMouseButton(ev)){
			model.sb.performMouseReleased(ev);
		} else if (SwingUtilities.isRightMouseButton(ev)){
			popupMenu.show((Component) ev.getSource(),ev.getX(),ev.getY());
			this.mEvent = ev;
		}
		repaint();

	}

	public void mouseDragged(MouseEvent ev) {
		if (SwingUtilities.isLeftMouseButton(ev)){
			model.sb.performMouseDragged(ev);
		}
		repaint();

	}

}
