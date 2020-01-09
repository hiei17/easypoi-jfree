import cn.afterturn.easypoi.entity.ImageEntity;
import org.jfree.data.category.DefaultCategoryDataset;
import util.ChartUtils;
import util.Serie;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/**
 * @author 何昌杰
 */
public class WordMonthReportExport {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        WordMonthReportData data=new WordMonthReportData();
        data.setCompanyFullName("项目公司全称");

        ImageEntity barChart = JfreeUtil2.createBar("并网容量对比", createBar());
        data.setSumPowerCompareBar(barChart);
        
        
        data.setPowerIrradiationLine(JfreeUtil2.createLine("日发电量与辐照量对比",createLineDataset()));

        Map<String, Object> map = transBean2Map(data);
                
        //word模板相对路径、word生成路径、word生成的文件名称、数据源
        WordUtil.exportWord("template/monthReport.docx", "D:/", "生成文件.docx", map);
    }

    public static DefaultCategoryDataset createBar() {
        // 标注类别
        String[] categories = {"目标发电量", "实际并网容量"};
        Vector<Serie> series = new Vector<Serie>();
        // 柱子名称：柱子所有的值集合
        series.add(new Serie("单位：MWp", new Double[]{49.9, 71.5}));

        // 1：创建数据集合
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }


    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;
    }


    public static DefaultCategoryDataset createLineDataset() {

       
        // 标注类别
        String[] categories = new String[31];
        for (int i = 0; i < categories.length; i++) {
            categories[i]=i+1+"";
            
        }
        Vector<Serie> series = new Vector<>();
        Double[] a=   new Double[31];
        for (int i = 0; i < a.length; i++) {
            a[i]=Math.random();
            
        }
        Double[] b=   new Double[31];
        for (int i = 0; i < a.length; i++) {
            b[i]=Math.random();
            
        }
        // 柱子名称：柱子所有的值集合
        series.add(new Serie("日发电量(万kwh)", a));
        series.add(new Serie("日辐照量(MJ/m²)", b));
      
       
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }
}
