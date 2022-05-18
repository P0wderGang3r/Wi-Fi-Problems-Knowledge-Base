package data_classes;

public class MalfunctionClass {
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

    public MalfunctionClass(int number, String name) {
        this.number = number;
        this.name = name;
    }
}
