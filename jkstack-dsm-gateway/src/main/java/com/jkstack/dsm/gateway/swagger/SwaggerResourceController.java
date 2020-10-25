package com.jkstack.dsm.gateway.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.net.URI;
import java.util.List;

/**
 * swagger聚合接口，三个接口都是swagger-ui.html需要访问的接口
 * 并限制只有在dev,test开发模式下才允许访问
 *
 * @author lifang
 * @since 2020-10-10
 */
@Controller
@Profile({"dev", "test"})
public class SwaggerResourceController {

    @Autowired
    private SwaggerResourceProvider swaggerResourceProvider;



    @ResponseBody
    @RequestMapping(value = "/swagger-resources/configuration/security")
    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
        return new ResponseEntity<>(SecurityConfigurationBuilder.builder().build(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/swagger-resources/configuration/ui")
    public ResponseEntity<UiConfiguration> uiConfiguration() {
        return new ResponseEntity<>(UiConfigurationBuilder.builder().build(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/swagger-resources")
    public ResponseEntity<List<SwaggerResource>> swaggerResources() {
        return new ResponseEntity<>(swaggerResourceProvider.get(), HttpStatus.OK);
    }
}
