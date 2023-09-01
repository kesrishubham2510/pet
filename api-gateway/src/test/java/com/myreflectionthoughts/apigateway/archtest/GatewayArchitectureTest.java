package com.myreflectionthoughts.apigateway.archtest;

import com.myreflectionthoughts.apigateway.gateway.handler.Handler;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SpringBootTest
public class GatewayArchitectureTest {

    private final String packagePath;
    private final ClassFileImporter importer;

    public GatewayArchitectureTest() {
        this.packagePath = "com.myreflectionthoughts.apigateway";
        this.importer = new ClassFileImporter();
    }

    @Test
    void provider_classes_should_reside_under_dataprovider() {

        ArchRule rule = classes()
                .that()
                .haveSimpleNameContaining("Provider")
                .should()
                .resideInAPackage(packagePath + ".gateway.dataprovider..")
                .because("Data providers works closely with data sources, hence it should be in gateway.dataprovider package");

        rule.check(importer.importPackages(packagePath));
    }

    @Test
    void all_handlers_should_reside_under_gateway() {

        ArchRule rule = classes()
                .that()
                .haveSimpleNameContaining("Handler")
                .or()
                .haveSimpleNameContaining("RequestHandler")
                .should()
                .resideInAPackage(packagePath + ".gateway.handler..")
                .because("request handlers handle Business logic on data based incoming requests and use exceptionMapper from Handler");

        rule.check(importer.importPackages(packagePath));
    }

    @Test
    void all_handlers_should_extend_Handler() {

        ArchRule rule = classes()
                .that()
                .resideInAnyPackage(packagePath + ".gateway.handler..")
                .and()
                .areAssignableTo(Handler.class)
                .should()
                .resideInAPackage(packagePath + ".gateway.handler..")
                .because("request handlers need exceptionMapper for Handling edge cases");

        rule.check(importer.importPackages(packagePath));
    }

    @Test
    void all_routers_should_be_annotated_with_configuration_and_reside_in_router() {

        ArchRule rule = classes()
                .that()
                .haveSimpleNameContaining("RequestRouter")
                .or()
                .haveSimpleNameContaining("Router")
                .should()
                .beAnnotatedWith(Configuration.class)
                .andShould()
                .resideInAPackage(packagePath + ".gateway.router..")
                .because("routers expose the endpoints to listen to requests");

        rule.check(importer.importPackages(packagePath));
    }

//    @Test
//    void gateways_should_not_be_accessible_by_any_other_flow(){
//        ArchRule rule = Architectures
//                .layeredArchitecture()
//                .consideringAllDependencies()
//                .layer("coreLayer")
//                .definedBy("..core..")
//                .layer("configurationLayer")
//                .definedBy("..configuration..")
//                .layer("gatewayLayer")
//                .definedBy("..gateway..")
//                .whereLayer("gatewayLayer").mayNotBeAccessedByAnyLayer();
//
//        rule.check(importer.importPath(packagePath));
//    }
}
