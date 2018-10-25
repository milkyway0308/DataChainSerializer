package skywolf46.DataChainSerializer.Calculators;

public class DirectCalculator implements Calculator{
    private int calc;

    public DirectCalculator(int calc) {
        this.calc = calc;
    }

    @Override
    public int calculateBytes(int length) {
        return calc;
    }
}
