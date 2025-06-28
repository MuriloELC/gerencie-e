package view;

public class HtmlForm {
    private String method;
    private String action;
    private StringBuilder fields = new StringBuilder();

    public HtmlForm(String method, String action) {
        this.method = method;
        this.action = action;
    }

    public void addInput(String label, String name, String type) {
        fields.append("<label>").append(label).append(":</label><br>");
        fields.append("<input type='").append(type).append("' name='").append(name).append("' required><br><br>");
    }

    public void addSelect(String label, String name, String[] options) {
        fields.append("<label>").append(label).append(":</label><br>");
        fields.append("<select name='").append(name).append("'>");
        for (String option : options) {
            fields.append("<option value='").append(option).append("'>").append(option).append("</option>");
        }
        fields.append("</select><br><br>");
    }

    public void addButton(String label) {
        fields.append("<input type='submit' value='").append(label).append("'>");
    }

    public String render() {
        return "<form method='" + method + "' action='" + action + "'>" + fields.toString() + "</form>";
    }
}
