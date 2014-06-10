package processing.app.tools.encoder;

import java.util.Comparator;

import processing.app.FileTime;

public class TimeComparator implements Comparator<FileTime> {

	@Override
	public int compare(FileTime bip1, FileTime bip2) {
        return (bip1.getTime() < bip2.getTime() ) ? -1: (bip1.getTime() > bip2.getTime()) ? 1:0 ;

	}

}
