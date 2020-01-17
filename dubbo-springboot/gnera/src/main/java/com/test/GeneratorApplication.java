package com.test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;


/**
 * Description: dubbo-springboot
 * <p>
 * Created by az on 2019/10/26/026 15:55
 */
public class GeneratorApplication {
    @Test
    public void generateCode() {
        String packageName = "com.xiaoze.provider";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
//        generateByTables(serviceNameStartWithI, packageName, "lottery_bet","lottery_betoffcial","lottery_data","lottery_dresult","lottery_games","lottery_group","lottery_odds","lottery_opentime","lottery_report","lottery_rule","lottery_task","lottery_type","lottery_type_sort","manage_log","manage_resources","manage_resources_role","manage_role","manage_user","manage_user_login","manage_user_role","notice","sys_bank","sys_charge","sys_config","sys_question","user","user_bank","user_cash","user_data","user_group","user_login","user_money","user_money_log","user_msg","user_question","user_report");
        generateByTables(serviceNameStartWithI, packageName,"user");
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/test";
//        String dbUrl = "jdbc:mysql://localhost:3306/dyj";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("pf20226@")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
//                .setDbColumnUnderline(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);
        //修改替换成你需要的表名，多个表名传数组
        config
                .setActiveRecord(false)
                .setEnableCache(false)
                .setAuthor("K神带你飞")
                .setOutputDir("D:\\javaStudy\\dubbo-springboot\\dubbo-provider\\src\\main\\java")
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                )
//                .setTemplate(new TemplateConfig()
//                .setService("com.director.dyj.common.service.IBaseService")
//                .setServiceImpl("com.director.dyj.common.service.impl.BaseServiceImpl")
//                )
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
