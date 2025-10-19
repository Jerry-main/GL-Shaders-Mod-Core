package shadersmod.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/ShaderPackZip.class */
public class ShaderPackZip implements IShaderPack {
    protected File packFile;
    protected ZipFile packZipFile = null;

    public ShaderPackZip(String name, File file) {
        this.packFile = file;
    }

    @Override // shadersmod.client.IShaderPack
    public void close() throws IOException {
        if (this.packZipFile != null) {
            try {
                this.packZipFile.close();
            } catch (Exception e) {
            }
            this.packZipFile = null;
        }
    }

    @Override // shadersmod.client.IShaderPack
    public InputStream getResourceAsStream(String resName) {
        if (this.packZipFile == null) {
            try {
                this.packZipFile = new ZipFile(this.packFile);
            } catch (Exception e) {
            }
        }
        if (this.packZipFile != null) {
            try {
                ZipEntry entry = this.packZipFile.getEntry(resName.substring(1));
                if (entry != null) {
                    return this.packZipFile.getInputStream(entry);
                }
                return null;
            } catch (Exception e2) {
                return null;
            }
        }
        return null;
    }
}
