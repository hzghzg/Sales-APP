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
import java.util.List;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {
	private String salesId="Gummy";
	private boolean isNatTrade=false;
	@Mock
	private SalesDao salesDao;
	@Mock
	private SalesReportDao salesReportDao;
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
	@Test
	public void testIsEffectiveDate_givenEffectiveSales_thenReturnFalse() {
		Sales sales=mock(Sales.class);
		Date tomorrowTime=getTomorrowTime();
		Date yesterdayTime=getYesterdayTime();
		when(sales.getEffectiveTo()).thenReturn(tomorrowTime);
		when(sales.getEffectiveFrom()).thenReturn(yesterdayTime);
		salesApp.isEffectiveDate(sales);
		verify(sales,times(1)).getEffectiveFrom();
		verify(sales,times(1)).getEffectiveTo();
		Assert.assertEquals(false,salesApp.isEffectiveDate(sales));
	}
	@Test
	public void testIsEffectiveDate_givenNotEffectiveSales_thenReturnTrue() {
		Sales sales=mock(Sales.class);
		Date tomorrowTime=getTomorrowTime();
		Date yesterdayTime=getYesterdayTime();
		when(sales.getEffectiveTo()).thenReturn(yesterdayTime);
		when(sales.getEffectiveFrom()).thenReturn(tomorrowTime);
		salesApp.isEffectiveDate(sales);
		verify(sales,times(0)).getEffectiveFrom();
		verify(sales,times(1)).getEffectiveTo();
		Assert.assertEquals(true,salesApp.isEffectiveDate(sales));
	}

	@Test
	public void testGetSalesReportData_givenEffectiveSales_thenReturnSalesReportDataList() {
		Sales sales=new Sales();
		List<SalesReportData> salesReportDataList=new ArrayList<>();
		when(salesReportDao.getReportData(sales)).thenReturn(salesReportDataList);
		Assert.assertEquals(salesReportDataList,salesApp.getSalesReportData(sales));
	}

	@Test
	public void testGetHeaders_givenNatTrade_thenReturnHeaderIncludeTime() {
		boolean isNatTrade=true;
		List<String> headers=salesApp.getHeaders(isNatTrade);
		Assert.assertEquals("Time",headers.get(3));
	}

	@Test
	public void testGetHeaders_givenNotNatTrade_thenReturnHeaderIncludeLocalTime() {
		List<String> headers=salesApp.getHeaders(isNatTrade);
		Assert.assertEquals("Local Time",headers.get(3));
	}

	@Test
	public void testUploadDocument_givenSalesActivityReport_thenToXmlBeCall() {
		SalesActivityReport report=mock(SalesActivityReport.class);
		when(report.toXml()).thenReturn("something");
		salesApp.uploadDocument(report);
		verify(report,times(1)).toXml();
	}

	public Date getTomorrowTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, +1);
		return calendar.getTime();
	}
	public Date getYesterdayTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}


}
