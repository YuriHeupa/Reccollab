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

import processing.core.PApplet;

import java.awt.*;
import java.awt.font.TextAttribute;

/**
 * Base class for any control that uses styled text.
 *
 * @author Peter Lager
 */
public abstract class GTextBase extends GAbstractControl {

    protected static final int TPAD = 2;
    protected static final int TPAD2 = TPAD * 2;
    protected static final int TPAD4 = TPAD * 4;

    /**
     * The styled text used by this control
     */
    public StyledString stext = null;

    protected Font localFont = G4P.globalFont;

    /**
     * Constructor
     *
     * @param theApplet
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     */
    public GTextBase(PApplet theApplet, float p0, float p1, float p2, float p3) {
        super(theApplet, p0, p1, p2, p3);
    }

    /**
     * Load the styled string to be used by this control.
     *
     * @param fname the name of the file to use
     * @return true if loaded successfully else false
     */
    public boolean loadText(String fname) {
        StyledString ss = StyledString.load(winApp, fname);
        if (ss != null) {
            setStyledText(ss);
            stext.startIdx = stext.endIdx = -1;
            bufferInvalid = true;
            return true;
        }
        return false;
    }

    /**
     * Save the styled text used by this control to file.
     *
     * @param fname the name of the file to use
     * @return true if saved successfully else false
     */
    public boolean saveText(String fname) {
        if (stext != null) {
            stext.startIdx = stext.endIdx = -1;
            StyledString.save(winApp, stext, fname);
            return true;
        }
        return false;
    }

    /**
     * Set the font to be used in this control
     *
     * @param font AWT font to use
     */
    public void setFont(Font font) {
        if (font != null && font != localFont && buffer != null) {
            localFont = font;
            buffer.g2.setFont(localFont);
            bufferInvalid = true;
        }
    }

    /**
     * Clear <b>all</b> applied styles from the whole text.
     */
    public void setTextPlain() {
        stext.clearAttributes();
        bufferInvalid = true;
    }

    /**
     * Make the selected characters bold. <br>
     * Characters affected are >= start and < end
     *
     * @param start the first character to style
     * @param end   the first character not to style
     */
    public void setTextBold(int start, int end) {
        addAttributeImpl(G4P.WEIGHT, G4P.WEIGHT_BOLD, start, end);
    }

    /**
     * Make all the characters bold.
     */
    public void setTextBold() {
        addAttributeImpl(G4P.WEIGHT, G4P.WEIGHT_BOLD);
    }

    /**
     * Make the selected characters italic. <br>
     * Characters affected are >= start and < end
     *
     * @param start the first character to style
     * @param end   the first character not to style
     */
    public void setTextItalic(int start, int end) {
        addAttributeImpl(G4P.POSTURE, G4P.POSTURE_OBLIQUE, start, end);
    }

    /**
     * Make all the characters italic.
     */
    public void setTextItalic() {
        addAttributeImpl(G4P.POSTURE, G4P.POSTURE_OBLIQUE);
    }

    /**
     * Make the selected characters underlined. <br>
     * Characters affected are >= start and < end
     *
     * @param start the first character to style
     * @param end   the first character not to style
     */
    public void setTextUnderlined(int start, int end) {
        addAttributeImpl(G4P.UNDERLINE, G4P.UNDERLINE_ON, start, end);
    }

    /**
     * Make all the characters underlined.
     */
    public void setTextUnderlined() {
        addAttributeImpl(G4P.UNDERLINE, G4P.UNDERLINE_ON);
    }

    /**
     * Get the text used for this control.
     *
     * @return the displayed text without styling
     */
    public StyledString getStyledText() {
        return stext;
    }

    /**
     * Allows the user to provide their own styled text for this component
     *
     * @param ss
     */
    public void setStyledText(StyledString ss) {
        if (ss != null) {
            stext = ss;
            stext.setWrapWidth((int) width - TPAD2);
            bufferInvalid = true;
        }
    }

    /**
     * Get the text used for this control.
     *
     * @return the displayed text without styling
     */
    public String getText() {
        return stext.getPlainText();
    }

    /**
     * Set the text to be displayed.
     *
     * @param text
     */
    public void setText(String text) {
        if (text == null || text.length() == 0)
            text = " ";
        stext = new StyledString(text, Integer.MAX_VALUE);
        bufferInvalid = true;
    }

    /**
     * Apply the style to the whole text
     *
     * @param style the style attribute
     * @param value 'amount' to apply
     */
    protected void addAttributeImpl(TextAttribute style, Object value) {
        stext.addAttribute(style, value);
        bufferInvalid = true;
    }

    /**
     * Apply the style to a portion of the strin
     *
     * @param style the style attribute
     * @param value 'amount' to apply
     * @param s     first character to be included for styling
     * @param e     the first character not to be included for stylin
     */
    protected void addAttributeImpl(TextAttribute style, Object value, int s,
                                    int e) {
        if (s >= e)
            return;
        if (s < 0)
            s = 0;
        if (e > stext.length())
            e = stext.length();
        stext.addAttribute(style, value, s, e);
        bufferInvalid = true;
    }

}
