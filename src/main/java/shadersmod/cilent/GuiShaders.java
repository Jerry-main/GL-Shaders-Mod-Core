package shadersmod.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.lwjgl.Sys;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/GuiShaders.class */
public class GuiShaders extends bli {
    protected bli parentGui;
    private int updateTimer = -1;
    public boolean needReinit;
    private GuiSlotShaders shaderList;

    public GuiShaders(bli par1GuiScreen, bib par2GameSettings) {
        this.parentGui = par1GuiScreen;
    }

    private static String toStringOnOff(boolean value) {
        return value ? "On" : "Off";
    }

    /* renamed from: b */
    public void m2b() throws IOException {
        if (Shaders.shadersConfig == null) {
            Shaders.loadConfig();
        }
        List buttonList = this.n;
        int width = this.l;
        int height = this.m;
        buttonList.add(new biy(17, ((width * 3) / 4) - 60, 30, 160, 18, "NormapMap: " + toStringOnOff(Shaders.configNormalMap)));
        buttonList.add(new biy(18, ((width * 3) / 4) - 60, 50, 160, 18, "SpecularMap: " + toStringOnOff(Shaders.configSpecularMap)));
        buttonList.add(new biy(15, ((width * 3) / 4) - 60, 70, 160, 18, "RenderResMul: " + String.format("%.04f", Float.valueOf(Shaders.configRenderResMul))));
        buttonList.add(new biy(16, ((width * 3) / 4) - 60, 90, 160, 18, "ShadowResMul: " + String.format("%.04f", Float.valueOf(Shaders.configShadowResMul))));
        buttonList.add(new biy(10, ((width * 3) / 4) - 60, 110, 160, 18, "HandDepth: " + String.format("%.04f", Float.valueOf(Shaders.configHandDepthMul))));
        buttonList.add(new biy(9, ((width * 3) / 4) - 60, 130, 160, 18, "CloudShadow: " + toStringOnOff(Shaders.configCloudShadow)));
        buttonList.add(new biy(19, ((width * 3) / 4) - 60, 190, 160, 18, "OldLighting: " + toStringOnOff(Shaders.configOldLighting)));
        buttonList.add(new biy(6, ((width * 3) / 4) - 60, height - 25, 160, 20, "Done"));
        buttonList.add(new biy(5, (width / 4) - 80, height - 25, 160, 20, "Open shaderpacks folder"));
        this.shaderList = new GuiSlotShaders(this);
        this.shaderList.d(7, 8);
        this.needReinit = false;
    }

    /* renamed from: k */
    public void m3k() throws IOException {
        super.k();
        this.shaderList.p();
    }

    /* renamed from: a */
    protected void m4a(biy button) throws IOException {
        int i;
        int i2;
        int i3;
        if (button.l) {
            switch (button.k) {
                case Shaders.ProgramSkyBasic /* 4 */:
                    Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
                    button.j = "tweakBlockDamage: " + toStringOnOff(Shaders.configTweakBlockDamage);
                    break;
                case Shaders.ProgramSkyTextured /* 5 */:
                    switch (C00001.$SwitchMap$net$minecraft$util$Util$EnumOS[h.a().ordinal()]) {
                        case 1:
                            try {
                                Runtime.getRuntime().exec(new String[]{"/usr/bin/open", Shaders.shaderpacksdir.getAbsolutePath()});
                                break;
                            } catch (IOException var7) {
                                var7.printStackTrace();
                                break;
                            }
                        case 2:
                            String var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", Shaders.shaderpacksdir.getAbsolutePath());
                            try {
                                Runtime.getRuntime().exec(var2);
                                break;
                            } catch (IOException var6) {
                                var6.printStackTrace();
                                break;
                            }
                    }
                    boolean var8 = false;
                    try {
                        Class var3 = Class.forName("java.awt.Desktop");
                        Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                        var3.getMethod("browse", URI.class).invoke(var4, new File(this.j.w, Shaders.shaderpacksdirname).toURI());
                    } catch (Throwable var5) {
                        var5.printStackTrace();
                        var8 = true;
                    }
                    if (var8) {
                        System.out.println("Opening via system class!");
                        Sys.openURL("file://" + Shaders.shaderpacksdir.getAbsolutePath());
                        break;
                    }
                    break;
                case Shaders.ProgramClouds /* 6 */:
                    new File(Shaders.shadersdir, "current.cfg");
                    try {
                        Shaders.storeConfig();
                    } catch (Exception e) {
                    }
                    if (this.needReinit) {
                        this.needReinit = false;
                        Shaders.loadShaderPack();
                        Shaders.uninit();
                        this.j.g.a();
                    }
                    this.j.a(this.parentGui);
                    break;
                case 7:
                case 8:
                default:
                    this.shaderList.a(button);
                    break;
                case Shaders.ProgramTerrainCutoutMip /* 9 */:
                    Shaders.configCloudShadow = !Shaders.configCloudShadow;
                    button.j = "CloudShadow: " + toStringOnOff(Shaders.configCloudShadow);
                    break;
                case Shaders.ProgramTerrainCutout /* 10 */:
                    float val = Shaders.configHandDepthMul;
                    float[] choices = {0.0625f, 0.125f, 0.25f, 0.5f, 1.0f};
                    if (!s()) {
                        i3 = 0;
                        while (i3 < choices.length && choices[i3] <= val) {
                            i3++;
                        }
                        if (i3 == choices.length) {
                            i3 = 0;
                        }
                    } else {
                        i3 = choices.length - 1;
                        while (i3 >= 0 && val <= choices[i3]) {
                            i3--;
                        }
                        if (i3 < 0) {
                            i3 = choices.length - 1;
                        }
                    }
                    Shaders.configHandDepthMul = choices[i3];
                    button.j = "HandDepth: " + String.format("%.4f", Float.valueOf(Shaders.configHandDepthMul));
                    break;
                case Shaders.ProgramDamagedBlock /* 11 */:
                    Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
                    int i4 = Shaders.configTexMinFilB;
                    Shaders.configTexMinFilS = i4;
                    Shaders.configTexMinFilN = i4;
                    button.j = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                case Shaders.ProgramWater /* 12 */:
                    Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                    button.j = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                case 13:
                    Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                    button.j = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                case 14:
                    Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
                    button.j = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                case Shaders.ProgramItem /* 15 */:
                    float val2 = Shaders.configRenderResMul;
                    float[] choices2 = {0.25f, 0.33333334f, 0.5f, 0.70710677f, 1.0f, 1.4142135f, 2.0f};
                    if (!s()) {
                        i2 = 0;
                        while (i2 < choices2.length && choices2[i2] <= val2) {
                            i2++;
                        }
                        if (i2 == choices2.length) {
                            i2 = 0;
                        }
                    } else {
                        i2 = choices2.length - 1;
                        while (i2 >= 0 && val2 <= choices2[i2]) {
                            i2--;
                        }
                        if (i2 < 0) {
                            i2 = choices2.length - 1;
                        }
                    }
                    Shaders.configRenderResMul = choices2[i2];
                    button.j = "RenderResMul: " + String.format("%.4f", Float.valueOf(Shaders.configRenderResMul));
                    Shaders.scheduleResize();
                    break;
                case Shaders.ProgramEntities /* 16 */:
                    float val3 = Shaders.configShadowResMul;
                    float[] choices3 = {0.25f, 0.33333334f, 0.5f, 0.70710677f, 1.0f, 1.4142135f, 2.0f, 3.0f, 4.0f};
                    if (!s()) {
                        i = 0;
                        while (i < choices3.length && choices3[i] <= val3) {
                            i++;
                        }
                        if (i == choices3.length) {
                            i = 0;
                        }
                    } else {
                        i = choices3.length - 1;
                        while (i >= 0 && val3 <= choices3[i]) {
                            i--;
                        }
                        if (i < 0) {
                            i = choices3.length - 1;
                        }
                    }
                    Shaders.configShadowResMul = choices3[i];
                    button.j = "ShadowResMul: " + String.format("%.4f", Float.valueOf(Shaders.configShadowResMul));
                    Shaders.scheduleResizeShadow();
                    break;
                case Shaders.ProgramArmorGlint /* 17 */:
                    Shaders.configNormalMap = !Shaders.configNormalMap;
                    button.j = "NormapMap: " + toStringOnOff(Shaders.configNormalMap);
                    this.j.A();
                    break;
                case Shaders.ProgramSpiderEyes /* 18 */:
                    Shaders.configSpecularMap = !Shaders.configSpecularMap;
                    button.j = "SpecularMap: " + toStringOnOff(Shaders.configSpecularMap);
                    this.j.A();
                    break;
                case Shaders.ProgramHand /* 19 */:
                    Shaders.configOldLighting = !Shaders.configOldLighting;
                    button.j = "OldLighting: " + toStringOnOff(Shaders.configOldLighting);
                    Shaders.updateBlockLightLevel();
                    this.j.g.a();
                    break;
            }
        }
    }

    /* renamed from: shadersmod.client.GuiShaders$1 */
    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/GuiShaders$1.class */
    static /* synthetic */ class C00001 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$util$Util$EnumOS = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$util$Util$EnumOS[a.d.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$util$Util$EnumOS[a.c.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* renamed from: a */
    public void m5a(int mouseX, int mouseY, float partialTicks) {
        c();
        this.shaderList.a(mouseX, mouseY, partialTicks);
        if (this.updateTimer <= 0) {
            this.shaderList.updateList();
            this.updateTimer += 20;
        }
        a(this.q, "Shaders ", this.l / 2, 16, 16777215);
        a(this.q, " v2.7.1", this.l - 40, 10, 8421504);
        super.a(mouseX, mouseY, partialTicks);
    }

    /* renamed from: e */
    public void m6e() {
        super.e();
        this.updateTimer--;
    }

    public bhz getMc() {
        return this.j;
    }

    public void drawCenteredString(String text, int x, int y, int color) {
        a(this.q, text, x, y, color);
    }

    public static void addShadersButton(blc guiOptions) {
    }
}
