package skywolf46.DataChainSerializer.Calculators;

public class PerWordCalculator implements Calculator {

    private int calc;

    public PerWordCalculator(int calc) {
        this.calc = calc;
    }

    @Override
    public int calculateBytes(int length) {
        return length * calc;
    }

}
