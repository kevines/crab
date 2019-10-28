package com.wentuo.crab.modular.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.modular.system.entity.Relation;
import com.wentuo.crab.modular.system.mapper.RelationMapper;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class RelationService extends ServiceImpl<RelationMapper, Relation> {

    public void deleteByRole(Long roleId) {
        Relation relation = new Relation();
        relation.setRoleId(roleId);
        Wrapper<Relation> wrapper = new QueryWrapper<>(relation);
        this.remove(wrapper);
    }

    public List<Relation> queryRelationByRole(Long roleId) {
        Relation relation = new Relation();
        relation.setRoleId(roleId);
        Wrapper<Relation> wrapper = new QueryWrapper<>(relation);
        return  this.baseMapper.selectList(wrapper);
    }
}
