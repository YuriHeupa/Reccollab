package processing.app.screens;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.Reccollab;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.event.MouseEvent;

import java.awt.*;

public class About extends BaseObject {

    private GLabel aboutText;

    public About() {
        super();
    }

    @Override
    public void Awake() {
        view.AddImage(0, 0, "./resources/sprites/border.png");
        aboutText = view.AddLabel(20, 50, 560, 500, Lang.ABOUT_DESC, false);
        aboutText.setFont(new Font("Verdana", Font.PLAIN, 22));
        view.AddLabel(160, 160, 260, 20, "", false, GCScheme.GREEN_SCHEME);
        view.AddButton((Reccollab.app.width / 2) - 60,
                Reccollab.app.height - 60,
                120, 30, Lang.BACK, this, "ButtonBackClicked");
    }

    @Override
    public void SetViewActive(boolean state) {
    }

    public void ButtonBackClicked(GButton source, GEvent event) {
        EnableView("Home");
    }

    @Override
    public void Update() {
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
