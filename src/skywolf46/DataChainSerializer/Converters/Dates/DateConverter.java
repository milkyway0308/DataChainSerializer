package skywolf46.DataChainSerializer.Converters.Dates;


import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

import java.util.Date;

public class DateConverter implements ClassConverter<Date> {
    @Override
    public void writeData(Date date, ObjectSerializer serializer) {
        serializer.writeLong(date.getTime());
    }

    @Override
    public Date readData(ObjectDeserializer deserializer) {
        return new Date(deserializer.getVariables().get(0).asLong());
    }
}
