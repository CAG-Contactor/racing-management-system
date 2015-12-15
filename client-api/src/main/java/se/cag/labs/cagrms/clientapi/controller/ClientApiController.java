/*
 * User: joel
 * Date: 2015-12-15
 * Time: 20:46
 */
package se.cag.labs.cagrms.clientapi.controller;

import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.web.bind.annotation.*;

@Api(basePath = "*",
        value = "Client API",
        description = "The back-end service facade for the web client of the " +
                "CAG Racing Management System.<br>" +
                "It provides operations that are passed on to the other back-end services.<br>" +
                "Moreover, this service is responible for authenticating requests before passing " +
                "them on."
)
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log4j
public class ClientApiController {
}
