package skywolf46.DataChainSerializer.Converters.Dates;

import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Deserializers.ObjectFieldDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectFieldSerializer;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GregorianCalendarConverter implements ClassConverter<GregorianCalendar> {
    @Override
    public void writeData(GregorianCalendar cal, ObjectSerializer serializer) {
        ObjectFieldSerializer ofd = (ObjectFieldSerializer) serializer.addSubSerializer(new ObjectFieldSerializer());
        ofd.writeInt("Year",cal.get(Calendar.YEAR));
        ofd.writeInt("Month",cal.get(Calendar.MONTH));
        ofd.writeInt("Date",cal.get(Calendar.DATE));
        ofd.writeInt("Hour",cal.get(Calendar.HOUR_OF_DAY));
        ofd.writeInt("Minute",cal.get(Calendar.MINUTE));
        ofd.writeInt("Second",cal.get(Calendar.SECOND));
    }

    @Override
    public GregorianCalendar readData(ObjectDeserializer deserializer) {
        ObjectFieldDeserializer ofd = (ObjectFieldDeserializer) deserializer.getSubDeserializers().get(0);
        return new GregorianCalendar(
                ofd.getReader("Year").asInt(),
                ofd.getReader("Month").asInt(),
                ofd.getReader("Date").asInt(),
                ofd.getReader("Hour").asInt(),
                ofd.getReader("Minute").asInt(),
                ofd.getReader("Second").asInt()
        );
    }
}
