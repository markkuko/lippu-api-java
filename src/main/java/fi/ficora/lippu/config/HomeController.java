package fi.ficora.lippu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home redirection to swagger api documentation.
 */
@Controller
class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @SuppressWarnings("SameReturnValue")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        log.debug("Request to swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}
