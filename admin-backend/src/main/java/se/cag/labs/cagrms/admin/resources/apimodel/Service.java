package se.cag.labs.cagrms.admin.resources.apimodel;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Service {

    @Setter(AccessLevel.PRIVATE) private String name;
    @Setter(AccessLevel.PRIVATE) private boolean alive;
    @Setter(AccessLevel.PRIVATE) private boolean dbUp;
    @Setter(AccessLevel.PRIVATE) private String [] info;

    public Service(String name, boolean alive, boolean dbUp, String[] info) {
        this.name = name;
        this.alive = alive;
        this.dbUp = dbUp;
        this.info = info;
    }
}
