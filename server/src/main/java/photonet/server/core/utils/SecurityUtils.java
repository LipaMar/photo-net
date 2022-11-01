package photonet.server.core.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  public static String loggedUserName() {
    return getAuthentication().getName();
  }

  public static boolean isLoggedUser(String userName) {
    return loggedUserName().equals(userName);
  }

  public static boolean isNotLoggedUser(String userName) {
    return !isLoggedUser(userName);
  }

  public static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

}
