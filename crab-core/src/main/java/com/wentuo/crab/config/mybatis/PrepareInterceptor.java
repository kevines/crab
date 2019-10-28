
package com.wentuo.crab.config.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wentuo.crab.enums.ActionEnum;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

/**
 * mybatis数据权限拦截器 - prepare
 *
 * @author GaoYuan
 * @date 2018/4/17 上午9:52
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
public class PrepareInterceptor implements Interceptor {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(PrepareInterceptor.class);

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (log.isInfoEnabled()) {
//            log.info("进入 PrepareInterceptor 拦截器...");
        }
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            String sql = this.getSqlByInvocation(invocation);
        }

        getOperateType(invocation);
        return invocation.proceed();
    }


    private String getSqlByInvocation(Invocation invocation) {
        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        return boundSql.getSql();
    }

    private String getOperateType(Invocation invocation) {
        try {
            final Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            SqlCommandType commondType = ms.getSqlCommandType();
            if (commondType.compareTo(SqlCommandType.SELECT) == 0) {
                return "select";
            }

            if (commondType.compareTo(SqlCommandType.INSERT) == 0) {
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(args[1]);
                //获取SQL
                Class clazz = args[1].getClass();
                // 根据配置自动生成分表SQL
                TableName tableSplit = (TableName) clazz.getAnnotation(TableName.class);
                String tableName = tableSplit.value().replaceAll("`", "");
                if (tableName.equals("app_user_login_log")) {
                    return "";
                }
                log.info("{}!{}!{}!{}!{}", "log", ActionEnum.INSERT.getCode(), tableName, jsonObject, jsonObject.get("id"));
                return "insert";
            }
            if (commondType.compareTo(SqlCommandType.UPDATE) == 0) {
                JSONObject jsonObject = null;

                jsonObject = (JSONObject) JSONObject.toJSON(args[1]);
                jsonObject = (JSONObject) jsonObject.get("et");
                //获取SQL
                Map<String, Object> map = (Map<String, Object>) args[1];
                Object object = map.get("et");
                Class clazz = object.getClass();
                // 根据配置自动生成分表SQL
                TableName tableSplit = (TableName) clazz.getAnnotation(TableName.class);
                log.info(tableSplit.value().replaceAll("`", ""));
                log.info("{}!{}!{}!{}!{}", "log", ActionEnum.UPDATE.getCode(), tableSplit.value().replaceAll("`", ""), jsonObject, jsonObject.get("id"));

                return "update";
            }
            if (commondType.compareTo(SqlCommandType.DELETE) == 0) {
                return "delete";
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
