package com.jkstack.dsm;

import com.jkstack.dsm.user.entity.DepartmentEntity;
import com.jkstack.dsm.user.entity.UserEntity;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lifang
 * @since 2020/10/12
 */
public class HibernateDDLGenerator {


    public static void main(String[] args) throws Exception {
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("dialect", "org.hibernate.dialect.MySQLDialect");
        settings.put("hibernate.connection.url", "jdbc:mysql://127.0.0.1:3306/db_jkstack_dsm?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "123456");
        settings.put("hibernate.implicit_naming_strategy", "org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl");
        settings.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();
        MetadataSources metadata = new MetadataSources(serviceRegistry);
        metadata.addAnnotatedClass(UserEntity.class);

        EnumSet<TargetType> enumSet = EnumSet.of(TargetType.STDOUT);
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";");
        schemaExport.execute(enumSet, SchemaExport.Action.CREATE, metadata.buildMetadata(), serviceRegistry);
    }
}
