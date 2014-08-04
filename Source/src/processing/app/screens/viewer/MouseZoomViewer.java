package processing.app.screens.viewer;

import processing.app.*;
import processing.app.controls.*;
import processing.app.tools.io.MouseInfo;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MouseZoomViewer extends BaseObject {

    GTextField SourcePathInput;
    GTextField LogsPathInput;
    GTextField OutputPathInput;

    GTextField WidthInput;
    GTextField HeightInput;

    Thread generateThread;
    JDialog generateDialog = new JDialog();

    public MouseZoomViewer() {
        super();
        setParent("TreatImages");
    }

    @Override
    public void Awake() {
        int y = 70;

        view.AddLabel(4, 88 + y, 192, 16, Lang.IMAGES, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 112 + y, 192, 16, Lang.MOUSE_LOGS, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 136 + y, 192, 16, Lang.SAVE_PATH, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 160 + y, 192, 16, Lang.WIDTH + ":", GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 184 + y, 192, 16, Lang.HEIGHT + ":", GAlign.RIGHT, GAlign.MIDDLE, false);

        SourcePathInput = view.AddTextField(196, 88 + y, 216, 16, G4P.SCROLLBARS_NONE);
        SourcePathInput.setEnabled(false);
        LogsPathInput = view.AddTextField(196, 112 + y, 216, 16, G4P.SCROLLBARS_NONE);
        LogsPathInput.setEnabled(false);
        OutputPathInput = view.AddTextField(196, 136 + y, 216, 16, G4P.SCROLLBARS_NONE);
        OutputPathInput.setEnabled(false);
        WidthInput = view.AddTextField(196, 160 + y, 46, 16, G4P.SCROLLBARS_NONE);
        HeightInput = view.AddTextField(196, 184 + y, 46, 16, G4P.SCROLLBARS_NONE);

        view.AddButton(420, 88 + y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this,
                "SearchMainImagePathButtonClick", "resources/sprites/folderIcon.png",
                1, GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(420, 112 + y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this,
                "SearchLogPathButtonClick", "resources/sprites/folderIcon.png",
                1, GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(420, 136 + y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this,
                "SearchOutputPathButtonClick", "resources/sprites/folderIcon.png",
                1, GAlign.RIGHT, GAlign.MIDDLE);

        view.AddButton(470, 22 + y, 80, 24, Lang.GENERATE, GCScheme.SCHEME_15,
                this, "GenerateButtonClicked");

    }

    @Override
    public void SetViewActive(boolean state) {

    }

    public void GenerateButtonClicked(GButton source, GEvent event) {

        if (SourcePathInput.getText().isEmpty() || SourcePathInput.getText().equals(" ") || LogsPathInput.getText()
                .equals(" "))
            return;

        if (generateThread != null) {
            if (generateThread.isAlive()) {
                generateDialog.setVisible(true);
                generateDialog.setLocationRelativeTo(Reccollab.jframe);
                return;
            }
        }

        final JPanel p1 = new JPanel(new GridBagLayout());
        p1.add(new JLabel(Lang.GENERATING), new GridBagConstraints());
        final JLabel load = new JLabel("0%");
        p1.add(load, new GridBagConstraints());
        generateDialog.setResizable(false);
        generateDialog.getContentPane().add(p1);
        generateDialog.setSize(180, 60);
        generateDialog.setLocationRelativeTo(Reccollab.jframe);
        generateDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        generateThread = new Thread() {
            public void run() {

                ArrayList<FileTime> imagesList = new ArrayList<FileTime>();

                File sourceLogs = new File(LogsPathInput.getText());
                File[] listTxtFiles = sourceLogs.listFiles(Utils.TEXT_FILTER);

                ArrayList<MouseInfo> mouseInfos = new ArrayList<MouseInfo>();

                File sourceImages = new File(SourcePathInput.getText());
                File[] listImageFiles = sourceImages.listFiles(Utils.IMAGE_FILTER);

                final String output = (!OutputPathInput.getText().isEmpty()
                        && !OutputPathInput.getText().equals(" ")) ?
                        OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Reccollab";

                if (sourceImages.isDirectory() && sourceLogs.isDirectory()) { // make sure it's a directory

                    // Get time of all foreground files
                    for (File f : listImageFiles)
                        imagesList.add(new FileTime(f));

                    for (File f : listTxtFiles) {
                        BufferedReader br = null;

                        try {

                            String currentLine;

                            br = new BufferedReader(new FileReader(f.getAbsolutePath()));

                            while ((currentLine = br.readLine()) != null) {
                                String handleTime = currentLine.substring(0, 26);
                                int button = Integer.valueOf(currentLine.substring(29, 30));
                                int x = Integer.valueOf(currentLine.substring(32, currentLine.indexOf("x")));
                                int y = Integer.valueOf(currentLine.substring(currentLine.indexOf("x") + 1,
                                        currentLine.indexOf("y")));
                                int w = Integer.valueOf(currentLine.substring(currentLine.indexOf("R") + 2,
                                        currentLine.indexOf("w")));
                                int h = Integer.valueOf(currentLine.substring(currentLine.indexOf("w") + 1,
                                        currentLine.length() - 2));
                                mouseInfos.add(new MouseInfo(x, y, w, h, button, handleTime));
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (br != null) br.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    float percent = 0;
                    float factorPercentLoad = 100.0f / mouseInfos.size();

                    PImage buffer = null;
                    for (MouseInfo m : mouseInfos) {
                        //Progress dialog
                        percent += factorPercentLoad;
                        load.setText(String.valueOf((int) (percent)) + "%");

                        // Assign the nearest
                        File assignImage = null;
                        for (int i = 0; i < imagesList.size(); i++) {
                            if (imagesList.get(i).getTime() >= m.getTime()) {
                                assignImage = imagesList.get(i).getFile();
                                break;
                            }
                        }

                        if (assignImage != null) {
                            buffer = Reccollab.app.loadImage(assignImage.getAbsolutePath());
                            PGraphics pg = Reccollab.app.createGraphics(Integer.valueOf(WidthInput.getText()),
                                    Integer.valueOf(HeightInput.getText()));
                            pg.beginDraw();
                            int x = m.getX() - (Integer.valueOf(WidthInput.getText()) / 2);
                            int y = m.getY() - (Integer.valueOf(HeightInput.getText()) / 2);
                            int w = Integer.valueOf(WidthInput.getText());
                            int h = Integer.valueOf(HeightInput.getText());
                            if (x < 0) {
                                w += x * -1;
                                x = 0;
                            }
                            if (y < 0) {
                                h += y * -1;
                                y = 0;
                            }
                            pg.image(buffer.get(x, y, w, h), 0, 0);
                            pg.endDraw();
                            pg.save(output + File.separator + m.getInfo() + ".jpg");
                        }

                    }

                }

                load.setText("100%");

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        p1.remove(load);
                        generateDialog.dispose();
                        JOptionPane.showMessageDialog(Reccollab.jframe,
                                Lang.GENERATE_SUCCES);
                        Utils.OpenFile(output + File.separator);
                    }
                });
            }
        };

        generateThread.start();
        generateDialog.setVisible(true);
    }

    public void SearchMainImagePathButtonClick(GButton source, GEvent event) {
        Reccollab.app.selectFolder("Selecione uma pasta:", "selectMainImageFolder", null, this);
    }

    public void SearchLogPathButtonClick(GButton source, GEvent event) {
        Reccollab.app.selectFolder("Selecione uma pasta:", "selectLogFolder", null, this);
    }

    public void SearchOutputPathButtonClick(GButton source, GEvent event) {
        Reccollab.app.selectFolder("Selecione uma pasta:", "selectOutputFolder", null, this);
    }

    public void selectMainImageFolder(File selection) {
        if (selection == null)
            return;
        String path = selection.getAbsolutePath();
        SourcePathInput.setText(path);
    }

    public void selectLogFolder(File selection) {
        if (selection == null)
            return;
        String path = selection.getAbsolutePath();
        LogsPathInput.setText(path);
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
