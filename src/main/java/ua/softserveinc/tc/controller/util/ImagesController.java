package ua.softserveinc.tc.controller.util;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Gender;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.BadUploadException;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.util.FileUploadFormObject;
import ua.softserveinc.tc.util.ImagesHolderUtil;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.LogicalRequestsValidator;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

/**
 * Created by Nestor on 22.05.2016.
 * <p>
 * Controller handles all images-related work
 * Such as: request for images, uploads of new images
 */

@Controller
public class ImagesController {
    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    @Log
    private Logger log;

    @Autowired
    private ApplicationConfigurator applicationConfigurator;


    /**
     * Uploading a new profile picture for a Child
     *
     * @param form an object holding the uploaded MultiPartFile
     * @param kidId ID of a Child
     * @return redirects back to kid's profile view
     */
    @RequestMapping(value = "/uploadImage/{kidId}", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute FileUploadFormObject form,
                                    @PathVariable String kidId) throws AccessDeniedException {
        if (!LogicalRequestsValidator.isRequestValid(kidId)) {
            throw new ResourceNotFoundException();
        }

        MultipartFile file = form.getFile();
        Long id = Long.parseLong(kidId);
        Child kid = childService.findById(id);

        if (!file.isEmpty()) {
            byte[] bytes;
            try {
                if(file.getSize()/1024/1024 > applicationConfigurator.getMaxUploadImgSizeMb()){
                    return "redirect:/" + ChildConstants.View.KID_PROFILE + "?id=" + kidId;
                }

                bytes = file.getBytes();
                if(ImageIO.read(new ByteArrayInputStream(bytes)) == null){

                    log.error("Uploaded image has a wrong extension");
                    return "redirect:/" + ChildConstants.View.KID_PROFILE + "?id=" + kidId;
                }

                kid.setImage(bytes);
                childService.update(kid);
            } catch (IOException ioe) {
                log.error("Failed converting image", ioe);
                return "redirect:/" + ChildConstants.View.KID_PROFILE + "?id=" + kidId;
            }
            catch (JpaSystemException jpae){
                log.error("Database persistence exception", jpae);
                return "redirect:/" + ChildConstants.View.KID_PROFILE + "?id=" + kidId;
            }
        }
        return "redirect:/" + ChildConstants.View.KID_PROFILE + "?id=" + kidId;
    }

    @RequestMapping(value = "/uploadImage/{kidId}", method = RequestMethod.GET)
    public String redirect(@PathVariable String kidId){
        return "redirect:/" + ChildConstants.View.KID_PROFILE + "?id=" + kidId;
    }

    /**
     * Returns profile pictures to client
     *
     * @param kidId id of the kid whose avatar is to be rendered
     * @param principal authorization object
     * @return
     *
     * @throws ResourceNotFoundException
     * @throws AccessDeniedException
     */
    @RequestMapping(value = "/images/{kidId}",
            produces = MediaType.IMAGE_JPEG_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public byte[] getProfilePic(@PathVariable String kidId, Principal principal)
            throws
            AccessDeniedException,
            ResourceNotFoundException {

        if (!LogicalRequestsValidator.isRequestValid(kidId)) {
            throw new ResourceNotFoundException();
        }
        Long id = Long.parseLong(kidId);

        Child kid = childService.findById(id);

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

}
