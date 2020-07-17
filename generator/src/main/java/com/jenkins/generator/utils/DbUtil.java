package com.jenkins.generator.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/15
 */
public class DbUtil {

    public static Connection getConnection()
    {
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/onlineVideo";
            String user = "onlineVideo";
            String password = "Qianqi971126";
            connection = DriverManager.getConnection(url, user, password);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;

    }

    public static List<Field> getColumnsByTableName(String tableName) throws SQLException {
        List<Field> fieldList = new ArrayList<>();
        Connection connection = getConnection();
        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("show full columns from `" + tableName + "`");
        if(resultSet !=null)
        {
            while(resultSet.next())
            {
                String type = resultSet.getString("Type");
                String comment = resultSet.getString("Comment");
                String columnName = resultSet.getString("Field");
                String nullable = resultSet.getString("Null");
                Field field = new Field();
                field.setLowerCamelName(toLowerCamel(columnName));
                field.setUpperCamelName(toUpperCamel(columnName));
                if(("YES").equals(nullable))
                {
                    field.setNullable(true);
                }else {
                    field.setNullable(false);
                }
                field.setComment(comment);
                field.setJavaType(sqlTypeToJavaType(type));
                field.setLength(sqlTypeLength(type));
                fieldList.add(field);
            }
        }
        return fieldList;

    }


    public static String toLowerCamel(String columnName)
    {
        String[] s = columnName.split("_");
        int length = s.length;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++)
        {
            if(i==0)
            {
                sb.append(s[i]);
            }
            else {
                sb.append(s[i].substring(0, 1).toUpperCase()).append(s[i].substring(1));
            }
        }
        return sb.toString();
    }

    public static String toUpperCamel(String columnName)
    {
        String lowerCamel = toLowerCamel(columnName);
        return lowerCamel.substring(0,1).toUpperCase()+lowerCamel.substring(1);
    }

    public static String sqlTypeToJavaType(String sqlType) {
        if (sqlType.toUpperCase().contains("varchar".toUpperCase())
                || sqlType.toUpperCase().contains("char".toUpperCase())
                || sqlType.toUpperCase().contains("text".toUpperCase())) {
            return "String";
        } else if (sqlType.toUpperCase().contains("datetime".toUpperCase())) {
            return "Date";
        } else if (sqlType.toUpperCase().contains("int".toUpperCase())) {
            return "Integer";
        } else if (sqlType.toUpperCase().contains("long".toUpperCase())) {
            return "Long";
        } else if (sqlType.toUpperCase().contains("decimal".toUpperCase())) {
            return "BigDecimal";
        } else {
            return "String";
        }
    }

    public static int sqlTypeLength(String sqlType)
    {
        int length = 0;
        if(sqlType.toLowerCase().contains("varchar"))
        {
            length = Integer.parseInt(sqlType.substring(sqlType.indexOf('(') + 1, sqlType.indexOf(')')));
        }
        return length;
    }
    public static void main(String[] args) throws SQLException {
        System.out.println(getColumnsByTableName("section"));
    }
}
