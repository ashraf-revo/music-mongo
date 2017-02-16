package org.revo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ashraf on 22/01/17.
 */

@Controller
public class Main {
    @GetMapping(value = {"", "/{[path:[^.]*}", "/{[path:[^.]*}/{[path:[^.]*}", "/{[path:[^.]*}/{[path:[^.]*}/{[path:[^.]*}", "/{[path:[^.]*}/{[path:[^.]*}/{[path:[^.]*}/{[path:[^.]*}"})
    public ModelAndView index() {
        return new ModelAndView("forward:/index.html");
    }
}

