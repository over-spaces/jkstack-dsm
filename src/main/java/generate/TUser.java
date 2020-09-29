package generate;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_user
 * @author 
 */
@Data
public class TUser implements Serializable {
    private Integer id;

    private Date createdDate;

    private Date modifyDate;

    private String name;

    private String password;

    private static final long serialVersionUID = 1L;
}