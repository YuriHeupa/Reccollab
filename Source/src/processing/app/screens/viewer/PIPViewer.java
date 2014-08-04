package processing.app.screens.viewer;

import processing.app.*;
import processing.app.controls.*;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class PIPViewer extends BaseObject {

    GTextField SourcePathInput;
    GTextField OutputPathInput;
    GTextField PIPImagePathInput;

    GDropList SizeSelectionList;
    GDropList PositionSelectionList;

    GSlider alphaSlider;

    GTextField widthSize;
    GTextField heightSize;

    GTextField widthPos;
    GTextField heightPos;

    Thread pipThread;
    JDialog pipDialog = new JDialog();

    public PIPViewer() {
        super();
        setParent("TreatImages");
    }

    @Override
    public void Awake() {
        int y = 70;
        view.AddLabel(4, 88 + y, 192, 16, Lang.BACKGROUND_IMAGES, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 112 + y, 192, 16, Lang.FOREGROUND_IMAGES, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 136 + y, 192, 16, Lang.SAVE_PATH, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 168 + y, 192, 16, Lang.TRANSPARENCY, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 192 + y, 192, 16, Lang.SIZE, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 216 + y, 192, 16, Lang.POSITION, GAlign.RIGHT, GAlign.MIDDLE, false);
        widthSize = view.AddTextField(366, 192 + y, 50, 16, G4P.SCROLLBARS_NONE, Lang.WIDTH);
        heightSize = view.AddTextField(420, 192 + y, 50, 16, G4P.SCROLLBARS_NONE, Lang.HEIGHT);
        widthPos = view.AddTextField(366, 216 + y, 50, 16, G4P.SCROLLBARS_NONE, "X");
        heightPos = view.AddTextField(420, 216 + y, 50, 16, G4P.SCROLLBARS_NONE, "Y");
        view.AddLabel(364, 168 + y, 160, 16, Lang.PERCENT, GAlign.LEFT, GAlign.MIDDLE, false);
        SourcePathInput = view.AddTextField(196, 88 + y, 216, 16, G4P.SCROLLBARS_NONE);
        SourcePathInput.setEnabled(false);
        PIPImagePathInput = view.AddTextField(196, 112 + y, 216, 16, G4P.SCROLLBARS_NONE);
        PIPImagePathInput.setEnabled(false);
        OutputPathInput = view.AddTextField(196, 136 + y, 216, 16, G4P.SCROLLBARS_NONE);
        OutputPathInput.setEnabled(false);
        alphaSlider = view.AddSlider(196, 152 + y, 160, 50, 10, 0, 0, 100, false, true, false);

        view.AddButton(420, 88 + y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this,
                "SearchMainImagePathButtonClick", "resources/sprites/folderIcon.png",
                1, GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(420, 112 + y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this,
                "SearchPIPImagePathButtonClick", "resources/sprites/folderIcon.png",
                1, GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(420, 136 + y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this,
                "SearchOutputPathButtonClick", "resources/sprites/folderIcon.png",
                1, GAlign.RIGHT, GAlign.MIDDLE);

        view.AddButton(480, 22 + y, 80, 24, Lang.GENERATE, GCScheme.SCHEME_15,
                this, "GenerateButtonClicked");

        String[] sizes = {Lang.SMALL, Lang.MEDIUM, Lang.BIG, Lang.FULLSCREEN, Lang.CUSTOM};
        SizeSelectionList = view.AddDropList(196, 192 + y, 160, 80, 4, GCScheme.SCHEME_8, sizes, 0, this,
                "SizeChanged");

        String[] positions = {Lang.TOP_LEFT, Lang.TOP_RIGHT,
                Lang.BOTTOM_LEFT, Lang.BOTTOM_RIGHT, Lang.CUSTOM};
        PositionSelectionList = view.AddDropList(196, 216 + y, 160, 80, 4, GCScheme.SCHEME_8, positions, 0, this,
                "PosChanged");

    }

    private int GetTransparency() {
        return (int) ((alphaSlider.getValueF() * -255.0f) / 100.0f) + 255;
    }

    @Override
    public void SetViewActive(boolean state) {
        widthSize.setVisible((SizeSelectionList.getSelectedIndex() == 4));
        heightSize.setVisible((SizeSelectionList.getSelectedIndex() == 4));
        widthPos.setVisible((PositionSelectionList.getSelectedIndex() == 4));
        heightPos.setVisible((PositionSelectionList.getSelectedIndex() == 4));
    }

    public void GenerateButtonClicked(GButton source, GEvent event) {
        if (SourcePathInput.getText().isEmpty() || SourcePathInput.getText().equals(" ") ||
                PIPImagePathInput.getText().isEmpty() || PIPImagePathInput.getText().equals(" "))
            return;

        if (pipThread != null) {
            if (pipThread.isAlive()) {
                pipDialog.setVisible(true);
                pipDialog.setLocationRelativeTo(Reccollab.jframe);
                return;
            }
        }

        final JPanel p1 = new JPanel(new GridBagLayout());
        p1.add(new JLabel(Lang.GENERATING), new GridBagConstraints());
        final JLabel load = new JLabel("0%");
        p1.add(load, new GridBagConstraints());
        pipDialog.setResizable(false);
        pipDialog.getContentPane().add(p1);
        pipDialog.setSize(180, 60);
        pipDialog.setLocationRelativeTo(Reccollab.jframe);
        pipDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        pipThread = new Thread() {
            public void run() {

                ArrayList<FileTime> backgroundList = new ArrayList<FileTime>();
                ArrayList<FileTime> foregroundList = new ArrayList<FileTime>();

                File source = new File(SourcePathInput.getText());
                File PIP = new File(PIPImagePathInput.getText());

                float percent = 0;
                float factorPercentLoad = 100.0f / source.listFiles(Utils.IMAGE_FILTER).length;

                final String output = (!OutputPathInput.getText().isEmpty()
                        && !OutputPathInput.getText().equals(" ")) ?
                        OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Reccollab";

                if (source.isDirectory() && PIP.isDirectory()) { // make sure it's a directory
                    // Get time of all background files
                    for (File f : source.listFiles(Utils.IMAGE_FILTER))
                        backgroundList.add(new FileTime(f));
                    // Get time of all foreground files
                    for (File f : PIP.listFiles(Utils.IMAGE_FILTER))
                        foregroundList.add(new FileTime(f));

                    PImage backgroundBuffer = null;
                    PImage foregroundBuffer = null;

                    // Process images by background source
                    for (FileTime f : backgroundList) {
                        //Progress dialog
                        percent += factorPercentLoad;
                        load.setText(String.valueOf((int) (percent)) + "%");

                        // Assign the nearest foreground to the background
                        File assignForeground = null;
                        for (int i = 0; i < foregroundList.size(); i++) {
                            if (foregroundList.get(i).getTime() <= f.getTime()) {
                                assignForeground = foregroundList.get(i).getFile();
                                break;
                            }
                        }

                        backgroundBuffer = Reccollab.app.loadImage(f.getFile().getAbsolutePath());
                        if (assignForeground != null)
                            foregroundBuffer = Reccollab.app.loadImage(assignForeground.getAbsolutePath());

                        int x = 0;
                        int y = 0;
                        int height = 0;
                        int width = 0;
                        int sizeFactor = 0;

                        switch (SizeSelectionList.getSelectedIndex()) {
                            case 0: // PEQUENO
                                sizeFactor = 20;
                                break;
                            case 1: // MEDIO
                                sizeFactor = 30;
                                break;
                            case 2: // GRANDE
                                sizeFactor = 40;
                                break;
                            case 3: // TELA INTEIRA
                                sizeFactor = 100;
                                break;
                        }

                        int paddingXFactor = 1;
                        int paddingYFactor = 1;

                        if (SizeSelectionList.getSelectedIndex() == 4) {
                            try {
                                width = Integer.valueOf(widthSize.getText());
                            } catch (NumberFormatException ex) {
                                width = 0;
                            }
                            try {
                                height = Integer.valueOf(heightSize.getText());
                            } catch (NumberFormatException ex) {
                                height = 0;
                            }
                        } else {
                            width = (sizeFactor * backgroundBuffer.width) / 100;
                            height = (sizeFactor * backgroundBuffer.height) / 100;
                        }

                        switch (PositionSelectionList.getSelectedIndex()) {
                            case 0: // CIMA / ESQUERDA 0, 0
                                paddingXFactor = 0;
                                paddingYFactor = 0;
                                break;
                            case 1: // CIMA / DIREITA -WIDTH, 0
                                paddingXFactor = -1;
                                paddingYFactor = 0;
                                break;
                            case 2: // BAIXO / ESQUERDA 0, -HEIGHT
                                paddingXFactor = 0;
                                paddingYFactor = -1;
                                break;
                            case 3: // BAIXO / DIREITA -WIDTH, -HEIGHT
                                paddingXFactor = -1;
                                paddingYFactor = -1;
                                break;
                        }

                        if (PositionSelectionList.getSelectedIndex() == 4) {
                            try {
                                x = Integer.valueOf(widthPos.getText());
                            } catch (NumberFormatException ex) {
                                x = 0;
                            }
                            try {
                                y = Integer.valueOf(heightPos.getText());
                            } catch (NumberFormatException ex) {
                                y = 0;
                            }
                        } else {
                            x = ((paddingXFactor == 0) ? 0 : backgroundBuffer.width) + // First pad by background
                                    (paddingXFactor * width); // Then by foreground
                            y = ((paddingYFactor == 0) ? 0 : backgroundBuffer.height) +  // First pad by background
                                    (paddingYFactor * height); // Then by foreground
                        }

                        PGraphics pg = Reccollab.app.createGraphics(backgroundBuffer.width, backgroundBuffer.height);
                        pg.beginDraw();
                        pg.image(backgroundBuffer, 0, 0);
                        pg.tint(255, GetTransparency());
                        if (foregroundBuffer != null)
                            pg.image(foregroundBuffer, x, y, width, height);
                        pg.endDraw();
                        pg.save(output + File.separator + f.getFile().getName()); // Save with the name of the background

                    }
                }
                load.setText("100%");

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        p1.remove(load);
                        pipDialog.dispose();
                        JOptionPane.showMessageDialog(Reccollab.jframe,
                                Lang.GENERATE_SUCCES);
                        Utils.OpenFile(output + File.separator);
                    }
                });
            }
        };

        pipThread.start();
        pipDialog.setVisible(true);

    }

    public void SizeChanged(GDropList source, GEvent event) {
        widthSize.setVisible((source.getSelectedIndex() == 4));
        heightSize.setVisible((source.getSelectedIndex() == 4));
    }

    public void PosChanged(GDropList source, GEvent event) {
        widthPos.setVisible((source.getSelectedIndex() == 4));
        heightPos.setVisible((source.getSelectedIndex() == 4));
    }

    public void SearchMainImagePathButtonClick(GButton source, GEvent event) {
        Reccollab.app.selectFolder(Lang.SELECT_FOLDER, "selectMainImageFolder", null, this);
    }

    public void SearchPIPImagePathButtonClick(GButton source, GEvent event) {
        Reccollab.app.selectFolder(Lang.SELECT_FOLDER, "selectPIPImageFolder", null, this);
    }

    public void SearchOutputPathButtonClick(GButton source, GEvent event) {
        Reccollab.app.selectFolder(Lang.SELECT_FOLDER, "selectOutputFolder", null, this);
    }

    public void selectMainImageFolder(File selection) {
        if (selection == null)
            return;
        String path = selection.getAbsolutePath();
        SourcePathInput.setText(path);
    }

    public void selectPIPImageFolder(File selection) {
        if (selection == null)
            return;
        String path = selection.getAbsolutePath();
        PIPImagePathInput.setText(path);
    }

    public void selectOutputFolder(File selection) {
        if (selection == null)
            return;
        String path = selection.getAbsolutePath();
        OutputPathInput.setText(path);
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub

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
