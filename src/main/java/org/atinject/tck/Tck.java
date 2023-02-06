package org.atinject.tck;

import org.atinject.tck.auto.Car;
import org.atinject.tck.auto.Convertible;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Tck {

	public static List<DynamicNode> testFor(Car car, boolean supportsStatic, boolean supportsPrivate) {
		if (car == null) {
			throw new NullPointerException("car");
		}

		if (!(car instanceof Convertible convertible)) {
			throw new ClassCastException("car doesn't implement Convertible");
		}

		TestSuite testSuite = new TestSuite("JSR330 - TCK");

		testSuite.addAllOf(new Convertible.Tests(convertible));

		if (supportsStatic) {
			testSuite.addSubSuite(new Convertible.StaticTests(convertible));
		}
		if (supportsPrivate) {
			testSuite.addSubSuite(new Convertible.PrivateTests(convertible));
		}
		return testSuite.build();
	}

	public static class TestSuite {

		private final String name;
		private final List<DynamicTest> cases = new ArrayList<>();
		private final List<TestSuite> children = new ArrayList<>();

		public TestSuite(String name) {
			this.name = name;
		}

		public void addSubSuite(String name, Consumer<AssertionContext> consumer) {
			DynamicTest dynamicTest = DynamicTest.dynamicTest(name, () -> {
				AssertionContext assertionContext = new AssertionContext();
				consumer.accept(assertionContext);
				assertionContext.doAsserts();
			});

			cases.add(dynamicTest);
		}

		public void addAllOf(Object instance) {
			Arrays.stream(instance.getClass().getMethods())
					.filter(method -> method.getParameterCount() == 1)
					.filter(method -> method.getParameterTypes()[0] == AssertionContext.class)
					.forEach(method -> addSubSuite(method.getName(), new DynamicMethodInvoker(method, instance)));
		}

		public TestSuite addSubSuite(String name) {
			TestSuite testSuite = new TestSuite(name);
			children.add(testSuite);
			return testSuite;
		}

		public void addAll(TestCase testCase) {
			testCase.configure(this);
		}

		public void addSubSuite(TestCase testCase) {
			addSubSuite(testCase.getClass().getSimpleName(), testCase);
		}

		public void addSubSuite(String name, TestCase testCase) {
			TestSuite testSuite = addSubSuite(name);
			testCase.configure(testSuite);
		}

		public DynamicNode buildAndWrap() {
			final List<DynamicNode> result = build();
			return DynamicContainer.dynamicContainer(name, result);
		}

		public List<DynamicNode> build() {
			final List<DynamicNode> result = new ArrayList<>(cases);
			cases.clear();

			children.stream()
					.map(TestSuite::buildAndWrap)
					.forEach(result::add);
			children.clear();
			return result;
		}
	}

	private record DynamicMethodInvoker(Method method, Object instance) implements Consumer<AssertionContext> {

		@Override
		public void accept(AssertionContext assertionContext) {
			method.setAccessible(true);
			try {
				method.invoke(instance, assertionContext);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	public interface TestCase {
		void configure(TestSuite testSuite);
	}
}
