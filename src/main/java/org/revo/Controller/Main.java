package org.revo.Controller;

import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ashraf on 22/01/17.
 */

@Controller
public class Main {

    private final UserService userService;

    @Autowired
    public Main(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/{[path:[^.]*}", "/{[path:[^.]*}/{[path:[^.]*}", "/{[path:[^.]*}/{[path:[^.]*}/{[path:[^.]*}", "/{[path:[^.]*}/{[path:[^.]*}/{[path:[^.]*}/{[path:[^.]*}"})
    public ModelAndView index() {
        return new ModelAndView("forward:/index.html");
    }
}

