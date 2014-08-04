package processing.app.screens.others;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.Reccollab;
import processing.app.controls.*;
import processing.app.screens.Master;
import processing.event.MouseEvent;

public class Warning extends BaseObject {

    GLabel Title;
    GTextArea WarningArea;

    public Warning() {
        super();
        setParent("Master");
    }

    @Override
    public void Awake() {
        Title = new GLabel(Reccollab.app, 48, 62, 504, 20);
        Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
        Title.setText(Lang.WARNING_AREA);
        Title.setTextBold();
        Title.setOpaque(false);
        Title.setVisible(false);
        WarningArea = new GTextArea(Reccollab.app, 24, 100, 552, 346, G4P.SCROLLBARS_VERTICAL_ONLY | G4P
                .SCROLLBARS_AUTOHIDE);
        WarningArea.setLocalColorScheme(GCScheme.RED_SCHEME);
        WarningArea.setOpaque(true);
        WarningArea.setVisible(false);
        WarningArea.setTextEditEnabled(false);
        WarningArea.setText(Lang.APP_SUCCESS_LOAD);
        view.AddButton(470, 52, 80, 24, Lang.BACK, GCScheme.SCHEME_15,
                this, "BackToConfigButtonClicked");
    }

    public void BackToConfigButtonClicked(GButton source, GEvent event) {
        EnablePrevious();

    }

    public void Warn(String warning) {
        if (warning.isEmpty())
            return;
        Master.WarningButton.setText(warning);
        String tmpStr = WarningArea.getText();
        WarningArea.setText(warning + "\n" + tmpStr);
    }

    @Override
    public void Update() {

    }

    @Override
    public void SetViewActive(boolean state) {
        Title.setVisible(state);
        WarningArea.setVisible(state);
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
