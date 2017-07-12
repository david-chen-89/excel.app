package excel.app;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import shipment.report.Util;

public class UtilTest {

	@Test
	public void testChgReference() {
		assertEquals(Util.chgReference("E149546841,E149546840,E149546839,6"), "E149546841,E149546840,E149546839");
		assertEquals(Util.chgReference("E149546841, E149546840, E149546839, 6"), "E149546841, E149546840, E149546839");
		assertEquals(Util.chgReference("E149546841,E149546840"), "E149546841,E149546840");
		assertEquals(Util.chgReference(""), "");
	}

	@Test
	public void testChgNote() {
		assertEquals(Util.chgNote("P2345 message from XXXXX: leave at back door if no one is home"), "leave at back door if no one is home");
		assertEquals(Util.chgNote("P2345 message from XXXXX:leave at back door if no one is home"), "leave at back door if no one is home");
		assertEquals(Util.chgNote("p2345 message from XXXXX: leave at back door if no one is home").trim(), "leave at back door if no one is home");
		assertEquals(Util.chgNote("abc p2345 message from XXXXX: leave at back door if no one is home").trim(), "abc leave at back door if no one is home");
	}
}
