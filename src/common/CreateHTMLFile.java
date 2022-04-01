package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateHTMLFile {

    private String path = "";
    private String fileName = "";
    private String contents = "";

    public CreateHTMLFile(String path, String fileName, String contents) {
        path = (!path.substring(0, path.length() - 1).equals("\\")) ? path + "\\" : path;
        this.path = path;
        this.fileName = fileName;
        this.contents = contents;
    }

    /**
     * ���� ������ �����Ѵ�.
     */
    public void create() {
        try {
            File f = new File(path + fileName);

            if (!f.exists()) {
                f.createNewFile();
            }

            FileWriter fw = new FileWriter(path + fileName, false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(contents);
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {

        } catch (Exception e) {

        } finally {

        }

    }

    /**
     * ������ �����Ѵ�.
     */
    public void delete() {
        try {
            File f = new File(path + fileName);

            if (!f.exists()) {
                f.delete();
            }
        } catch (Exception e) {

        } finally {

        }
    }
}
