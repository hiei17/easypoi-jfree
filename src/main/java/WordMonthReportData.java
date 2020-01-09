import cn.afterturn.easypoi.entity.ImageEntity;
import lombok.Data;

/**
 * @Author: panda
 * @Date: 2020/1/9 0009 10:04
 */
@Data
public class WordMonthReportData {

    /**
     * 取项目公司全称 如 甘肃肃南裕固族自治县中能产业园有限公司
     */
    String companyFullName;

    /**
     * 取“电站查询”中的“电站简称”名称
     * 如 肃南县柳古墩滩50MW并网光伏发电项目
     */

    /**
     * 报告月份 如  2019年11月
     */

    /**
     * 编制报表月份 如 2019年12月[编制报表月份]
     */

    /** 
     * 承包开始时间  如  2019年01月01日[]
     */

    /**
     * 并网容量对比 柱状图
     */
    ImageEntity sumPowerCompareBar;
}
