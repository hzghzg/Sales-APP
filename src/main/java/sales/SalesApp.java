package sales;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {
	private SalesDao salesDao;
	private SalesReportDao salesReportDao;
	private Sales sales;
	public void generateSalesActivityReport(String salesId, boolean isNatTrade) {

		if (salesId == null) return;
		this.sales = getSales(salesId);

		if (isEffectiveDate(this.sales)) return;

		List<SalesReportData> reportDataList = getSalesReportData(this.sales);

		List<String> headers = getHeaders(isNatTrade);

		SalesActivityReport report = this.generateReport(headers, reportDataList);

		uploadDocument(report);

	}

	public Sales getSales(String salesId) {
		//SalesDao salesDao = new SalesDao();//销售数据访问对象
		return salesDao.getSalesBySalesId(salesId);//拿到sales
	}

	public boolean isEffectiveDate(Sales sales) {
		Date today = new Date();
		return (today.after(sales.getEffectiveTo()) || today.before(sales.getEffectiveFrom()));//如果不是有效日期
	}

	public List<SalesReportData> getSalesReportData(Sales sales) {
		//SalesReportDao salesReportDao = new SalesReportDao();//销售报告访问对象
		return salesReportDao.getReportData(sales);
	}


	public List<String> getHeaders(boolean isNatTrade) {
		List<String> headers = null;
		if (isNatTrade) {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}
		return headers;
	}

	public void uploadDocument(SalesActivityReport report) {
		EcmService ecmService = new EcmService();
		ecmService.uploadDocument(report.toXml());
	}

	public SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}