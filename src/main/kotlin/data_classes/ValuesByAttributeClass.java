package data_classes;

import java.util.ArrayList;

public class ValuesByAttributeClass {
    private final AttributeClass attribute;

    public AttributeClass getAttribute() {
        return attribute;
    }

    public ValuesByAttributeClass(AttributeClass attribute) {
        this.attribute = attribute;
    }

    //------------------------------------------------------------------------------------------------------------------

    private ArrayList<String> values = new ArrayList<String>();

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

}
