package com.dihaw.portal.liferay.passwordchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * Controller used to manage help informations.
 * 
 */
@Controller
@RequestMapping("HELP")
public class HelpController {
    /** The logger for this instance. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @RenderMapping
    public String showHelp() {
	return "help";
    }
}
