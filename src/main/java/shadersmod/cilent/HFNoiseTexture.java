package shadersmod.client;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/HFNoiseTexture.class */
public class HFNoiseTexture {
    public int texID = GL11.glGenTextures();
    public int textureUnit = 15;

    public HFNoiseTexture(int width, int height) {
        byte[] image = genHFNoiseImage(width, height);
        ByteBuffer data = BufferUtils.createByteBuffer(image.length);
        data.put(image);
        data.flip();
        GL11.glBindTexture(3553, this.texID);
        GL11.glTexImage2D(3553, 0, 6407, width, height, 0, 6407, 5121, data);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glBindTexture(3553, 0);
    }

    public int getID() {
        return this.texID;
    }

    public void destroy() {
        GL11.glDeleteTextures(this.texID);
        this.texID = 0;
    }

    private int random(int seed) {
        int seed2 = seed ^ (seed << 13);
        int seed3 = seed2 ^ (seed2 >> 17);
        return seed3 ^ (seed3 << 5);
    }

    private byte random(int x, int y, int z) {
        int seed = ((random(x) + random(y * 19)) * random(z * 23)) - z;
        return (byte) (random(seed) % 128);
    }

    private byte[] genHFNoiseImage(int width, int height) {
        byte[] image = new byte[width * height * 3];
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int z = 1; z < 4; z++) {
                    int i = index;
                    index++;
                    image[i] = random(x, y, z);
                }
            }
        }
        return image;
    }
}
