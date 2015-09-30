package ua.at.programmers.netorg.shell;

import java.io.OutputStream;
import java.io.PrintStream;

import groovy.lang.GroovyShell;
import groovy.lang.Binding;

public class ShellBuilder {

    private OutputStream outLog;

    public ShellBuilder(OutputStream outLog) {
        this.outLog = outLog;
    }

    public void getResult(String inScript) {
        Binding binding = new Binding();
        binding.setProperty("out", new PrintStream(outLog));
        GroovyShell groovyShell = new GroovyShell(binding);
        groovyShell.evaluate(inScript);
    }
}
