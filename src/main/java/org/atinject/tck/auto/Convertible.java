/*
 * Copyright (C) 2009 The JSR-330 Expert Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.atinject.tck.auto;

import org.atinject.tck.AssertionContext;
import org.atinject.tck.Tck;
import org.atinject.tck.auto.accessories.Cupholder;
import org.atinject.tck.auto.accessories.RoundThing;
import org.atinject.tck.auto.accessories.SpareTire;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Provider;

import static org.junit.jupiter.api.Assertions.*;

public class Convertible implements Car {

	@Inject
	@Drivers
	Seat driversSeatA;
	@Inject
	@Drivers
	Seat driversSeatB;
	@Inject
	SpareTire spareTire;
	@Inject
	Cupholder cupholder;
	@Inject
	Provider<Engine> engineProvider;

	private boolean methodWithZeroParamsInjected;
	private boolean methodWithMultipleParamsInjected;
	private boolean methodWithNonVoidReturnInjected;

	private Seat constructorPlainSeat;
	private Seat constructorDriversSeat;
	private Tire constructorPlainTire;
	private Tire constructorSpareTire;
	private Provider<Seat> constructorPlainSeatProvider = nullProvider();
	private Provider<Seat> constructorDriversSeatProvider = nullProvider();
	private Provider<Tire> constructorPlainTireProvider = nullProvider();
	private Provider<Tire> constructorSpareTireProvider = nullProvider();

	@Inject
	Seat fieldPlainSeat;
	@Inject
	@Drivers
	Seat fieldDriversSeat;
	@Inject
	Tire fieldPlainTire;
	@Inject
	@Named("spare")
	Tire fieldSpareTire;
	@Inject
	Provider<Seat> fieldPlainSeatProvider = nullProvider();
	@Inject
	@Drivers
	Provider<Seat> fieldDriversSeatProvider = nullProvider();
	@Inject
	Provider<Tire> fieldPlainTireProvider = nullProvider();
	@Inject
	@Named("spare")
	Provider<Tire> fieldSpareTireProvider = nullProvider();

	private Seat methodPlainSeat;
	private Seat methodDriversSeat;
	private Tire methodPlainTire;
	private Tire methodSpareTire;
	private Provider<Seat> methodPlainSeatProvider = nullProvider();
	private Provider<Seat> methodDriversSeatProvider = nullProvider();
	private Provider<Tire> methodPlainTireProvider = nullProvider();
	private Provider<Tire> methodSpareTireProvider = nullProvider();

	@Inject
	static Seat staticFieldPlainSeat;
	@Inject
	@Drivers
	static Seat staticFieldDriversSeat;
	@Inject
	static Tire staticFieldPlainTire;
	@Inject
	@Named("spare")
	static Tire staticFieldSpareTire;
	@Inject
	static Provider<Seat> staticFieldPlainSeatProvider = nullProvider();
	@Inject
	@Drivers
	static Provider<Seat> staticFieldDriversSeatProvider = nullProvider();
	@Inject
	static Provider<Tire> staticFieldPlainTireProvider = nullProvider();
	@Inject
	@Named("spare")
	static Provider<Tire> staticFieldSpareTireProvider = nullProvider();

	private static Seat staticMethodPlainSeat;
	private static Seat staticMethodDriversSeat;
	private static Tire staticMethodPlainTire;
	private static Tire staticMethodSpareTire;
	private static Provider<Seat> staticMethodPlainSeatProvider = nullProvider();
	private static Provider<Seat> staticMethodDriversSeatProvider = nullProvider();
	private static Provider<Tire> staticMethodPlainTireProvider = nullProvider();
	private static Provider<Tire> staticMethodSpareTireProvider = nullProvider();

	@Inject
	Convertible(
			Seat plainSeat,
			@Drivers Seat driversSeat,
			Tire plainTire,
			@Named("spare") Tire spareTire,
			Provider<Seat> plainSeatProvider,
			@Drivers Provider<Seat> driversSeatProvider,
			Provider<Tire> plainTireProvider,
			@Named("spare") Provider<Tire> spareTireProvider) {
		constructorPlainSeat = plainSeat;
		constructorDriversSeat = driversSeat;
		constructorPlainTire = plainTire;
		constructorSpareTire = spareTire;
		constructorPlainSeatProvider = plainSeatProvider;
		constructorDriversSeatProvider = driversSeatProvider;
		constructorPlainTireProvider = plainTireProvider;
		constructorSpareTireProvider = spareTireProvider;
	}

	Convertible() {
		throw new AssertionError("Unexpected call to non-injectable constructor");
	}

	void setSeat(Seat unused) {
		throw new AssertionError("Unexpected call to non-injectable method");
	}

	@Inject
	void injectMethodWithZeroArgs() {
		methodWithZeroParamsInjected = true;
	}

	@Inject
	String injectMethodWithNonVoidReturn() {
		methodWithNonVoidReturnInjected = true;
		return "unused";
	}

	@Inject
	void injectInstanceMethodWithManyArgs(
			Seat plainSeat,
			@Drivers Seat driversSeat,
			Tire plainTire,
			@Named("spare") Tire spareTire,
			Provider<Seat> plainSeatProvider,
			@Drivers Provider<Seat> driversSeatProvider,
			Provider<Tire> plainTireProvider,
			@Named("spare") Provider<Tire> spareTireProvider) {
		methodWithMultipleParamsInjected = true;

		methodPlainSeat = plainSeat;
		methodDriversSeat = driversSeat;
		methodPlainTire = plainTire;
		methodSpareTire = spareTire;
		methodPlainSeatProvider = plainSeatProvider;
		methodDriversSeatProvider = driversSeatProvider;
		methodPlainTireProvider = plainTireProvider;
		methodSpareTireProvider = spareTireProvider;
	}

	@Inject
	static void injectStaticMethodWithManyArgs(
			Seat plainSeat,
			@Drivers Seat driversSeat,
			Tire plainTire,
			@Named("spare") Tire spareTire,
			Provider<Seat> plainSeatProvider,
			@Drivers Provider<Seat> driversSeatProvider,
			Provider<Tire> plainTireProvider,
			@Named("spare") Provider<Tire> spareTireProvider) {
		staticMethodPlainSeat = plainSeat;
		staticMethodDriversSeat = driversSeat;
		staticMethodPlainTire = plainTire;
		staticMethodSpareTire = spareTire;
		staticMethodPlainSeatProvider = plainSeatProvider;
		staticMethodDriversSeatProvider = driversSeatProvider;
		staticMethodPlainTireProvider = plainTireProvider;
		staticMethodSpareTireProvider = spareTireProvider;
	}

	/**
	 * Returns a provider that always returns null. This is used as a default
	 * value to avoid null checks for omitted provider injections.
	 */
	private static <T> Provider<T> nullProvider() {
		return () -> null;
	}

	public static class Tests implements Tck.TestCase {

		private final Convertible car;
		private final Cupholder cupholder;
		private final SpareTire spareTire;
		private final Tire plainTire;
		private final Engine engine;

		public Tests(Convertible car) {
			this.car = car;
			this.cupholder = car.cupholder;
			this.spareTire = car.spareTire;
			this.plainTire = car.fieldPlainTire;
			this.engine = car.engineProvider.get();
		}

		// smoke tests: if these fail all bets are off

		public void testFieldsInjected(AssertionContext assertions) {
			assertions.theField(cupholder, "car.cupholder").isSet();
			assertions.theField(spareTire, "car.spareTire").isSet();
		}

		public void testProviderReturnedValues(AssertionContext assertions) {
			assertions.theField(engine, "engine")
					.inClass(Car.class)
					.isSet();
		}

		// injecting different kinds of members

		public void testMethodWithZeroParametersInjected(AssertionContext assertions) {
			assertions.theMethod("injectMethodWithZeroArgs")
					.inClass(Car.class)
					.wasCalled(car.methodWithZeroParamsInjected);
		}

		public void testMethodWithMultipleParametersInjected(AssertionContext assertions) {
			assertions.theMethod("methodWithMultipleParamsInjected")
					.inClass(Car.class)
					.wasCalled(car.methodWithMultipleParamsInjected);
		}

		public void testNonVoidMethodInjected(AssertionContext assertions) {
			assertions.theMethod("methodWithNonVoidReturnInjected")
					.inClass(Car.class)
					.wasCalled(car.methodWithNonVoidReturnInjected);
		}

		public void testPublicNoArgsConstructorInjected(AssertionContext assertions) {
			assertions.theMethod("publicNoArgsConstructorInjected")
					.inClass(Engine.class)
					.wasCalled(engine.publicNoArgsConstructorInjected);
		}

		public void testSubtypeFieldsInjected(AssertionContext assertions) {
			assertions.theField("hasSpareTireBeenFieldInjected")
					.inClass(SpareTire.class)
					.isSet(spareTire.hasSpareTireBeenFieldInjected());
		}

		public void testSubtypeMethodsInjected(AssertionContext assertions) {
			assertions.theMethod("subtypeMethodInjection")
					.inClass(SpareTire.class)
					.wasCalled(spareTire.hasSpareTireBeenMethodInjected());
		}

		public void testSupertypeFieldsInjected(AssertionContext assertions) {
			assertions.theField("fieldInjection")
					.inClass(Tire.class)
					.isSet(spareTire.hasTireBeenFieldInjected());
		}

		public void testSupertypeMethodsInjected(AssertionContext assertions) {
			assertions.theMethod("supertypeMethodInjection")
					.inClass(Tire.class)
					.wasCalled(spareTire.hasTireBeenMethodInjected());
		}

		public void testTwiceOverriddenMethodInjectedWhenMiddleLacksAnnotation(AssertionContext assertions) {
			assertions.theMethod("injectTwiceOverriddenWithOmissionInMiddle")
					.inClass(V8Engine.class)
					.wasCalled(engine.overriddenTwiceWithOmissionInMiddleInjected);
		}

		// injected values

		public void testQualifiersNotInheritedFromOverriddenMethod(AssertionContext assertions) {
			assertions.assertThat(engine.qualifiersInheritedFromOverriddenMethod)
					.withFailMessage("Engine.injectQualifiers() was called with the wrong arguments")
					.isFalse();
		}

		public void testConstructorInjectionWithValues(AssertionContext assertions) {
			assertions.assertThat(car.constructorPlainSeat)
					.withFailMessage("car.constructorPlainSeat should not have been of instance DriversSeat")
					.isNotInstanceOf(DriversSeat.class);
			assertions.assertThat(car.constructorPlainTire)
					.withFailMessage("car.constructorPlainTire should not have been of instance SpareTire")
					.isNotInstanceOf(DriversSeat.class);

			assertions.assertThat(car.constructorDriversSeat)
					.withFailMessage("car.constructorPlainTire should have been of instance DriversSeat")
					.isInstanceOf(DriversSeat.class);
			assertions.assertThat(car.constructorSpareTire)
					.withFailMessage("car.constructorSpareTire should have been of instance SpareTire")
					.isInstanceOf(SpareTire.class);
		}

		public void testFieldInjectionWithValues(AssertionContext assertions) {
			assertions.assertThat(car.fieldPlainSeat)
					.withFailMessage("car.fieldPlainSeat should not have been of instance DriversSeat")
					.isNotInstanceOf(DriversSeat.class);
			assertions.assertThat(car.fieldPlainTire)
					.withFailMessage("car.fieldPlainTire should not have been of instance SpareTire")
					.isNotInstanceOf(DriversSeat.class);

			assertions.assertThat(car.fieldDriversSeat)
					.withFailMessage("car.fieldDriversSeat should have been of instance DriversSeat")
					.isInstanceOf(DriversSeat.class);
			assertions.assertThat(car.fieldSpareTire)
					.withFailMessage("car.fieldSpareTire should have been of instance SpareTire")
					.isInstanceOf(SpareTire.class);
		}

		public void testMethodInjectionWithValues(AssertionContext assertions) {
			assertions.assertThat(car.methodPlainSeat)
					.withFailMessage("car.methodPlainSeat should not have been of instance DriversSeat")
					.isNotInstanceOf(DriversSeat.class);
			assertions.assertThat(car.methodPlainTire)
					.withFailMessage("car.methodPlainTire should not have been of instance SpareTire")
					.isNotInstanceOf(DriversSeat.class);

			assertions.assertThat(car.methodDriversSeat)
					.withFailMessage("car.methodDriversSeat should have been of instance DriversSeat")
					.isInstanceOf(DriversSeat.class);
			assertions.assertThat(car.methodSpareTire)
					.withFailMessage("car.methodSpareTire should have been of instance SpareTire")
					.isInstanceOf(SpareTire.class);
		}

		// injected providers

		public void testConstructorInjectionWithProviders(AssertionContext assertions) {
			assertions.assertThat(car.constructorPlainSeatProvider)
					.withFailMessage("car.constructorPlainSeatProvider should not have been of instance DriversSeat")
					.isNotInstanceOf(DriversSeat.class);
			assertions.assertThat(car.constructorPlainTireProvider)
					.withFailMessage("car.constructorPlainTireProvider should not have been of instance SpareTire")
					.isNotInstanceOf(DriversSeat.class);

			assertions.assertThat(car.constructorDriversSeatProvider)
					.withFailMessage("car.constructorDriversSeatProvider should have been of instance DriversSeat")
					.isInstanceOf(DriversSeat.class);
			assertions.assertThat(car.constructorSpareTireProvider)
					.withFailMessage("car.constructorSpareTireProvider should have been of instance SpareTire")
					.isInstanceOf(SpareTire.class);
		}

		public void testFieldInjectionWithProviders(AssertionContext assertions) {
			assertFalse(car.fieldPlainSeatProvider.get() instanceof DriversSeat, "Expected unqualified value");
			assertFalse(car.fieldPlainTireProvider.get() instanceof SpareTire, "Expected unqualified value");
			assertTrue(car.fieldDriversSeatProvider.get() instanceof DriversSeat, "Expected qualified value");
			assertTrue(car.fieldSpareTireProvider.get() instanceof SpareTire, "Expected qualified value");
		}

		public void testMethodInjectionWithProviders(AssertionContext assertions) {
			assertFalse(car.methodPlainSeatProvider.get() instanceof DriversSeat, "Expected unqualified value");
			assertFalse(car.methodPlainTireProvider.get() instanceof SpareTire, "Expected unqualified value");
			assertTrue(car.methodDriversSeatProvider.get() instanceof DriversSeat, "Expected qualified value");
			assertTrue(car.methodSpareTireProvider.get() instanceof SpareTire, "Expected qualified value");
		}


		// singletons

		public void testConstructorInjectedProviderYieldsSingleton(AssertionContext assertions) {
			assertSame(car.constructorPlainSeatProvider.get(), car.constructorPlainSeatProvider.get(), "Expected same value");
		}

		public void testFieldInjectedProviderYieldsSingleton(AssertionContext assertions) {
			assertSame(car.fieldPlainSeatProvider.get(), car.fieldPlainSeatProvider.get(), "Expected same value");
		}

		public void testMethodInjectedProviderYieldsSingleton(AssertionContext assertions) {
			assertSame(car.methodPlainSeatProvider.get(), car.methodPlainSeatProvider.get(), "Expected same value");
		}

		public void testCircularlyDependentSingletons(AssertionContext assertions) {
			// uses provider.get() to get around circular deps
			assertSame(cupholder.seatProvider.get().getCupholder(), cupholder);
		}


		// non singletons

		public void testSingletonAnnotationNotInheritedFromSupertype(AssertionContext assertions) {
			assertNotSame(car.driversSeatA, car.driversSeatB);
		}

		public void testConstructorInjectedProviderYieldsDistinctValues(AssertionContext assertions) {
			assertNotSame(car.constructorDriversSeatProvider.get(), car.constructorDriversSeatProvider.get(), "Expected distinct values");
			assertNotSame(car.constructorPlainTireProvider.get(), car.constructorPlainTireProvider.get(), "Expected distinct values");
			assertNotSame(car.constructorSpareTireProvider.get(), car.constructorSpareTireProvider.get(), "Expected distinct values");
		}

		public void testFieldInjectedProviderYieldsDistinctValues(AssertionContext assertions) {
			assertNotSame(car.fieldDriversSeatProvider.get(), car.fieldDriversSeatProvider.get(), "Expected distinct values");
			assertNotSame(car.fieldPlainTireProvider.get(), car.fieldPlainTireProvider.get(), "Expected distinct values");
			assertNotSame(car.fieldSpareTireProvider.get(), car.fieldSpareTireProvider.get(), "Expected distinct values");
		}

		public void testMethodInjectedProviderYieldsDistinctValues(AssertionContext assertions) {
			assertNotSame(car.methodDriversSeatProvider.get(), car.methodDriversSeatProvider.get(), "Expected distinct values");
			assertNotSame(car.methodPlainTireProvider.get(), car.methodPlainTireProvider.get(), "Expected distinct values");
			assertNotSame(car.methodSpareTireProvider.get(), car.methodSpareTireProvider.get(), "Expected distinct values");
		}


		// mix inheritance + visibility

		public void testPackagePrivateMethodInjectedDifferentPackages(AssertionContext assertions) {
			assertTrue(spareTire.subPackagePrivateMethodInjected);
			assertTrue(spareTire.superPackagePrivateMethodInjected);
		}

		public void testOverriddenProtectedMethodInjection(AssertionContext assertions) {
			assertTrue(spareTire.subProtectedMethodInjected);
			assertFalse(spareTire.superProtectedMethodInjected);
		}

		public void testOverriddenPublicMethodNotInjected(AssertionContext assertions) {
			assertTrue(spareTire.subPublicMethodInjected);
			assertFalse(spareTire.superPublicMethodInjected);
		}


		// inject in order

		public void testFieldsInjectedBeforeMethods(AssertionContext assertions) {
			assertFalse(spareTire.methodInjectedBeforeFields);
		}

		public void testSupertypeMethodsInjectedBeforeSubtypeFields(AssertionContext assertions) {
			assertFalse(spareTire.subtypeFieldInjectedBeforeSupertypeMethods);
		}

		public void testSupertypeMethodInjectedBeforeSubtypeMethods(AssertionContext assertions) {
			assertFalse(spareTire.subtypeMethodInjectedBeforeSupertypeMethods);
		}


		// necessary injections occur

		public void testPackagePrivateMethodInjectedEvenWhenSimilarMethodLacksAnnotation(AssertionContext assertions) {
			assertTrue(spareTire.subPackagePrivateMethodForOverrideInjected);
		}


		// override or similar method without @Inject

		public void testPrivateMethodNotInjectedWhenSupertypeHasAnnotatedSimilarMethod(AssertionContext assertions) {
			assertFalse(spareTire.superPrivateMethodForOverrideInjected);
		}

		public void testPackagePrivateMethodNotInjectedWhenOverrideLacksAnnotation(AssertionContext assertions) {
			assertFalse(engine.subPackagePrivateMethodForOverrideInjected);
			assertFalse(engine.superPackagePrivateMethodForOverrideInjected);
		}

		public void testPackagePrivateMethodNotInjectedWhenSupertypeHasAnnotatedSimilarMethod(AssertionContext assertions) {
			assertFalse(spareTire.superPackagePrivateMethodForOverrideInjected);
		}

		public void testProtectedMethodNotInjectedWhenOverrideNotAnnotated(AssertionContext assertions) {
			assertFalse(spareTire.protectedMethodForOverrideInjected);
		}

		public void testPublicMethodNotInjectedWhenOverrideNotAnnotated(AssertionContext assertions) {
			assertFalse(spareTire.publicMethodForOverrideInjected);
		}

		public void testTwiceOverriddenMethodNotInjectedWhenOverrideLacksAnnotation(AssertionContext assertions) {
			assertFalse(engine.overriddenTwiceWithOmissionInSubclassInjected);
		}

		public void testOverriddingMixedWithPackagePrivate2(AssertionContext assertions) {
			assertTrue(spareTire.packagePrivateMethod2Injected);
			assertTrue(((Tire) spareTire).packagePrivateMethod2Injected);
			assertFalse(((RoundThing) spareTire).packagePrivateMethod2Injected);

			assertTrue(plainTire.packagePrivateMethod2Injected);
			assertTrue(((RoundThing) plainTire).packagePrivateMethod2Injected);
		}

		public void testOverriddingMixedWithPackagePrivate3(AssertionContext assertions) {
			assertFalse(spareTire.packagePrivateMethod3Injected);
			assertTrue(((Tire) spareTire).packagePrivateMethod3Injected);
			assertFalse(((RoundThing) spareTire).packagePrivateMethod3Injected);

			assertTrue(plainTire.packagePrivateMethod3Injected);
			assertTrue(((RoundThing) plainTire).packagePrivateMethod3Injected);
		}

		public void testOverriddingMixedWithPackagePrivate4(AssertionContext assertions) {
			assertFalse(plainTire.packagePrivateMethod4Injected);
			assertTrue(((RoundThing) plainTire).packagePrivateMethod4Injected);
		}

		// inject only once

		public void testOverriddenPackagePrivateMethodInjectedOnlyOnce(AssertionContext assertions) {
			assertFalse(engine.overriddenPackagePrivateMethodInjectedTwice);
		}

		public void testSimilarPackagePrivateMethodInjectedOnlyOnce(AssertionContext assertions) {
			assertFalse(spareTire.similarPackagePrivateMethodInjectedTwice);
		}

		public void testOverriddenProtectedMethodInjectedOnlyOnce(AssertionContext assertions) {
			assertFalse(spareTire.overriddenProtectedMethodInjectedTwice);
		}

		public void testOverriddenPublicMethodInjectedOnlyOnce(AssertionContext assertions) {
			assertFalse(spareTire.overriddenPublicMethodInjectedTwice);
		}

		@Override
		public void configure(Tck.TestSuite testSuite) {
			testSuite.addAllOf(this);
		}
	}

	public static class StaticTests implements Tck.TestCase {

		private final Convertible convertible;

		public StaticTests(Convertible convertible) {
			this.convertible = convertible;
		}

		public void testSubtypeStaticFieldsInjected(AssertionContext assertions) {
			assertTrue(SpareTire.hasBeenStaticFieldInjected());
		}

		public void testSubtypeStaticMethodsInjected(AssertionContext assertions) {
			assertTrue(SpareTire.hasBeenStaticMethodInjected());
		}

		public void testSupertypeStaticFieldsInjected(AssertionContext assertions) {
			assertTrue(Tire.hasBeenStaticFieldInjected());
		}

		public void testSupertypeStaticMethodsInjected(AssertionContext assertions) {
			assertTrue(Tire.hasBeenStaticMethodInjected());
		}

		public void testStaticFieldInjectionWithValues(AssertionContext assertions) {
			assertFalse(staticFieldPlainSeat instanceof DriversSeat, "Expected unqualified value");
			assertFalse(staticFieldPlainTire instanceof SpareTire, "Expected unqualified value");
			assertTrue(staticFieldDriversSeat instanceof DriversSeat, "Expected qualified value");
			assertTrue(staticFieldSpareTire instanceof SpareTire, "Expected qualified value");
		}

		public void testStaticMethodInjectionWithValues(AssertionContext assertions) {
			assertFalse(staticMethodPlainSeat instanceof DriversSeat, "Expected unqualified value");
			assertFalse(staticMethodPlainTire instanceof SpareTire, "Expected unqualified value");
			assertTrue(staticMethodDriversSeat instanceof DriversSeat, "Expected qualified value");
			assertTrue(staticMethodSpareTire instanceof SpareTire, "Expected qualified value");
		}

		public void testStaticFieldsInjectedBeforeMethods(AssertionContext assertions) {
			assertFalse(SpareTire.staticMethodInjectedBeforeStaticFields);
		}

		public void testSupertypeStaticMethodsInjectedBeforeSubtypeStaticFields(AssertionContext assertions) {
			assertFalse(SpareTire.subtypeStaticFieldInjectedBeforeSupertypeStaticMethods);
		}

		public void testSupertypeStaticMethodsInjectedBeforeSubtypeStaticMethods(AssertionContext assertions) {
			assertFalse(SpareTire.subtypeStaticMethodInjectedBeforeSupertypeStaticMethods);
		}

		public void testStaticFieldInjectionWithProviders(AssertionContext assertions) {
			assertFalse(staticFieldPlainSeatProvider.get() instanceof DriversSeat, "Expected unqualified value");
			assertFalse(staticFieldPlainTireProvider.get() instanceof SpareTire, "Expected unqualified value");
			assertTrue(staticFieldDriversSeatProvider.get() instanceof DriversSeat, "Expected qualified value");
			assertTrue(staticFieldSpareTireProvider.get() instanceof SpareTire, "Expected qualified value");
		}

		public void testStaticMethodInjectionWithProviders(AssertionContext assertions) {
			assertFalse(staticMethodPlainSeatProvider.get() instanceof DriversSeat, "Expected unqualified value");
			assertFalse(staticMethodPlainTireProvider.get() instanceof SpareTire, "Expected unqualified value");
			assertTrue(staticMethodDriversSeatProvider.get() instanceof DriversSeat, "Expected qualified value");
			assertTrue(staticMethodSpareTireProvider.get() instanceof SpareTire, "Expected qualified value");
		}

		@Override
		public void configure(Tck.TestSuite testSuite) {
			testSuite.addAllOf(this);
		}
	}

	public static class PrivateTests implements Tck.TestCase {

		private final Engine engine;
		private final SpareTire spareTire;

		public PrivateTests(Convertible convertible) {
			this.engine = convertible.engineProvider.get();
			this.spareTire = convertible.spareTire;
		}

		public void testSupertypePrivateMethodInjected(AssertionContext assertions) {
			assertTrue(spareTire.superPrivateMethodInjected);
			assertTrue(spareTire.subPrivateMethodInjected);
		}

		public void testPackagePrivateMethodInjectedSamePackage(AssertionContext assertions) {
			assertTrue(engine.subPackagePrivateMethodInjected);
			assertFalse(engine.superPackagePrivateMethodInjected);
		}

		public void testPrivateMethodInjectedEvenWhenSimilarMethodLacksAnnotation(AssertionContext assertions) {
			assertTrue(spareTire.subPrivateMethodForOverrideInjected);
		}

		public void testSimilarPrivateMethodInjectedOnlyOnce(AssertionContext assertions) {
			assertFalse(spareTire.similarPrivateMethodInjectedTwice);
		}

		@Override
		public void configure(Tck.TestSuite testSuite) {
			testSuite.addAllOf(this);
		}
	}
}
