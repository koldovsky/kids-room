package ua.softserveinc.tc.util;

import org.slf4j.Logger;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import ua.softserveinc.tc.constants.ImageConstants;

public class ImagesHolderUtil {
    @Log
    private static Logger log;

    private static byte[] defaultPictureBoy = {};
    private static byte[] defaultPictureGirl = {};

    static {
        URL urlDefaultBoy = ImagesHolderUtil.class.getResource("/images/default-boy.jpg");
        URL urlDefaultGirl = ImagesHolderUtil.class.getResource("/images/default-girl.jpg");
        ByteArrayOutputStream outChild = new ByteArrayOutputStream(
                ImageConstants.DEFAULT_SIZE_PHOTO_CHILD_IN_BYTES);
        try(InputStream inBoy = urlDefaultBoy.openStream();
            InputStream inGirl = urlDefaultGirl.openStream()) {

            IOUtils.copy(inBoy, outChild);
            defaultPictureBoy = outChild.toByteArray();
            outChild.reset();
            IOUtils.copy(inGirl, outChild);
            defaultPictureGirl = outChild.toByteArray();

        } catch(IOException ioe) {
            log.error("Failed to load child's profile pic", ioe);
        }
    }

    private ImagesHolderUtil(){}

    public static byte[] getDefaultPictureBoy() {
        return defaultPictureBoy;
    }

    public static byte[] getDefaultPictureGirl() {
        return defaultPictureGirl;
    }

}
