package ua.softserveinc.tc.controller.admin;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.ConfigurationDto;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.ConfigValidator;

import java.io.IOException;


/**
 * Created by Nestor on 11.06.2016.
 * Handles changes to global environment settings made by Administrator
 */

@Controller
public class ConfigurationController {
    @Autowired
    private ApplicationConfigurator appConfig;

    @Autowired
    private ConfigValidator configValidator;

    @Log
    private static Logger log;

    @RequestMapping(value = "/adm-config", method = RequestMethod.GET)
    public String getConfiguration(Model model) {
        model.addAttribute(AdminConstants.ATR_CONFIG, appConfig.getObjectDto());
        return AdminConstants.EDIT_CONFIG;
    }

    @RequestMapping(value = "/adm-config", method = RequestMethod.POST)
    public String setConfiguration(@ModelAttribute(value = AdminConstants.ATR_CONFIG) ConfigurationDto cDto,
                                   BindingResult bindingResult) {
        configValidator.validate(cDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return AdminConstants.EDIT_CONFIG;
        }
        try {
            appConfig.acceptConfiguration(cDto);
        } catch (IOException ioe) {
            log.error("Failed to write to config file", ioe);
            bindingResult.rejectValue(ValidationConstants.ConfigFields.ERROR_MSG_PLACEHOLDER,
                    ValidationConstants.PROPERTIES_WRITE_FAILED);
            return AdminConstants.EDIT_CONFIG;
        }
        return AdminConstants.EDIT_CONFIG;
    }

}


