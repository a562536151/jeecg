/*
package org.jeecg.modules.demo.cc.Utils;

import org.jeecg.modules.demo.cc.entity.Aa;
import org.jeecg.modules.demo.cc.entity.CategoryEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class copyCollection {

    public static List<Aa> copyCollection(List<Aa> origin) {
        List<Aa> target = new ArrayList<>();

        for (Aa o : origin) {
            CategoryEntity t = new CategoryEntity();
            Field[] originFields = o.getClass().getDeclaredFields();
            Field[] targetFields = t.getClass().getDeclaredFields();

            for (Field originField : originFields) {
                for (Field targetField : targetFields) {
                    if (originField.getName().equals(targetField.getName())) {
                        originField.setAccessible(true);
                        targetField.setAccessible(true);

                        try {
                            Object value = originField.get(o);
                            targetField.set(t, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                }
            }

            target.add(t);
        }

        return target;
    }
}
}
*/
