package shadersmod.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/launch/SMCTweaker.class */
public class SMCTweaker implements ITweaker {
    public List<String> args;
    public File gameDir;
    public File assetsDir;
    public String version;

    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String version) {
        this.args = args;
        this.gameDir = gameDir;
        this.assetsDir = assetsDir;
        this.version = version;
    }

    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        launchClassLoader.addTransformerExclusion("shadersmod.common");
        launchClassLoader.addTransformerExclusion("shadersmod.transform");
        launchClassLoader.registerTransformer("shadersmod.transform.SMCClassTransformer");
    }

    public String[] getLaunchArguments() {
        ArrayList argumentList = (ArrayList) Launch.blackboard.get("ArgumentList");
        if (argumentList.isEmpty()) {
            new ArrayList();
            if (this.gameDir != null) {
                argumentList.add("--gameDir");
                argumentList.add(this.gameDir.getPath());
            }
            if (this.assetsDir != null) {
                argumentList.add("--assetsDir");
                argumentList.add(this.assetsDir.getPath());
            }
            argumentList.add("--version");
            argumentList.add(this.version);
            argumentList.addAll(this.args);
        }
        return new String[0];
    }

    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }
}

