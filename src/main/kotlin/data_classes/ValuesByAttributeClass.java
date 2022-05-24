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

    private ArrayList<AttributeValueClass> values = new ArrayList<>();

    public ArrayList<AttributeValueClass> getValues() {
        return values;
    }

    public void setValues(ArrayList<AttributeValueClass> values) {
        this.values = values;
    }

}
