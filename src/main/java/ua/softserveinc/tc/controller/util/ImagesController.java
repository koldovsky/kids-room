package ua.softserveinc.tc.controller.util;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Gender;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.util.FileUploadFormObject;
import ua.softserveinc.tc.util.ImagesHolderUtil;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.LogicalRequestsValidator;
import ua.softserveinc.tc.constants.ImageConstants;
import ua.softserveinc.tc.validator.KidProfileImageValidator;
import ua.softserveinc.tc.validator.NumberRequestValidator;
import org.springframework.ui.Model;

import java.io.IOException;
import java.security.Principal;

/**
 * Controller handles all images-related work
 * Such as: request for images, uploads of new images
 */

@Controller
public class ImagesController {

    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    @Autowired
    private KidProfileImageValidator imageValidator;

    @Autowired
    private NumberRequestValidator numberRequestValidator;

    @Autowired
    private ApplicationConfigurator applicationConfigurator;

    @Log
    private Logger log;


    /**
     * Uploading a new profile picture for a Child. There is a restriction
     * that imposed on image format size. The precise values of this
     * properties is set in application properties. When some restrictions
     * are not met then appropriate message is sent to user form.
     *
     * @param fileForm an object holding the uploaded MultiPartFile
     * @param kidId ID of a Child
     * @return Model with uploaded image or error message
     */
    @PostMapping("/profile")
    public Model uploadImage(@ModelAttribute(ImageConstants.IMAGE_UPLOAD_MODEL_ATTRIBUTE)
                                FileUploadFormObject fileForm,
                             @RequestParam(ChildConstants.ID_PARAMETER_KEY) String kidId,
                             BindingResult bindingResult,
                             Model model) {

        model.addAttribute(ImageConstants.IMAGE_UPLOAD_MODEL_ATTRIBUTE, fileForm);
        numberRequestValidator.validate(kidId, bindingResult);
        if (bindingResult.hasErrors()) {
            return model;
        }

        MultipartFile file = fileForm.getFile();
        Long id = Long.parseLong(kidId);
        Child kid = childService.findByIdTransactional(id);
        model.addAttribute(ChildConstants.View.KID_ATTRIBUTE, kid);
        imageValidator.validate(file, bindingResult);
        if (bindingResult.hasErrors()) {
            return model;
        }

            try {
                byte[] bytes = file.getBytes();
                kid.setImage(bytes);
                childService.update(kid);
            } catch (IOException exc){
                log.error("Failed to save image", exc);
            }

        return model;
    }

    /**
     * Returns profile pictures to client
     *
     * @param kidId id of the kid whose avatar is to be rendered
     * @param principal authorization object
     * @return image as a byte array
     *
     * @throws ResourceNotFoundException
     * @throws AccessDeniedException
     */
    @GetMapping(value = "/images/{kidId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getProfilePic(@PathVariable String kidId, Principal principal)
            throws
            AccessDeniedException,
            ResourceNotFoundException {

        if (!LogicalRequestsValidator.isRequestValid(kidId)) {
            throw new ResourceNotFoundException();
        }
        Long id = Long.parseLong(kidId);

        Child kid = childService.findByIdTransactional(id);

        User current = userService.getUserByEmail(principal.getName());
        if (current.getRole() != Role.MANAGER && !current.equals(kid.getParentId())) {
            throw new AccessDeniedException("Have to be manager or parent");
        }
        if (kid.getImage() != null) {
            return kid.getImage();
        }
        if (kid.getGender() == Gender.FEMALE) {
            return ImagesHolderUtil.getDefaultPictureGirl();
        }
        return  ImagesHolderUtil.getDefaultPictureBoy();
    }

    @GetMapping("/uploadImage/{kidId}")
    public String redirect(@PathVariable String kidId){
        return "redirect:/" + ChildConstants.View.KID_PROFILE + "?id=" + kidId;
    }


}
