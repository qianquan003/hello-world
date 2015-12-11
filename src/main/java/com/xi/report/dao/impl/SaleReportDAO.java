package com.xi.report.dao.impl;

import java.util.List;

import com.xi.common.base.dao.impl.BaseDao;
import com.xi.report.dao.ISaleReportDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class SaleReportDAO extends BaseDao implements ISaleReportDAO {
	/**
	 * 房管员销售报表
	 */

	public List<?> getMonthlyReport_Room(String starDate,String endDate) {
		String sql=" WITH TEST_CO AS(SELECT  a.ROM_Id,a.Rom_CEA_Id,a.CO_Id,a.CUT_Id,a.UserName_CHS,a.CEA_Id,(SELECT top 1 CO_Id from V_Sale_Center_Manage_Report b where b.ROM_Id=a.ROM_Id and b.CO_Id<a.CO_Id ORDER BY CO_Create_Time DESC) Last_CO_Id,(SELECT top 1 CUT_Id from V_Sale_Center_Manage_Report b where b.ROM_Id=a.ROM_Id and b.CO_Id<a.CO_Id ORDER BY CO_Create_Time DESC) Last_CUT_Id,(SELECT top 1 b.ROM_Id from V_Sale_Center_Manage_Report b where b.CUT_Id=a.CUT_Id and b.CO_Id<a.CO_Id  ORDER BY CO_Create_Time DESC) Last_Rom_Id,1 type from V_Sale_Center_Manage_Report a   " +
		" 	where CO_Create_Time >='"+starDate+"' and CO_Create_Time<='"+endDate+"' UNION ALL " +
		" SELECT a.ROM_Id,a.Rom_CEA_Id,a.CO_Id,a.CUT_Id,a.UserName_CHS,a.CEA_Id,(SELECT top 1 CO_Id from V_Sale_Center_Manage_Report b where b.ROM_Id=a.ROM_Id and b.CO_Id>a.CO_Id ORDER BY CO_Create_Time ASC) next_CO_Id,(SELECT top 1 CUT_Id from V_Sale_Center_Manage_Report b where b.ROM_Id=a.ROM_Id and b.CO_Id>a.CO_Id ORDER BY CO_Create_Time ASC) next_CUT_Id,(SELECT top 1 b.ROM_Id from V_Sale_Center_Manage_Report b where b.CUT_Id=a.CUT_Id and b.CO_Id>a.CO_Id  ORDER BY CO_Create_Time ASC) next_Rom_Id,2 type from V_Sale_Center_Manage_Report a  " +
		" 	where a.CO_Expire_Date >='"+starDate+"' and a.CO_Expire_Date<='"+endDate+"') " +
		" SELECT t.SeviceCenter_Id,isnull(ares.CEA_Name,' ') SerivceCenter_Name,t.UserID,t.UserName_CHS,isnull(a.num1+a.num2+a.num3+a.num4,0)  signing,isnull(a.num1,0) signing1,isnull(a.num2,0) signing2,isnull(a.num4,0) signing4,isnull(a.num3,0) signing3,isnull(b.num2,0) termination2,isnull(b.num4,0) termination4,isnull(b.num3,0) termination3,isnull(b.num1,0) termination1,isnull(b.num1+b.num2+b.num3+b.num4,0) termination,isnull((a.num1+a.num2+a.num3+a.num4),0)-isnull((b.num1+b.num2+b.num3+b.num4),0) Profit,isnull(a.isAddre,0) isAddre,isnull(a.isAddreNo,0) isAddreNo,ares.CEA_OrderField from tbl_User t LEFT JOIN Area ares on t.SeviceCenter_Id=ares.CEA_Id LEFT JOIN   " +
		" (SELECT CEA_Id,UserName_CHS,SUM(case  when (Last_CO_Id IS NULL and Last_Rom_Id is NULL and Rom_CEA_Id=CEA_Id) then 1 else 0 end) isAddre,SUM(case  when (Last_CO_Id IS NULL and Last_Rom_Id is NULL and Rom_CEA_Id<>CEA_Id) then 1 else 0 end) isAddreNo,SUM(case  when (Last_CO_Id IS NULL and Last_Rom_Id is NULL) then 1 else 0 end) num1,SUM(case  when (ROM_Id=Last_Rom_Id and Last_Rom_Id is not null) then 1 else 0 end) num2,SUM(case  when (Last_Rom_Id is NOT NULL and ROM_Id<>Last_Rom_Id) then 1 else 0 end) num3,SUM(case  when (Last_CO_Id IS NOT NULL  and CUT_Id<>Last_CUT_Id and Last_Rom_Id is NULL) then 1 else 0 end) num4 from TEST_CO where type=1  and UserName_CHS IS not NULL and CEA_Id is NOT NULL  " +
		"  	GROUP BY CEA_Id,UserName_CHS) as a on t.UserName_CHS=a.UserName_CHS " +
		" LEFT JOIN (SELECT CEA_Id,UserName_CHS,SUM(case  when (Last_CO_Id IS NULL and Last_Rom_Id is NULL) then 1 else 0 end) num1,SUM(case  when (ROM_Id=Last_Rom_Id and Last_Rom_Id is not null) then 1 else 0 end) num2,SUM(case  when (Last_Rom_Id is NOT NULL and ROM_Id<>Last_Rom_Id) then 1 else 0 end) num3,SUM(case  when (Last_CO_Id IS NOT NULL  and CUT_Id<>Last_CUT_Id and Last_Rom_Id is NULL) then 1 else 0 end) num4 from TEST_CO where type=2  and UserName_CHS IS not NULL and CEA_Id is NOT NULL  " +
		"  	GROUP BY CEA_Id,UserName_CHS) as b on t.UserName_CHS=b.UserName_CHS " +
		" where a.UserName_CHS is NOT NULL or b.UserName_CHS is NOT NULL ORDER BY ares.CEA_OrderField desc" +
		" ";
		return findBySQL(sql.toString());
	}
	
	/**
	 * 服务中心经理销售报表
	 */

	public List<?> getMonthlyReport_Manager(String starDate,String endDate)
	{
		String sql=" WITH TEST_CO AS(SELECT  a.ROM_Id,a.Rom_CEA_Id,a.CO_Id,a.CUT_Id,a.UserName_CHS,a.CEA_Id,(SELECT top 1 CO_Id from V_Sale_Center_Manage_Report b where b.ROM_Id=a.ROM_Id and b.CO_Id<a.CO_Id ORDER BY CO_Create_Time DESC) Last_CO_Id,(SELECT top 1 CUT_Id from V_Sale_Center_Manage_Report b where b.ROM_Id=a.ROM_Id and b.CO_Id<a.CO_Id ORDER BY CO_Create_Time DESC) Last_CUT_Id,(SELECT top 1 b.ROM_Id from V_Sale_Center_Manage_Report b where b.CUT_Id=a.CUT_Id and b.CO_Id<a.CO_Id  ORDER BY CO_Create_Time DESC) Last_Rom_Id,1 type from V_Sale_Center_Manage_Report a   " +
		" where CO_Create_Time >='"+starDate+"' and CO_Create_Time<='"+endDate+"' UNION ALL " +
		" SELECT a.ROM_Id,a.Rom_CEA_Id,a.CO_Id,a.CUT_Id,a.UserName_CHS,a.CEA_Id,(SELECT top 1 CO_Id from V_Sale_Center_Manage_Report b where b.ROM_Id=a.ROM_Id and b.CO_Id>a.CO_Id ORDER BY CO_Create_Time ASC) next_CO_Id,(SELECT top 1 CUT_Id from V_Sale_Center_Manage_Report b where b.ROM_Id=a.ROM_Id and b.CO_Id>a.CO_Id ORDER BY CO_Create_Time ASC) next_CUT_Id,(SELECT top 1 b.ROM_Id from V_Sale_Center_Manage_Report b where b.CUT_Id=a.CUT_Id and b.CO_Id>a.CO_Id  ORDER BY CO_Create_Time ASC) next_Rom_Id,2 type from V_Sale_Center_Manage_Report a  " +
		" where a.CO_Expire_Date >='"+starDate+"' and a.CO_Expire_Date<='"+endDate+"') " +
		" SELECT t.CEA_Id,t.CEA_Name,(SELECT top 1 UserID from tbl_User where SeviceCenter_Id=t.CEA_Id and PositionID=25) UserID,ISNULL((SELECT top 1 UserName_CHS from tbl_User where SeviceCenter_Id=t.CEA_Id and PositionID=25),' ') as seviceManage,isnull(a.num1+a.num2+a.num3+a.num4,0)  signing,isnull(a.num1,0) signing1,isnull(a.num2,0) signing2,isnull(a.num4,0) signing4,isnull(a.num3,0) signing3,isnull(b.num2,0) termination2,isnull(b.num4,0) termination4,isnull(b.num3,0) termination3,isnull(b.num1,0) termination1,isnull(b.num1+b.num2+b.num3+b.num4,0) termination,isnull((a.num1+a.num2+a.num3+a.num4),0)-isnull((b.num1+b.num2+b.num3+b.num4),0) Profit,t.CEA_OrderField from Area t " +
		" LEFT JOIN (SELECT CEA_Id,SUM(case  when (Last_CO_Id IS NULL and Last_Rom_Id is NULL) then 1 else 0 end) num1,SUM(case  when (ROM_Id=Last_Rom_Id and Last_Rom_Id is not null) then 1 else 0 end) num2,SUM(case  when (Last_Rom_Id is NOT NULL and ROM_Id<>Last_Rom_Id) then 1 else 0 end) num3,SUM(case  when (Last_CO_Id IS NOT NULL  and CUT_Id<>Last_CUT_Id and Last_Rom_Id is NULL) then 1 else 0 end) num4 from TEST_CO where type=1  and UserName_CHS IS not NULL and CEA_Id is NOT NULL GROUP BY CEA_Id) as a on t.CEA_Id=a.CEA_Id  " +
		" LEFT JOIN (SELECT CEA_Id,SUM(case  when (Last_CO_Id IS NULL and Last_Rom_Id is NULL) then 1 else 0 end) num1,SUM(case  when (ROM_Id=Last_Rom_Id and Last_Rom_Id is not null) then 1 else 0 end) num2,SUM(case  when (Last_Rom_Id is NOT NULL and ROM_Id<>Last_Rom_Id) then 1 else 0 end) num3,SUM(case  when (Last_CO_Id IS NOT NULL  and CUT_Id<>Last_CUT_Id and Last_Rom_Id is NULL) then 1 else 0 end) num4 from TEST_CO where type=2  and UserName_CHS IS not NULL and CEA_Id is NOT NULL GROUP BY CEA_Id) as b on t.CEA_Id=b.CEA_Id " +
		" where a.CEA_Id is NOT NULL or b.CEA_Id is NOT NULL ORDER BY t.CEA_OrderField " +
		" ";
		List<?> list = findBySQL(sql.toString());
		return list;
	}


	public Integer getRoomId(String address) {
		String sql="SELECT top 1 ROM_Id from Room WHERE ROM_Address ='"+address+"'";
		List<?> list=findBySQL(sql);
		Integer romId=0;
		if(list.size()>0){
			Object Obj = (Object) list.get(0);
			String objStr = Obj.toString();
			romId=Integer.parseInt(objStr);
		}
		return romId;
	}
}
