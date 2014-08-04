package processing.app.screens.viewer;

import processing.app.BaseObjectAdapter;
import processing.app.Lang;
import processing.app.Reccollab;
import processing.app.Utils;
import processing.app.controls.*;
import processing.app.tools.io.MouseInfo;
import processing.core.PGraphics;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MouseViewer extends BaseObjectAdapter {

    GTextField SourcePathInput;
    GTextField OutputPathInput;
    Thread generateThread;
    JDialog generatingDialog = new JDialog();

    GOption heatMapPos;
    GOption heatMapClicks;
    GCheckbox heatMap;
    GCheckbox accumulate;
    GCheckbox generalInfo;

    public MouseViewer() {
        super();
        setParent("GenerateImages");
    }

    @Override
    public void Awake() {
        int y = 70;

        view.AddLabel(4, 88 + y, 192, 16, Lang.MOUSE_LOGS, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 112 + y, 192, 16, Lang.SAVE_PATH, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 136 + y, 192, 16, Lang.ACCUMULATE_TRACK, GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 160 + y, 192, 16, "Heatmap:", GAlign.RIGHT, GAlign.MIDDLE, false);
        view.AddLabel(4, 184 + y, 192, 16, Lang.GENERAL_INFO, GAlign.RIGHT, GAlign.MIDDLE, false);

        accumulate = new GCheckbox(Reccollab.app, 196, 136 + y, 24, 20);
        accumulate.setOpaque(false);
        view.AddControl(accumulate);

        GToggleGroup group = new GToggleGroup();

        heatMap = new GCheckbox(Reccollab.app, 196, 160 + y, 24, 20);
        heatMap.setOpaque(false);
        view.AddControl(heatMap);

        heatMapPos = new GOption(Reccollab.app, 220, 160 + y, 80, 20);
        heatMapPos.setOpaque(false);
        heatMapPos.setText(Lang.POSITION);
        view.AddControl(heatMapPos);
        group.addControl(heatMapPos);

        heatMapClicks = new GOption(Reccollab.app, 290, 160 + y, 80, 20);
        heatMapClicks.setOpaque(false);
        heatMapClicks.setText("Clicks");
        view.AddControl(heatMapClicks);
        group.addControl(heatMapClicks);

        generalInfo = new GCheckbox(Reccollab.app, 196, 184 + y, 24, 20);
        generalInfo.setOpaque(false);
        view.AddControl(generalInfo);

        SourcePathInput = view.AddTextField(196, 88 + y, 216, 16, G4P.SCROLLBARS_NONE);
        SourcePathInput.setEnabled(false);
        OutputPathInput = view.AddTextField(196, 112 + y, 216, 16, G4P.SCROLLBARS_NONE);
        OutputPathInput.setEnabled(false);

        view.AddButton(420, 88 + y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this,
                "SearchSourcePathButtonClick", "resources/sprites/folderIcon.png",
                1, GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(420, 112 + y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this,
                "SearchOutputPathButtonClick", "resources/sprites/folderIcon.png",
                1, GAlign.RIGHT, GAlign.MIDDLE);

        view.AddButton(480, 22 + y, 80, 24, Lang.GENERATE, GCScheme.SCHEME_15,
                this, "GenerateButtonClicked");
    }

    public void GenerateButtonClicked(GButton source, GEvent event) {

        if (SourcePathInput.getText().isEmpty() || SourcePathInput.getText().equals(" "))
            return;

        if (generateThread != null) {
            if (generateThread.isAlive()) {
                generatingDialog.setVisible(true);
                generatingDialog.setLocationRelativeTo(Reccollab.jframe);
                return;
            }
        }

        final JPanel p1 = new JPanel(new GridBagLayout());
        p1.add(new JLabel(Lang.GENERATING), new GridBagConstraints());
        final JLabel load = new JLabel("0%");
        p1.add(load, new GridBagConstraints());
        generatingDialog.setResizable(false);
        generatingDialog.getContentPane().add(p1);
        generatingDialog.setSize(180, 60);
        generatingDialog.setLocationRelativeTo(Reccollab.jframe);
        generatingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        generateThread = new Thread() {
            public void run() {

                File source = new File(SourcePathInput.getText());
                File[] listTxtFiles = source.listFiles(Utils.TEXT_FILTER);

                ArrayList<MouseInfo> mouseInfos = new ArrayList<MouseInfo>();

                float percent = 0;
                float factorPercentLoad = 50.0f / listTxtFiles.length;

                final String output = (!OutputPathInput.getText().isEmpty()
                        && !OutputPathInput.getText().equals(" ")) ?
                        OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Reccollab";

                if (source.isDirectory()) { // make sure it's a directory
                    for (File f : source.listFiles(Utils.TEXT_FILTER)) {
                        if (percent < 50)
                            percent += factorPercentLoad;
                        load.setText(String.valueOf((int) (percent)) + "%");

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
                }

                load.setText("50%");

                for (MouseInfo m : mouseInfos) {
                    if (percent < 100)
                        percent += factorPercentLoad;
                    load.setText(String.valueOf((int) (percent)) + "%");

                    PGraphics pg = Reccollab.app.createGraphics(100, 100);
                    pg.beginDraw();
                    pg.text(m.getInfo(), 0, 0);
                    pg.endDraw();
                    pg.save(output + File.separator + "mouse_" + Utils.dateFormat() + ".jpg");

                }

                load.setText("100%");

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        p1.remove(load);
                        generatingDialog.dispose();
                        JOptionPane.showMessageDialog(Reccollab.jframe,
                                Lang.GENERATE_SUCCES);

                        Utils.OpenFile(output + File.separator);
                    }
                });
            }
        };

        generateThread.start();
        generatingDialog.setVisible(true);

    }

    public void SearchSourcePathButtonClick(GButton source, GEvent event) {
        Reccollab.app.selectFolder(Lang.SELECT_FOLDER, "selectSourceFolder", null, this);
    }

    public void SearchOutputPathButtonClick(GButton source, GEvent event) {
        Reccollab.app.selectFolder(Lang.SELECT_FOLDER, "selectOutputFolder", null, this);
    }

    public void selectSourceFolder(File selection) {
        if (selection == null)
            return;
        String path = selection.getAbsolutePath();
        SourcePathInput.setText(path);
    }

    public void selectOutputFolder(File selection) {
        if (selection == null)
            return;
        String path = selection.getAbsolutePath();
        OutputPathInput.setText(path);
    }

}
