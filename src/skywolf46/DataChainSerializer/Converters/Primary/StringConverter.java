package skywolf46.DataChainSerializer.Converters.Primary;


import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

public class StringConverter implements ClassConverter<String> {
    @Override
    public void writeData(String s, ObjectSerializer serializer) {
        serializer.writeString(s);
    }

    @Override
    public String readData(ObjectDeserializer deserializer) {
        return deserializer.getVariables().get(0).asString();
    }
}
