package ua.softserveinc.tc.entity.converter;

import java.sql.Time;
import java.time.LocalTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime,Time> {

  @Override
  public Time convertToDatabaseColumn(LocalTime localTime) {
    return (localTime == null ? null : Time.valueOf(localTime));
  }

  @Override
  public LocalTime convertToEntityAttribute(Time time) {
    return (time == null ? null : time.toLocalTime());
  }
}
