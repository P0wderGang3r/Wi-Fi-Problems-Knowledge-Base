package data_classes;

public class DiagnosticsMalfunctionClass {
    private final String malfunctionName;

    private final double coefficient;

    public String getMalfunctionName() {
        return malfunctionName;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public DiagnosticsMalfunctionClass(String malfunctionName, double coefficient) {
        this.malfunctionName = malfunctionName;
        this.coefficient = coefficient;
    }
}
