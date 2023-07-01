package org.jeecg.modules.demo.cc.controller;

import com.aspose.words.*;

public class RemoveBlankPagesWithFooter {
    public static void removeBlankPages(String filePath) throws Exception {
        Document doc = new Document(filePath);

        for (int i = doc.getSections().getCount() - 1; i >= 0; i--) {
            Section section = doc.getSections().get(i);
            if (isSectionBlank(section)) {
                removeFooterFromPreviousSection(doc, i);
                doc.getSections().removeAt(i);
            }
        }

        doc.save(filePath);
    }

    private static boolean isSectionBlank(Section section) throws Exception {
        NodeCollection<Node> nodes = section.getBody().getChildNodes();
        for (Node node : nodes) {
            if (node.getNodeType() == NodeType.PARAGRAPH || node.getNodeType() == NodeType.TABLE) {
                return false;
            }
        }
        return true;
    }

    private static void removeFooterFromPreviousSection(Document doc, int sectionIndex) throws Exception {
        if (sectionIndex > 0) {
            Section previousSection = doc.getSections().get(sectionIndex - 1);
            HeaderFooter footer = previousSection.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);
            if (footer != null) {
                footer.removeAllChildren();
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\1\\Desktop\\works\\44444.docx";  // Word文件路径

        try {
            removeBlankPages(filePath);
            System.out.println("带有页脚的空白页已成功删除。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
