package com.init.mybatis_8.dao;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.Properties;

/**
 * 完成插件签名：
 * 告诉Mybatis当前插件用来拦截哪个对象哪个方法
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "parameterize", args = java.sql.Statement.class)
})
public class MyFirstPlugin implements Interceptor {

    /**
     * intercept:拦截
     * 拦截目标对象的目标方法的执行
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MyFirstPlugin...intercept:" + invocation.getMethod());
        Object target = invocation.getTarget();
        System.out.println("当前拦截到的对象:" + target);
        //拿到：statementHandler==》ParameterHandler==》parameterObject
        //拿到target元数据
        MetaObject metaObject = SystemMetaObject.forObject(target);
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句用的参数是：" + value);
        //修改完sql语句要用的参数
        metaObject.setValue("parameterHandler.parameterObject", 2);
        //执行目标方法
        Object proceed = invocation.proceed();
        //返回执行后的返回值
        return proceed;
    }

    /**
     * plugin
     * 包装目标对象：包装：为目标对象创建一个代理对象
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        System.out.println("MyFirstPlugin...plugin:mybatis将要包装的对象" + target);
        //我们可以使用Plugin的wrap方法来使用当前的Interceptor来包装我们目标对象
        Object wrap = Plugin.wrap(target, this);
        //为当前target创建动态代理
        return wrap;
    }

    /**
     * 将插件的property属性设置进来
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置信息：" + properties);
    }
}
