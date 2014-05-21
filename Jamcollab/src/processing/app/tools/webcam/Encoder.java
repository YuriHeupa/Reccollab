package processing.app.tools.webcam;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;


import com.github.sarxos.webcam.Webcam;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

public class Encoder {
	
	private IMediaWriter writer;
	private long startTime;
	
	public BufferedImage convertToType(BufferedImage sourceImage, int targetType) {

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
	
	public Encoder(String fileName) {
		writer = ToolFactory.makeWriter(fileName);
		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, 
				Webcam.getDefault().getViewSize().width/2, 
				Webcam.getDefault().getViewSize().height/2);
		startTime = System.nanoTime();
	}
	
	public void save() {
		writer.close();
	}
	
	public void write(BufferedImage img) {
		BufferedImage frame = convertToType(img, BufferedImage.TYPE_3BYTE_BGR);
		writer.encodeVideo(0, frame, System.nanoTime() - startTime, 
				TimeUnit.NANOSECONDS);
	}
	
	
}
