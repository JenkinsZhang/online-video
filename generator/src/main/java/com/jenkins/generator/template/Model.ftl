package com.jenkins.server.model;

<#list typeSet as type>
    <#if type=='Date'>
        import java.util.Date;
        import com.fasterxml.jackson.annotation.JsonFormat;
    </#if>
    <#if type=='BigDecimal'>
        import java.math.BigDecimal;
    </#if>
</#list>

public class ${Entity}Model {

<#list fieldList as field>
    /**
    * ${field.comment}
    */
    <#if field.javaType=='Date'>
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    </#if>
    private ${field.javaType} ${field.lowerCamelName};

</#list>
<#list fieldList as field>
    public ${field.javaType} get${field.upperCamelName}() {
        return ${field.lowerCamelName};
    }

    public void set${field.upperCamelName}(${field.javaType} ${field.lowerCamelName}) {
        this.${field.lowerCamelName} = ${field.lowerCamelName};
    }

</#list>

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        <#list fieldList as field>
            sb.append(", ${field.lowerCamelName}=").append(${field.lowerCamelName});
        </#list>
        sb.append("]");
        return sb.toString();
    }

}