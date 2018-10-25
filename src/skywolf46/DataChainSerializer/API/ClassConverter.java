package skywolf46.DataChainSerializer.API;


import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

public interface ClassConverter<T> {
    void writeData(T t, ObjectSerializer serializer);

    T readData(ObjectDeserializer deserializer);

}
