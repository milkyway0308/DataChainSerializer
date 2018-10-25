package skywolf46.DataChainSerializer.Converters.Primary;

import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

public class CharConverter implements ClassConverter<Character> {
    @Override
    public void writeData(Character character, ObjectSerializer serializer) {
        serializer.writeChar(character);
    }

    @Override
    public Character readData(ObjectDeserializer deserializer) {
        return deserializer.getVariables().get(0).asChar();
    }
}
