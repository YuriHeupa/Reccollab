/*
  Part of the GUI for Processing library 
  	http://www.lagers.org.uk/g4p/index.html
	http://gui4processing.googlecode.com/svn/trunk/

  Copyright (c) 2013 Peter Lager

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
 */

package processing.app.controls;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * The preview panel class for the color selector.
 *
 * @author Peter Lager
 */
@SuppressWarnings("serial")
class ColorPreviewPanel extends JPanel implements ChangeListener {
    JLabel lblPrev, lblCurr;
    JLabel lblPrevColor, lblCurrColor;

    public ColorPreviewPanel(Color c) {
        setLayout(new FlowLayout());
        lblPrev = new JLabel("Initial Color");
        lblCurr = new JLabel("Current Color");
        lblPrevColor = new JLabel("                   ");
        lblPrevColor.setOpaque(true);
        lblPrevColor.setBackground(c);
        lblCurrColor = new JLabel("                   ");
        lblCurrColor.setOpaque(true);
        lblCurrColor.setBackground(c);
        add(lblCurr);
        add(lblCurrColor);
        add(lblPrevColor);
        add(lblPrev);
    }

    public void stateChanged(ChangeEvent e) {
        ColorSelectionModel csm = (ColorSelectionModel) e.getSource();
        lblCurrColor.setBackground(csm.getSelectedColor());
    }

    public void setPrevColor(Color pcol) {
        lblPrevColor.setBackground(pcol);
    }
}
