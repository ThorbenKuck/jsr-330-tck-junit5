package org.atinject.tck.auto;

import org.atinject.tck.Tck;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

public class ExampleTest {

	@TestFactory
	public List<DynamicNode> test() {
		return Tck.testFor(new TestCar(), true, true);
	}

}
