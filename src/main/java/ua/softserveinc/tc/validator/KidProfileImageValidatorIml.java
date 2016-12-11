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
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import java.io.IOException;

/**
 * Validator for checking file of class MultipartFile for validity.
 * The set of validity checks is defined in interface {@link KidProfileImageValidator}
 * The maximum size and acceptable image formats are set in application properties.
 *
 * Created by Sviatoslav Hryb on 06-Dec-16.
 */
@Component
public class KidProfileImageValidatorIml implements KidProfileImageValidator {
    @Log
    private Logger log;

    @Autowired
    private ApplicationConfigurator applicationConfigurator;

    @Override
    public boolean isNotNullOrEmpty(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    @Override
    public boolean isHaveAcceptableSize(MultipartFile file) {
        return file.getSize() <= applicationConfigurator.getMaxUploadImgSizeMb()
                * ImageConstants.ONE_MEGA_BYTE_IN_BYTES;
    }

    @Override
    public boolean isHaveAcceptableFormat(MultipartFile file) {
        boolean result = false;
        try (ImageInputStream iis =
                ImageIO.createImageInputStream(file.getInputStream())) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            out:
            while (readers.hasNext()) {
                ImageReader reader = readers.next();
                for (String imgFo : applicationConfigurator.getImageAcceptableFormats())
                    if (imgFo.toLowerCase().equals(reader.getFormatName().toLowerCase())) {
                        result = true;
                        break out;
                    }
            }
        } catch(IOException e) {
            log.error("Failed to save image", e);
        }
        return result;
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (!(o instanceof MultipartFile)) {
            log.error("Validator error! Entered wrong object for validation");
            errors.rejectValue(ValidationConstants.IMAGE,
                    ValidationConstants.IMAGE_VALIDATION_NOT_CORRECT_USAGE);
        } else {
            if (!isNotNullOrEmpty((MultipartFile)o))
                errors.rejectValue(ValidationConstants.IMAGE,
                        ValidationConstants.IMAGE_VALIDATION_EMPTY_FILE);
            else {
                if (!isHaveAcceptableSize((MultipartFile) o))
                    errors.rejectValue(ValidationConstants.IMAGE,
                            ValidationConstants.IMAGE_VALIDATION_NOT_ACCEPTABLE_SIZE);
                if (!isHaveAcceptableFormat((MultipartFile) o))
                    errors.rejectValue(ValidationConstants.IMAGE,
                            ValidationConstants.IMAGE_VALIDATION_NOT_ACCEPTABLE_FORMAT);
            }
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.isAssignableFrom(aClass);
    }

}
