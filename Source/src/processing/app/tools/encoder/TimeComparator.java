package processing.app.tools.encoder;

import java.util.Comparator;

public class TimeComparator implements Comparator<BufferedImagePlus> {

	@Override
	public int compare(BufferedImagePlus bip1, BufferedImagePlus bip2) {
        return (bip1.getTime() < bip2.getTime() ) ? -1: (bip1.getTime() > bip2.getTime()) ? 1:0 ;

	}

}
