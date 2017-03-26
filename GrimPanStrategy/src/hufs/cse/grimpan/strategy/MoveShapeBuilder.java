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
public class MoveShapeBuilder implements ShapeBuilder {

	GrimPanModel model = null;
	
	public MoveShapeBuilder(GrimPanModel model){
		this.model = model;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);

		getSelectedShape();
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);

		endShapeMove();
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseDragged(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);

		moveShapeByMouse();
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
		if (selIndex != -1){
			model.setLastMousePosition(model.getClickedMousePosition());
			Color scolor = shape.getGrimStrokeColor();
			Color fcolor = shape.getGrimFillColor();
			shape.setGrimStrokeColor(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 127));
			shape.setGrimFillColor(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 127));
		}
		model.setSelectedShape(selIndex);
	}
	private void moveShapeByMouse(){
		int selIndex = model.getSelectedShape();
		GrimShape shape = null;
		if (selIndex != -1){
			shape = model.shapeList.get(selIndex);
			shape.translate(
					(float)(model.getMousePosition().x-model.getLastMousePosition().x), 
					(float)(model.getMousePosition().y-model.getLastMousePosition().y));
		}
	}
	private void endShapeMove(){
		int selIndex = model.getSelectedShape();
		GrimShape shape = null;
		if (selIndex != -1){
			shape = model.shapeList.get(selIndex);
			Color scolor = shape.getGrimStrokeColor();
			Color fcolor = shape.getGrimFillColor();
			shape.setGrimStrokeColor(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue()));
			shape.setGrimFillColor(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue()));
		}
	}
	@Override
	public void performMouseRightClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
