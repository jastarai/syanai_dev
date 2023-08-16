package jp.jast.spframework.thymeleaf;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.Configuration;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

public class ExtDialect extends AbstractDialect
implements IExpressionEnhancingDialect {

private final Configuration configuration;

@Override
public Map<String, Object> getAdditionalExpressionObjects(
    IProcessingContext processingContext) {

Map<String, Object> map = new HashMap<>();
map.put("extFields",
        new ExtFields(configuration, processingContext));
return map;
}

public ExtDialect(Configuration configuration) {
super();
this.configuration = configuration;
}

public String getPrefix() {
return "ext";
}

}    