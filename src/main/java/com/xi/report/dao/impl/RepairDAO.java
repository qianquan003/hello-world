package com.xi.report.dao.impl;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.xi.common.base.dao.impl.BaseDao;
import com.xi.report.dao.IRepairDAO;
import com.xi.report.entity.RepairDTO;
@Repository
@Scope("prototype")
public class RepairDAO extends BaseDao implements IRepairDAO{
	
	@Resource(name = "hw_sessionFactory")
	@Override
	public void setInjectionSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	public List<RepairDTO> getRepair(String month) {
		String sql = "SELECT tkb.belongArea, tkb.address, rrf.fee, rrf.create_Time, rrf.create_Operator FROM Rpt_Repair_Fee rrf LEFT JOIN TWorkbill tkb ON rrf.workbillID = tkb.workbillNo WHERE rrf.fee <> 0 and CONVERT (VARCHAR (7),rrf.create_Time,120)='"+month+"'";
		List<?> list = findBySQL(sql);
		List<RepairDTO> listRep = new ArrayList<RepairDTO>();
		if(list!=null && list.size()>0){
			Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < list.size(); i++) {
				Object[] decimal = (Object[]) list.get(i);
				RepairDTO dto = new RepairDTO();
				dto.setBelongArea(Integer.parseInt(decimal[0]+""));
				dto.setAddress(decimal[1]+"");
				dto.setFee(Double.valueOf(decimal[2]+""));
				try {
					Date d = (Date) f.parseObject(decimal[3]+"");
					Timestamp ts = new Timestamp(d.getTime());
					dto.setCreateTime(ts);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dto.setCreateOperator(decimal[4]+"");
				listRep.add(dto);
			}
		}
		return listRep;
	}

}
