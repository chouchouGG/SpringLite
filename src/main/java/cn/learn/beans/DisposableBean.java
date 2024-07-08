package cn.learn.beans;

/**
 * 由希望在销毁时释放资源的 Bean 实现的接口。当 BeanFactory 销毁一个单例时，应该调用该 destroy 方法。应用程序上下文应该在关闭时处理所有的单例。
 */
public interface DisposableBean {

    /**
     * 该方法由 BeanFactory 或应用上下文调用，以在销毁 bean 之前执行任何必要的清理工作，比如：释放其持有的资源（例如关闭数据库连接）。
     */
    void destroy() throws Exception;

}
