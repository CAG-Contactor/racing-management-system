package se.cag.labs.cagrms.clientapi.service;

import lombok.*;

@Data
@NoArgsConstructor
public class User {
    private String userId;
    private String displayName;
    private String password;
}
