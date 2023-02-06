package org.atinject.tck;

import org.assertj.core.api.BooleanAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.SoftAssertions;

public class AssertionContext {
	private final SoftAssertions softAssertions = new SoftAssertions();

	public BooleanAssert assertThat(boolean b) {
		return softAssertions.assertThat(b);
	}

	public <T> ObjectAssert<T> assertThat(T object) {
		return softAssertions.assertThat(object);
	}

	public FieldValueCheckWithClassName theField(Object value, String name) {
		return new FieldValueCheckWithClassName(value, name);
	}

	public FieldFlagCheckWithClassName theField(String name) {
		return new FieldFlagCheckWithClassName(name);
	}

	public MethodFlagCheckWithClassName theMethod(String name) {
		String methodName = name;
		if (!methodName.endsWith(")")) {
			methodName = methodName + "()";
		}
		return new MethodFlagCheckWithClassName(methodName);
	}

	public class MethodFlagCheckWithClassName extends MethodFlagCheck {

		public MethodFlagCheckWithClassName(String methodName) {
			super(methodName);
		}

		public MethodFlagCheck inClass(String className) {
			return new MethodFlagCheck(className + "." + methodName);
		}

		public MethodFlagCheck inClass(Class<?> clazz) {
			return inClass(clazz.getSimpleName());
		}
	}

	public class MethodFlagCheck {

		protected final String methodName;

		public MethodFlagCheck(String methodName) {
			this.methodName = methodName;
		}

		public void wasCalled(boolean shouldBeTrue) {
			softAssertions.assertThat(shouldBeTrue)
					.withFailMessage("The method \"" + methodName + "\" was not called, but should have been")
					.isTrue();
		}

		public void wasNotCalled(boolean shouldBeTrue) {
			softAssertions.assertThat(shouldBeTrue)
					.withFailMessage("The method \"" + methodName + "\" was called, but should NOT have been")
					.isTrue();
		}
	}

	public class FieldFlagCheckWithClassName extends FieldFlagCheck {

		public FieldFlagCheckWithClassName(String name) {
			super(name);
		}

		public FieldFlagCheck inClass(String className) {
			return new FieldFlagCheck(className + "." + fieldName);
		}

		public FieldFlagCheck inClass(Class<?> clazz) {
			return inClass(clazz.getSimpleName());
		}
	}

	public class FieldFlagCheck {

		protected final String fieldName;

		public FieldFlagCheck(String fieldName) {
			this.fieldName = fieldName;
		}

		public void isSet(boolean shouldBeTrue) {
			softAssertions.assertThat(shouldBeTrue)
					.withFailMessage("The field \"" + fieldName + "\" is not set")
					.isTrue();
		}

		public void wasInjectedCorrectly(boolean successful) {
			softAssertions.assertThat(successful)
					.withFailMessage("The field \"" + fieldName + "\" was not injected correctly")
					.isTrue();
		}
	}

	public class FieldValueCheckWithClassName extends FieldValueCheck {

		public FieldValueCheckWithClassName(Object value, String fieldName) {
			super(value, fieldName);
		}

		public FieldValueCheck inClass(String className) {
			return new FieldValueCheck(value, className + "." + fieldName);
		}

		public FieldValueCheck inClass(Class<?> clazz) {
			return inClass(clazz.getSimpleName());
		}
	}

	public class FieldValueCheck {
		protected final Object value;
		protected final String fieldName;

		public FieldValueCheck(Object value, String fieldName) {
			this.value = value;
			this.fieldName = fieldName;
		}

		public void isSet() {
			softAssertions.assertThat(value)
					.withFailMessage("The field injection for \"" + fieldName + "\" is missing")
					.isNotNull();
		}
	}

	public void doAsserts() {
		softAssertions.assertAll();
	}
}