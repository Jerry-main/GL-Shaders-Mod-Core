package shadersmod.client;

import java.io.InputStream;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/ShaderPackNone.class */
public class ShaderPackNone implements IShaderPack {
    @Override // shadersmod.client.IShaderPack
    public void close() {
    }

    @Override // shadersmod.client.IShaderPack
    public InputStream getResourceAsStream(String resName) {
        return null;
    }
}
