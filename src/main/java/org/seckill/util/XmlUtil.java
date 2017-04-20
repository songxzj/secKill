package org.seckill.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtil {

    private final static Logger logger = LoggerFactory.getLogger(XmlUtil.class);
	
	/**
	 * 格式化 xml
	 * @param inputXML
	 * @return
	 */
	public static String formatXML(String inputXML) {

        SAXReader reader = new SAXReader();

        String requestXML = null;
        XMLWriter writer = null;

        try {
            Document doc = reader.read(new StringReader(inputXML));

            //创建输出格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            //设置xml的输出编码
            format.setEncoding("UTF-8");
            //创建输出(目标)
            StringWriter out = new StringWriter();
            //创建输出流
            writer = new XMLWriter(out, format);

            writer.write(doc);
            writer.flush();
            requestXML = out.toString();
        } catch (DocumentException e) {
            logger.error("Method[formatXML]文档异常：{}",e.getMessage(),e);
        } catch (IOException e) {
            logger.error("Method[formatXML] IO 流异常：{}",e.getMessage(),e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error("Method[formatXML]关闭 writer 异常：{}",e.getMessage(),e);
                }
            }
        }
        return requestXML;
    }

}
