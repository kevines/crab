package com.wentuo.crab.modular.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Data
public class MenuResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long menuId;
    /**
     * 菜单编号
     */
    private String code;
    /**
     * 菜单父编号
     */
    private String pcode;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * url地址
     */
    private String url;
    /**
     * 菜单层级
     */
    private Integer levels;



    private List<MenuResult> children;


    /**
     * 是否被选中
     */
    private Boolean checked = false;


}
