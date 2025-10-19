package shadersmod.client;

import java.io.InputStream;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/IShaderPack.class */
public interface IShaderPack {
    void close();

    InputStream getResourceAsStream(String str);
}
