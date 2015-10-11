package ua.at.programmers.netorg.plugin;

import ua.at.programmers.netorg.interfaces.IntPlugin;

import java.io.IOException;
import java.io.File;
import java.io.FileFilter;

import java.util.List;
import java.util.LinkedList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.lang.ClassNotFoundException;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;

public class PluginLoad {

    public static List<IntPlugin> load(List<IntPlugin> plugins) {
        if ( plugins == null )
            plugins = new LinkedList<IntPlugin>();

        File plugDir = new File("plugins");
        File[] jars = plugDir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return file.isFile() && file.getName().endsWith(".jar");
                }
            });
        for (int i = 0; i < jars.length; i++) {
            try {
                URL url = jars[i].toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
                JarFile jarFile = new JarFile(url.getFile());
                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();
                    if ( (name.endsWith(".class")) && !(name.endsWith("IntPlugin.class"))
                        && !(name.matches("(.*)\\$(.*)")) ) {
                        name = name.replace(".class", "");
                        name = name.replace("/", ".");
                        System.out.println(name);
                        Class plugin = classLoader.loadClass(name);
                        //run construct
                        plugins.add((IntPlugin) plugin.newInstance());
                    }
                }
            } catch(MalformedURLException me) {
                System.out.println("Malformed URL Exception");
            } catch(ClassNotFoundException cnfe) {
                System.out.println("Class not found");
            } catch(IOException ioe) {
                System.out.println("IOException");
            } catch(InstantiationException ie) {
                System.out.println("Instantiation Exception");
            } catch(IllegalAccessException iae) {
                System.out.println("Illegal Access Exception");
            }
        }
        return plugins;
    }
}
