package dubbo.sample.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 4088470353220902055L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;
}
