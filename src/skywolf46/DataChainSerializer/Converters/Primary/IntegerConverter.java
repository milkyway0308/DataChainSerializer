package skywolf46.DataChainSerializer.Converters.Primary;


import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

public class IntegerConverter implements ClassConverter<Integer> {
    @Override
    public void writeData(Integer integer, ObjectSerializer serializer) {
        serializer.writeInt(integer);
//        System.out.println("Write int");
    }

    @Override
    public Integer readData(ObjectDeserializer deserializer) {
        return deserializer.getVariables().get(0).asInt();
    }
}
