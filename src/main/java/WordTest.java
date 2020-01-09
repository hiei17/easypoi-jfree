import java.util.HashMap;

/**
 * @author 何昌杰
 */
public class WordTest {

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>(4);

        //模拟饼状图数据
        HashMap<String, Integer> datas = new HashMap<>(3);
      
        //模拟其它普通数据
        map.put("username", "张三");
      
        //word模板相对路径、word生成路径、word生成的文件名称、数据源
        WordUtil.exportWord("template/0102月度运维分析报告R1##(1).docx", "D:/", "生成文件2.docx", map);
    }
}
