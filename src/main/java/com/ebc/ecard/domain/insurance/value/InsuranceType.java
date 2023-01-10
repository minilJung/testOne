package com.ebc.ecard.domain.insurance.value;

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
public enum InsuranceType {
    LIFE("L"), // 생명보험
    GENERAL("G"); // 손해보험

    private final String value;
    InsuranceType(String value) {
        this.value = value;
    }

    @MappedTypes(InsuranceType.class)
    public static class InsuranceTypeHandler extends BaseTypeHandler<InsuranceType> {

        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, InsuranceType parameter, JdbcType jdbcType)
                throws SQLException {
            ps.setString(i, parameter.getValue());
        }

        @Override
        public InsuranceType getNullableResult(ResultSet rs, String columnName) throws SQLException {
            String code = rs.getString(columnName);
            return getValue(code);
        }

        @Override
        public InsuranceType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            String code = rs.getString(columnIndex);
            return getValue(code);
        }

        @Override
        public InsuranceType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            String code = cs.getString(columnIndex);
            return getValue(code);
        }

        private InsuranceType getValue(String value) {
            try {
                for (InsuranceType enumValue: InsuranceType.values()) {
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
