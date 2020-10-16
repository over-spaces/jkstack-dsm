package com.jkstack.dsm.gateway.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lifang
 * @since 2020/10/16
 */
@Primary
@Component
public class GatewaySwaggerProvider implements SwaggerResourcesProvider {

    /**
     * swagger默认的url后缀
     */
    protected static final String API_URI = "/v3/api-docs";

    /**
     * 网关路由
     */
    @Resource
    private RouteLocator routeLocator;
    @Autowired
    private GatewayProperties gatewayProperties;



    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();

        //取出gateway的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

        //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(), "/" + routeDefinition.getId() + API_URI))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}
