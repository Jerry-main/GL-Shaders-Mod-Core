package shadersmod.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/ShaderPackFolder.class */
public class ShaderPackFolder implements IShaderPack {
    protected File packFile;

    public ShaderPackFolder(String name, File file) {
        this.packFile = file;
    }

    @Override // shadersmod.client.IShaderPack
    public void close() {
    }

    @Override // shadersmod.client.IShaderPack
    public InputStream getResourceAsStream(String resName) {
        try {
            File resFile = new File(this.packFile, resName.substring(1));
            if (resFile != null) {
                return new BufferedInputStream(new FileInputStream(resFile));
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
