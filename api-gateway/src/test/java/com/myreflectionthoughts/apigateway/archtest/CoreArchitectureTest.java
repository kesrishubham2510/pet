package com.myreflectionthoughts.apigateway.archtest;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


@SpringBootTest
public class CoreArchitectureTest {

    private final String packagePath;
    private final ClassFileImporter importer;

    public CoreArchitectureTest() {
        this.packagePath = "com.myreflectionthoughts.apigateway";
        importer = new ClassFileImporter();
    }

    @Test
    void exceptions_must_reside_inside_core() {
        ArchRule rule = classes()
                .that()
                .areAssignableTo(RuntimeException.class)
                .should()
                .resideInAPackage(packagePath + ".core.exception..")
                .because("Exceptions are defined in the scope of application, and should be handled");

        rule.check(importer.importPackages(packagePath));
    }

    @Test
    void useCases_should_not_reside_outside_core() {

        ArchRule rule = classes()
                .that()
                .haveSimpleNameContaining("UseCase")
                .should()
                .resideInAPackage(packagePath + ".core.usecase..")
                .because("Use case are the ultimate core around which the development takes place");

        rule.check(importer.importPackages(packagePath));
    }

    @Test
    void class_in_core_should_not_depend_on_outside_classes() {

        ArchRule rule = noClasses()
                .that()
                .resideInAPackage(packagePath + ".core..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(packagePath + ".configuration..", packagePath + ".gateway..");

        rule.check(importer.importPackages(packagePath));
    }

    @Test
    void useCases_can_be_accessible_only_inside_core_or_through_handlers() {
        ArchRule rule = Architectures.layeredArchitecture()
                .consideringOnlyDependenciesInAnyPackage(packagePath + "..")
                .layer("component")
                .definedBy(packagePath + ".core.component..")
                .layer("usecases")
                .definedBy(packagePath + ".core.usecase..")
                .layer("handlers")
                .definedBy(packagePath + ".gateway.handler..")
                .whereLayer("usecases")
                .mayOnlyBeAccessedByLayers("handlers", "component")
                .because("Only Handlers and component should access the useCase");

        rule.check(importer.importPackages(packagePath));

    }

    @Test
    void core_should_not_access_any_other_layer() {

        ArchRule rule = Architectures.layeredArchitecture()
                .consideringOnlyDependenciesInAnyPackage(packagePath + "..")
                .layer("core")
                .definedBy(packagePath + ".core..")
                .layer("gateway")
                .definedBy(packagePath + ".gateway..")
                .layer("configuration")
                .definedBy(packagePath + ".configuration..")
                .whereLayer("core").mayNotAccessAnyLayer()
                .because("core is the most central component around which development takes place");

        rule.check(importer.importPackages(packagePath));
    }

    //    to make sure LogUtils or LogUtility resides in core
    @Test
    void logUtility_should_reside_in_core(){
        ArchRule rule = noClasses().that()
                .haveSimpleNameStartingWith("Log")
                .and()
                .haveSimpleNameContaining("Utils")
                .or()
                .haveSimpleNameContaining("Utility")
                .should()
                .resideOutsideOfPackage(packagePath+".core..");

        rule.check(importer.importPackages(packagePath));
    }
}
