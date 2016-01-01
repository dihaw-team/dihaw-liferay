package com.dihaw.portal.liferay.webcontoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping(value = "VIEW", params = "!tabs1")
public class DefaultController {
    /** The logger for this instance. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Default rendering.
     * 
     * @return <tt>roles</tt>
     */
    @RenderMapping
    public String showDefault() {
	return "roles";
    }
}
