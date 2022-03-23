package com.aibaixun.iotdm.mybatis;

import com.aibaixun.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.*;
import org.springframework.util.CollectionUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 集合 json 映射类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
@MappedJdbcTypes(value = {
        JdbcType.VARBINARY,
        JdbcType.VARCHAR
})
@MappedTypes({List.class})
public abstract class  ListTypeHandler<T> extends BaseTypeHandler<List<T>> {

        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) throws SQLException {
            String content = CollectionUtils.isEmpty(parameter) ? null : JsonUtil.toJSONString(parameter);
            ps.setString(i, content);
        }

        @Override
        public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
            return this.getListByJsonArrayString(rs.getString(columnName));
        }

        @Override
        public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            return this.getListByJsonArrayString(rs.getString(columnIndex));
        }

        @Override
        public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            return this.getListByJsonArrayString(cs.getString(columnIndex));
        }


        private List<T> getListByJsonArrayString(String content) {
            return StringUtils.isBlank(content) ? new ArrayList<>() : JsonUtil.toList(content, this.specificType());
        }

        /**
        * 具体类型，由子类提供
        *
        * @return 具体类型
        */
        protected abstract Class<T> specificType();


}
