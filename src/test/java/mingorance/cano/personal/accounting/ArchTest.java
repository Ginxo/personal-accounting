package mingorance.cano.personal.accounting;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("mingorance.cano.personal.accounting");

        noClasses()
            .that()
                .resideInAnyPackage("mingorance.cano.personal.accounting.service..")
            .or()
                .resideInAnyPackage("mingorance.cano.personal.accounting.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..mingorance.cano.personal.accounting.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
