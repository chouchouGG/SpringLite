package cn.learn.core.io.reader;

import cn.hutool.core.util.StrUtil;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.entity.BeanReference;
import cn.learn.beans.registry.BeanDefinitionRegistry;
import cn.learn.context.impl.AbstractXmlApplicationContext;
import cn.learn.core.io.loader.ResourceLoader;
import cn.learn.core.io.resource.Resource;
import cn.learn.exception.BeansException;
import cn.learn.scan.BeanDefinitionScanner;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    // 自动扫包组件（只有配置了<component-scan>标签，该组件才生效）
    private BeanDefinitionScanner autoScanner = null;

    /**
     * 在 ApplicationContext 中有使用，{@link AbstractXmlApplicationContext#reader}，注册器就是具有注册功能的 DefaultListableBeanFactory
     * @param registry bean工厂（具有注册功能）
     * @param resourceLoader 资源加载器
     */
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            Resource resource = resourceLoader.getResource(location);
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            try {
                InputStream inputStream = resource.getInputStream();
                doLoadBeanDefinitions(inputStream);
            } catch (IOException | ClassNotFoundException | DocumentException e) {
                throw new BeansException("解析XML文件异常：" + resource, e);
            }
        }
    }


    /**
     * 解析XML格式的Bean配置文件的核心逻辑
     *
     * @param inputStream 文件的输入流
     */
    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        // 1. 读取并解析 XML 文档
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // 【处理 <component-scan> 标签】：自动扫描指定包路径中的组件类
        resolveTagForComponentScan(root);

        // 【处理 <bean> 标签】
        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {
            resolveTagForBean(bean);
        }
    }

    private void resolveTagForBean(Element bean) throws ClassNotFoundException {
        // 获取 <bean> 标签的属性配置
        String id = bean.attributeValue("id");
        String name = bean.attributeValue("name");
        String className = bean.attributeValue("class");
        String initMethod = bean.attributeValue("init-method");
        String destroyMethodName = bean.attributeValue("destroy-method");
        String beanScope = bean.attributeValue("scope");


        // 获取 Class 对象，方便获取类中的名称
        Class<?> beanClass = Class.forName(className);
        // 优先级：id > name
        String beanName = StrUtil.isNotEmpty(id) ? id : name;
        if (StrUtil.isEmpty(beanName)) {
            beanName = StrUtil.lowerFirst(beanClass.getSimpleName());
        }

        // 创建 BeanDefinition 对象
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        beanDefinition.setInitMethodName(initMethod);
        beanDefinition.setDestroyMethodName(destroyMethodName);

        if (StrUtil.isNotEmpty(beanScope)) {
            beanDefinition.setScope(beanScope);
        }

        // 循环遍历处理每个 <property> 属性标签
        List<Element> propertyList = bean.elements("property");
        for (Element property : propertyList) {
            // 解析标签：property
            String propertyName = property.attributeValue("name");
            String propertyValue = property.attributeValue("value");
            String propertyRef = property.attributeValue("ref");
            // 获取属性值的value：引入对象、值对象
            Object value = StrUtil.isNotEmpty(propertyRef) ? new BeanReference(propertyRef) : propertyValue;
            // 创建属性信息
            beanDefinition.getPropertyValues().addPropertyValue(propertyName, value);
        }

        if (super.getRegistry().containsBeanDefinition(beanName)) {
            throw new BeansException("bean对象命名重复[" + beanName + "]，请检查配置");
        }

        // 注册 BeanDefinition
        super.getRegistry().registerBeanDefinition(beanName, beanDefinition);
    }

    private void resolveTagForComponentScan(Element root) {
        List<Element> componentScanList = root.elements("component-scan");
        if (componentScanList != null && !componentScanList.isEmpty()) {
            // 确保只配置一个 <component-scan> 标签
            if (componentScanList.size() > 1) {
                throw new BeansException("<component-scan/> 标签只能配置一个，当前配置了多个，请检查配置。");
            }

            Element componentScan = componentScanList.get(0);
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("\"<component-scan/> 标签中的 'base-package' 属性不能为空字符串或null");
            }
            // note: 【核心方法】- 扫描指定包路径中的组件类
            scanPackage(scanPath);
        }
    }

    private void scanPackage(String scanPath) {
        autoScanner = new BeanDefinitionScanner(getRegistry());
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        autoScanner.doScanOnlyForSetBeanClass(basePackages);
    }

}