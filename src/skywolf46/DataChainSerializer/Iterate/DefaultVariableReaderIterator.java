package skywolf46.DataChainSerializer.Iterate;


import skywolf46.DataChainSerializer.Data.VariableReader;

import java.util.Iterator;
import java.util.List;

public class DefaultVariableReaderIterator implements Iterator<VariableReader> {
    private List<VariableReader> readers;
    private int pointer = 0;

    public DefaultVariableReaderIterator(List<VariableReader> vr) {
        this.readers = vr;
    }

    @Override
    public boolean hasNext() {
        return pointer < readers.size();
    }

    @Override
    public VariableReader next() {
        return readers.get(pointer++);
    }

    @Override
    public void remove() {
        throw new RuntimeException("Variable reader iterator not support remove()");
    }
}
