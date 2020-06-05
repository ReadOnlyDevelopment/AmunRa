package de.katzenpapst.amunra._internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

class Runner {

	public static void runAllAnnotatedWith(Class<? extends Annotation> annotation) throws Exception {
		Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()).setScanners(new MethodAnnotationsScanner()));
		Set<Method> methods = reflections.getMethodsAnnotatedWith(annotation);

		for (Method m : methods) {
			// for simplicity, invokes methods as static without parameters
			m.invoke(null);
		}
	}

	public Runner() throws Exception {
		runAllAnnotatedWith(IgnoreClass.class);
		runAllAnnotatedWith(IgnorePack.class);
	}

}