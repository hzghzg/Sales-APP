package sales;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {
	private String salesId="Gummy";
	private boolean isNatTrade=false;
	@Mock
	private SalesDao salesDao;
	@Mock
	private Sales sales;
	@InjectMocks
	private SalesApp salesApp;
	@Test
	public void testGenerateReport_givenNullSalesId_thenGenerateReportNotBeCalled() {

		SalesApp salesApp =spy(new SalesApp());
		salesApp.generateSalesActivityReport(null,isNatTrade);
		verify(salesApp,times(1)).generateSalesActivityReport(null,isNatTrade);
		verify(salesApp,never()).generateReport(anyList(),anyList());
	}

	@Test
	public void testGenerateReport_givenSalesIdAndIsNatTrade_thenGenerateReportBeCalled() {
		Sales sales=new Sales();
		SalesApp salesApp =spy(new SalesApp());
		doReturn(sales).when(salesApp).getSales(salesId);
		doReturn(false).when(salesApp).isEffectiveDate(sales);
		doReturn(new ArrayList<SalesReportData>()).when(salesApp).getSalesReportData(sales);
		doReturn(new ArrayList<String>()).when(salesApp).getHeaders(isNatTrade);
		doReturn(new SalesActivityReport()).when(salesApp).generateReport(anyList(),anyList());
		doNothing().when(salesApp).uploadDocument(any());
		salesApp.generateSalesActivityReport(salesId,isNatTrade);
		verify(salesApp,times(1)).generateReport(anyList(),anyList());
	}

	@Test
	public void testGetSales_givenExistSalesId_thenReturnSales() {
		Sales sales=new Sales();
		when(salesDao.getSalesBySalesId(salesId)).thenReturn(sales);
		Assert.assertEquals(sales,salesApp.getSales(salesId));
	}
	@Test
	public void testGetSales_givenNotExistSalesId_thenReturnNull() {
		String salesId="notExistSalesId";
		when(salesDao.getSalesBySalesId(salesId)).thenReturn(null);
		Assert.assertEquals(null,salesApp.getSales(salesId));
	}
//	@Test
//	public void testIsEffectiveDate_givenEffectiveSales_thenReturnFalse() {
//		Date tomorrowTime=getTomorrowTime();
//		Date yesterdayTime=getYesterdayTime();
//		when(sales.getEffectiveTo()).thenReturn(tomorrowTime);
//		when(sales.getEffectiveFrom()).thenReturn(yesterdayTime);
//		Assert.assertEquals(false,salesApp.isEffectiveDate(sales));
//	}
	@Test
	public void testIsEffectiveDate_givenNotEffectiveSales_thenReturnTrue() {
		Date tomorrowTime=getTomorrowTime();
		Date yesterdayTime=getYesterdayTime();
		when(sales.getEffectiveTo()).thenReturn(yesterdayTime);
		when(sales.getEffectiveFrom()).thenReturn(tomorrowTime);
		Assert.assertEquals(true,salesApp.isEffectiveDate(sales));
	}
	public Date getTomorrowTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,+1);
		return calendar.getTime();
	}
	public Date getYesterdayTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,-1);
		return calendar.getTime();
	}


}
