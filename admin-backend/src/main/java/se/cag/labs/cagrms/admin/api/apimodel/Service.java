package se.cag.labs.cagrms.admin.api.apimodel;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Service {

    @Setter(AccessLevel.PRIVATE) private String name;
    @Setter(AccessLevel.PRIVATE) private String alive;
    @Setter(AccessLevel.PRIVATE) private String dbUp;
    @Setter(AccessLevel.PRIVATE) private String [] info;

    public Service(String name, String alive, String dbUp, String[] info) {
        this.name = name;
        this.alive = alive;
        this.dbUp = dbUp;
        this.info = info;
    }
}
