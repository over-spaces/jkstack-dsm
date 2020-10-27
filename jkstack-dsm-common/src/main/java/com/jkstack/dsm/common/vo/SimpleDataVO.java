package com.jkstack.dsm.common.vo;

import com.jkstack.dsm.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lifang
 * @since 2020/10/15
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel
public class SimpleDataVO implements Serializable {

    @ApiModelProperty(value = "业务ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    public SimpleDataVO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleDataVO that = (SimpleDataVO) o;
        if(id == null || that.id == null) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
