package pl.jacek.coursebooking.utils.entity;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;

public class EntityUtils {

//    public static void dtoToEntity(Object object) {
//        Class clazz = object.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//
//        for(Field field : fields) {
//            field.setAccessible(true);
//            Object newValue = field.get();
//            if(newValue != null) {
//                field.set(this, newValue);
//            }
//        }
//        BeanUtils.c
//    }

//    public Student dtoToEntity() {
//        return new Student(
//                this.getFirstName(),
//                this.getLastName(),
//                this.getAge(),
//                this.getEmail(),
//                this.getCourses()
//        );
//    }
}
