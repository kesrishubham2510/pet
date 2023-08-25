package com.myreflectionthoughts.apimasterdetails.archTest;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

public class ConfigurationArchitectureTest {

    private final String packagePath;
    private final ClassFileImporter importer;

    public ConfigurationArchitectureTest() {
        this.packagePath = "com.myreflectionthoughts.apimasterdetails";
        this.importer = new ClassFileImporter();
    }

    @Test
    void configuration_should_not_be_accessible_by_any_layer(){

        ArchRule rule = Architectures
                        .layeredArchitecture()
                        .consideringAllDependencies()
                        .layer("core")
                        .definedBy(packagePath+".core..")
                        .layer("gateway")
                        .definedBy(packagePath+".gateway..")
                        .layer("configuration")
                        .definedBy(packagePath+".configuration..")
                        .whereLayer("configuration")
                        .mayNotBeAccessedByAnyLayer()
                        .because("Configuration is the last external layer and should not accessible by internal ones");

        rule.check(importer.importPackages(packagePath));
    }
}
