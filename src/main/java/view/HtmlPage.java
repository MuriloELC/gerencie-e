package view;

import java.util.ArrayList;
import java.util.List;

public class HtmlPage {
    private String title;
    private String contextPath;
    private List<String> bodyContent = new ArrayList<>();

    public HtmlPage(String title, String contextPath) {
        this.title = title;
        this.contextPath = contextPath;
    }

    public void addToBody(String html) {
        bodyContent.add(html);
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html lang='pt-BR'>");
        sb.append("<head>");
        sb.append("<meta charset='UTF-8'>");
        sb.append("<title>").append(title).append("</title>");
        sb.append("<link rel='stylesheet' href='").append(contextPath).append("/css/style.css'>");
        sb.append("</head>");
        sb.append("<body>");

        for (String content : bodyContent) {
            sb.append(content);
        }

        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
}
