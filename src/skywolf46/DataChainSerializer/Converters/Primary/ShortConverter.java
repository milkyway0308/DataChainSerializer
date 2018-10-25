package skywolf46.DataChainSerializer.Converters.Primary;


import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

public class ShortConverter implements ClassConverter<Short> {
    @Override
    public void writeData(Short aShort, ObjectSerializer serializer) {
        serializer.writeShort(aShort);
    }

    @Override
    public Short readData(ObjectDeserializer deserializer) {
        return deserializer.getVariables().get(0).asShort();
    }
}
