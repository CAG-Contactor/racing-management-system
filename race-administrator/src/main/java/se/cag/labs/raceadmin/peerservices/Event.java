package se.cag.labs.raceadmin.peerservices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Event<T> {
  private String eventType;
  private T data;
  private final String uuid = UUID.randomUUID().toString();
}
