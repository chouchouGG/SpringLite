package cn.learn.beans.xml;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.entity.BeanReference;
import cn.learn.beans.entity.PropertyValue;
import cn.learn.beans.exception.BeansException;
import cn.learn.beans.factory.support.registry.BeanDefinitionRegistry;
import cn.learn.beans.factory.support.reader.AbstractBeanDefinitionReader;
import cn.learn.core.io.Resource;
import cn.learn.core.io.ResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
        try (InputStream inputStream = resource.getInputStream()){
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
        // 打印加载的Bean
        displayBeanDefNames();
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
            // 判断当前节点是否是元素节点
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }
            // 判断当前元素节点是否是 <bean> 标签
            if (!"bean".equals(childNodes.item(i).getNodeName())) {
                continue;
            }

            // 解析 <bean> 标签
            Element bean = (Element) childNodes.item(i);
            // 获取 <bean> 标签的 id、name 和 class 属性
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            // 获取 Class 对象，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级：id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 创建 BeanDefinition 对象
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            // 循环遍历处理每个 <property> 属性标签
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) {
                    continue;
                }
                Element propertyNode = (Element) bean.getChildNodes().item(j);
                if (!"property".equals(propertyNode.getNodeName())) {
                    continue;
                }
                // 解析标签：property
                String attrName = propertyNode.getAttribute("name");
                String attrValue = propertyNode.getAttribute("value");
                String attrRef = propertyNode.getAttribute("ref");
                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (super.getBeanRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册 BeanDefinition
            super.getBeanRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    /**
     * 日志处理：负责打印加载的Bean
     */
    private void displayBeanDefNames() {
        String[] names = super.getBeanRegistry().getBeanDefinitionNames();
        log.info("注册了 {} 个Bean：{}", names.length, ArrayUtil.toString(names));
    }

}