package data_classes;

import java.util.ArrayList;

public class AttributePictureClass {
    private final MalfunctionClass malfunction;

    public MalfunctionClass getMalfunction() {
        return malfunction;
    }

    public AttributePictureClass(MalfunctionClass malfunction) {
        this.malfunction = malfunction;
    }

    private boolean isEditable = true;

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    //------------------------------------------------------------------------------------------------------------------

    private final ArrayList<ValuesByAttributeClass> valuesByAttributes = new ArrayList<>();

    public ArrayList<ValuesByAttributeClass> getValuesByAttributes() {
        return valuesByAttributes;
    }

}
