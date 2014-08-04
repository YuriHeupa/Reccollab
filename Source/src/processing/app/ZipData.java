package processing.app;

import java.io.File;
import java.util.zip.ZipEntry;

public class ZipData {

    private File file;
    private ZipEntry zipEntry;

    public ZipData(File file, String folder) {
        this.file = file;
        zipEntry = new ZipEntry(folder + File.separator + file.getName());
    }

    public ZipEntry getZipEntry() {
        return zipEntry;
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public boolean isFolder() {
        return file.isDirectory();
    }

    public String fileName() {
        return file.getName();
    }

}
