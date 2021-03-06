package processing.app.screens.others;

import processing.app.BaseObjectAdapter;
import processing.app.Lang;
import processing.app.Reccollab;
import processing.app.controls.*;
import processing.app.tools.screenshot.ScreenShotHandler;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ShareScreenshot extends BaseObjectAdapter {

    Thread shareThread;
    JDialog shareDialog = new JDialog();
    PImage lastImage = null;
    PImage lastImageResized = null;

    // Change this to the URL of the PHP script on your server
    String sharedURL = "http://localhost/sharetest";
    String scriptURL = "/saveimg.php";
    String scriptURL2 = "/showimg.php";
    String TMPFNAME = "_tmp_file_to_upload";
    int myImg = -1;
    private GTextField URLPathInput;

    public ShareScreenshot() {
        super();
        setParent("ShareSubTab");
    }

    private String getScriptURL() {
        return URLPathInput.getText() + scriptURL;
    }

    private String getScriptURL2() {
        return URLPathInput.getText() + scriptURL2;
    }

    @Override
    public void Update() {
        if (view.isVisible()) {
            lastImage = ScreenShotHandler.getImageTaken();
            if (lastImage != null) {
                lastImageResized = lastImage.get(0, 0, lastImage.width, lastImage.height);
                lastImageResized.resize(320, 240);
                Reccollab.app.image(lastImageResized, 140, 145);
            } else {
                Reccollab.app.text(Lang.NO_IMAGE_CAPTURED, 300, 220);
            }
        }
    }

    @Override
    public void Awake() {

        int y = 70;
        view.AddLabel(4, 22 + y, 192, 16, "URL: ", GAlign.RIGHT, GAlign.MIDDLE, false);
        URLPathInput = view.AddTextField(196, 22 + y, 216, 16, G4P.SCROLLBARS_NONE, sharedURL);


        view.AddButton(460, 22 + y, 120, 24, Lang.SHARE, GCScheme.SCHEME_15,
                this, "ShareButtonClicked");
    }

    public void ShareButtonClicked(GButton source, GEvent event) {

        if (shareThread != null) {
            if (shareThread.isAlive()) {
                shareDialog.setVisible(true);
                shareDialog.setLocationRelativeTo(Reccollab.jframe);
                return;
            }
        }

        final JPanel p1 = new JPanel(new GridBagLayout());
        p1.add(new JLabel(Lang.SHARING), new GridBagConstraints());
        shareDialog.setResizable(false);
        shareDialog.getContentPane().add(p1);
        shareDialog.setSize(180, 60);
        shareDialog.setLocationRelativeTo(Reccollab.jframe);
        shareDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        shareThread = new Thread() {
            public void run() {

                final boolean shared = share();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        shareDialog.dispose();
                        if (shared) {
                            JOptionPane.showMessageDialog(Reccollab.jframe,
                                    Lang.SHARE_SUCESS);
                            Reccollab.app.link(getScriptURL2() + "?img=" + myImg);
                        } else
                            JOptionPane.showMessageDialog(Reccollab.jframe,
                                    Lang.SHARE_ERROR);

                    }
                });
            }
        };

        shareThread.start();
        shareDialog.setVisible(true);

    }

    private boolean share() {
        if (lastImage == null)
            return false;
        myImg = (int) (Reccollab.app.random(20000));
        saveToWeb(myImg + ".png", lastImage);
        return true;
    }

    private void saveToWeb(String filename, PImage img) {
        PGraphics pg = Reccollab.app.createGraphics(img.width, img.height);
        pg.image(img, 0, 0);
        // TODO: encode jpg/png without saving to disk
        if (isJPG(filename)) {
            pg.save(TMPFNAME + ".jpg");
            postData(filename, "image/jpeg", Reccollab.app.loadBytes(TMPFNAME + ".jpg"));
        }
        if (isPNG(filename)) {
            pg.save(TMPFNAME + ".png");
            postData(filename, "image/png", Reccollab.app.loadBytes(TMPFNAME + ".png"));
        }
    }

    private void postData(String filename, String ctype, byte[] bytes) {
        try {
            URL u = new URL(getScriptURL());
            URLConnection c = u.openConnection();
            // post multipart data

            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);

            // set request headers
            c.setRequestProperty("Content-Type", "multipart/form-data; boundary=AXi93A");

            // open a stream which can write to the url
            DataOutputStream dstream = new DataOutputStream(c.getOutputStream());

            // write content to the server, begin with the tag that says a content element is coming
            dstream.writeBytes("--AXi93A\r\n");

            // describe the content
            dstream.writeBytes("Content-Disposition: form-data; name=p5uploader; filename=" + filename +
                    " \r\nContent-Type: " + ctype +
                    "\r\nContent-Transfer-Encoding: binary\r\n\r\n");
            dstream.write(bytes, 0, bytes.length);

            // close the multipart form request
            dstream.writeBytes("\r\n--AXi93A--\r\n\r\n");
            dstream.flush();
            dstream.close();

            // print the response
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
                String responseLine = in.readLine();
                while (responseLine != null) {
                    PApplet.println(responseLine);
                    responseLine = in.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isJPG(String filename) {
        return filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg");
    }

    private boolean isPNG(String filename) {
        return filename.toLowerCase().endsWith(".png");
    }

}
