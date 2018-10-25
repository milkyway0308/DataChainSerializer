package skywolf46.DataChainSerializer.Converters.Collections.Maps;

import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

import java.util.HashMap;

public class HashMapConverter implements ClassConverter<HashMap> {
    @Override
    public void writeData(HashMap hashMap, ObjectSerializer serializer) {

    }

    @Override
    public HashMap readData(ObjectDeserializer deserializer) {
        HashMap m = new HashMap();
        return m;
    }
}
