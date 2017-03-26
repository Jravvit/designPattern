/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 * @author cskim
 *
 */
public interface ShapeBuilder {

	void performMousePressed(MouseEvent e);
	void performMouseReleased(MouseEvent e);
	void performMouseDragged(MouseEvent e);
	void performMouseRightClicked(MouseEvent e);
}
