package data_classes;

public class DiagnosticsClass {
    private final AttributeClass attribute;
    private String value;

    public AttributeClass getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DiagnosticsClass(AttributeClass attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }
}
