/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.strategy;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * @author cskim
 *
 */
public class DeleteShapeBuilder implements ShapeBuilder {

	GrimPanModel model = null;
	
	public DeleteShapeBuilder(GrimPanModel model){
		this.model = model;
	}
	
	public void performMouseRightClicked(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);
		removeShape();
	}
	
	private void getSelectedShape(){
		int selIndex = -1;
		GrimShape shape = null;
		for (int i=model.shapeList.size()-1; i >= 0; --i){
			shape = model.shapeList.get(i);
			if (shape.contains(model.getMousePosition().getX(), model.getMousePosition().getY())){
				selIndex = i;
				break;
			}
		}
		model.setSelectedShape(selIndex);
	}
	
	private void removeShape() {
		try {
			int selIndex = -1;
			getSelectedShape();
			selIndex = model.getSelectedShape();
			model.shapeList.remove(selIndex);
		} catch (Exception e){
		}
	}
	
	@Override
	public void performMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
