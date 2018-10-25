package skywolf46.DataChainSerializer.Converters.Collections.Lists;

import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Data.VariableReader;
import skywolf46.DataChainSerializer.DataChainSerializer;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Exception.ConverterNotDefinedException;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListConverter implements ClassConverter<LinkedList> {
    @Override
    public void writeData(LinkedList link, ObjectSerializer serializer) {
        for(Object o : link){
            ClassConverter cv = DataChainSerializer.getConverter(o.getClass());
            if(cv == null)
                throw new ConverterNotDefinedException(o.getClass());
            serializer.writeObject(o);
        }
    }

    @Override
    public LinkedList readData(ObjectDeserializer deserializer) {
        List<VariableReader> va = deserializer.getVariables();
        LinkedList ar = new LinkedList();
        for(VariableReader vr : va){
            ar.add(vr.getObject());
        }
        return ar;
    }
}
