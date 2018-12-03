package com.example.dubboserver.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDemo {

    @XmlElement(name="name")
    private String name;

    @XmlElement(name="age")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "XmlDemo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void toXml() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(XmlDemo.class);
        Marshaller ms = jc.createMarshaller();
        XmlDemo demo=new XmlDemo();
        demo.setAge(13);
        demo.setName("jack");
        StringWriter writer = new StringWriter();
        ms.marshal(demo, writer);
        String result = writer.toString();
        System.out.println("对象转XML字符串： "+ result +"\n");
        System.out.println("---------------------------------------- \n");
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        System.out.println("翻转XML为对象："+demo.getName());

    }
}
