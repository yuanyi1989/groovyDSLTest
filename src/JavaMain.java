import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 袁意 on 2017/1/6.
 */
public class JavaMain {

    public static void main(String[] args) throws ScriptException, IOException, ResourceException, groovy.util.ScriptException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        redeploy();
        useGroovyEngine(10);
        System.out.println("push any key to redeploy");
        new Scanner(System.in).next();
        redeploy();
        useGroovyEngine(10);
    }

    public static void useGroovyShell(){
        DataProvider provider = new DataProvider();

        long start = System.currentTimeMillis();
        GroovyShell shell = new GroovyShell();
        for(int i=0; i<1000000; i++) {
            shell.setVariable("data", provider.next());
            try (FileReader reader = new FileReader(new File("src/Main.groovy"))) {
                shell.evaluate(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (FileReader reader = new FileReader(new File("src/Rule2"))) {
                shell.evaluate(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis()-start);
    }

    private static final ExecutorService service = Executors.newFixedThreadPool(10);
    private static Constructor<Binding> constructor;
    private static Method method;

    public static void redeploy() throws IOException, ResourceException, groovy.util.ScriptException, NoSuchMethodException {
        String[] roots = new  String[]{"src/Rule5.groovy"} ;//定义Groovy脚本引擎的根路径
        GroovyScriptEngine engine = new GroovyScriptEngine(roots);
        Class main = engine.loadScriptByName("Rule5.groovy");

        constructor = main.getConstructor(Binding.class);
        method = main.getMethod("run");
    }

    public static void useGroovyEngine(int amount) {
        for (int i=0; i<5; i++) {
            service.execute(new Task(amount, constructor, method));
        }
    }

    private static class Task implements  Runnable{

        private int count;
        private Constructor constructor;
        private Method method;
        public Task(int count, Constructor constructor, Method method){
            this.count = count;
            this.constructor = constructor;
            this.method = method;
        }
        @Override
        public void run() {
            DataProvider provider = new DataProvider();
            for(int i=0; i<count; i++) {
                Binding binding = new Binding();
                AlarmData data = provider.next();
                binding.setVariable("data", data);
                try {
                    Object instance = constructor.newInstance(binding);
                    method.invoke(instance);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
