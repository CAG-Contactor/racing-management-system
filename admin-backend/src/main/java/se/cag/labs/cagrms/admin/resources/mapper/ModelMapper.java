package se.cag.labs.cagrms.admin.resources.mapper;

import se.cag.labs.cagrms.admin.api.Race;
import se.cag.labs.cagrms.admin.resources.apimodel.UserResult;

import java.util.List;
import java.util.stream.Collectors;

public final class ModelMapper {

    private ModelMapper() {
      //Not used
    }

    public static List<Race> createUserResultResponse(List<UserResult> userResults) {
      return userResults.stream()
              .map(ModelMapper::mapUserResultToRace)
              .collect(Collectors.toList());
    }

  private static Race mapUserResultToRace(UserResult userResult) {
    return Race.builder()
            .id(userResult.getId())
            .userId(userResult.getUser().getUserId())
            .splitTime(userResult.getSplitTime())
            .time(userResult.getTime())
            .resultType(userResult.getResult())
            .build();
  }
}