package processing.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class AppZip {

    private List<ZipData> fileList;

    public AppZip(String source) {
        fileList = new ArrayList<ZipData>();
    }

    /**
     * Zip it
     *
     * @param zipFile output ZIP file location
     */
    public void zipIt(final String outputPath, final String zipName) {

        byte[] buffer = new byte[1024];

        try {

            FileOutputStream fos = new FileOutputStream(outputPath + File.separator + zipName + "_" + Utils
                    .dateFormat() + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (ZipData data : fileList) {
                if (!data.isFolder()) {
                    zos.putNextEntry(data.getZipEntry());
                    FileInputStream in =
                            new FileInputStream(data.getAbsolutePath());

                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }

                    in.close();
                }
            }

            zos.closeEntry();
            zos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     *
     * @param node file or directory
     */
    public void addToFileList(File dirObj, String folder) {
        if (!dirObj.exists())
            Utils.createFolder(dirObj.getAbsolutePath());
        File[] files = dirObj.listFiles();

        for (int i = 0; i < files.length; i++) {
            fileList.add(new ZipData(files[i], folder));
            if (files[i].isDirectory()) {
                addToFileList(files[i], folder + File.separator + files[i].getName());
            }
        }
    }

}