package tqs.hw.covidtracker;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class GenericErrorController implements ErrorController {


    @RequestMapping("/error")
    @ResponseBody
    String getErrorPath(){
        return "Uh oh! You shouldn't be here!";
    }
}
