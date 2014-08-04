package processing.app.tools.encoder;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.ICodec.ID;
import processing.app.FileTime;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Encoder {

    private static IMediaWriter writer;

    private static ArrayList<ICodec.ID> availableCodecs = new ArrayList<ICodec.ID>();
    private static ArrayList<String> availableFormats = new ArrayList<String>();

    private static Encoder instance;

    // Dafault is ICodec.ID.CODEC_ID_MPEG4
    public Encoder() {
        availableCodecs.add(ICodec.ID.CODEC_ID_NONE);
        availableCodecs.add(ICodec.ID.CODEC_ID_AC3);
        availableCodecs.add(ICodec.ID.CODEC_ID_MPEG2VIDEO);
        availableCodecs.add(ICodec.ID.CODEC_ID_MPEG4);
        availableFormats.add("MP4");
        availableFormats.add("AVI");
        availableFormats.add("FLV");
        availableFormats.add("WMA");
    }

    public static void instantiate() {
        if (instance == null)
            instance = new Encoder();
    }

    public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {

        BufferedImage image;

        // if the source image is already the target type, return the source image

        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        }

        // otherwise create a new image of the target type and draw the new image

        else {
            image = new BufferedImage(sourceImage.getWidth(),
                    sourceImage.getHeight(), targetType);

            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;

    }

    public static String[] codecList() {
        String[] tmp = new String[availableCodecs.size()];
        for (int i = 0; i < availableCodecs.size(); i++) {
            tmp[i] = availableCodecs.get(i).name().substring(9, availableCodecs.get(i).name().length());
        }
        return tmp;
    }

    public static String[] formatList() {
        String[] tmp = new String[availableFormats.size()];
        for (int i = 0; i < availableFormats.size(); i++) {
            tmp[i] = availableFormats.get(i);
        }
        return tmp;
    }

    public static ICodec.ID GetCodecAt(int index) {
        if (availableCodecs.get(index) == null)
            return null;
        return availableCodecs.get(index);
    }

    public static void Encode(JLabel load, ArrayList<FileTime> imgs, String fileName, int fps, ID codec) {
        if (codec == null || imgs.size() <= 0)
            return;

        BufferedImage openStrem = imgs.get(0).getFileAsImage();

        writer = ToolFactory.makeWriter(fileName);
        writer.addVideoStream(0, 0, codec,
                openStrem.getWidth() / 2, openStrem.getHeight() / 2);

        float percent = 0;
        float factorPercentLoad = 100.0f / imgs.size();

        BufferedImage buffer = null;
        for (int i = 0; i < imgs.size(); i++) {
            buffer = imgs.get(i).getFileAsImage();
            if (buffer == null)
                continue;
            if (buffer.getHeight() != openStrem.getHeight() ||
                    buffer.getWidth() != openStrem.getWidth())
                continue;
            BufferedImage frame = convertToType(imgs.get(i).getFileAsImage(), BufferedImage.TYPE_3BYTE_BGR);
            writer.encodeVideo(0, frame, (int) (i * (1000.0f / (float) (fps))),
                    TimeUnit.MILLISECONDS);

            if (percent < 100)
                percent += factorPercentLoad;
            load.setText(String.valueOf((int) (percent)) + "%");
        }

        writer.close();

    }

}
