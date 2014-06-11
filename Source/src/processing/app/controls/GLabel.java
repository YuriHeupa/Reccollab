/*
  Part of the GUI for Processing library 
  	http://www.lagers.org.uk/g4p/index.html
	http://gui4processing.googlecode.com/svn/trunk/

  Copyright (c) 2008-12 Peter Lager

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

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.util.LinkedList;

import processing.app.controls.HotSpot.HSrect;
import processing.app.controls.StyledString.TextLayoutInfo;
import processing.core.PApplet;
import processing.core.PGraphicsJava2D;
import processing.event.MouseEvent;

/**
 * The label component.
 * 
 * This control can display text with/without an icon.
 * 
 * @author Peter Lager
 * 
 */
public class GLabel extends GTextIconAlignBase {

	// Mouse over status
	protected int status = 0;
	
	public GLabel(PApplet theApplet, float p0, float p1, float p2, float p3) {
		this(theApplet, p0, p1, p2, p3, "    ");
	}

	/**
	 * Create a label control.
	 * 
	 * use setIcon to add an icon
	 * 
	 * @param theApplet
	 * @param p0
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param text
	 */
	public GLabel(PApplet theApplet, float p0, float p1, float p2, float p3,
			String text) {
		super(theApplet, p0, p1, p2, p3);
		// The image buffer is just for the typing area
		buffer = (PGraphicsJava2D) winApp.createGraphics((int) width,
				(int) height, PApplet.JAVA2D);
		buffer.g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		buffer.rectMode(PApplet.CORNER);
		buffer.g2.setFont(localFont);
		setText(text);
		opaque = false;


		// ========================================================================
		// Setup the hotspots
		hotspots = new HotSpot[] { new HSrect(1, 0, 0, width, height)};
		// ========================================================================


		createEventHandler(G4P.sketchApplet, "handleLabelEvents",
				new Class<?>[] { GLabel.class, GEvent.class },
				new String[] { "label", "event" });
		// Now register control with applet
		registeredMethods = DRAW_METHOD | MOUSE_METHOD;
		cursorOver = ARROW;
		G4P.addControl(this);
	}

	public void draw() {
		if (!visible)
			return;

		// Update buffer if invalid
		updateBuffer();
		winApp.pushStyle();

		winApp.pushMatrix();
		// Perform the rotation
		winApp.translate(cx, cy);
		winApp.rotate(rotAngle);
		// Move matrix to line up with top-left corner
		winApp.translate(-halfWidth, -halfHeight);
		// Draw buffer
		winApp.imageMode(PApplet.CORNER);
		if (alphaLevel < 255)
			winApp.tint(TINT_FOR_ALPHA, alphaLevel);
		winApp.image(buffer, 0, 0);
		winApp.popMatrix();

		winApp.popStyle();
	}

	

	/**
	 * 
	 * When a mouse button is clicked on a GLabel it generates the
	 * GEvent.CLICKED event. If you also want the button to generate
	 * GEvent.PRESSED and GEvent.RELEASED events then you need the following
	 * statement.<br>
	 * 
	 * <pre>
	 * btnName.fireAllEvents(true);
	 * </pre>
	 * 
	 * <br>
	 * 
	 * <pre>
	 * void handleLabelEvents(void handleLabelEvents(GLabel label, GEvent event) {
	 *   if(label == labelName && event == GEvent.CLICKED){
	 *        int buttonState = labelName.stateValue();
	 *    }
	 * </pre>
	 * 
	 * <br>
	 * Where
	 * 
	 * <pre>
	 * <b>btnName</b>
	 * </pre>
	 * 
	 * is the GLabel identifier (variable name) <br>
	 * <br>
	 * 
	 */
	public void mouseEvent(MouseEvent event) {
		if (!visible || !enabled || !available)
			return;

		calcTransformedOrigin(event.getX(), event.getY());
		currSpot = whichHotSpot(ox, oy);

		if (currSpot >= 0 || focusIsWith == this)
			cursorIsOver = this;
		else if (cursorIsOver == this)
			cursorIsOver = null;

		switch (event.getAction()) {
		case MouseEvent.PRESS:
			if (focusIsWith != this && currSpot >= 0 && z > focusObjectZ()) {
				dragging = false;
				status = PRESS_CONTROL;
				takeFocus();
				bufferInvalid = true;
			}
			break;
		case MouseEvent.CLICK:
			// No need to test for isOver() since if the component has focus
			// and the mouse has not moved since MOUSE_PRESSED otherwise we
			// would not get the Java MouseEvent.MOUSE_CLICKED event
			if (focusIsWith == this) {
				status = OFF_CONTROL;
				bufferInvalid = true;
				loseFocus(null);
				dragging = false;
				fireEvent(this, GEvent.CLICKED);
			}
			break;
		case MouseEvent.RELEASE:
			// if the mouse has moved then release focus otherwise
			// MOUSE_CLICKED will handle it
			if (focusIsWith == this && dragging) {
				dragging = false;
				loseFocus(null);
				status = OFF_CONTROL;
				bufferInvalid = true;
			}
			break;
		case MouseEvent.MOVE:
			int currStatus = status;
			// If dragged state will stay as PRESSED
			if (currSpot >= 0)
				status = OVER_CONTROL;
			else
				status = OFF_CONTROL;
			if (currStatus != status)
				bufferInvalid = true;
			break;
		case MouseEvent.DRAG:
			dragging = (focusIsWith == this);
			break;
		}
	}
	
	protected void updateBuffer() {
		if (bufferInvalid) {
			Graphics2D g2d = buffer.g2;
			// Get the latest lines of text
			LinkedList<TextLayoutInfo> lines = stext.getLines(g2d);
			bufferInvalid = false;
			buffer.beginDraw();
			// Back ground colour
			if (opaque == true)
				buffer.background(palette[6]);
			else
				buffer.background(buffer.color(255, 0));
			// Calculate text and icon placement
			calcAlignment();
			// If there is an icon draw it
			if (iconW != 0)
				buffer.image(bicon[0], siX, siY);
			float wrapWidth = stext.getWrapWidth();
			float sx = 0, tw = 0;
			buffer.translate(stX, stY);
			for (TextLayoutInfo lineInfo : lines) {
				TextLayout layout = lineInfo.layout;
				buffer.translate(0, layout.getAscent());
				switch (textAlignH) {
				case CENTER:
					tw = layout.getVisibleAdvance();
					tw = (tw > wrapWidth) ? tw - wrapWidth : tw;
					sx = (wrapWidth - tw) / 2;
					break;
				case RIGHT:
					tw = layout.getVisibleAdvance();
					tw = (tw > wrapWidth) ? tw - wrapWidth : tw;
					sx = wrapWidth - tw;
					break;
				case LEFT:
				case JUSTIFY:
				default:
					sx = 0;
				}
				// display text
				g2d.setColor(jpalette[2]);
				layout.draw(g2d, sx, 0);
				
				buffer.translate(0, layout.getDescent() + layout.getLeading());
			}
			buffer.endDraw();
		}
	}

}
