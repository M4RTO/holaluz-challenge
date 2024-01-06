package com.example.holaluz.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;


@AnalyzeClasses(packages = "com.example.holaluz", importOptions = ImportOption.DoNotIncludeTests.class)
@SuppressWarnings("squid:S2187")
public class LayeredArchitectureTest {

    private static final String DOMAIN = "Domain";
    private static final String ADAPTERS = "Adapters";
    private static final String APPLICATION = "Application";
    private static final String CONFIG = "Config";

    @ArchTest
    public static final ArchRule layer_dependencies_are_respected = Architectures.layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer(CONFIG).definedBy("com.example.holaluz.config..")
            .layer(DOMAIN).definedBy("com.example.holaluz.domain..")
            .layer(ADAPTERS).definedBy("com.example.holaluz.adapter..")
            .layer(APPLICATION).definedBy("com.example.holaluz.application..")

            .whereLayer(APPLICATION).mayOnlyBeAccessedByLayers(ADAPTERS, CONFIG)
            .whereLayer(ADAPTERS).mayOnlyBeAccessedByLayers(CONFIG)
            .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(APPLICATION, ADAPTERS, CONFIG);

}
