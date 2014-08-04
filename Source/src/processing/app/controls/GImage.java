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

import processing.app.controls.HotSpot.HSalpha;
import processing.app.controls.HotSpot.HSmask;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

public class GImage extends GAbstractControl {

    private static PImage[] errImage = null;

    protected PImage bimage = null;
    protected PImage mask = null;

    protected int status;

    /**
     * The control size will be set to the size of the image file. <br>
     * There is no alpha mask file..
     *
     * @param theApplet
     * @param p0
     * @param p1
     * @param sprite    the image file
     */
    public GImage(PApplet theApplet, float p0, float p1, String sprite) {
        this(theApplet, p0, p1, 0, 0, sprite, null);
    }

    /**
     * The control size will be set to the size of the image file <br>
     *
     * @param theApplet
     * @param p0
     * @param p1
     * @param sprite    the image file
     * @param fnameMask the alpha mask filename or null if no mask
     */
    public GImage(PApplet theApplet, float p0, float p1, String sprite,
                  String fnameMask) {
        this(theApplet, p0, p1, 0, 0, sprite, fnameMask);
    }

    /**
     * Create an image of the size specified by the parameters. <br>
     * The images will be resized to fit and there is no alpha mask file.
     *
     * @param theApplet
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @param sprite    the image file
     */
    public GImage(PApplet theApplet, float p0, float p1, float p2,
                  float p3, String sprite) {
        this(theApplet, p0, p1, p2, p3, sprite, null);
    }

    /**
     * Create an image of the size specified by the parameters. <br>
     * The images will be resized to fit.
     *
     * @param theApplet
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @param sprite    the image file
     * @param fnameMask the alpha mask filename or null if no mask
     */
    public GImage(PApplet theApplet, float p0, float p1, float p2,
                  float p3, String sprite, String fnameMask) {
        super(theApplet, p0, p1, p2, p3);
        if (errImage == null)
            errImage = ImageManager.loadImage(winApp, new String[]{
                    "err0.png", "err1.png", "err2.png"});

        // ========================================================================
        // First of all load images
        // Make sure we have an array of filenames
        if (sprite == null)
            sprite = "err0.png";
        bimage = ImageManager.loadImage(winApp, sprite);
        // Get mask image if available
        if (fnameMask != null)
            mask = winApp.loadImage(fnameMask);
        // ========================================================================

        // ========================================================================
        // Now decide if maintain default size or user size
        if (width > 0 && height > 0) { // User size
            if (bimage.width != width || bimage.height != height)
                bimage.resize((int) width, (int) height);
            if (mask != null && (mask.width != width || mask.height != height))
                mask.resize((int) width, (int) height);
        } else { // Default size
            resize(bimage.width, bimage.height);
        }
        // ========================================================================

        // ========================================================================
        // Setup the hotspaots
        if (mask != null) { // if we have a mask use it for the hot spot
            hotspots = new HotSpot[]{new HSmask(1, mask)};
        } else { // no mask then use alpha channel of the OFF image
            hotspots = new HotSpot[]{new HSalpha(1, 0, 0, bimage,
                    PApplet.CORNER)};
        }
        // ========================================================================

        z = -Z_SLIPPY;
        // Now register control with applet
        createEventHandler(G4P.sketchApplet, "handleSpriteEvents",
                new Class<?>[]{GImage.class, GEvent.class},
                new String[]{"sprite", "event"});
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
        winApp.image(bimage, 0, 0);
        winApp.popMatrix();
        winApp.popStyle();
    }

    /**
     * When a mouse button is clicked on a GImage it generates the
     * GEvent.CLICKED event. <br>
     * <p>
     * <pre>
     * void handleImageEvents(void handleImageEvents(GImage image, GEvent event) {
     *   if(image == imgName && event == GEvent.CLICKED){
     *        // code for image click event
     *    }
     * </pre>
     * <p>
     * <br>
     * Where
     * <p>
     * <pre>
     * <b>imgName</b>
     * </pre>
     * <p>
     * is the GImage identifier (variable name) <br>
     * <br>
     */
    public void mouseEvent(MouseEvent event) {
        if (!visible || !enabled || !available)
            return;

        calcTransformedOrigin(winApp.mouseX, winApp.mouseY);
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
                    fireEvent(this, GEvent.PRESSED);
                }
                break;
            case MouseEvent.CLICK:
                // No need to test for isOver() since if the component has focus
                // and the mouse has not moved since MOUSE_PRESSED otherwise we
                // would not get the Java MouseEvent.MOUSE_CLICKED event
                if (focusIsWith == this) {
                    status = OFF_CONTROL;
                    loseFocus(null);
                    dragging = false;
                    fireEvent(this, GEvent.CLICKED);
                }
                break;
            case MouseEvent.RELEASE:
                // if the mouse has moved then release focus otherwise
                // MOUSE_CLICKED will handle it
                if (focusIsWith == this && dragging) {
                    if (currSpot >= 0)
                        fireEvent(this, GEvent.CLICKED);
                    else {
                        fireEvent(this, GEvent.RELEASED);
                    }
                    dragging = false;
                    loseFocus(null);
                    status = OFF_CONTROL;
                }
                break;
            case MouseEvent.MOVE:
                // If dragged state will stay as PRESSED
                if (currSpot >= 0)
                    status = OVER_CONTROL;
                else
                    status = OFF_CONTROL;
                break;
            case MouseEvent.DRAG:
                dragging = (focusIsWith == this);
                break;
        }
    }

    /**
     * Enable or disable the ability of the component to generate mouse events.<br>
     * If the control is to be disabled when it is clicked then this will force
     * the mouse off image is used.
     *
     * @param enable true to enable else false
     */
    public void setEnabled(boolean enable) {
        super.setEnabled(enable);
        if (!enable)
            status = OFF_CONTROL;
    }

}
