package sales;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class SalesAppTest {

	@Test
	public void testGenerateReport_givenNullSalesId_thenGenerateReportNotBeCalled() {

		SalesApp salesApp =spy(new SalesApp());
		salesApp.generateSalesActivityReport(null, 1000, false, false);
		verify(salesApp,times(1)).generateSalesActivityReport(null, 1000, false, false);
		verify(salesApp,never()).generateReport(anyList(),anyList());
	}
}
