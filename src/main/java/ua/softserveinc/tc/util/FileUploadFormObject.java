package ua.softserveinc.tc.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Nestor on 18.06.2016.
 */
public class FileUploadFormObject {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
