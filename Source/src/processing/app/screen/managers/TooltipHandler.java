package processing.app.screen.managers;

import processing.app.BaseObject;
import processing.app.Reccollab;
import processing.app.Vector2D;
import processing.app.controls.GCScheme;
import processing.app.controls.GImageButton;
import processing.app.controls.GLabel;
import processing.event.MouseEvent;

public class TooltipHandler extends BaseObject {

    private static TooltipHandler instance;
    private static GImageButton tooltipImg;
    private static GLabel tooltipText;
    private static int showFrame = 0;
    private static Vector2D showPos = new Vector2D(0, 0);

    public static void instantiate() {
        if (instance == null)
            instance = new TooltipHandler();
    }

    public static void Show(Vector2D position, String text) {
        showFrame = Reccollab.app.frameCount;
        if (text.length() > 45)
            text = text.substring(0, 45);
        tooltipText.setText(text);
        showPos.set(position);
    }

    @Override
    public void Update() {
        boolean condition = (showFrame + 1) >= Reccollab.app.frameCount;
        tooltipImg.setVisible(condition);
        tooltipText.setVisible(condition);
        if (condition) {
            tooltipImg.moveTo(showPos.x - (tooltipImg.getWidth() / 2),
                    showPos.y - tooltipImg.getHeight());
            tooltipText.moveTo(showPos.x - (tooltipImg.getWidth() / 2) + 6,
                    showPos.y - tooltipImg.getHeight());
        }
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
    public void Awake() {
        tooltipImg = new GImageButton(Reccollab.app, 0, 0, 192, 50,
                new String[]{"./resources/sprites/tooltip.png",
                        "./resources/sprites/tooltip.png",
                        "./resources/sprites/tooltip.png"},
                "./resources/sprites/tooltip.png");
        tooltipImg.setVisible(false);
        tooltipText = new GLabel(Reccollab.app, 0, 0, 176, 40);
        tooltipText.setText("Desligado");
        tooltipText.setLocalColorScheme(GCScheme.RED_SCHEME);
        tooltipText.setOpaque(false);
        tooltipText.setVisible(false);
        tooltipText.setZ(50);
        showFrame = Reccollab.app.frameCount - 10;

    }

    @Override
    public void SetViewActive(boolean state) {
        // TODO Auto-generated method stub

    }

    @Override
    public void Init() {
        // TODO Auto-generated method stub

    }
}
