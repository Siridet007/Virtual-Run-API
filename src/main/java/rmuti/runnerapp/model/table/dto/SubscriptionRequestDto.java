package rmuti.runnerapp.model.table.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionRequestDto {
    String topicName;
    List<String> tokens;
}
