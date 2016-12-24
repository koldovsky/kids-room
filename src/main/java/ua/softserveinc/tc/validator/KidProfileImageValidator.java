package ua.softserveinc.tc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Interface of validator for checking if the file of class MultipartFile
 * (represent image) is not null, or empty, less or equals than maximum
 * acceptable file size and if it is in acceptable format.
 * The MultipartFile represent the image of kid profile page.
 *
 * Created by Sviatoslav Hryb on 06-Dec-16.
 */
public interface KidProfileImageValidator extends Validator {

    /**
     * Checks if the given file is corrupted in context of image.
     *
     * @param file object of interface MultipartFile for checking
     * @return true if the file is corrupted, otherwise - false.
     */
    boolean isCorrupted(MultipartFile file);

    /**
     * Checks if the given object e has acceptable size.
     * The implementors of this method decide which size
     * is acceptable.
     *
     * @param file object of interface MultipartFile for checking
     * @return true if file is an acceptable format, otherwise return false.
     */
    boolean isAcceptableSize(MultipartFile file);

    /**
     * Checks if the given object e is in acceptable format.
     * The implementors of this method decide which format
     * is acceptable.
     *
     * @param file object of interface MultipartFile for checking
     * @return true if file is in acceptable format, otherwise return false.
     */
    boolean isAcceptableFormat(MultipartFile file);

}
