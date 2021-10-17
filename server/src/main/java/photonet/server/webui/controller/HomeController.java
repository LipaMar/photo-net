package photonet.server.webui.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;


@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.HOME)
public class HomeController {

}
