package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import ua.softserveinc.tc.constants.ImageConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import org.springframework.stereotype.Component;
import ua.softserveinc.tc.util.Log;
import org.slf4j.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.StreamSupport;
import java.util.Spliterators;
import java.util.Spliterator;

/**
 * Validator for checking file of class MultipartFile for validity.
 * The set of validity checks is defined in interface {@link KidProfileImageValidator}
 * The maximum size and acceptable image formats are set in application properties.
 *
 */
@Component
public class KidProfileImageValidatorIml implements KidProfileImageValidator {
    @Log
    private Logger log;

    @Autowired
    private ApplicationConfigurator applicationConfigurator;

    @Override
    public boolean isCorrupted(MultipartFile file) {
        BufferedImage image = null;
        ImageReader imgReader = getImageReader(file);
        if(imgReader != null) {
            try (ImageInputStream inStream =
                         ImageIO.createImageInputStream(file.getInputStream())) {
                imgReader.setInput(inStream);
                image = imgReader.read(0);
            } catch (IOException e) {
                log.error("Failed to read image from file : " + file, e);
                image = null;
            }
        }
        return image == null;
    }

    @Override
    public boolean isAcceptableSize(MultipartFile file) {
        boolean result = false;
        if(file != null) {
            result = file.getSize() <= applicationConfigurator.getMaxUploadImgSizeMb()
                    * ImageConstants.ONE_MEGA_BYTE_IN_BYTES;
        }
        return result;
    }

    @Override
    public boolean isAcceptableFormat(MultipartFile file) {
        return isAcceptableFormat(getImageReader(file));
    }

    @Override
    public void validate(Object o, Errors errors) {

        if (!(o instanceof MultipartFile)) {
            log.error("Validator error! Entered wrong object for validation");
            errors.rejectValue(ValidationConstants.IMAGE,
                    ValidationConstants.IMAGE_VALIDATION_NOT_CORRECT_USAGE);
        } else {
            MultipartFile imgFile = (MultipartFile)o;

            if (imgFile.isEmpty()) {
                errors.rejectValue(ValidationConstants.IMAGE,
                        ValidationConstants.IMAGE_VALIDATION_EMPTY_FILE);
            } else {
                boolean acceptableSize = isAcceptableSize(imgFile);
                boolean acceptableFormat = isAcceptableFormat(imgFile);

                if (!acceptableSize && acceptableFormat) {

                    errors.rejectValue(ValidationConstants.IMAGE,
                            ValidationConstants.IMAGE_VALIDATION_NOT_ACCEPTABLE_SIZE);
                } else if (acceptableSize && !acceptableFormat) {

                    errors.rejectValue(ValidationConstants.IMAGE,
                            ValidationConstants.IMAGE_VALIDATION_NOT_ACCEPTABLE_FORMAT);
                } else if (!acceptableFormat) {

                    errors.rejectValue(ValidationConstants.IMAGE,
                            ValidationConstants.IMAGE_VALIDATION_NOT_ACCEPTABLE_SIZE_FORMAT);
                } else if (isCorrupted(imgFile)) {

                    errors.rejectValue(ValidationConstants.IMAGE,
                            ValidationConstants.IMAGE_VALIDATION_CORRUPTION_FILE);
                }
            }
        }
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.isAssignableFrom(aClass);
    }

    /*
     * Returns object of the class ImageReader for given file of class MultipartFile
     *
     * @param file the file of the class MultipartFile
     * @return object of the class ImageReader or null if here are no acceptable object
     */
    private ImageReader getImageReader (MultipartFile file) {
        ImageReader result = null;
        if (file != null) {
            try (ImageInputStream inStream =
                         ImageIO.createImageInputStream(file.getInputStream())) {
                Iterator<ImageReader> readers = ImageIO.getImageReaders(inStream);
                result = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                        readers, Spliterator.IMMUTABLE), false)
                        .filter(this::isAcceptableFormat)
                        .findAny()
                        .orElse(null);
            } catch (IOException e) {
                log.error("Failed to read image from file : " + file, e);
            }
        }
        return result;
    }

    /*
     * Checks if the given object of ImageReader belong to acceptable format.
     * The acceptable format is set in application properties
     *
     * @param imgReader the object of ImageReader for checking
     * @return true is format is acceptable, otherwise - false
     */
    private boolean isAcceptableFormat(ImageReader imgReader) {
        boolean result = false;
        if(imgReader != null) {
            result = Arrays.stream(applicationConfigurator.getImageAcceptableFormats()).anyMatch(
                    imgFo -> {
                        boolean match = false;
                        try {
                            match = imgFo.toLowerCase()
                                    .equals(imgReader.getFormatName().toLowerCase());
                        } catch (IOException e) {
                            log.error("Failed to read image. ImageReader:" + imgReader, e);
                        }
                        return match;
                    }
            );
        }
        return result;
    }

}
