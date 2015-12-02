package se.cag.labs.usermanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dawi on 2015-11-20.
 */
@Data
@NoArgsConstructor
public class Token {
	@ApiModelProperty(value = "Session ID", required = true)
	private String token;

    public Token(String token) {
        this.token = token;
    }
}
