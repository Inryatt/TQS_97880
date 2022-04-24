package tqs.hw.covidtracker;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class GenericErrorController implements ErrorController {


    @GetMapping("/error")
    @ResponseBody
    String getErrorPath(){
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Error in API - Someone accessed an nonexistent page.");

        return "Uh oh! You shouldn't be here!";

    }
}
