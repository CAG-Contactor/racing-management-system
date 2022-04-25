package se.cag.labs.cagrms.raceadmin;

@Repository
public interface ActiveRaceRepository extends MongoRepository<RaceStatus, String> {

  RaceStatus findByUserUserId();
}
