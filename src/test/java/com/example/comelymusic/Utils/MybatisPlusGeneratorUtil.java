package com.example.comelymusic.Utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * description: mybatis-plus代码生成器
 *
 * @author: zhangtian
 * @since: 2022-04-08 18:48
 */
public class MybatisPlusGeneratorUtil {

    @Test
    void codeGenerate() {
        /*数据库配置*/
        DataSourceConfig dataSourceConfig =
                new DataSourceConfig
                        .Builder("jdbc:mysql://39.99.112.72:3306/comely-music?characterEncoding=UTF8&userSSL=false", "root", "123456")
                        .dbQuery(new MySqlQuery())
                        .build();


        /*创建一共代码生成器对象*/
        AutoGenerator generator = new AutoGenerator(dataSourceConfig);

        String property = System.getProperty("user.dir");

        /*全局配置*/
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .fileOverride()
                .outputDir(property + "/src/main/java")
                .author("zhangtian")
//                .enableKotlin()
                .enableSwagger()
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd")
                .build();

        generator.global(globalConfig);

        /*包配置(PackageConfig)*/
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("com.example.comelymusic")
                .moduleName("generate")
                .entity("entity")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .controller("controller")
                .other("other")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://"))
                .build();

        generator.packageInfo(packageConfig);

        TemplateConfig templateConfig = new TemplateConfig.Builder()
                .disable(TemplateType.ENTITY)
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .mapperXml("/templates/mapper.xml")
                .controller("/templates/controller.java")
                .build();

        generator.template(templateConfig);

        /*策略配置*/
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
//                .likeTable(new LikeTable("USER"))
                // 这里是需要生成的对应表名
                .addInclude("fans_artist")
//                .addTablePrefix("t_", "c_")
//                .addFieldSuffix("_flag")
                /*   .entityBuilder()
                   .controllerBuilder()
                   .mapperBuilder()
                   .serviceBuilder()*/
                .build();

        strategyConfig.entityBuilder()
//                .superClass(BaseEntity.class)
                .disableSerialVersionUID()
                .enableChainModel()
                .enableLombok()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation()
                .enableActiveRecord()
                .versionColumnName("version")
                .versionPropertyName("version")
                .logicDeleteColumnName("deleted")
                .logicDeletePropertyName("deleted")
                .naming(NamingStrategy.no_change)
                .columnNaming(NamingStrategy.underline_to_camel)
//                .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
//                .addIgnoreColumns("age")
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Property("update_time", FieldFill.INSERT_UPDATE))
                .idType(IdType.AUTO)
                .formatFileName("%s")
                .build();

        strategyConfig.controllerBuilder()
//                .superClass(BaseController.class)
//                .enableHyphenStyle()
                .enableRestStyle()
                .formatFileName("%sController")
                .build();

        strategyConfig.serviceBuilder()
//                .superServiceClass(BaseService.class)
//                .superServiceImplClass(BaseServiceImpl.class)
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImp")
                .build();

        strategyConfig
                .mapperBuilder()
                .superClass(BaseMapper.class)
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()
//                .cache(MyMapperCache.class)
                .formatMapperFileName("%sDao")
                .formatXmlFileName("%sXml")
                .build();

        generator.strategy(strategyConfig);

        /*执行代码生成器*/
        generator.execute();

    }
}
