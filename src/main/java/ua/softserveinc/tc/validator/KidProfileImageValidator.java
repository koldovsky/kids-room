package ua.softserveinc.tc.validator;

import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

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
     * Checks if the given reference file is not null or is not
     * consists object with size equals 0.
     *
     * @param file reference of interface MultipartFile for checking
     * @return true if file is not null and relevant object is not null,
     * otherwise return false.
     */
    boolean isNotNullOrEmpty(MultipartFile file);

    /**
     * Checks if the given object e has acceptable size.
     * The implementors of this method decide which size
     * is acceptable.
     *
     * @param file object of interface MultipartFile for checking
     * @return true if file is an acceptable format, otherwise return false.
     */
    boolean isHaveAcceptableSize(MultipartFile file);

    /**
     * Checks if the given object e is in acceptable format.
     * The implementors of this method decide which format
     * is acceptable.
     *
     * @param file object of interface MultipartFile for checking
     * @return true if file is in acceptable format, otherwise return false.
     */
    boolean isHaveAcceptableFormat(MultipartFile file);

}
