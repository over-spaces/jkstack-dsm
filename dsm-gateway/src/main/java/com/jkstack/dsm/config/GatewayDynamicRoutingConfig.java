package com.jkstack.dsm.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 从nacos中获取路由信息，动态更新
 * @author lifang
 * @since 2020-0925
 */
@Component
@DependsOn({"gatewayConfig"})
public class GatewayDynamicRoutingConfig  {

    private static final Logger logger = LoggerFactory.getLogger(GatewayDynamicRoutingConfig.class);

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;


    private ConfigService configService;

    @PostConstruct
    public void init() {
        logger.info("从nacos初始化gateway route配置...");
        try{
            configService = initConfigService();
            if(configService == null){
                logger.warn("initConfigService fail");
                return;
            }
            String configInfo = configService.getConfig(GatewayConfig.NACOS_ROUTE_DATA_ID, GatewayConfig.NACOS_ROUTE_GROUP, GatewayConfig.DEFAULT_TIMEOUT);
            logger.trace("获取网关当前配置:\r\n{}",configInfo);
            List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
            for(RouteDefinition definition : definitionList){
                logger.info("update route : {}",definition.toString());
                dynamicRouteService.add(definition);
            }
        } catch (Exception e) {
            logger.error("初始化网关路由时发生错误",e);
        }
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTE_DATA_ID,GatewayConfig.NACOS_ROUTE_GROUP);
    }

    /**
     * 监听Nacos下发的动态路由配置
     * @param dataId
     * @param group
     */
    public void dynamicRouteByNacosListener (String dataId, String group){
        try {
            configService.addListener(dataId, group, new Listener()  {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    logger.info("进行网关更新:\n\r{}",configInfo);
                    List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
                    for(RouteDefinition definition : definitionList){
                        logger.info("update route : {}",definition.toString());
                        dynamicRouteService.update(definition);
                    }
                }
                @Override
                public Executor getExecutor() {
                    logger.info("getExecutor\n\r");
                    return null;
                }
            });
        } catch (NacosException e) {
            logger.error("从nacos接收动态路由配置出错!!!",e);
        }
    }

    /**
     * 初始化网关路由 nacos config
     * @return
     */
    private ConfigService initConfigService(){
        try{
            Properties properties = new Properties();
            properties.setProperty("serverAddr",GatewayConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace",GatewayConfig.NACOS_NAMESPACE);
            return configService= NacosFactory.createConfigService(properties);
        } catch (Exception e) {
            logger.error("初始化网关路由时发生错误",e);
            return null;
        }
    }
}
