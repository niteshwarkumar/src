//SecurityObject.java is an object that is placed in a user's session
//it must be present in the session for the jsp or servlet to be viewable

package app.security;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


public class SecurityObject implements Serializable {

    //a dummy field
    private String blank = "";

}
