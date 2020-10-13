package com.jkstack.dsm;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author lifang
 * @since 2020/10/13
 */
@FeignClient(value = "dsm-user")
public interface DepartmentControllerFeign {
}
