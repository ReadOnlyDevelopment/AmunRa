package _net.rom.stellar;

import _net.rom.stellar.util.GameUtil;
import _net.rom.stellar.util.LogHelper;

public interface IMod {
    String getModId();

    String getModName();

    String getVersion();

    int getBuildNum();

    default boolean isDevBuild() {
        return 0 == getBuildNum() || GameUtil.isDeobfuscated();
    }

    default LogHelper getLog() {
        return LogHelper.getRegisteredLogger(getModName()).orElse(new LogHelper(getModName(), getBuildNum()));
    }
}
