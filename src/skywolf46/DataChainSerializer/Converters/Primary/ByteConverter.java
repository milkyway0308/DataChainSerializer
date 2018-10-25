package skywolf46.DataChainSerializer.Converters.Primary;


import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

public class ByteConverter implements ClassConverter<Byte> {
    @Override
    public void writeData(Byte aByte, ObjectSerializer serializer) {
        serializer.writeByte(aByte);
    }

    @Override
    public Byte readData(ObjectDeserializer deserializer) {
        return deserializer.getVariables().get(0).asByte();
    }
}
