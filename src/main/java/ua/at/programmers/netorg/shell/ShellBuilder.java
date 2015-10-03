package ua.at.programmers.netorg.shell;

import java.util.List;
import java.util.LinkedList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.io.OutputStream;
import java.io.PrintStream;

import groovy.lang.GroovyShell;
import groovy.lang.Binding;

import ua.at.programmers.netorg.interfaces.IntPlugin;

import org.codehaus.groovy.runtime.MethodClosure;

public class ShellBuilder {

    private OutputStream outLog;
    private List<IntPlugin> plugins;

    public ShellBuilder(OutputStream outLog) {
        this.outLog = outLog;
    }

    public void addPlugins(List<IntPlugin> plugins) {
        this.plugins = plugins;
    }

    private String getPluginsFunc(Binding binding) {
        StringBuilder sb = new StringBuilder();
        for (IntPlugin plugin : plugins) {
            String className;
            Method[] methods;
            Class cls = plugin.getClass();
            className = cls.getName();
            methods = cls.getDeclaredMethods( );
            for (Method method : methods) {
                String methodName;
                methodName = method.getName();
                if (methodName.startsWith("shell_")) {
                    System.out.println(className);
                    System.out.println(methodName);
                    MethodClosure mc = new MethodClosure (plugin, methodName);
                    String newMethodName = methodName.replace("shell_", "");
                    binding.setVariable(newMethodName, mc);
                    sb.append("");
                }
            }
        }
        return sb.toString();
    }

    public void getResult(String inScript) {
        Binding binding = new Binding();
        binding.setProperty("out", new PrintStream(outLog));
        GroovyShell groovyShell = new GroovyShell(binding);
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPluginsFunc(binding) + "\n");
        sb.append(inScript);
        groovyShell.evaluate(sb.toString());
    }
}
