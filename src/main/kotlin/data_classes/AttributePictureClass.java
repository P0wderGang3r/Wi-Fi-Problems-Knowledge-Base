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

    //------------------------------------------------------------------------------------------------------------------

    private final ArrayList<AttributeClass> attributes = new ArrayList<>();

    public ArrayList<AttributeClass> getAttributes() {
        return attributes;
    }

}
