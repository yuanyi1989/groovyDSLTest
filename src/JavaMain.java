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

/**
 * Created by 袁意 on 2017/1/6.
 */
public class JavaMain {

    public static void main(String[] args) throws ScriptException, IOException, ResourceException, groovy.util.ScriptException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        useGroovyEngine();
    }

    public static void useGroovyShell(){
        DataProvider provider = new DataProvider();

        long start = System.currentTimeMillis();
        GroovyShell shell = new GroovyShell();
        for(int i=0; i<100; i++) {
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
    public static void useGroovyEngine() throws IOException, ResourceException, groovy.util.ScriptException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        DataProvider provider = new DataProvider();
        String[] roots = new  String[]{"src/Rule5.groovy"} ;//定义Groovy脚本引擎的根路径
        GroovyScriptEngine engine = new GroovyScriptEngine(roots);
        Class main = engine.loadScriptByName("Rule5.groovy");

        Constructor<Binding> constructor = main.getConstructor(Binding.class);
        Method mainMethod = main.getMethod("run");
        long start = System.currentTimeMillis();
        for(int i=0; i<1; i++) {
            Binding binding = new Binding();
            AlarmData data = provider.next();
            binding.setVariable("data", data);
            Object instance = constructor.newInstance(binding);
            mainMethod.invoke(instance);
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
