package ua.at.programmers.netorg.interfaces;

import java.awt.Panel;

public interface IntPlugin {

    public void setLog(IntLogWriter logWriter);

    public String getName();

    public Panel getPanel();
}
