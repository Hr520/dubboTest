
package com.xiaoze.provider.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description: dubbo-springboot
 * <p>
 * Created by az on 2019/10/26/026 17:02
 */

@Data
@Accessors(chain = true)
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
}

