package processing.app.screens.statics;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.Reccollab;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.controls.GTextArea;
import processing.event.MouseEvent;

import java.util.List;

public class ProgressStatics extends BaseObject {

    static GTextArea SubOption1Text;
    GLabel OptionLabel;
    GLabel SubOptionLabel1;

    public ProgressStatics() {
        super();
        setParent("Master");
    }

    public static void SetProcess(List<String> processes) {
        if (SubOption1Text == null)
            return;

        SubOption1Text.setText("");
        for (String word : processes) {
            String tmpStr = SubOption1Text.getText();
            SubOption1Text.setText(word + "\n" + tmpStr);
        }

    }

    @Override
    public void Awake() {
        OptionLabel = new GLabel(Reccollab.app, 64, 88, 72, 16);
        OptionLabel.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
        OptionLabel.setText(Lang.PROCESS);
        OptionLabel.setOpaque(false);
        OptionLabel.setVisible(false);
        SubOptionLabel1 = new GLabel(Reccollab.app, 64, 112, 136, 16);
        SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
        SubOptionLabel1.setText(Lang.MORE_USED);
        SubOptionLabel1.setOpaque(false);
        SubOptionLabel1.setVisible(false);
        SubOption1Text = new GTextArea(Reccollab.app, 204, 112, 350, 200, G4P.SCROLLBARS_NONE);
        SubOption1Text.setOpaque(true);
        SubOption1Text.setVisible(false);
        SubOption1Text.setEnabled(false);

    }

    @Override
    public void Update() {}

    @Override
    public void SetViewActive(boolean state) {
        OptionLabel.setVisible(state);
        SubOptionLabel1.setVisible(state);
        SubOption1Text.setVisible(state);

    }

    @Override
    public void Mouse(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void Exit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Init() {
        // TODO Auto-generated method stub

    }

}
