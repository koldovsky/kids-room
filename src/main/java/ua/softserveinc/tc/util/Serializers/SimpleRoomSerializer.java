package ua.softserveinc.tc.util.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.*;
import ua.softserveinc.tc.entity.Room;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles JSON Serialization for DayOff Entity's ManyToMany mapping.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SimpleRoomSerializer  extends JsonSerializer<Set<Room>> {

    @Override
    public void serialize(final Set<Room> rooms, final JsonGenerator generator,
                          final SerializerProvider provider) throws IOException, JsonProcessingException {
        final Set<SimpleRoom> simpleRooms = new HashSet<>();
        for (final Room room : rooms) {
            simpleRooms.add(new SimpleRoom(room.getId(), room.getName()));
        }
        generator.writeObject(simpleRooms);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class SimpleRoom {

        private Long id;

        private String name;

    }
}
