package rss.feed.aggregator.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
 
      @RequestMapping(method = RequestMethod.GET)
        public String getIndexPage() {
            return "Feeds";
        }
      
      @RequestMapping(value = "/Register", method = RequestMethod.GET)
      public String getRegister() {
          return "Register";
      }
 
}
