package bg.tusofia.fksu.pe.fleamarket.buslogic.intercept;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

// NOTE: ejb/interceptor - for auditing
@Interceptor
public class PerformanceAuditingInterceptor {

	private static final Logger LOGGER = Logger.getLogger(PerformanceAuditingInterceptor.class.getName());

	@AroundInvoke
	public Object audut(InvocationContext invocationContext) throws Exception {
		long start = System.currentTimeMillis();
		try {
			return invocationContext.proceed();
		} finally {
			long invocationTime = System.currentTimeMillis() - start;
			LOGGER.finer(getQualifiedMethodName(invocationContext.getMethod()) + " invocation time: " + invocationTime);
		}

	}

	private String getQualifiedMethodName(Method method) {
		String targetClass = method.getDeclaringClass().getName();
		String targetMethod = method.getName();
		return targetClass + "." + targetMethod + "()";
	}

}
