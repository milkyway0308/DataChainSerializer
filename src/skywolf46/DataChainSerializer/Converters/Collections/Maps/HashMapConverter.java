package skywolf46.DataChainSerializer.Converters.Collections.Maps;

import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Deserializers.ObjectFieldDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectFieldSerializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapConverter implements ClassConverter<HashMap> {
    @Override
    public void writeData(HashMap hashMap, ObjectSerializer serializer) {
        serializer.writeInt(hashMap.size());
        ObjectSerializer key = serializer.addSubSerializer(new ObjectSerializer());
        ObjectSerializer val = serializer.addSubSerializer(new ObjectSerializer());
        Set<Map.Entry> sEntry = hashMap.entrySet();
        for(Map.Entry ev : sEntry){
            key.writeObject(ev.getKey());
            val.writeObject(ev.getValue());
        }
    }

    @Override
    public HashMap readData(ObjectDeserializer deserializer) {
        HashMap m = new HashMap();
        int size = deserializer.getVariables().get(0).asInt();
        ObjectDeserializer key = deserializer.getSubDeserializers().get(0);
        ObjectDeserializer val = deserializer.getSubDeserializers().get(1);
        for (int i = 0; i < size; i++)
            m.put(key.getVariables().get(i).getObject(), val.getVariables().get(i).getObject());
        return m;
    }
}
