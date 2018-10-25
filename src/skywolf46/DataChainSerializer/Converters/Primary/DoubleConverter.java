package skywolf46.DataChainSerializer.Converters.Primary;

import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

public class DoubleConverter implements ClassConverter<Double> {
    @Override
    public void writeData(Double aDouble, ObjectSerializer serializer) {
        serializer.writeDouble(aDouble);
    }

    @Override
    public Double readData(ObjectDeserializer deserializer) {
        return deserializer.getVariables().get(0).asDouble();
    }
}
