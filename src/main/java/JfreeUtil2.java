import cn.afterturn.easypoi.entity.ImageEntity;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.util.Assert;
import util.ChartUtils;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author 何昌杰
 * <p>
 * 参考API博客 https://blog.csdn.net/dagecao/article/details/86536680
 */
@Slf4j
public class JfreeUtil2 {

    private static final String tempImgPath = "D:\\tempJfree.jpeg";
    private static final String noDataMessage = "暂无数据";
    private static final Integer width=500;
    private static final Integer height = 300;
 

    /**
     * 将图片转化为字节数组
     *
     * @return 字节数组
     */
    private synchronized static byte[] imgToByte() {
        File file = new File(tempImgPath);
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        //删除临时文件
        file.delete();
        return buffer;
    }




    public static ImageEntity createChart(String title, DefaultCategoryDataset data) {
        boolean legend= data.getColumnCount()<2;
        JFreeChart chart = ChartFactory.createBarChart(title, "", "", data, PlotOrientation.VERTICAL,
                legend,false,false);
 
        
        // 3:设置抗锯齿，防止字体显示不清楚
        ChartUtils.setAntiAlias(chart);// 抗锯齿
        // 4:对柱子进行渲染
        ChartUtils.setBarRenderer(chart.getCategoryPlot(), false);//
        // 5:对其他部分进行渲染
        ChartUtils.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
        ChartUtils.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
        // 设置标注无边框
        if (legend){

            chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
        }
       return covertEasyPoiImagViaTempFile(500,300,chart);
        
    }

    public static ImageEntity covertEasyPoiImagViaTempFile(int width, int height, JFreeChart chart) {
        //设置抗锯齿
        chart.setTextAntiAlias(false);
        try {
            ChartUtilities.saveChartAsPNG(new File(tempImgPath), chart, width, height);
        } catch (IOException e1) {
            log.error("生成饼状图失败！");
        }

        ImageEntity imageEntity = new ImageEntity(imgToByte(), width, height);
        Assert.notNull(imageEntity.getData(), "生成饼状图对象失败！");
        return imageEntity;
    }
}
