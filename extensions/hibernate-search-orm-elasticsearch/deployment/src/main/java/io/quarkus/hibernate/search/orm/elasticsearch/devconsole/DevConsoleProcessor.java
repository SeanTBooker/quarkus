package io.quarkus.hibernate.search.orm.elasticsearch.devconsole;

import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

import io.quarkus.deployment.IsDevelopment;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.devconsole.spi.DevConsoleRouteBuildItem;
import io.quarkus.devconsole.spi.DevConsoleRuntimeTemplateInfoBuildItem;
import io.quarkus.hibernate.search.orm.elasticsearch.runtime.devconsole.HibernateSearchDevConsoleRecorder;
import io.quarkus.hibernate.search.orm.elasticsearch.runtime.devconsole.HibernateSearchSupplier;

public class DevConsoleProcessor {

    @BuildStep(onlyIf = IsDevelopment.class)
    public DevConsoleRuntimeTemplateInfoBuildItem collectBeanInfo() {
        return new DevConsoleRuntimeTemplateInfoBuildItem("entities", new HibernateSearchSupplier());
    }

    @BuildStep
    @Record(value = STATIC_INIT, optional = true)
    DevConsoleRouteBuildItem invokeEndpoint(HibernateSearchDevConsoleRecorder recorder) {
        return new DevConsoleRouteBuildItem("entities", "POST", recorder.indexEntity());
    }
}
