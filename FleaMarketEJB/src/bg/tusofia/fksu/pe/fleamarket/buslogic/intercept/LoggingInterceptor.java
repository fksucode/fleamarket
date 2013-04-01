package bg.tusofia.fksu.pe.fleamarket.buslogic.intercept;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

// NOTE: ejb/interceptor - for logging
@Interceptor
public class LoggingInterceptor {

	private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

	@AroundInvoke
	public Object log(InvocationContext invocationContext) throws Exception {
		LOGGER.finest("Entering method:" + getQualifiedMethodName(invocationContext.getMethod()));
		return invocationContext.proceed();
	}

	@AroundTimeout
	public Object logTimeout(InvocationContext invocationContext) throws Exception {
		LOGGER.finest("Entering timeout method:" + getQualifiedMethodName(invocationContext.getMethod()));
		return invocationContext.proceed();
	}

	private String getQualifiedMethodName(Method method) {
		String targetClass = method.getDeclaringClass().getName();
		String targetMethod = method.getName();
		return targetClass + "." + targetMethod + "()";
	}

}
