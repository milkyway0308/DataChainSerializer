package skywolf46.DataChainSerializer.Enums;


import skywolf46.DataChainSerializer.Calculators.Calculator;
import skywolf46.DataChainSerializer.Calculators.DirectCalculator;
import skywolf46.DataChainSerializer.Calculators.PerWordCalculator;

import java.util.HashMap;

public enum WriteType {
    STRING(0,new PerWordCalculator(2)), FLOAT(1,new DirectCalculator(4)), INTEGER(2,new DirectCalculator(4)),
    DOUBLE(3,new DirectCalculator(8)), BYTE(4,new DirectCalculator(1)),
    SHORT(5,new DirectCalculator(2)), CHAR(6,new DirectCalculator(2)), BOOLEAN(7,new DirectCalculator(1)),
    CHARS(8,new PerWordCalculator(2)), BYTES(9,new PerWordCalculator(1)), LONG(10,new DirectCalculator(8)),

    CUSTOM_OBJECT(9999,new DirectCalculator(0));
    private int numb;
    private static HashMap<Integer,WriteType> types;
    private Calculator calc;
    WriteType(int item, Calculator calc){
        numb = item;
        add();
        this.calc = calc;
    }

    private void add(){
        if(types == null)
            types = new HashMap<>();
        types.put(numb,this);
    }

    public static WriteType get(int a){
        return types.get(a);
    }

    public int getNumbering(){
        return numb;
    }

    public Calculator getCalculator() {
        return calc;
    }
}
