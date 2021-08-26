package org.antop.billiardslove.constants;

public class MapStruct {

    private MapStruct() {
    }

    /**
     * https://mapstruct.org/documentation/stable/reference/html/#configuration-options
     * <p>
     * default: the mapper uses no component model, instances are typically retrieved via Mappers#getMapper(Class)
     * cdi: the generated mapper is an application-scoped CDI bean and can be retrieved via @Inject
     * spring: the generated mapper is a singleton-scoped Spring bean and can be retrieved via @Autowired
     * jsr330: the generated mapper is annotated with {@code @Named} and can be retrieved via @Inject, e.g. using Spring
     */
    public static final String COMPONENT_MODEL = "spring";

}
