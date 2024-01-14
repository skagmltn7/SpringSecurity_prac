package prac.security2.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class SecurityUtil {

    static public String getAccessToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
