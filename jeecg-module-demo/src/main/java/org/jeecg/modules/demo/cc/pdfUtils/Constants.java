package org.jeecg.modules.demo.cc.pdfUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;








public class Constants {
    /**
     * aspose需要的签名文件名称
     */
    //public static String licensePath = "jeecg-boot-module-system/src/main/resources/templates/wordTemplate/license.xml";


    //docx文件路径
   // static String docxPath = "jeecg-boot-module-system/src/main/resources/templates/wordTemplate/";

    /**
     * 模板，签名文件存放的路径
     */
//public static String mlTemplatePath = "/Users/lisw/work/work-project/workTppdf/";
    //public static String docXmlTemplatePath = "jeecg-boot-module-system/src/main/resources/templates/wordTemplate/";
}


















/*
@Component
public class Constants {

    private static String programmeFilePath;

    @Value("${jeecg.path.upload}")
    public void setProgrammeFilePath(String programmeFilePath) {
        Constants.programmeFilePath = programmeFilePath;
    }

    */
/**
     * aspose需要的签名文件名称
     *//*

    public static String licensePath = "license.xml";

    */
/**
     * 模板，签名文件存放的路径
     *//*

    public static String docXmlTemplatePath;

    @PostConstruct
    public static void init() {
        docXmlTemplatePath = programmeFilePath;
    }
}
*/
