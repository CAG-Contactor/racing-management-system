package se.cag.labs.cagrms.admin.filter;

import lombok.Getter;
import se.cag.labs.cagrms.admin.AdminConfiguration;
import se.cag.labs.cagrms.admin.resources.apimodel.User;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static javax.ws.rs.HttpMethod.OPTIONS;
import static javax.ws.rs.HttpMethod.POST;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
  private static final String TOKEN_HDR = "x-cag-token";
  private static final String USER_HDR = "x-cag-user";
  private static final String PASSWORD_HDR = "x-cag-password";
  private static final Map<String, Token> tokens = new HashMap<>();
  private  final String user;
  private  final String password;

  public AuthenticationFilter(AdminConfiguration configuration) {
    user = configuration.getAdminUser();
    password = configuration.getAdminPassword();
  }

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    scavengeTokens();
    if (OPTIONS.equals(requestContext.getMethod())) {
      // Ugly late nite solution: allow OPTIONS so that CORS works...
      // Need to write a proper HTTP interceptor in the client.
      return;
    } else if (Objects.equals(requestContext.getUriInfo().getPath(), "admin/login") &&
        POST.equals(requestContext.getMethod()))  {
      // For now just check against single admin user/password configured via property
      String user = requestContext.getHeaderString(USER_HDR);
      String password = requestContext.getHeaderString(PASSWORD_HDR);
      if (Objects.equals(user, this.user) && Objects.equals(password, this.password)) {
        Token token = tokens.getOrDefault(user, new Token(UUID.randomUUID().toString()));
        tokens.put(user, token);
        token.bumped();
        requestContext.abortWith(
            Response.ok()
                .header(TOKEN_HDR, token.getValue())
                .entity(User.builder().displayName("Mr. Admin").build())
                .build()
        );
      } else {
        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
      }
    } else {
      // For all other request
      String token = requestContext.getHeaderString(TOKEN_HDR);
      boolean tokenIsValid = tokens.values().stream()
          .filter(t -> t.match(token))
          .findFirst()
          .map(Token::bumped)
          .isPresent();
      if (!tokenIsValid) {
        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
      }
    }
  }

  private void scavengeTokens() {
    tokens.values().removeIf(token -> token.timeout < System.currentTimeMillis());
  }

  @Getter
  private static class Token {
    private String value;
    private long timeout;

    private Token(String value) {
      this.value = value;
    }

    private Token bumped() {
      timeout = System.currentTimeMillis() + 1000*60*60; // One hour timeout
      return this;
    }

    private boolean match(String token) {
      return Objects.equals(value, token);
    }
  }
}