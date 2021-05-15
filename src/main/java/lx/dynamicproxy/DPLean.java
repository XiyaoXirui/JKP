package lx.dynamicproxy;

import lx.Log;
import lx.dynamicproxy.bean.Fruit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

public class DPLean {

    private static class FruitInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (proxy instanceof Fruit) {
//                Fruit.Apple apple = new Fruit.Apple();
//                Log.d("invoke method: " + method.getName());
//                method.invoke(apple, args);


                // 获取静态非public内部类
                Class<?> clz = Class.forName("lx.dynamicproxy.bean.Fruit$Apple$FruitThread");
                Constructor<?> constructor = clz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Thread t = (Thread) constructor.newInstance();
                t.start();


                Thread.sleep(1000);
                Thread.getAllStackTraces().keySet().forEach(thread -> {
                    if (thread.getName().equals("lxxxx")) {
                        try {
                            thread.join();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                );
                Log.d("Main thread exit");
            }
            return null;
        }
    }

    public static void main(String[] args) {
//        new Fruit().pB();
        Fruit fruit = (Fruit) Proxy.newProxyInstance(
                DPLean.class.getClassLoader(),
                new Class[] {Fruit.class},
                new FruitInvocationHandler()
                );
        fruit.pB();


    }
}


