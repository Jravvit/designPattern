/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.strategy;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import javax.swing.SwingUtilities;

/**
 * @author cskim
 *
 */
public class PolygonShapeBuilder implements ShapeBuilder {

	GrimPanModel model = null;
	
	public PolygonShapeBuilder(GrimPanModel model){
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
		
			System.out.println(e);
		
		model.polygonPoints.add(p1);
		model.curDrawShape = Util.buildPath2DFromPoints(model.polygonPoints);
		if (e.isShiftDown()) {
			((Path2D)(model.curDrawShape)).closePath();
			model.polygonPoints.clear();
			model.shapeList
			.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
					model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
			model.curDrawShape = null;
		}
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);

		model.polygonPoints.add(p1);
		model.curDrawShape = Util.buildPath2DFromPoints(model.polygonPoints);
		if (e.isShiftDown()) {
			((Path2D)(model.curDrawShape)).closePath();
			model.polygonPoints.clear();
			model.shapeList
			.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
					model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
			model.curDrawShape = null;
		}
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseDragged(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);

	}

	@Override
	public void performMouseRightClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
