package skywolf46.DataChainSerializer.Converters.Primary;


import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

public class FloatConverter implements ClassConverter<Float> {
    @Override
    public void writeData(Float f, ObjectSerializer serializer) {
        serializer.writeDouble(f);
    }

    @Override
    public Float readData(ObjectDeserializer deserializer) {
        return deserializer.getVariables().get(0).asFloat();
    }
}
