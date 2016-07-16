package ua.softserveinc.tc.util;

import org.slf4j.Logger;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nestor on 22.06.2016.
 */

public class ImagesHolderUtil {
    @Log
    private static Logger log;

    //default non-null values
    private static byte[] defaultPictureBoy = "pic-boy".getBytes();
    private static byte[] defaultPictureGirl = "pic-girl".getBytes();

    public static byte[] getDefaultPictureBoy() {
        return defaultPictureBoy;
    }

    public static byte[] getDefaultPictureGirl() {
        return defaultPictureGirl;
    }

    static{
        File imgPathBoy = new File("src/main/resources/images/default-boy.jpg");
        File imgPathGirl = new File("src/main/resources/images/default-girl.jpg");

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(1000)){
            BufferedImage bufferedImage = ImageIO.read(imgPathBoy);
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            defaultPictureBoy = baos.toByteArray();

            baos.reset();
            bufferedImage = ImageIO.read(imgPathGirl);
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            defaultPictureGirl = baos.toByteArray();

        } catch (IOException ioe) {
            log.error("Failed to load child's profile pic", ioe);
        }

    }

    private ImagesHolderUtil(){}
}
