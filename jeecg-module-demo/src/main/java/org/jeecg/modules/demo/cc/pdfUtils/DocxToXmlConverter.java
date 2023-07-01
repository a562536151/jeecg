package org.jeecg.modules.demo.cc.pdfUtils;

import com.aspose.words.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipFile;

@Component
@Slf4j
public class DocxToXmlConverter {




    public void docxToXml(Map<String, Object> programFile, String param) throws Exception {


        String docxPath = (String) programFile.get("docxPath");
        // docx文件路径
        String docxFilePath = docxPath + param;
        // 输出文件路径
        String xmlFilePath = docxPath + "document.xml";


        Document doc = new Document(docxFilePath);



        try (ZipFile zipFile = new ZipFile(docxFilePath);
             InputStream inputStream = zipFile.getInputStream(zipFile.getEntry("word/document.xml"));
             FileOutputStream outputStream = new FileOutputStream(xmlFilePath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            log.info("生成文件：" + xmlFilePath);
        } catch (IOException e) {
            log.error("文件处理失败", e);
        }
    }



}

