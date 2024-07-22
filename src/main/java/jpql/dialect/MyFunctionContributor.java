package jpql.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MyFunctionContributor implements FunctionContributor {
    // 사용자 정의 함수
    // 아래는 h2에서 지원하는 사용자 지정 함수이다.
    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry()
                .register("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
