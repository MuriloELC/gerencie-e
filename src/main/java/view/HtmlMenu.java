package view;

import java.util.LinkedHashMap;
import java.util.Map;

public class HtmlMenu {
    private Map<String, String> links = new LinkedHashMap<>();

    public void addItem(String label, String action) {
        links.put(label, action);
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<nav><ul class='menu'>");
        for (Map.Entry<String, String> entry : links.entrySet()) {
            sb.append("<li><a href='?action=").append(entry.getValue()).append("'>")
              .append(entry.getKey()).append("</a></li>");
        }
        sb.append("</ul></nav>");
        return sb.toString();
    }
}