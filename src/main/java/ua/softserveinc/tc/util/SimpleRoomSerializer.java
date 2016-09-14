package ua.softserveinc.tc.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.softserveinc.tc.entity.Room;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleRoomSerializer  extends JsonSerializer<Set<Room>> {

    @Override
    public void serialize(final Set<Room> rooms, final JsonGenerator generator,
                          final SerializerProvider provider) throws IOException, JsonProcessingException {
        final Set<SimpleRoom> simpleCollaborations = new HashSet<>();
        for (final Room room : rooms) {
            simpleCollaborations.add(new SimpleRoom(room.getId(), room.getName()));
        }
        generator.writeObject(simpleCollaborations);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class SimpleRoom {

        private Long id;

        private String name;

    }
}
