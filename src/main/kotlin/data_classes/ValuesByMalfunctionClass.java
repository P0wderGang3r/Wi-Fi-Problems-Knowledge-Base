package data_classes;

import java.util.ArrayList;

public class ValuesByMalfunctionClass {
    private final MalfunctionClass malfunction;

    private final AttributeClass attribute;

    public MalfunctionClass getMalfunction() {
        return malfunction;
    }

    public AttributeClass getAttribute() {
        return attribute;
    }

    public ValuesByMalfunctionClass(MalfunctionClass malfunction, AttributeClass attribute) {
        this.malfunction = malfunction;
        this.attribute = attribute;
    }

    //------------------------------------------------------------------------------------------------------------------

    private final ArrayList<String> values = new ArrayList<String>();

    public ArrayList<String> getValues() {
        return values;
    }

}
