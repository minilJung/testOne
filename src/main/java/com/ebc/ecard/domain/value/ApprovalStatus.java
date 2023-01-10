package com.ebc.ecard.domain.value;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;

@Getter
public enum ApprovalStatus {
    APPROVED("A"),
    PENDING("P"),
    DENIED("D");

    private final String value;
    ApprovalStatus(String value) {
        this.value = value;
    }

    @MappedTypes(ApprovalStatus.class)
    public static class ApprovalStatusHandler extends BaseTypeHandler<ApprovalStatus> {

        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, ApprovalStatus parameter, JdbcType jdbcType)
                throws SQLException {
            ps.setString(i, parameter.getValue());
        }

        @Override
        public ApprovalStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
            String code = rs.getString(columnName);
            return getValue(code);
        }

        @Override
        public ApprovalStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            String code = rs.getString(columnIndex);
            return getValue(code);
        }

        @Override
        public ApprovalStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            String code = cs.getString(columnIndex);
            return getValue(code);
        }

        private ApprovalStatus getValue(String value) {
            try {
                for (ApprovalStatus enumValue: ApprovalStatus.values()) {
                    if (enumValue.getValue().equals(value)) {
                        return enumValue;
                    }
                }
                return null;
            } catch (Exception e) {
                throw new TypeException("Can't make enum  '" + value + "'", e);
            }
        }
    }

}
