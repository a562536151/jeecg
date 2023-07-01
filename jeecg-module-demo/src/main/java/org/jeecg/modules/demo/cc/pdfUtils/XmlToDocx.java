package org.jeecg.modules.demo.cc.pdfUtils;

import com.aspose.words.Document;
import com.aspose.words.NodeCollection;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


@Component
@Slf4j
public class XmlToDocx {

    @Autowired
    private  XmlTplUtil xmlTplUtil;



    public void toDocx(String xmlTemplate, String docxTemplate, String xmlTemp, String toFilePath, Map map) {
        try (Writer w1 = new OutputStreamWriter(new FileOutputStream(xmlTemp), "utf-8")) {
            // 1.map是动态传入的数据
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开

            // 2.把map中的数据动态由freemarker传给xml
            map.put("docxTemplates",docxTemplate);
            xmlTplUtil.process(xmlTemplate, map, w1);

            // 3.把填充完成的xml写入到docx中
            XmlToDocx xtd = new XmlToDocx();
            xtd.outDocx(new File(xmlTemp), docxTemplate, toFilePath);
            log.info("map数据填充xml成功");

            // 加载文档



        } catch (Exception e) {
            log.error(docxTemplate + "xml填充数据失败", e);
        }
    }






    public void outDocx(File documentFile, String docxTemplate, String toFilePath) throws ZipException, IOException {
        try (ZipFile zipFile = new ZipFile(new File(docxTemplate));
             ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(toFilePath))) {
            Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
            int len = -1;
            byte[] buffer = new byte[1024];
            while (zipEntrys.hasMoreElements()) {
                ZipEntry next = zipEntrys.nextElement();
                InputStream is = zipFile.getInputStream(next);
                // 把输入流的文件传到输出流中 如果是word/document.xml由我们输入
                zipout.putNextEntry(new ZipEntry(next.toString()));
                if ("word/document.xml".equals(next.toString())) {
                    InputStream in = new FileInputStream(documentFile);
                    while ((len = in.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    in.close();
                } else {
                    while ((len = is.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    is.close();
                }
            }
        } catch (Exception e) {
            log.error(docxTemplate + "动态生成数据的docunment.xml文件失败", e);
        }
    }



}
