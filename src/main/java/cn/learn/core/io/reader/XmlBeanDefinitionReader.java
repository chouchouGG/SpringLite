package cn.learn.core.io.reader;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.entity.BeanReference;
import cn.learn.beans.entity.PropertyValue;
import cn.learn.exception.BeansException;
import cn.learn.beans.registry.BeanDefinitionRegistry;
import cn.learn.core.io.resource.Resource;
import cn.learn.core.io.loader.ResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * 解析XML格式的Bean配置文件的核心逻辑
     *
     * @param inputStream 文件的输入流
     */
    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        // 1. 读取并解析 XML 文档
        Document doc = XmlUtil.readXML(inputStream);
        // 2. 获取 XML 文档的根元素
        Element root = doc.getDocumentElement();
        // 3. 获取根元素的所有子节点
        NodeList childNodes = root.getChildNodes();

        // 循环遍历处理每一个 <bean> 标签
        for (int i = 0; i < childNodes.getLength(); i++) {
            // 若当前节点，非元素节点 / 非 <bean> 标签，则跳过
            if (!(childNodes.item(i) instanceof Element) || !"bean".equals(childNodes.item(i).getNodeName())) {
                continue;
            }

            // 解析当前 <bean> 标签
            Element bean = (Element) childNodes.item(i);
            // 获取 <bean> 标签的属性配置
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");
            String beanScope = bean.getAttribute("scope");


            // 获取 Class 对象，方便获取类中的名称
            Class<?> beanClass = Class.forName(className);
            // 优先级：id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(beanClass.getSimpleName());
            }

            // 创建 BeanDefinition 对象
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClass(beanClass);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            // 循环遍历处理每个 <property> 属性标签
            NodeList beanChildNodes = bean.getChildNodes();
            for (int j = 0; j < beanChildNodes.getLength(); j++) {
                // 若当前节点，非元素节点 / 非 <property> 标签，则跳过
                if (!(beanChildNodes.item(j) instanceof Element) || !"property".equals(beanChildNodes.item(j).getNodeName())) {
                    continue;
                }

                // 解析标签：property
                Element property = (Element) beanChildNodes.item(j);
                String propertyName = property.getAttribute("name");
                String propertyValue = property.getAttribute("value");
                String propertyRef = property.getAttribute("ref");
                // 获取属性值的value：引入对象、值对象
                Object value = StrUtil.isNotEmpty(propertyRef) ? new BeanReference(propertyRef) : propertyValue;
                // 创建属性信息
                PropertyValue valueToSet = new PropertyValue(propertyName, value);
                beanDefinition.getPropertyValues().addPropertyValue(valueToSet);
            }

            if (super.getBeanRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("bean对象命名重复[" + beanName + "]，请检查配置");
            }

            // 注册 BeanDefinition
            super.getBeanRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

}