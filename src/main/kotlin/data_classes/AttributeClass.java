package data_classes;

import java.util.ArrayList;

public class AttributeClass {

    //-----------------------------------------------ОПИСАНИЕ ПРИЗНАКА--------------------------------------------------
    private final int number;

    private String name;

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeClass(int number, String name) {
        this.number = number;
        this.name = name;
    }

    //----------------------------------------------ВОЗМОЖНЫЕ ЗНАЧЕНИЯ--------------------------------------------------

    private final ArrayList<AttributeValueClass> availableValues = new ArrayList<>();

    public ArrayList<AttributeValueClass> getAvailableValues() {
        return availableValues;
    }

    //---------------------------------------------НОРМАЛЬНЫЕ ЗНАЧЕНИЯ--------------------------------------------------

    private final ArrayList<AttributeValueClass> normalValues = new ArrayList<>();

    public ArrayList<AttributeValueClass> getNormalValues() {
        return normalValues;
    }

}
