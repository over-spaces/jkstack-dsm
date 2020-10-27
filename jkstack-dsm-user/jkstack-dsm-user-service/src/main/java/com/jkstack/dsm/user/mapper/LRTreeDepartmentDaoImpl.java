package com.jkstack.dsm.user.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jkstack.dsm.common.lr.LRNode;
import com.jkstack.dsm.user.entity.DepartmentEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author lifang@winployee.com
 * @since 2019/12/27
 */
@Repository
public class LRTreeDepartmentDaoImpl implements LRTreeDao {

    private static Logger logger = LoggerFactory.getLogger(LRTreeDepartmentDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String TABLE_NAME;
    private final static String COLUMN_BUSINESS_ID = "department_id";
    private final static String PARENT_COLUMN_NAME = "parent_department_id";

    static {
        Class<DepartmentEntity> clazz = DepartmentEntity.class;

        TableName annotation = clazz.getAnnotation(TableName.class);

        TABLE_NAME = annotation.value();
    }

    @Override
    @Transactional(readOnly = true)
    public LRNode getVirtualRootNode() {
        LRNode lrNode = new DepartmentEntity();
        lrNode.setDeep(0);
        lrNode.setLft(1);
        String sql = String.format("select max(rgt) from %s", TABLE_NAME);
        Integer right = jdbcTemplate.queryForObject(sql, Integer.class);
        right = Math.max(Optional.ofNullable(right).orElse(0), 1);
        lrNode.setRgt(right + 1);
        return lrNode;
    }

    @Override
    public void updateNodeLR(String businessId, Integer left, Integer right, Integer deep) {
        String sql = String.format("UPDATE %s SET lft=:lft, rgt=:rgt,deep=:deep where %s=:businessId", TABLE_NAME, COLUMN_BUSINESS_ID);
        Map<String, Object> params = new HashMap<>(4);
        params.put("businessId", businessId);
        params.put("lft", left);
        params.put("rgt", right);
        params.put("deep", deep);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void batchUpdateNodesLR(int baseValue, int step) {
        long start = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>(2);
        params.put("baseValue", baseValue);
        params.put("step", step);
        //将树形中所有lft和rgt节点大于父节点左值的节点都+2
        String sql;
        sql = String.format(" UPDATE %s SET rgt = rgt + :step WHERE rgt > :baseValue", TABLE_NAME);
        namedParameterJdbcTemplate.update(sql, params);
        sql = String.format(" UPDATE %s SET lft = lft + :step WHERE lft > :baseValue", TABLE_NAME);
        namedParameterJdbcTemplate.update(sql, params);

        long end = System.currentTimeMillis();
        logger.debug("耗时:" + (end - start) / 1000);
    }

    @Override
    public void batchUpdateChildrenLRToNegative(int left, int right) {
        Map<String, Object> params = new HashMap<>();
        params.put("myLeft", left);
        params.put("myRight", right);
        String sqlMy = String.format("UPDATE %s SET lft = -lft , rgt = -rgt WHERE  lft > :myLeft and rgt < :myRight", TABLE_NAME);
        namedParameterJdbcTemplate.update(sqlMy, params);
    }

    @Override
    public void batchUpdateChildrenLRByDiff(int left, int right, int diff, int diffDeep) {
        //所有的子节点对应增减 diffLeft 原备份为负值，先取正
        Map<String, Object> params = new HashMap<>();
        params.put("myLeft", -left);
        params.put("myRight", -right);
        params.put("diffLeft", diff);
        params.put("diffRight", diff);
        params.put("diffDeep", diffDeep);

        /*注意：由于lft、rht 为负值  大小关系倒置*/
        String sql = String.format("UPDATE %s SET lft = ABS(lft) + :diffLeft, rgt = ABS(rgt) + :diffRight,deep = deep + :diffDeep WHERE lft > :myRight and rgt < :myLeft", TABLE_NAME);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public long count() {
        String sql = String.format("select count(id) from %s", TABLE_NAME);
        Long l = jdbcTemplate.queryForObject(sql, Long.class);
        return l == null ? 0 : l;
    }

    @Override
    public List<LRNode> fullLoad() {
        final List<LRNode> nodes = new ArrayList<>(100);
        final Map<String, LRNode> nodeMap = new HashMap<>(100);

        String sql = String.format("select %s, %s from %s order by %s", COLUMN_BUSINESS_ID, PARENT_COLUMN_NAME, TABLE_NAME, PARENT_COLUMN_NAME);
        jdbcTemplate.query(sql, rs -> {
            String businessId = rs.getString(1);
            String parentBusinessId = rs.getString(2);

            LRNode lrNode = new DepartmentEntity();
            lrNode.setBusinessId(businessId);
            lrNode.setParentNodeBusinessId(parentBusinessId);
            lrNode.setLft(null);
            lrNode.setRgt(null);
            lrNode.setDeep(null);
            nodes.add(lrNode);
            nodeMap.put(businessId, lrNode);
        });

        nodes.stream()
                .filter(node -> StringUtils.isNotBlank(node.getParentNodeBusinessId()))
                .forEach(node -> {
                    node.setParentNode(nodeMap.get(node.getParentNodeBusinessId()));
                });
        return nodes;
    }

    @Override
    public void batchUpdate(List<LRNode> allNodes) {
        Map<String, Object>[] params = new HashMap[allNodes.size()];
        int index = 0;
        for (LRNode node : allNodes) {
            params[index] = new HashMap<>(4);
            params[index].put("businessId", node.getBusinessId());
            params[index].put("lft", node.getLft());
            params[index].put("rgt", node.getRgt());
            params[index].put("deep", node.getDeep());
            index++;
        }
        String sql = String.format("UPDATE %s SET lft=:lft , rgt=:rgt, deep=:deep WHERE %s=:businessId", TABLE_NAME, COLUMN_BUSINESS_ID);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }
}
