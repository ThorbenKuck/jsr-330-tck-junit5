package org.atinject.tck.auto;

import jakarta.inject.Provider;

public class TestCar extends Convertible {

	public TestCar() {
		super(null, null, null, null, () -> null, () -> null, () -> null, () -> null);
		super.engineProvider = () -> null;
	}
}
