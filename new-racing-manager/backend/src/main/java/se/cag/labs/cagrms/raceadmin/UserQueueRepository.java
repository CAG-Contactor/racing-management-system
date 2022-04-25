package se.cag.labs.cagrms.raceadmin;

@Repository
public interface UserQueueRepository extends MongoRepository<User, String> {
  User findUserByUserId(final String userId);
}
