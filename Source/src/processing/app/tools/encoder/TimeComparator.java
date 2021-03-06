package processing.app.tools.encoder;

import processing.app.FileTime;

import java.util.Comparator;

public class TimeComparator implements Comparator<FileTime> {

    @Override
    public int compare(FileTime bip1, FileTime bip2) {
        return (bip1.getTime() < bip2.getTime()) ? -1 : (bip1.getTime() > bip2.getTime()) ? 1 : 0;

    }

}
