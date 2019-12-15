package nl.medicaldataworks.railway.central.util;

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.Authentication;

public class KeycloakUtil {

    public String getPreferredUsernameFromAuthentication(Authentication authentication){
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        return account.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }
}
