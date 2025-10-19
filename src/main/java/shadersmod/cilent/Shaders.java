package shadersmod.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import shadersmod.common.SMCLog;
import shadersmod.common.SMCVersion;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/Shaders.class */
public class Shaders {

    /* renamed from: mc */
    static bhz f2mc;
    static buo entityRenderer;
    public static ContextCapabilities capabilities;
    public static String glVersionString;
    public static String glVendorString;
    public static String glRendererString;
    public static boolean isSleeping;
    public static boolean isHandRendered;
    public static ain itemToRender;
    static float clearColorR;
    static float clearColorG;
    static float clearColorB;
    static float skyColorR;
    static float skyColorG;
    static float skyColorB;
    public static boolean useEntityHurtFlash;
    static double previousCameraPositionX;
    static double previousCameraPositionY;
    static double previousCameraPositionZ;
    static double cameraPositionX;
    static double cameraPositionY;
    static double cameraPositionZ;
    static int preShadowPassThirdPersonView;
    static final int MaxDrawBuffers = 8;
    static final int MaxColorBuffers = 8;
    static final int MaxDepthBuffers = 3;
    static final int MaxShadowColorBuffers = 8;
    static final int MaxShadowDepthBuffers = 2;
    public static final int ProgramNone = 0;
    public static final int ProgramBasic = 1;
    public static final int ProgramTextured = 2;
    public static final int ProgramTexturedLit = 3;
    public static final int ProgramSkyBasic = 4;
    public static final int ProgramSkyTextured = 5;
    public static final int ProgramClouds = 6;
    public static final int ProgramTerrain = 7;
    public static final int ProgramTerrainSolid = 8;
    public static final int ProgramTerrainCutoutMip = 9;
    public static final int ProgramTerrainCutout = 10;
    public static final int ProgramDamagedBlock = 11;
    public static final int ProgramWater = 12;
    public static final int ProgramBlock = 13;
    public static final int ProgramBeaconBeam = 14;
    public static final int ProgramItem = 15;
    public static final int ProgramEntities = 16;
    public static final int ProgramArmorGlint = 17;
    public static final int ProgramSpiderEyes = 18;
    public static final int ProgramHand = 19;
    public static final int ProgramWeather = 20;
    public static final int ProgramComposite = 21;
    public static final int ProgramComposite1 = 22;
    public static final int ProgramComposite2 = 23;
    public static final int ProgramComposite3 = 24;
    public static final int ProgramComposite4 = 25;
    public static final int ProgramComposite5 = 26;
    public static final int ProgramComposite6 = 27;
    public static final int ProgramComposite7 = 28;
    public static final int ProgramFinal = 29;
    public static final int ProgramShadow = 30;
    public static final int ProgramShadowSolid = 31;
    public static final int ProgramShadowCutout = 32;
    public static final int ProgramCount = 33;
    public static final int MaxCompositePasses = 8;
    public static final int texMinFilRange = 3;
    public static final int texMagFilRange = 2;
    static File currentshader;
    static String currentshadername;
    public static final boolean enableShadersOption = true;
    private static final boolean enableShadersDebug = true;
    public static float fogColorR;
    public static float fogColorG;
    public static float fogColorB;
    private static HFNoiseTexture noiseTexture;
    static Map<aou, Integer> mapBlockToEntityData;
    private static final Pattern gbufferFormatPattern;
    private static final Pattern gbufferMipmapEnabledPattern;
    private static final Pattern patternLoadEntityDataMap;
    public static int[] entityData;
    public static int entityDataIndex;
    public static boolean isInitializedOnce = false;
    public static boolean isShaderPackInitialized = false;
    public static boolean hasGlGenMipmap = false;
    public static boolean hasForge = false;
    public static int numberResetDisplayList = 0;
    static boolean needResetModels = false;
    private static int renderDisplayWidth = 0;
    private static int renderDisplayHeight = 0;
    public static int renderWidth = 0;
    public static int renderHeight = 0;
    public static boolean isRenderingWorld = false;
    public static boolean isRenderingSky = false;
    public static boolean isCompositeRendered = false;
    public static boolean isRenderingDfb = false;
    public static boolean isShadowPass = false;
    public static int activeTexUnit = 0;
    public static boolean renderItemPass1DepthMask = false;
    static float[] sunPosition = new float[4];
    static float[] moonPosition = new float[4];
    static float[] shadowLightPosition = new float[4];
    static float[] upPosition = new float[4];
    static float[] shadowLightPositionVector = new float[4];
    static float[] upPosModelView = {0.0f, 100.0f, 0.0f, 0.0f};
    static float[] sunPosModelView = {0.0f, 100.0f, 0.0f, 0.0f};
    static float[] moonPosModelView = {0.0f, -100.0f, 0.0f, 0.0f};
    private static float[] tempMat = new float[16];
    static long worldTime = 0;
    static long lastWorldTime = 0;
    static long diffWorldTime = 0;
    static float celestialAngle = 0.0f;
    static float sunAngle = 0.0f;
    static float shadowAngle = 0.0f;
    static int moonPhase = 0;
    static long systemTime = 0;
    static long lastSystemTime = 0;
    static long diffSystemTime = 0;
    static int frameCounter = 0;
    static float frameTimeCounter = 0.0f;
    static int systemTimeInt32 = 0;
    static float rainStrength = 0.0f;
    static float wetness = 0.0f;
    public static float wetnessHalfLife = 600.0f;
    public static float drynessHalfLife = 200.0f;
    public static float eyeBrightnessHalflife = 10.0f;
    static boolean usewetness = false;
    static int isEyeInWater = 0;
    static int eyeBrightness = 0;
    static float eyeBrightnessFadeX = 0.0f;
    static float eyeBrightnessFadeY = 0.0f;
    static float eyePosY = 0.0f;
    static float centerDepth = 0.0f;
    static float centerDepthSmooth = 0.0f;
    static float centerDepthSmoothHalflife = 1.0f;
    static boolean centerDepthSmoothEnabled = false;
    static int superSamplingLevel = 1;
    static boolean updateChunksErrorRecorded = false;
    static boolean lightmapEnabled = false;
    static boolean fogEnabled = true;
    public static int entityAttrib = 10;
    public static int midTexCoordAttrib = 11;
    public static int tangentAttrib = 12;
    public static boolean useEntityAttrib = false;
    public static boolean useMidTexCoordAttrib = false;
    public static boolean useMultiTexCoord3Attrib = false;
    public static boolean useTangentAttrib = false;
    public static boolean progUseEntityAttrib = false;
    public static boolean progUseMidTexCoordAttrib = false;
    public static boolean progUseTangentAttrib = false;
    public static int atlasSizeX = 0;
    public static int atlasSizeY = 0;
    public static int uniformEntityHurt = -1;
    public static int uniformEntityFlash = -1;
    static int shadowPassInterval = 0;
    public static boolean needResizeShadow = false;
    static int shadowMapWidth = 1024;
    static int shadowMapHeight = 1024;
    static int spShadowMapWidth = 1024;
    static int spShadowMapHeight = 1024;
    static float shadowMapFOV = 90.0f;
    static float shadowMapHalfPlane = 160.0f;
    static boolean shadowMapIsOrtho = true;
    static int shadowPassCounter = 0;
    public static boolean shouldSkipDefaultShadow = false;
    static boolean waterShadowEnabled = false;
    static int usedColorBuffers = 0;
    static int usedDepthBuffers = 0;
    static int usedShadowColorBuffers = 0;
    static int usedShadowDepthBuffers = 0;
    static int usedColorAttachs = 0;
    static int usedDrawBuffers = 0;
    static int dfb = 0;
    static int sfb = 0;
    private static int[] gbuffersFormat = new int[8];
    public static int activeProgram = 0;
    private static final String[] programNames = {"", "gbuffers_basic", "gbuffers_textured", "gbuffers_textured_lit", "gbuffers_skybasic", "gbuffers_skytextured", "gbuffers_clouds", "gbuffers_terrain", "gbuffers_terrain_solid", "gbuffers_terrain_cutout_mip", "gbuffers_terrain_cutout", "gbuffers_damagedblock", "gbuffers_water", "gbuffers_block", "gbuffers_beaconbeam", "gbuffers_item", "gbuffers_entities", "gbuffers_armor_glint", "gbuffers_spidereyes", "gbuffers_hand", "gbuffers_weather", "composite", "composite1", "composite2", "composite3", "composite4", "composite5", "composite6", "composite7", "final", "shadow", "shadow_solid", "shadow_cutout"};
    private static final int[] programBackups = {0, 0, 1, 2, 1, 2, 2, 3, 7, 7, 7, 7, 7, 7, 2, 3, 3, 2, 2, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 30};
    static int[] programsID = new int[33];
    private static int[] programsRef = new int[33];
    private static int programIDCopyDepth = 0;
    private static String[] programsDrawBufSettings = new String[33];
    private static String newDrawBufSetting = null;
    static IntBuffer[] programsDrawBuffers = new IntBuffer[33];
    static IntBuffer activeDrawBuffers = null;
    private static String[] programsColorAtmSettings = new String[33];
    private static String newColorAtmSetting = null;
    private static String activeColorAtmSettings = null;
    private static int[] programsCompositeMipmapSetting = new int[33];
    private static int newCompositeMipmapSetting = 0;
    private static int activeCompositeMipmapSetting = 0;
    public static Properties loadedShaders = null;
    public static Properties shadersConfig = null;
    public static cdq defaultTexture = null;
    public static boolean normalMapEnabled = false;
    public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
    public static boolean[] shadowMipmapEnabled = new boolean[2];
    public static boolean[] shadowFilterNearest = new boolean[2];
    public static boolean[] shadowColorMipmapEnabled = new boolean[8];
    public static boolean[] shadowColorFilterNearest = new boolean[8];
    public static boolean configTweakBlockDamage = true;
    public static boolean configCloudShadow = true;
    public static float configHandDepthMul = 0.125f;
    public static float configRenderResMul = 1.0f;
    public static float configShadowResMul = 1.0f;
    public static int configTexMinFilB = 0;
    public static int configTexMinFilN = 0;
    public static int configTexMinFilS = 0;
    public static int configTexMagFilB = 0;
    public static int configTexMagFilN = 0;
    public static int configTexMagFilS = 0;
    public static boolean configShadowClipFrustrum = true;
    public static boolean configNormalMap = true;
    public static boolean configSpecularMap = true;
    public static boolean configOldLighting = false;
    public static final String[] texMinFilDesc = {"Nearest", "Nearest-Nearest", "Nearest-Linear"};
    public static final String[] texMagFilDesc = {"Nearest", "Linear"};
    public static final int[] texMinFilValue = {9728, 9984, 9986};
    public static final int[] texMagFilValue = {9728, 9729};
    static IShaderPack shaderPack = null;
    static String packNameNone = "(none)";
    static String packNameDefault = "(internal)";
    static String shaderpacksdirname = "shaderpacks";
    static String optionsfilename = "optionsshaders.txt";
    static File shadersdir = new File(bhz.z().w, "shaders");
    static File shaderpacksdir = new File(bhz.z().w, shaderpacksdirname);
    static File configFile = new File(bhz.z().w, optionsfilename);
    public static float blockLightLevel05 = 0.5f;
    public static float blockLightLevel06 = 0.6f;
    public static float blockLightLevel08 = 0.8f;
    public static float aoLevel = 0.8f;
    public static float blockAoLight = 1.0f - aoLevel;
    public static float sunPathRotation = 0.0f;
    public static float shadowAngleInterval = 0.0f;
    public static int fogMode = 0;
    public static float shadowIntervalSize = 2.0f;
    public static int terrainIconSize = 16;
    public static int[] terrainTextureSize = new int[2];
    private static boolean noiseTextureEnabled = false;
    private static int noiseTextureResolution = 256;
    static final int[] dfbColorTexturesA = new int[16];
    static final int[] colorTexturesToggle = new int[8];
    static final int[] colorTextureTextureImageUnit = {0, 1, 2, 3, 7, 8, 9, 10};
    static final boolean[][] programsToggleColorTextures = new boolean[33][8];
    private static final int bigBufferSize = 2196;
    private static final ByteBuffer bigBuffer = (ByteBuffer) BufferUtils.createByteBuffer(bigBufferSize).limit(0);
    static final float[] faProjection = new float[16];
    static final float[] faProjectionInverse = new float[16];
    static final float[] faModelView = new float[16];
    static final float[] faModelViewInverse = new float[16];
    static final float[] faShadowProjection = new float[16];
    static final float[] faShadowProjectionInverse = new float[16];
    static final float[] faShadowModelView = new float[16];
    static final float[] faShadowModelViewInverse = new float[16];
    static final FloatBuffer projection = nextFloatBuffer(16);
    static final FloatBuffer projectionInverse = nextFloatBuffer(16);
    static final FloatBuffer modelView = nextFloatBuffer(16);
    static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
    static final FloatBuffer shadowProjection = nextFloatBuffer(16);
    static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
    static final FloatBuffer shadowModelView = nextFloatBuffer(16);
    static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
    static final FloatBuffer previousProjection = nextFloatBuffer(16);
    static final FloatBuffer previousModelView = nextFloatBuffer(16);
    static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
    static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
    static final IntBuffer dfbColorTextures = nextIntBuffer(16);
    static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
    static final IntBuffer sfbColorTextures = nextIntBuffer(8);
    static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
    static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
    static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
    static final IntBuffer drawBuffersNone = nextIntBuffer(8);
    static final IntBuffer drawBuffersAll = nextIntBuffer(8);
    static final IntBuffer drawBuffersClear0 = nextIntBuffer(8);
    static final IntBuffer drawBuffersClear1 = nextIntBuffer(8);
    static final IntBuffer drawBuffersClearColor = nextIntBuffer(8);
    static final IntBuffer drawBuffersColorAtt0 = nextIntBuffer(8);
    static final IntBuffer[] drawBuffersBuffer = nextIntBufferArray(33, 8);

    static {
        drawBuffersNone.limit(0);
        drawBuffersColorAtt0.put(36064).position(0).limit(1);
        gbufferFormatPattern = Pattern.compile("[ \t]*const[ \t]*int[ \t]*(\\w+)Format[ \t]*=[ \t]*([RGBA81632F]*)[ \t]*;.*");
        gbufferMipmapEnabledPattern = Pattern.compile("[ \t]*const[ \t]*bool[ \t]*(\\w+)MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*");
        patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
        entityData = new int[32];
        entityDataIndex = 0;
    }

    private Shaders() {
    }

    private static ByteBuffer nextByteBuffer(int size) {
        ByteBuffer buffer = bigBuffer;
        int pos = buffer.limit();
        buffer.position(pos).limit(pos + size);
        return buffer.slice();
    }

    private static IntBuffer nextIntBuffer(int size) {
        ByteBuffer buffer = bigBuffer;
        int pos = buffer.limit();
        buffer.position(pos).limit(pos + (size * 4));
        return buffer.asIntBuffer();
    }

    private static FloatBuffer nextFloatBuffer(int size) {
        ByteBuffer buffer = bigBuffer;
        int pos = buffer.limit();
        buffer.position(pos).limit(pos + (size * 4));
        return buffer.asFloatBuffer();
    }

    private static IntBuffer[] nextIntBufferArray(int count, int size) {
        IntBuffer[] aib = new IntBuffer[count];
        for (int i = 0; i < count; i++) {
            aib[i] = nextIntBuffer(size);
        }
        return aib;
    }

    public static void loadConfig() throws IOException {
        SMCLog.info("Load ShadersMod configuration.");
        try {
            if (!shaderpacksdir.exists()) {
                shaderpacksdir.mkdir();
            }
        } catch (Exception e) {
            SMCLog.error("Failed openning shaderpacks directory.");
        }
        shadersConfig = new Properties();
        shadersConfig.setProperty("shaderPack", "");
        if (configFile.exists()) {
            try {
                Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8);
                shadersConfig.load(reader);
                reader.close();
            } catch (Exception e2) {
            }
        }
        if (!configFile.exists()) {
            try {
                storeConfig();
            } catch (Exception e3) {
            }
        }
        configNormalMap = Boolean.parseBoolean(shadersConfig.getProperty("normalMapEnabled", "true"));
        configSpecularMap = Boolean.parseBoolean(shadersConfig.getProperty("specularMapEnabled", "true"));
        configTweakBlockDamage = Boolean.parseBoolean(shadersConfig.getProperty("tweakBlockDamage", shadersConfig.getProperty("dtweak", "false")));
        configCloudShadow = Boolean.parseBoolean(shadersConfig.getProperty("cloudShadow", "true"));
        configHandDepthMul = Float.parseFloat(shadersConfig.getProperty("handDepthMul", "0.125"));
        configRenderResMul = Float.parseFloat(shadersConfig.getProperty("renderResMul", "1.0"));
        configShadowResMul = Float.parseFloat(shadersConfig.getProperty("shadowResMul", "1.0"));
        configShadowClipFrustrum = Boolean.parseBoolean(shadersConfig.getProperty("shadowClipFrustrum", "true"));
        configOldLighting = Boolean.parseBoolean(shadersConfig.getProperty("oldLighting", "false"));
        configTexMinFilB = Integer.parseInt(shadersConfig.getProperty("TexMinFilB", "0")) % 3;
        configTexMinFilN = Integer.parseInt(shadersConfig.getProperty("TexMinFilN", Integer.toString(configTexMinFilB))) % 3;
        configTexMinFilS = Integer.parseInt(shadersConfig.getProperty("TexMinFilS", Integer.toString(configTexMinFilB))) % 3;
        configTexMagFilB = Integer.parseInt(shadersConfig.getProperty("TexMagFilB", "0")) % 2;
        configTexMagFilN = Integer.parseInt(shadersConfig.getProperty("TexMagFilN", "0")) % 2;
        configTexMagFilS = Integer.parseInt(shadersConfig.getProperty("TexMagFilS", "0")) % 2;
        currentshadername = shadersConfig.getProperty("shaderPack", packNameDefault);
        loadShaderPack();
    }

    public static void storeConfig() throws IOException {
        SMCLog.info("Save ShadersMod configuration.");
        shadersConfig.setProperty("normalMapEnabled", Boolean.toString(configNormalMap));
        shadersConfig.setProperty("specularMapEnabled", Boolean.toString(configSpecularMap));
        shadersConfig.setProperty("tweakBlockDamage", Boolean.toString(configTweakBlockDamage));
        shadersConfig.setProperty("cloudShadow", Boolean.toString(configCloudShadow));
        shadersConfig.setProperty("handDepthMul", Float.toString(configHandDepthMul));
        shadersConfig.setProperty("renderResMul", Float.toString(configRenderResMul));
        shadersConfig.setProperty("shadowResMul", Float.toString(configShadowResMul));
        shadersConfig.setProperty("shadowClipFrustrum", Boolean.toString(configShadowClipFrustrum));
        shadersConfig.setProperty("oldLighting", Boolean.toString(configOldLighting));
        shadersConfig.setProperty("TexMinFilB", Integer.toString(configTexMinFilB));
        shadersConfig.setProperty("TexMinFilN", Integer.toString(configTexMinFilN));
        shadersConfig.setProperty("TexMinFilS", Integer.toString(configTexMinFilS));
        shadersConfig.setProperty("TexMagFilB", Integer.toString(configTexMagFilB));
        shadersConfig.setProperty("TexMagFilN", Integer.toString(configTexMagFilN));
        shadersConfig.setProperty("TexMagFilS", Integer.toString(configTexMagFilS));
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8);
            shadersConfig.store(writer, (String) null);
            writer.close();
        } catch (Exception e) {
        }
    }

    public static void setShaderPack(String par1name) {
        currentshadername = par1name;
        shadersConfig.setProperty("shaderPack", par1name);
        loadShaderPack();
    }

    public static void loadShaderPack() {
        if (shaderPack != null) {
            shaderPack.close();
            shaderPack = null;
        }
        String packName = shadersConfig.getProperty("shaderPack", packNameDefault);
        if (!packName.isEmpty() && !packName.equals(packNameNone)) {
            if (packName.equals(packNameDefault)) {
                shaderPack = new ShaderPackDefault();
            } else {
                try {
                    File packFile = new File(shaderpacksdir, packName);
                    if (packFile.isDirectory()) {
                        shaderPack = new ShaderPackFolder(packName, packFile);
                    } else if (packFile.isFile() && packName.toLowerCase().endsWith(".zip")) {
                        shaderPack = new ShaderPackZip(packName, packFile);
                    }
                } catch (Exception e) {
                }
            }
        }
        if (shaderPack != null) {
            SMCLog.info("Loaded shaderpack.");
        } else {
            SMCLog.info("Did not load shaderpack.");
            shaderPack = new ShaderPackNone();
        }
    }

    static ArrayList listofShaders() {
        ArrayList<String> list = new ArrayList<>();
        list.add(packNameNone);
        list.add(packNameDefault);
        try {
            if (!shaderpacksdir.exists()) {
                shaderpacksdir.mkdir();
            }
            File[] listOfFiles = shaderpacksdir.listFiles();
            for (File file : listOfFiles) {
                String name = file.getName();
                if (file.isDirectory() || (file.isFile() && name.toLowerCase().endsWith(".zip"))) {
                    list.add(name);
                }
            }
        } catch (Exception e) {
        }
        return list;
    }

    static String versiontostring(int vv) {
        String vs = Integer.toString(vv);
        return Integer.toString(Integer.parseInt(vs.substring(1, 3))) + "." + Integer.toString(Integer.parseInt(vs.substring(3, 5))) + "." + Integer.toString(Integer.parseInt(vs.substring(5)));
    }

    static void checkOptifine() {
    }

    public static int checkFramebufferStatus(String location) {
        int status = GL30.glCheckFramebufferStatus(36160);
        if (status != 36053) {
            SMCLog.error("FramebufferStatus 0x%04X at %s\n", Integer.valueOf(status), location);
        }
        return status;
    }

    public static int checkGLError(String location) {
        int errorCode = GL11.glGetError();
        if (errorCode != 0 && 0 == 0) {
            if (errorCode == 1286) {
                int status = GL30.glCheckFramebufferStatus(36160);
                SMCLog.error("GL error 0x%04X: %s (Fb status 0x%04X) at %s\n", Integer.valueOf(errorCode), GLU.gluErrorString(errorCode), Integer.valueOf(status), location);
            } else {
                SMCLog.error("GL error 0x%04X: %s at %s\n", Integer.valueOf(errorCode), GLU.gluErrorString(errorCode), location);
            }
        }
        return errorCode;
    }

    public static int checkGLError(String location, String info) {
        int errorCode = GL11.glGetError();
        if (errorCode != 0) {
            SMCLog.error("GL error 0x%04x: %s at %s %s\n", Integer.valueOf(errorCode), GLU.gluErrorString(errorCode), location, info);
        }
        return errorCode;
    }

    public static int checkGLError(String location, String info1, String info2) {
        int errorCode = GL11.glGetError();
        if (errorCode != 0) {
            SMCLog.error("GL error 0x%04x: %s at %s %s %s\n", Integer.valueOf(errorCode), GLU.gluErrorString(errorCode), location, info1, info2);
        }
        return errorCode;
    }

    private static void printChat(String str) {
        f2mc.q.d().a(new ho(str));
    }

    private static void printChatAndLogError(String str) {
        SMCLog.error(str);
        f2mc.q.d().a(new ho(str));
    }

    public static void printIntBuffer(String title, IntBuffer buf) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
        int lim = buf.limit();
        for (int i = 0; i < lim; i++) {
            sb.append(" ").append(buf.get(i));
        }
        sb.append("]");
        SMCLog.info(sb.toString());
    }

    public static void startup(bhz mc) throws IOException {
        f2mc = mc;
        bhz.z();
        capabilities = GLContext.getCapabilities();
        glVersionString = GL11.glGetString(7938);
        glVendorString = GL11.glGetString(7936);
        glRendererString = GL11.glGetString(7937);
        Object[] objArr = new Object[8];
        objArr[0] = SMCVersion.versionString;
        objArr[1] = glVersionString;
        objArr[2] = glVendorString;
        objArr[3] = glRendererString;
        objArr[4] = (capabilities.OpenGL20 ? " 2.0 " : " - ") + (capabilities.OpenGL21 ? " 2.1 " : " - ") + (capabilities.OpenGL30 ? " 3.0 " : " - ") + (capabilities.OpenGL32 ? " 3.2 " : " - ") + (capabilities.OpenGL40 ? " 4.0 " : " - ");
        objArr[5] = Integer.valueOf(GL11.glGetInteger(34852));
        objArr[6] = Integer.valueOf(GL11.glGetInteger(36063));
        objArr[7] = Integer.valueOf(GL11.glGetInteger(34930));
        SMCLog.info("ShadersMod version : %s\nOpenGL Version : %s\nVendor :  %s\nRenderer : %s\nCapabilities %s\nGL_MAX_DRAW_BUFFERS = %d\nGL_MAX_COLOR_ATTACHMENTS_EXT = %d\nGL_MAX_TEXTURE_IMAGE_UNITS = %d", objArr);
        hasGlGenMipmap = capabilities.OpenGL30;
        loadConfig();
    }

    private static String toStringYN(boolean b) {
        return b ? "Y" : "N";
    }

    public static void updateBlockLightLevel() {
        if (configOldLighting) {
            blockLightLevel05 = 0.5f;
            blockLightLevel06 = 0.6f;
            blockLightLevel08 = 0.8f;
        } else {
            blockLightLevel05 = 1.0f;
            blockLightLevel06 = 1.0f;
            blockLightLevel08 = 1.0f;
        }
    }

    public static void init() throws IOException, NumberFormatException {
        boolean firstInit;
        int n;
        if (!isInitializedOnce) {
            isInitializedOnce = true;
            firstInit = true;
        } else {
            firstInit = false;
        }
        if (!isShaderPackInitialized) {
            checkGLError("Shaders.init pre");
            if ("Intel".equals(glVendorString)) {
                printChat(String.format("OpenGL %s\n%s %s", glVersionString, glVendorString, glRendererString));
            }
            if (!capabilities.OpenGL20) {
                printChatAndLogError("No OpenGL 2.0");
            }
            if (!capabilities.GL_EXT_framebuffer_object) {
                printChatAndLogError("No EXT_framebuffer_object");
            }
            dfbDrawBuffers.position(0).limit(8);
            dfbColorTextures.position(0).limit(16);
            dfbDepthTextures.position(0).limit(3);
            sfbDrawBuffers.position(0).limit(8);
            sfbDepthTextures.position(0).limit(2);
            sfbColorTextures.position(0).limit(8);
            usedColorBuffers = 4;
            usedDepthBuffers = 1;
            usedShadowColorBuffers = 0;
            usedShadowDepthBuffers = 0;
            usedColorAttachs = 1;
            usedDrawBuffers = 1;
            Arrays.fill(gbuffersFormat, 6408);
            Arrays.fill(shadowHardwareFilteringEnabled, false);
            Arrays.fill(shadowMipmapEnabled, false);
            Arrays.fill(shadowFilterNearest, false);
            Arrays.fill(shadowColorMipmapEnabled, false);
            Arrays.fill(shadowColorFilterNearest, false);
            centerDepthSmoothEnabled = false;
            noiseTextureEnabled = false;
            sunPathRotation = 0.0f;
            shadowIntervalSize = 2.0f;
            aoLevel = 0.8f;
            blockAoLight = 1.0f - aoLevel;
            useEntityAttrib = false;
            useMidTexCoordAttrib = false;
            useMultiTexCoord3Attrib = false;
            useTangentAttrib = false;
            waterShadowEnabled = false;
            updateChunksErrorRecorded = false;
            updateBlockLightLevel();
            for (int i = 0; i < 33; i++) {
                if (programNames[i] == "") {
                    programsRef[i] = 0;
                    programsID[i] = 0;
                    programsDrawBufSettings[i] = null;
                    programsColorAtmSettings[i] = null;
                    programsCompositeMipmapSetting[i] = 0;
                } else {
                    newDrawBufSetting = null;
                    newColorAtmSetting = null;
                    newCompositeMipmapSetting = 0;
                    int pr = setupProgram(i, "/shaders/" + programNames[i] + ".vsh", "/shaders/" + programNames[i] + ".fsh");
                    programsRef[i] = pr;
                    programsID[i] = pr;
                    programsDrawBufSettings[i] = pr != 0 ? newDrawBufSetting : null;
                    programsColorAtmSettings[i] = pr != 0 ? newColorAtmSetting : null;
                    programsCompositeMipmapSetting[i] = pr != 0 ? newCompositeMipmapSetting : 0;
                }
            }
            int maxDrawBuffers = GL11.glGetInteger(34852);
            new HashMap();
            for (int p = 0; p < 33; p++) {
                Arrays.fill(programsToggleColorTextures[p], false);
                if (p == 29) {
                    programsDrawBuffers[p] = null;
                } else if (programsID[p] == 0) {
                    if (p == 30) {
                        programsDrawBuffers[p] = drawBuffersNone;
                    } else {
                        programsDrawBuffers[p] = drawBuffersColorAtt0;
                    }
                } else {
                    String str = programsDrawBufSettings[p];
                    if (str != null) {
                        IntBuffer intbuf = drawBuffersBuffer[p];
                        int numDB = str.length();
                        if (numDB > usedDrawBuffers) {
                            usedDrawBuffers = numDB;
                        }
                        if (numDB > maxDrawBuffers) {
                            numDB = maxDrawBuffers;
                        }
                        programsDrawBuffers[p] = intbuf;
                        intbuf.limit(numDB);
                        for (int i2 = 0; i2 < numDB; i2++) {
                            int drawBuffer = 0;
                            if (str.length() > i2) {
                                int ca = str.charAt(i2) - '0';
                                if (p != 30) {
                                    if (ca >= 0 && ca <= 7) {
                                        programsToggleColorTextures[p][ca] = true;
                                        drawBuffer = ca + 36064;
                                        if (ca > usedColorAttachs) {
                                            usedColorAttachs = ca;
                                        }
                                        if (ca > usedColorBuffers) {
                                            usedColorBuffers = ca;
                                        }
                                    }
                                } else if (ca >= 0 && ca <= 1) {
                                    drawBuffer = ca + 36064;
                                    if (ca > usedShadowColorBuffers) {
                                        usedShadowColorBuffers = ca;
                                    }
                                }
                            }
                            intbuf.put(i2, drawBuffer);
                        }
                    } else if (p != 30 && p != 31 && p != 32) {
                        programsDrawBuffers[p] = dfbDrawBuffers;
                        usedDrawBuffers = usedColorBuffers;
                        Arrays.fill(programsToggleColorTextures[p], 0, usedColorBuffers, true);
                    } else {
                        programsDrawBuffers[p] = sfbDrawBuffers;
                    }
                }
            }
            usedColorAttachs = usedColorBuffers;
            shadowPassInterval = usedShadowDepthBuffers > 0 ? 1 : 0;
            shouldSkipDefaultShadow = usedShadowDepthBuffers > 0;
            dfbDrawBuffers.position(0).limit(usedDrawBuffers);
            dfbColorTextures.position(0).limit(usedColorBuffers * 2);
            for (int i3 = 0; i3 < usedDrawBuffers; i3++) {
                dfbDrawBuffers.put(i3, 36064 + i3);
            }
            if (usedDrawBuffers > maxDrawBuffers) {
                printChatAndLogError("Not enough draw buffers! Requires " + usedDrawBuffers + ".  Has " + maxDrawBuffers + ".");
            }
            sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);
            for (int i4 = 0; i4 < usedShadowColorBuffers; i4++) {
                sfbDrawBuffers.put(i4, 36064 + i4);
            }
            for (int i5 = 0; i5 < 33; i5++) {
                int i6 = i5;
                while (true) {
                    n = i6;
                    if (programsID[n] != 0 || programBackups[n] == n) {
                        break;
                    } else {
                        i6 = programBackups[n];
                    }
                }
                if (n != i5 && i5 != 30) {
                    programsID[i5] = programsID[n];
                    programsDrawBufSettings[i5] = programsDrawBufSettings[n];
                    programsDrawBuffers[i5] = programsDrawBuffers[n];
                }
            }
            resize();
            resizeShadow();
            if (noiseTextureEnabled) {
                setupNoiseTexture();
            }
            if (defaultTexture == null) {
                defaultTexture = ShadersTex.createDefaultTexture();
            }
            isShaderPackInitialized = true;
            loadEntityDataMap();
            resetDisplayList();
            if (!firstInit) {
                f2mc.q.d().a(new ho("Shaders initialized."));
            }
            checkGLError("Shaders.init");
        }
    }

    public static void resetDisplayList() {
        numberResetDisplayList++;
        needResetModels = true;
        SMCLog.info("Reset world renderers");
        f2mc.g.a();
        SMCLog.info(".");
    }

    public static void resetDisplayListModels() {
        if (needResetModels) {
            needResetModels = false;
            SMCLog.info("Reset model renderers");
            for (bze bzeVar : Dummy.get_renderManager_entityRenderMap(f2mc.ac()).values()) {
                if (bzeVar instanceof cad) {
                    cad rle = (cad) bzeVar;
                    resetDisplayListModel(rle.b());
                }
            }
        }
    }

    public static void resetDisplayListModel(bqd model) {
        if (model != null) {
            for (Object obj : model.r) {
                if (obj instanceof brq) {
                    resetDisplayListModelRenderer((brq) obj);
                }
            }
        }
    }

    public static void resetDisplayListModelRenderer(brq mrr) {
        Dummy.invoke_modelRenderer_resetDisplayList(mrr);
        if (mrr.m != null) {
            int n = mrr.m.size();
            for (int i = 0; i < n; i++) {
                resetDisplayListModelRenderer((brq) mrr.m.get(i));
            }
        }
    }

    private static int setupProgram(int program, String vShaderPath, String fShaderPath) throws IOException, NumberFormatException {
        checkGLError("pre setupProgram", programNames[program]);
        int programid = GL20.glCreateProgram();
        checkGLError("create");
        if (programid != 0) {
            progUseEntityAttrib = false;
            progUseMidTexCoordAttrib = false;
            progUseTangentAttrib = false;
            int vShader = createVertShader(vShaderPath);
            int fShader = createFragShader(fShaderPath);
            checkGLError("create");
            if (vShader != 0 || fShader != 0) {
                if (vShader != 0) {
                    GL20.glAttachShader(programid, vShader);
                    checkGLError("attach");
                }
                if (fShader != 0) {
                    GL20.glAttachShader(programid, fShader);
                    checkGLError("attach");
                }
                if (progUseEntityAttrib) {
                    GL20.glBindAttribLocation(programid, entityAttrib, "mc_Entity");
                    checkGLError("mc_Entity");
                }
                if (progUseMidTexCoordAttrib) {
                    GL20.glBindAttribLocation(programid, midTexCoordAttrib, "mc_midTexCoord");
                    checkGLError("mc_midTexCoord");
                }
                if (progUseTangentAttrib) {
                    GL20.glBindAttribLocation(programid, tangentAttrib, "at_tangent");
                    checkGLError("at_tangent");
                }
                GL20.glLinkProgram(programid);
                if (vShader != 0) {
                    GL20.glDetachShader(programid, vShader);
                    GL20.glDeleteShader(vShader);
                }
                if (fShader != 0) {
                    GL20.glDetachShader(programid, fShader);
                    GL20.glDeleteShader(fShader);
                }
                programsID[program] = programid;
                useProgram(program);
                checkGLError("setupProgram use");
                GL20.glValidateProgram(programid);
                checkGLError("setupProgram validate");
                printProgramLogInfo(programid, vShaderPath + "," + fShaderPath);
                checkGLError("setupProgram printLogInfo");
                int valid = GL20.glGetProgrami(programid, 35715);
                checkGLError("setupProgram getProgrami");
                if (valid == 1) {
                    SMCLog.info("Program " + programNames[program] + " loaded");
                } else {
                    printChatAndLogError("[Shaders] Error : Invalid program " + programNames[program]);
                    GL20.glDeleteProgram(programid);
                    programid = 0;
                }
                useProgram(0);
                checkGLError("setupProgram use 0");
            } else {
                GL20.glDeleteProgram(programid);
                programid = 0;
            }
        }
        checkGLError("end setupProgram", programNames[program]);
        return programid;
    }

    private static int createVertShader(String filename) throws IOException {
        BufferedReader reader;
        int vertShader = GL20.glCreateShader(35633);
        if (vertShader == 0) {
            return 0;
        }
        String vertexCode = "";
        try {
            reader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(filename)));
        } catch (Exception e) {
            try {
                reader = new BufferedReader(new FileReader(new File(filename)));
            } catch (Exception e2) {
                GL20.glDeleteShader(vertShader);
                return 0;
            }
        }
        if (reader != null) {
            while (true) {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    vertexCode = vertexCode + line + "\n";
                    if (line.matches("attribute [_a-zA-Z0-9]+ mc_Entity.*")) {
                        useEntityAttrib = true;
                        progUseEntityAttrib = true;
                    } else if (line.matches("attribute [_a-zA-Z0-9]+ mc_midTexCoord.*")) {
                        useMidTexCoordAttrib = true;
                        progUseMidTexCoordAttrib = true;
                    } else if (line.matches(".*gl_MultiTexCoord3.*")) {
                        useMultiTexCoord3Attrib = true;
                    } else if (line.matches("attribute [_a-zA-Z0-9]+ at_tangent.*")) {
                        useTangentAttrib = true;
                        progUseTangentAttrib = true;
                    }
                } catch (Exception e3) {
                    SMCLog.error("Couldn't read " + filename + "!");
                    e3.printStackTrace();
                    GL20.glDeleteShader(vertShader);
                    return 0;
                }
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception e4) {
                SMCLog.warning("Couldn't close " + filename + "!");
            }
        }
        GL20.glShaderSource(vertShader, vertexCode);
        GL20.glCompileShader(vertShader);
        printShaderLogInfo(vertShader, filename);
        return vertShader;
    }

    private static int createFragShader(String filename) throws IOException, NumberFormatException {
        BufferedReader reader;
        int fragShader = GL20.glCreateShader(35632);
        if (fragShader == 0) {
            return 0;
        }
        StringBuilder fragCode = new StringBuilder(ShadersTex.initialBufferSize);
        try {
            reader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(filename)));
        } catch (Exception e) {
            try {
                reader = new BufferedReader(new FileReader(new File(filename)));
            } catch (Exception e2) {
                GL20.glDeleteShader(fragShader);
                return 0;
            }
        }
        if (reader != null) {
            while (true) {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    fragCode.append(line).append('\n');
                    if (!line.matches("#version .*")) {
                        if (line.matches("uniform [ _a-zA-Z0-9]+ shadow;.*")) {
                            if (usedShadowDepthBuffers < 1) {
                                usedShadowDepthBuffers = 1;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ watershadow;.*")) {
                            waterShadowEnabled = true;
                            if (usedShadowDepthBuffers < 2) {
                                usedShadowDepthBuffers = 2;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowtex0;.*")) {
                            if (usedShadowDepthBuffers < 1) {
                                usedShadowDepthBuffers = 1;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowtex1;.*")) {
                            if (usedShadowDepthBuffers < 2) {
                                usedShadowDepthBuffers = 2;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowcolor;.*")) {
                            if (usedShadowColorBuffers < 1) {
                                usedShadowColorBuffers = 1;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowcolor0;.*")) {
                            if (usedShadowColorBuffers < 1) {
                                usedShadowColorBuffers = 1;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowcolor1;.*")) {
                            if (usedShadowColorBuffers < 2) {
                                usedShadowColorBuffers = 2;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ depthtex0;.*")) {
                            if (usedDepthBuffers < 1) {
                                usedDepthBuffers = 1;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ depthtex1;.*")) {
                            if (usedDepthBuffers < 2) {
                                usedDepthBuffers = 2;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ depthtex2;.*")) {
                            if (usedDepthBuffers < 3) {
                                usedDepthBuffers = 3;
                            }
                        } else if (line.matches("uniform [ _a-zA-Z0-9]+ gdepth;.*")) {
                            if (gbuffersFormat[1] == 6408) {
                                gbuffersFormat[1] = 34836;
                            }
                        } else if (usedColorBuffers < 5 && line.matches("uniform [ _a-zA-Z0-9]+ gaux1;.*")) {
                            usedColorBuffers = 5;
                        } else if (usedColorBuffers < 6 && line.matches("uniform [ _a-zA-Z0-9]+ gaux2;.*")) {
                            usedColorBuffers = 6;
                        } else if (usedColorBuffers < 7 && line.matches("uniform [ _a-zA-Z0-9]+ gaux3;.*")) {
                            usedColorBuffers = 7;
                        } else if (usedColorBuffers < 8 && line.matches("uniform [ _a-zA-Z0-9]+ gaux4;.*")) {
                            usedColorBuffers = 8;
                        } else if (usedColorBuffers < 5 && line.matches("uniform [ _a-zA-Z0-9]+ colortex4;.*")) {
                            usedColorBuffers = 5;
                        } else if (usedColorBuffers < 6 && line.matches("uniform [ _a-zA-Z0-9]+ colortex5;.*")) {
                            usedColorBuffers = 6;
                        } else if (usedColorBuffers < 7 && line.matches("uniform [ _a-zA-Z0-9]+ colortex6;.*")) {
                            usedColorBuffers = 7;
                        } else if (usedColorBuffers < 8 && line.matches("uniform [ _a-zA-Z0-9]+ colortex7;.*")) {
                            usedColorBuffers = 8;
                        } else if (usedColorBuffers < 8 && line.matches("uniform [ _a-zA-Z0-9]+ centerDepthSmooth;.*")) {
                            centerDepthSmoothEnabled = true;
                        } else if (line.matches("/\\* SHADOWRES:[0-9]+ \\*/.*")) {
                            String[] parts = line.split("(:| )", 4);
                            SMCLog.info("Shadow map resolution: " + parts[2]);
                            int i = Integer.parseInt(parts[2]);
                            spShadowMapHeight = i;
                            spShadowMapWidth = i;
                            int iRound = Math.round(spShadowMapWidth * configShadowResMul);
                            shadowMapHeight = iRound;
                            shadowMapWidth = iRound;
                        } else if (line.matches("[ \t]*const[ \t]*int[ \t]*shadowMapResolution[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts2 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Shadow map resolution: " + parts2[1]);
                            int i2 = Integer.parseInt(parts2[1]);
                            spShadowMapHeight = i2;
                            spShadowMapWidth = i2;
                            int iRound2 = Math.round(spShadowMapWidth * configShadowResMul);
                            shadowMapHeight = iRound2;
                            shadowMapWidth = iRound2;
                        } else if (line.matches("/\\* SHADOWFOV:[0-9\\.]+ \\*/.*")) {
                            String[] parts3 = line.split("(:| )", 4);
                            SMCLog.info("Shadow map field of view: " + parts3[2]);
                            shadowMapFOV = Float.parseFloat(parts3[2]);
                            shadowMapIsOrtho = false;
                        } else if (line.matches("/\\* SHADOWHPL:[0-9\\.]+ \\*/.*")) {
                            String[] parts4 = line.split("(:| )", 4);
                            SMCLog.info("Shadow map half-plane: " + parts4[2]);
                            shadowMapHalfPlane = Float.parseFloat(parts4[2]);
                            shadowMapIsOrtho = true;
                        } else if (line.matches("[ \t]*const[ \t]*float[ \t]*shadowDistance[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts5 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Shadow map distance: " + parts5[1]);
                            shadowMapHalfPlane = Float.parseFloat(parts5[1]);
                            shadowMapIsOrtho = true;
                        } else if (line.matches("[ \t]*const[ \t]*float[ \t]*shadowIntervalSize[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts6 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Shadow map interval size: " + parts6[1]);
                            shadowIntervalSize = Float.parseFloat(parts6[1]);
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*generateShadowMipmap[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("Generate shadow mipmap");
                            Arrays.fill(shadowMipmapEnabled, true);
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*generateShadowColorMipmap[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("Generate shadow color mipmap");
                            Arrays.fill(shadowColorMipmapEnabled, true);
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("Hardware shadow filtering enabled.");
                            Arrays.fill(shadowHardwareFilteringEnabled, true);
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering0[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowHardwareFiltering0");
                            shadowHardwareFilteringEnabled[0] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering1[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowHardwareFiltering1");
                            shadowHardwareFilteringEnabled[1] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex0Mipmap|shadowtexMipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowtex0Mipmap");
                            shadowMipmapEnabled[0] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex1Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowtex1Mipmap");
                            shadowMipmapEnabled[1] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor0Mipmap|shadowColor0Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowcolor0Mipmap");
                            shadowColorMipmapEnabled[0] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor1Mipmap|shadowColor1Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowcolor1Mipmap");
                            shadowColorMipmapEnabled[1] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex0Nearest|shadowtexNearest|shadow0MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowtex0Nearest");
                            shadowFilterNearest[0] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex1Nearest|shadow1MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowtex1Nearest");
                            shadowFilterNearest[1] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor0Nearest|shadowColor0Nearest|shadowColor0MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowcolor0Nearest");
                            shadowColorFilterNearest[0] = true;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor1Nearest|shadowColor1Nearest|shadowColor1MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                            SMCLog.info("shadowcolor1Nearest");
                            shadowColorFilterNearest[1] = true;
                        } else if (line.matches("/\\* WETNESSHL:[0-9\\.]+ \\*/.*")) {
                            String[] parts7 = line.split("(:| )", 4);
                            SMCLog.info("Wetness halflife: " + parts7[2]);
                            wetnessHalfLife = Float.parseFloat(parts7[2]);
                        } else if (line.matches("[ \t]*const[ \t]*float[ \t]*wetnessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts8 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Wetness halflife: " + parts8[1]);
                            wetnessHalfLife = Float.parseFloat(parts8[1]);
                        } else if (line.matches("/\\* DRYNESSHL:[0-9\\.]+ \\*/.*")) {
                            String[] parts9 = line.split("(:| )", 4);
                            SMCLog.info("Dryness halflife: " + parts9[2]);
                            drynessHalfLife = Float.parseFloat(parts9[2]);
                        } else if (line.matches("[ \t]*const[ \t]*float[ \t]*drynessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts10 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Dryness halflife: " + parts10[1]);
                            drynessHalfLife = Float.parseFloat(parts10[1]);
                        } else if (line.matches("[ \t]*const[ \t]*float[ \t]*eyeBrightnessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts11 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Eye brightness halflife: " + parts11[1]);
                            eyeBrightnessHalflife = Float.parseFloat(parts11[1]);
                        } else if (line.matches("[ \t]*const[ \t]*float[ \t]*centerDepthHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts12 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Center depth halflife: " + parts12[1]);
                            centerDepthSmoothHalflife = Float.parseFloat(parts12[1]);
                        } else if (line.matches("[ \t]*const[ \t]*float[ \t]*sunPathRotation[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts13 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Sun path rotation: " + parts13[1]);
                            sunPathRotation = Float.parseFloat(parts13[1]);
                        } else if (line.matches("[ \t]*const[ \t]*float[ \t]*ambientOcclusionLevel[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts14 = line.split("(=[ \t]*|;)");
                            SMCLog.info("AO Level: " + parts14[1]);
                            aoLevel = Float.parseFloat(parts14[1]);
                            blockAoLight = 1.0f - aoLevel;
                        } else if (line.matches("[ \t]*const[ \t]*int[ \t]*superSamplingLevel[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            int ssaa = Integer.parseInt(line.split("(=[ \t]*|;)")[1]);
                            if (ssaa > 1) {
                                SMCLog.info("Super sampling level: " + ssaa + "x");
                                superSamplingLevel = ssaa;
                            } else {
                                superSamplingLevel = 1;
                            }
                        } else if (line.matches("[ \t]*const[ \t]*int[ \t]*noiseTextureResolution[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                            String[] parts15 = line.split("(=[ \t]*|;)");
                            SMCLog.info("Noise texture enabled");
                            SMCLog.info("Noise texture resolution: " + parts15[1]);
                            noiseTextureResolution = Integer.parseInt(parts15[1]);
                            noiseTextureEnabled = true;
                        } else if (line.matches("[ \t]*const[ \t]*int[ \t]*\\w+Format[ \t]*=[ \t]*[RGBA81632F]*[ \t]*;.*")) {
                            Matcher m = gbufferFormatPattern.matcher(line);
                            m.matches();
                            String name = m.group(1);
                            String value = m.group(2);
                            int bufferindex = getBufferIndexFromString(name);
                            int format = getTextureFormatFromString(value);
                            if (bufferindex >= 0 && format != 0) {
                                gbuffersFormat[bufferindex] = format;
                                SMCLog.info("%s format: %s\n", name, value);
                            }
                        } else if (line.matches("/\\* GAUX4FORMAT:RGBA32F \\*/.*")) {
                            SMCLog.info("gaux4 format : RGB32AF");
                            gbuffersFormat[7] = 34836;
                        } else if (line.matches("/\\* GAUX4FORMAT:RGB32F \\*/.*")) {
                            SMCLog.info("gaux4 format : RGB32F");
                            gbuffersFormat[7] = 34837;
                        } else if (line.matches("/\\* GAUX4FORMAT:RGB16 \\*/.*")) {
                            SMCLog.info("gaux4 format : RGB16");
                            gbuffersFormat[7] = 32852;
                        } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*\\w+MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*")) {
                            if (filename.matches(".*composite[0-9]?.fsh") || filename.matches(".*final.fsh")) {
                                Matcher m2 = gbufferMipmapEnabledPattern.matcher(line);
                                m2.matches();
                                String name2 = m2.group(1);
                                int bufferindex2 = getBufferIndexFromString(name2);
                                if (bufferindex2 >= 0) {
                                    newCompositeMipmapSetting |= 1 << bufferindex2;
                                    SMCLog.info("%s mipmap enabled for %s\n", name2, filename);
                                }
                            }
                        } else if (line.matches("/\\* DRAWBUFFERS:[0-7N]* \\*/.*")) {
                            newDrawBufSetting = line.split("(:| )", 4)[2];
                        }
                    }
                } catch (Exception e3) {
                    SMCLog.error("Couldn't read " + filename + "!");
                    e3.printStackTrace();
                    GL20.glDeleteShader(fragShader);
                    return 0;
                }
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception e4) {
                SMCLog.error("Couldn't close " + filename + "!");
            }
        }
        GL20.glShaderSource(fragShader, fragCode);
        GL20.glCompileShader(fragShader);
        printShaderLogInfo(fragShader, filename);
        return fragShader;
    }

    private static boolean printShaderLogInfo(int obj, String name) {
        IntBuffer iVal = BufferUtils.createIntBuffer(1);
        GL20.glGetShader(obj, 35716, iVal);
        checkGLError("printLogInfo get length");
        int length = iVal.get();
        if (length > 1) {
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            GL20.glGetShaderInfoLog(obj, iVal, infoLog);
            checkGLError("printLogInfo get log");
            byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            if (infoBytes[length - 1] == 0) {
                infoBytes[length - 1] = 10;
            }
            String out = new String(infoBytes);
            SMCLog.info("Info log: " + name + "\n" + out);
            return false;
        }
        return true;
    }

    private static boolean printProgramLogInfo(int obj, String name) {
        IntBuffer iVal = BufferUtils.createIntBuffer(1);
        GL20.glGetProgram(obj, 35716, iVal);
        checkGLError("printLogInfo get length");
        int length = iVal.get();
        if (length > 1) {
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            GL20.glGetProgramInfoLog(obj, iVal, infoLog);
            checkGLError("printLogInfo get log");
            byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            if (infoBytes[length - 1] == 0) {
                infoBytes[length - 1] = 10;
            }
            String out = new String(infoBytes);
            SMCLog.info("Info log: " + name + "\n" + out);
            return false;
        }
        return true;
    }

    public static void setDrawBuffers(IntBuffer drawBuffers) {
        if (drawBuffers == null) {
            drawBuffers = drawBuffersNone;
        }
        if (activeDrawBuffers != drawBuffers) {
            activeDrawBuffers = drawBuffers;
            GL20.glDrawBuffers(drawBuffers);
        }
    }

    public static void useProgram(int program) {
        int itemID;
        aou block;
        checkGLError("pre-useProgram");
        if (isShadowPass) {
            program = 30;
            if (programsID[30] == 0) {
                normalMapEnabled = false;
                return;
            }
        }
        if (activeProgram == program) {
            return;
        }
        activeProgram = program;
        GL20.glUseProgram(programsID[program]);
        if (programsID[program] == 0) {
            normalMapEnabled = false;
            return;
        }
        if (checkGLError("useProgram ", programNames[program]) != 0) {
            programsID[program] = 0;
        }
        IntBuffer drawBuffers = programsDrawBuffers[program];
        if (isRenderingDfb) {
            setDrawBuffers(drawBuffers);
            checkGLError(programNames[program], " draw buffers = ", programsDrawBufSettings[program]);
        }
        activeCompositeMipmapSetting = programsCompositeMipmapSetting[program];
        uniformEntityHurt = GL20.glGetUniformLocation(programsID[activeProgram], "entityHurt");
        uniformEntityFlash = GL20.glGetUniformLocation(programsID[activeProgram], "entityFlash");
        switch (program) {
            case 1:
            case 2:
            case 3:
            case ProgramSkyBasic /* 4 */:
            case ProgramSkyTextured /* 5 */:
            case ProgramClouds /* 6 */:
            case 7:
            case 8:
            case ProgramTerrainCutoutMip /* 9 */:
            case ProgramTerrainCutout /* 10 */:
            case ProgramDamagedBlock /* 11 */:
            case ProgramWater /* 12 */:
            case 13:
            case ProgramEntities /* 16 */:
            case ProgramSpiderEyes /* 18 */:
            case ProgramHand /* 19 */:
            case ProgramWeather /* 20 */:
                normalMapEnabled = true;
                setProgramUniform1i("texture", 0);
                setProgramUniform1i("lightmap", 1);
                setProgramUniform1i("normals", 2);
                setProgramUniform1i("specular", 3);
                setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
                setProgramUniform1i("watershadow", 4);
                setProgramUniform1i("shadowtex0", 4);
                setProgramUniform1i("shadowtex1", 5);
                setProgramUniform1i("depthtex0", 6);
                setProgramUniform1i("depthtex1", 12);
                setProgramUniform1i("shadowcolor", 13);
                setProgramUniform1i("shadowcolor0", 13);
                setProgramUniform1i("shadowcolor1", 14);
                setProgramUniform1i("noisetex", 15);
                break;
            case 14:
            case ProgramItem /* 15 */:
            case ProgramArmorGlint /* 17 */:
            default:
                normalMapEnabled = false;
                break;
            case ProgramComposite /* 21 */:
            case ProgramComposite1 /* 22 */:
            case ProgramComposite2 /* 23 */:
            case ProgramComposite3 /* 24 */:
            case ProgramComposite4 /* 25 */:
            case ProgramComposite5 /* 26 */:
            case ProgramComposite6 /* 27 */:
            case ProgramComposite7 /* 28 */:
            case ProgramFinal /* 29 */:
                normalMapEnabled = false;
                setProgramUniform1i("gcolor", 0);
                setProgramUniform1i("gdepth", 1);
                setProgramUniform1i("gnormal", 2);
                setProgramUniform1i("composite", 3);
                setProgramUniform1i("gaux1", 7);
                setProgramUniform1i("gaux2", 8);
                setProgramUniform1i("gaux3", 9);
                setProgramUniform1i("gaux4", 10);
                setProgramUniform1i("colortex0", 0);
                setProgramUniform1i("colortex1", 1);
                setProgramUniform1i("colortex2", 2);
                setProgramUniform1i("colortex3", 3);
                setProgramUniform1i("colortex4", 7);
                setProgramUniform1i("colortex5", 8);
                setProgramUniform1i("colortex6", 9);
                setProgramUniform1i("colortex7", 10);
                setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
                setProgramUniform1i("watershadow", 4);
                setProgramUniform1i("shadowtex0", 4);
                setProgramUniform1i("shadowtex1", 5);
                setProgramUniform1i("gdepthtex", 6);
                setProgramUniform1i("depthtex0", 6);
                setProgramUniform1i("depthtex1", 11);
                setProgramUniform1i("depthtex2", 12);
                setProgramUniform1i("shadowcolor", 13);
                setProgramUniform1i("shadowcolor0", 13);
                setProgramUniform1i("shadowcolor1", 14);
                setProgramUniform1i("noisetex", 15);
                break;
            case ProgramShadow /* 30 */:
            case ProgramShadowSolid /* 31 */:
            case ProgramShadowCutout /* 32 */:
                setProgramUniform1i("tex", 0);
                setProgramUniform1i("texture", 0);
                setProgramUniform1i("lightmap", 1);
                setProgramUniform1i("normals", 2);
                setProgramUniform1i("specular", 3);
                setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
                setProgramUniform1i("watershadow", 4);
                setProgramUniform1i("shadowtex0", 4);
                setProgramUniform1i("shadowtex1", 5);
                setProgramUniform1i("shadowcolor", 13);
                setProgramUniform1i("shadowcolor0", 13);
                setProgramUniform1i("shadowcolor1", 14);
                setProgramUniform1i("noisetex", 15);
                break;
        }
        ain stack = f2mc.h.b(tz.a);
        ail item = stack != null ? stack.c() : null;
        if (item != null) {
            itemID = ail.g.a(item);
            block = (aou) aou.h.a(itemID);
        } else {
            itemID = -1;
            block = null;
        }
        setProgramUniform1i("heldItemId", itemID);
        setProgramUniform1i("heldBlockLightValue", block != null ? block.o(block.t()) : 0);
        setProgramUniform1i("fogMode", fogEnabled ? fogMode : 0);
        setProgramUniform3f("fogColor", fogColorR, fogColorG, fogColorB);
        setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
        setProgramUniform1i("worldTime", ((int) worldTime) % 24000);
        setProgramUniform1i("moonPhase", moonPhase);
        setProgramUniform1f("frameTimeCounter", frameTimeCounter);
        setProgramUniform1f("sunAngle", sunAngle);
        setProgramUniform1f("shadowAngle", shadowAngle);
        setProgramUniform1f("rainStrength", rainStrength);
        setProgramUniform1f("aspectRatio", renderWidth / renderHeight);
        setProgramUniform1f("viewWidth", renderWidth);
        setProgramUniform1f("viewHeight", renderHeight);
        setProgramUniform1f("near", 0.05f);
        setProgramUniform1f("far", f2mc.t.e * 16);
        setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
        setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
        setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
        setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
        setProgramUniform3f("previousCameraPosition", (float) previousCameraPositionX, (float) previousCameraPositionY, (float) previousCameraPositionZ);
        setProgramUniform3f("cameraPosition", (float) cameraPositionX, (float) cameraPositionY, (float) cameraPositionZ);
        setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
        setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
        setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
        setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
        setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
        setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
        if (usedShadowDepthBuffers > 0) {
            setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
            setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
            setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
            setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
        }
        setProgramUniform1f("wetness", wetness);
        setProgramUniform1f("eyeAltitude", eyePosY);
        setProgramUniform2i("eyeBrightness", eyeBrightness & 65535, eyeBrightness >> 16);
        setProgramUniform2i("eyeBrightnessSmooth", Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
        setProgramUniform2i("terrainTextureSize", terrainTextureSize[0], terrainTextureSize[1]);
        setProgramUniform1i("terrainIconSize", terrainIconSize);
        setProgramUniform1i("isEyeInWater", isEyeInWater);
        setProgramUniform1i("hideGUI", f2mc.t.av ? 1 : 0);
        setProgramUniform1f("centerDepthSmooth", centerDepthSmooth);
        setProgramUniform2i("atlasSize", atlasSizeX, atlasSizeY);
        checkGLError("useProgram ", programNames[program]);
    }

    public static void setProgramUniform1i(String name, int x) {
        int gp = programsID[activeProgram];
        if (gp != 0) {
            int uniform = GL20.glGetUniformLocation(gp, name);
            GL20.glUniform1i(uniform, x);
            checkGLError(programNames[activeProgram], name);
        }
    }

    public static void setProgramUniform2i(String name, int x, int y) {
        int gp = programsID[activeProgram];
        if (gp != 0) {
            int uniform = GL20.glGetUniformLocation(gp, name);
            GL20.glUniform2i(uniform, x, y);
            checkGLError(programNames[activeProgram], name);
        }
    }

    public static void setProgramUniform1f(String name, float x) {
        int gp = programsID[activeProgram];
        if (gp != 0) {
            int uniform = GL20.glGetUniformLocation(gp, name);
            GL20.glUniform1f(uniform, x);
            checkGLError(programNames[activeProgram], name);
        }
    }

    public static void setProgramUniform3f(String name, float x, float y, float z) {
        int gp = programsID[activeProgram];
        if (gp != 0) {
            int uniform = GL20.glGetUniformLocation(gp, name);
            GL20.glUniform3f(uniform, x, y, z);
            checkGLError(programNames[activeProgram], name);
        }
    }

    public static void setProgramUniformMatrix4ARB(String name, boolean transpose, FloatBuffer matrix) {
        int gp = programsID[activeProgram];
        if (gp != 0 && matrix != null) {
            int uniform = GL20.glGetUniformLocation(gp, name);
            GL20.glUniformMatrix4(uniform, transpose, matrix);
            checkGLError(programNames[activeProgram], name);
        }
    }

    private static int getBufferIndexFromString(String name) {
        if (name.equals("colortex0") || name.equals("gcolor")) {
            return 0;
        }
        if (name.equals("colortex1") || name.equals("gdepth")) {
            return 1;
        }
        if (name.equals("colortex2") || name.equals("gnormal")) {
            return 2;
        }
        if (name.equals("colortex3") || name.equals("composite")) {
            return 3;
        }
        if (name.equals("colortex4") || name.equals("gaux1")) {
            return 4;
        }
        if (name.equals("colortex5") || name.equals("gaux2")) {
            return 5;
        }
        if (name.equals("colortex6") || name.equals("gaux3")) {
            return 6;
        }
        return (name.equals("colortex7") || name.equals("gaux4")) ? 7 : -1;
    }

    private static int getTextureFormatFromString(String par) {
        if (par.matches("[ \t]*R8[ \t]*")) {
            return 33321;
        }
        if (par.matches("[ \t]*RG8[ \t]*")) {
            return 33323;
        }
        if (par.matches("[ \t]*RGB8[ \t]*")) {
            return 32849;
        }
        if (par.matches("[ \t]*RGBA8[ \t]*")) {
            return 32856;
        }
        if (par.matches("[ \t]*R16[ \t]*")) {
            return 33322;
        }
        if (par.matches("[ \t]*RG16[ \t]*")) {
            return 33324;
        }
        if (par.matches("[ \t]*RGB16[ \t]*")) {
            return 32852;
        }
        if (par.matches("[ \t]*RGBA16[ \t]*")) {
            return 32859;
        }
        if (par.matches("[ \t]*R32F[ \t]*")) {
            return 33326;
        }
        if (par.matches("[ \t]*RG32F[ \t]*")) {
            return 33328;
        }
        if (par.matches("[ \t]*RGB32F[ \t]*")) {
            return 34837;
        }
        if (par.matches("[ \t]*RGBA32F[ \t]*")) {
            return 34836;
        }
        return 0;
    }

    private static void setupNoiseTexture() {
        if (noiseTexture == null) {
            noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
        }
    }

    private static void loadEntityDataMap() throws IOException, NumberFormatException {
        mapBlockToEntityData = new IdentityHashMap(300);
        if (mapBlockToEntityData.isEmpty()) {
            for (nd key : aou.h.c()) {
                aou block = (aou) aou.h.c(key);
                int id = aou.h.a(block);
                mapBlockToEntityData.put(block, Integer.valueOf(id));
            }
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
        } catch (Exception e) {
        }
        if (reader != null) {
            while (true) {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    Matcher m = patternLoadEntityDataMap.matcher(line);
                    if (m.matches()) {
                        String name = m.group(1);
                        String value = m.group(2);
                        int id2 = Integer.parseInt(value);
                        aou block2 = aou.b(name);
                        if (block2 != null) {
                            mapBlockToEntityData.put(block2, Integer.valueOf(id2));
                        } else {
                            SMCLog.warning("Unknown block name %s", name);
                        }
                    } else {
                        System.out.format("unmatched %s\n", line);
                    }
                } catch (Exception e2) {
                    SMCLog.warning("Error parsing mc_Entity_x.txt");
                }
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception e3) {
            }
        }
    }

    private static IntBuffer fillIntBufferZero(IntBuffer buf) {
        int limit = buf.limit();
        for (int i = buf.position(); i < limit; i++) {
            buf.put(i, 0);
        }
        return buf;
    }

    public static void uninit() {
        if (isShaderPackInitialized) {
            checkGLError("Shaders.uninit pre");
            for (int i = 0; i < 33; i++) {
                if (programsRef[i] != 0) {
                    GL20.glDeleteProgram(programsRef[i]);
                    checkGLError("del programRef");
                }
                programsRef[i] = 0;
                programsID[i] = 0;
                programsDrawBufSettings[i] = null;
                programsDrawBuffers[i] = null;
                programsCompositeMipmapSetting[i] = 0;
            }
            if (dfb != 0) {
                GL30.glDeleteFramebuffers(dfb);
                dfb = 0;
                checkGLError("del dfb");
            }
            if (sfb != 0) {
                GL30.glDeleteFramebuffers(sfb);
                sfb = 0;
                checkGLError("del sfb");
            }
            if (dfbDepthTextures != null) {
                GL11.glDeleteTextures(dfbDepthTextures);
                fillIntBufferZero(dfbDepthTextures);
                checkGLError("del dfbDepthTextures");
            }
            if (dfbColorTextures != null) {
                GL11.glDeleteTextures(dfbColorTextures);
                fillIntBufferZero(dfbColorTextures);
                checkGLError("del dfbTextures");
            }
            if (sfbDepthTextures != null) {
                GL11.glDeleteTextures(sfbDepthTextures);
                fillIntBufferZero(sfbDepthTextures);
                checkGLError("del shadow depth");
            }
            if (sfbColorTextures != null) {
                GL11.glDeleteTextures(sfbColorTextures);
                fillIntBufferZero(sfbColorTextures);
                checkGLError("del shadow color");
            }
            if (dfbDrawBuffers != null) {
                fillIntBufferZero(dfbDrawBuffers);
            }
            if (noiseTexture != null) {
                noiseTexture.destroy();
                noiseTexture = null;
            }
            SMCLog.info("Uninit");
            shadowPassInterval = 0;
            shouldSkipDefaultShadow = false;
            isShaderPackInitialized = false;
            checkGLError("Shaders.uninit");
        }
    }

    public static void scheduleResize() {
        renderDisplayHeight = 0;
    }

    public static void scheduleResizeShadow() {
        needResizeShadow = true;
    }

    private static void resize() {
        renderDisplayWidth = f2mc.d;
        renderDisplayHeight = f2mc.e;
        renderWidth = Math.round(renderDisplayWidth * configRenderResMul);
        renderHeight = Math.round(renderDisplayHeight * configRenderResMul);
        setupFrameBuffer();
    }

    private static void resizeShadow() {
        needResizeShadow = false;
        shadowMapWidth = Math.round(spShadowMapWidth * configShadowResMul);
        shadowMapHeight = Math.round(spShadowMapHeight * configShadowResMul);
        setupShadowFrameBuffer();
    }

    private static void setupFrameBuffer() {
        if (dfb != 0) {
            GL30.glDeleteFramebuffers(dfb);
            GL11.glDeleteTextures(dfbDepthTextures);
            GL11.glDeleteTextures(dfbColorTextures);
        }
        dfb = GL30.glGenFramebuffers();
        GL11.glGenTextures((IntBuffer) dfbDepthTextures.clear().limit(usedDepthBuffers));
        GL11.glGenTextures((IntBuffer) dfbColorTextures.clear().limit(16));
        dfbDepthTextures.position(0);
        dfbColorTextures.position(0);
        dfbColorTextures.get(dfbColorTexturesA).position(0);
        GL30.glBindFramebuffer(36160, dfb);
        GL20.glDrawBuffers(0);
        GL11.glReadBuffer(0);
        for (int i = 0; i < usedDepthBuffers; i++) {
            GL11.glBindTexture(3553, dfbDepthTextures.get(i));
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
            GL11.glTexParameteri(3553, 34891, 6409);
            GL11.glTexImage2D(3553, 0, 6402, renderWidth, renderHeight, 0, 6402, 5126, (FloatBuffer) null);
        }
        GL30.glFramebufferTexture2D(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
        GL20.glDrawBuffers(dfbDrawBuffers);
        GL11.glReadBuffer(0);
        checkGLError("FT d");
        for (int i2 = 0; i2 < usedColorBuffers; i2++) {
            GL11.glBindTexture(3553, dfbColorTexturesA[i2]);
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexImage2D(3553, 0, gbuffersFormat[i2], renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer) null);
            GL30.glFramebufferTexture2D(36160, 36064 + i2, 3553, dfbColorTexturesA[i2], 0);
            checkGLError("FT c");
        }
        for (int i3 = 0; i3 < usedColorBuffers; i3++) {
            GL11.glBindTexture(3553, dfbColorTexturesA[8 + i3]);
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexImage2D(3553, 0, gbuffersFormat[i3], renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer) null);
            checkGLError("FT ca");
        }
        int status = GL30.glCheckFramebufferStatus(36160);
        if (status == 36054) {
            printChatAndLogError("Failed framebuffer incomplete");
            for (int i4 = 0; i4 < usedColorBuffers; i4++) {
                GL11.glBindTexture(3553, dfbColorTextures.get(i4));
                GL11.glTexImage2D(3553, 0, 6408, renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer) null);
                GL30.glFramebufferTexture2D(36160, 36064 + i4, 3553, dfbColorTextures.get(i4), 0);
                checkGLError("FT c");
            }
            status = GL30.glCheckFramebufferStatus(36160);
            if (status == 36053) {
                SMCLog.info("complete");
            }
        }
        GL11.glBindTexture(3553, 0);
        if (status != 36053) {
            printChatAndLogError("Failed creating framebuffer! (Status " + status + ")");
        } else {
            SMCLog.info("Framebuffer created.");
        }
    }

    private static void setupShadowFrameBuffer() {
        if (usedShadowDepthBuffers == 0) {
            return;
        }
        if (sfb != 0) {
            GL30.glDeleteFramebuffers(sfb);
            GL11.glDeleteTextures(sfbDepthTextures);
            GL11.glDeleteTextures(sfbColorTextures);
        }
        sfb = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(36160, sfb);
        GL11.glDrawBuffer(0);
        GL11.glReadBuffer(0);
        GL11.glGenTextures((IntBuffer) sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
        GL11.glGenTextures((IntBuffer) sfbColorTextures.clear().limit(usedShadowColorBuffers));
        sfbDepthTextures.position(0);
        sfbColorTextures.position(0);
        for (int i = 0; i < usedShadowDepthBuffers; i++) {
            GL11.glBindTexture(3553, sfbDepthTextures.get(i));
            GL11.glTexParameterf(3553, 10242, 10496.0f);
            GL11.glTexParameterf(3553, 10243, 10496.0f);
            int filter = shadowFilterNearest[i] ? 9728 : 9729;
            GL11.glTexParameteri(3553, 10241, filter);
            GL11.glTexParameteri(3553, 10240, filter);
            if (shadowHardwareFilteringEnabled[i]) {
                GL11.glTexParameteri(3553, 34892, 34894);
            }
            GL11.glTexImage2D(3553, 0, 6402, shadowMapWidth, shadowMapHeight, 0, 6402, 5126, (FloatBuffer) null);
        }
        GL30.glFramebufferTexture2D(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
        checkGLError("FT sd");
        for (int i2 = 0; i2 < usedShadowColorBuffers; i2++) {
            GL11.glBindTexture(3553, sfbColorTextures.get(i2));
            GL11.glTexParameterf(3553, 10242, 10496.0f);
            GL11.glTexParameterf(3553, 10243, 10496.0f);
            int filter2 = shadowColorFilterNearest[i2] ? 9728 : 9729;
            GL11.glTexParameteri(3553, 10241, filter2);
            GL11.glTexParameteri(3553, 10240, filter2);
            GL11.glTexImage2D(3553, 0, 6408, shadowMapWidth, shadowMapHeight, 0, 32993, 33639, (ByteBuffer) null);
            GL30.glFramebufferTexture2D(36160, 36064 + i2, 3553, sfbColorTextures.get(i2), 0);
            checkGLError("FT sc");
        }
        GL11.glBindTexture(3553, 0);
        if (usedShadowColorBuffers > 0) {
            GL20.glDrawBuffers(sfbDrawBuffers);
        }
        int status = GL30.glCheckFramebufferStatus(36160);
        if (status != 36053) {
            printChatAndLogError("Failed creating shadow framebuffer! (Status " + status + ")");
        } else {
            SMCLog.info("Shadow framebuffer created.");
        }
    }

    public static void beginRender(bhz minecraft, float partialTicks, long finishTimeNano) throws IOException, NumberFormatException {
        checkGLError("pre beginRender");
        f2mc = minecraft;
        f2mc.B.a("init");
        entityRenderer = f2mc.o;
        if (!isShaderPackInitialized) {
            init();
        }
        if (f2mc.d != renderDisplayWidth || f2mc.e != renderDisplayHeight) {
            resize();
        }
        if (needResizeShadow) {
            resizeShadow();
        }
        worldTime = f2mc.f.S();
        diffWorldTime = (worldTime - lastWorldTime) % 24000;
        if (diffWorldTime < 0) {
            diffWorldTime += 24000;
        }
        lastWorldTime = worldTime;
        moonPhase = f2mc.f.F();
        systemTime = System.currentTimeMillis();
        if (lastSystemTime == 0) {
            lastSystemTime = systemTime;
        }
        diffSystemTime = systemTime - lastSystemTime;
        lastSystemTime = systemTime;
        frameTimeCounter += diffSystemTime * 0.001f;
        frameTimeCounter %= 100000.0f;
        rainStrength = minecraft.f.j(partialTicks);
        float fadeScalar = diffSystemTime * 0.01f;
        float temp1 = (float) Math.exp((Math.log(0.5d) * fadeScalar) / (wetness < rainStrength ? drynessHalfLife : wetnessHalfLife));
        wetness = (wetness * temp1) + (rainStrength * (1.0f - temp1));
        vo voVarAa = f2mc.aa();
        isSleeping = (voVarAa instanceof vo) && voVarAa.cz();
        eyePosY = (((float) ((ve) voVarAa).q) * partialTicks) + (((float) ((ve) voVarAa).N) * (1.0f - partialTicks));
        eyeBrightness = voVarAa.av();
        float fadeScalar2 = diffSystemTime * 0.01f;
        float temp2 = (float) Math.exp((Math.log(0.5d) * fadeScalar2) / eyeBrightnessHalflife);
        eyeBrightnessFadeX = (eyeBrightnessFadeX * temp2) + ((eyeBrightness & 65535) * (1.0f - temp2));
        eyeBrightnessFadeY = (eyeBrightnessFadeY * temp2) + ((eyeBrightness >> 16) * (1.0f - temp2));
        isEyeInWater = (f2mc.t.aw == 0 && !f2mc.aa().cz() && f2mc.h.a(bcx.h)) ? 1 : 0;
        bhc skyColorV = f2mc.f.a(f2mc.aa(), partialTicks);
        skyColorR = (float) skyColorV.b;
        skyColorG = (float) skyColorV.c;
        skyColorB = (float) skyColorV.d;
        isRenderingWorld = true;
        isCompositeRendered = false;
        isHandRendered = false;
        if (usedShadowDepthBuffers >= 1) {
            GL13.glActiveTexture(33988);
            GL11.glBindTexture(3553, sfbDepthTextures.get(0));
            if (usedShadowDepthBuffers >= 2) {
                GL13.glActiveTexture(33989);
                GL11.glBindTexture(3553, sfbDepthTextures.get(1));
            }
        }
        GL13.glActiveTexture(33984);
        for (int i = 0; i < usedColorBuffers; i++) {
            GL11.glBindTexture(3553, dfbColorTexturesA[i]);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glBindTexture(3553, dfbColorTexturesA[8 + i]);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
        }
        GL11.glBindTexture(3553, 0);
        for (int i2 = 0; i2 < 4 && 4 + i2 < usedColorBuffers; i2++) {
            GL13.glActiveTexture(33991 + i2);
            GL11.glBindTexture(3553, dfbColorTextures.get(4 + i2));
        }
        GL13.glActiveTexture(33990);
        GL11.glBindTexture(3553, dfbDepthTextures.get(0));
        if (usedDepthBuffers >= 2) {
            GL13.glActiveTexture(33995);
            GL11.glBindTexture(3553, dfbDepthTextures.get(1));
            if (usedDepthBuffers >= 3) {
                GL13.glActiveTexture(33996);
                GL11.glBindTexture(3553, dfbDepthTextures.get(2));
            }
        }
        for (int i3 = 0; i3 < usedShadowColorBuffers; i3++) {
            GL13.glActiveTexture(33997 + i3);
            GL11.glBindTexture(3553, sfbColorTextures.get(i3));
        }
        if (noiseTextureEnabled) {
            GL13.glActiveTexture(33984 + noiseTexture.textureUnit);
            GL11.glBindTexture(3553, noiseTexture.getID());
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
        }
        GL13.glActiveTexture(33984);
        previousCameraPositionX = cameraPositionX;
        previousCameraPositionY = cameraPositionY;
        previousCameraPositionZ = cameraPositionZ;
        previousProjection.position(0);
        projection.position(0);
        previousProjection.put(projection);
        previousProjection.position(0);
        projection.position(0);
        previousModelView.position(0);
        modelView.position(0);
        previousModelView.put(modelView);
        previousModelView.position(0);
        modelView.position(0);
        checkGLError("beginRender");
        ShadersRender.renderShadowMap(entityRenderer, 0, partialTicks, finishTimeNano);
        f2mc.B.b();
        GL30.glBindFramebuffer(36160, dfb);
        for (int i4 = 0; i4 < usedColorBuffers; i4++) {
            colorTexturesToggle[i4] = 0;
            GL30.glFramebufferTexture2D(36160, 36064 + i4, 3553, dfbColorTexturesA[i4], 0);
        }
        checkGLError("end beginRender");
    }

    public static void beginRenderPass(int pass, float partialTicks, long finishTimeNano) {
        if (isShadowPass) {
            return;
        }
        GL30.glBindFramebuffer(36160, dfb);
        GL11.glViewport(0, 0, renderWidth, renderHeight);
        activeDrawBuffers = null;
        ShadersTex.bindNSTextures(Dummy.invoke_iTextureObject_getMultiTexID(defaultTexture));
        useProgram(2);
        checkGLError("end beginRenderPass");
    }

    public static void setViewport(int vx, int vy, int vw, int vh) {
        GL11.glColorMask(true, true, true, true);
        if (isShadowPass) {
            GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
            return;
        }
        GL11.glViewport(0, 0, renderWidth, renderHeight);
        GL30.glBindFramebuffer(36160, dfb);
        isRenderingDfb = true;
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        setDrawBuffers(drawBuffersNone);
        useProgram(2);
        checkGLError("beginRenderPass");
    }

    public static int setFogMode(int val) {
        fogMode = val;
        return val;
    }

    public static void setFogColor(float r, float g, float b) {
        fogColorR = r;
        fogColorG = g;
        fogColorB = b;
    }

    public static void setClearColor(float red, float green, float blue, float alpha) {
        buq.a(red, green, blue, alpha);
        clearColorR = red;
        clearColorG = green;
        clearColorB = blue;
    }

    public static void clearRenderBuffer() {
        if (isShadowPass) {
            checkGLError("shadow clear pre");
            GL30.glFramebufferTexture2D(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
            GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            GL20.glDrawBuffers(programsDrawBuffers[30]);
            checkFramebufferStatus("shadow clear");
            GL11.glClear(16640);
            checkGLError("shadow clear");
            return;
        }
        checkGLError("clear pre");
        GL20.glDrawBuffers(36064);
        GL11.glClear(16384);
        GL20.glDrawBuffers(36065);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glClear(16384);
        for (int i = 2; i < usedColorBuffers; i++) {
            GL20.glDrawBuffers(36064 + i);
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glClear(16384);
        }
        setDrawBuffers(dfbDrawBuffers);
        checkFramebufferStatus("clear");
        checkGLError("clear");
    }

    public static void setCamera(float partialTicks) {
        ve viewEntity = f2mc.aa();
        double x = viewEntity.M + ((viewEntity.p - viewEntity.M) * partialTicks);
        double y = viewEntity.N + ((viewEntity.q - viewEntity.N) * partialTicks);
        double z = viewEntity.O + ((viewEntity.r - viewEntity.O) * partialTicks);
        cameraPositionX = x;
        cameraPositionY = y;
        cameraPositionZ = z;
        GL11.glGetFloat(2983, (FloatBuffer) projection.position(0));
        SMath.invertMat4FBFA((FloatBuffer) projectionInverse.position(0), (FloatBuffer) projection.position(0), faProjectionInverse, faProjection);
        projection.position(0);
        projectionInverse.position(0);
        GL11.glGetFloat(2982, (FloatBuffer) modelView.position(0));
        SMath.invertMat4FBFA((FloatBuffer) modelViewInverse.position(0), (FloatBuffer) modelView.position(0), faModelViewInverse, faModelView);
        modelView.position(0);
        modelViewInverse.position(0);
        checkGLError("setCamera");
    }

    public static void setCameraShadow(float partialTicks) {
        ve viewEntity = f2mc.aa();
        double x = viewEntity.M + ((viewEntity.p - viewEntity.M) * partialTicks);
        double y = viewEntity.N + ((viewEntity.q - viewEntity.N) * partialTicks);
        double z = viewEntity.O + ((viewEntity.r - viewEntity.O) * partialTicks);
        cameraPositionX = x;
        cameraPositionY = y;
        cameraPositionZ = z;
        GL11.glGetFloat(2983, (FloatBuffer) projection.position(0));
        SMath.invertMat4FBFA((FloatBuffer) projectionInverse.position(0), (FloatBuffer) projection.position(0), faProjectionInverse, faProjection);
        projection.position(0);
        projectionInverse.position(0);
        GL11.glGetFloat(2982, (FloatBuffer) modelView.position(0));
        SMath.invertMat4FBFA((FloatBuffer) modelViewInverse.position(0), (FloatBuffer) modelView.position(0), faModelViewInverse, faModelView);
        modelView.position(0);
        modelViewInverse.position(0);
        GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        if (shadowMapIsOrtho) {
            GL11.glOrtho(-shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, shadowMapHalfPlane, 0.05000000074505806d, 256.0d);
        } else {
            GLU.gluPerspective(shadowMapFOV, shadowMapWidth / shadowMapHeight, 0.05f, 256.0f);
        }
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -100.0f);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        celestialAngle = f2mc.f.c(partialTicks);
        sunAngle = celestialAngle < 0.75f ? celestialAngle + 0.25f : celestialAngle - 0.75f;
        float angle = celestialAngle * (-360.0f);
        float angleInterval = shadowAngleInterval > 0.0f ? (angle % shadowAngleInterval) - (shadowAngleInterval * 0.5f) : 0.0f;
        if (sunAngle <= 0.5d) {
            GL11.glRotatef(angle - angleInterval, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(sunPathRotation, 1.0f, 0.0f, 0.0f);
            shadowAngle = sunAngle;
        } else {
            GL11.glRotatef((angle + 180.0f) - angleInterval, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(sunPathRotation, 1.0f, 0.0f, 0.0f);
            shadowAngle = sunAngle - 0.5f;
        }
        if (shadowMapIsOrtho) {
            float trans = shadowIntervalSize;
            float trans2 = trans / 2.0f;
            GL11.glTranslatef((((float) x) % trans) - trans2, (((float) y) % trans) - trans2, (((float) z) % trans) - trans2);
        }
        float raSun = sunAngle * 6.2831855f;
        float x1 = (float) Math.cos(raSun);
        float y1 = (float) Math.sin(raSun);
        float raTilt = sunPathRotation * 6.2831855f;
        float x2 = x1;
        float y2 = y1 * ((float) Math.cos(raTilt));
        float z2 = y1 * ((float) Math.sin(raTilt));
        if (sunAngle > 0.5d) {
            x2 = -x2;
            y2 = -y2;
            z2 = -z2;
        }
        shadowLightPositionVector[0] = x2;
        shadowLightPositionVector[1] = y2;
        shadowLightPositionVector[2] = z2;
        shadowLightPositionVector[3] = 0.0f;
        GL11.glGetFloat(2983, (FloatBuffer) shadowProjection.position(0));
        SMath.invertMat4FBFA((FloatBuffer) shadowProjectionInverse.position(0), (FloatBuffer) shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
        shadowProjection.position(0);
        shadowProjectionInverse.position(0);
        GL11.glGetFloat(2982, (FloatBuffer) shadowModelView.position(0));
        SMath.invertMat4FBFA((FloatBuffer) shadowModelViewInverse.position(0), (FloatBuffer) shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
        shadowModelView.position(0);
        shadowModelViewInverse.position(0);
        setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
        setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
        setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
        setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
        setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
        setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
        setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
        setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
        setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
        setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
        f2mc.t.aw = 1;
        checkGLError("setCamera");
    }

    public static void preCelestialRotate() {
        setUpPosition();
        GL11.glRotatef(sunPathRotation * 1.0f, 0.0f, 0.0f, 1.0f);
        checkGLError("preCelestialRotate");
    }

    public static void postCelestialRotate() {
        FloatBuffer modelView2 = tempMatrixDirectBuffer;
        modelView2.clear();
        GL11.glGetFloat(2982, modelView2);
        modelView2.get(tempMat, 0, 16);
        SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
        SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
        System.arraycopy(shadowAngle == sunAngle ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
        checkGLError("postCelestialRotate");
    }

    public static void setUpPosition() {
        FloatBuffer modelView2 = tempMatrixDirectBuffer;
        modelView2.clear();
        GL11.glGetFloat(2982, modelView2);
        modelView2.get(tempMat, 0, 16);
        SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
    }

    public static void genCompositeMipmap() {
        if (hasGlGenMipmap) {
            for (int i = 0; i < usedColorBuffers; i++) {
                if ((activeCompositeMipmapSetting & (1 << i)) != 0) {
                    GL13.glActiveTexture(33984 + colorTextureTextureImageUnit[i]);
                    GL11.glTexParameteri(3553, 10241, 9987);
                    GL30.glGenerateMipmap(3553);
                }
            }
            GL13.glActiveTexture(33984);
        }
    }

    public static void drawComposite() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, 1.0f, 0.0f);
        GL11.glEnd();
    }

    public static void renderCompositeFinal() {
        if (isShadowPass) {
            return;
        }
        checkGLError("pre-renderCompositeFinal");
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0d, 1.0d, 0.0d, 1.0d, 0.0d, 1.0d);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3008);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDepthFunc(519);
        GL11.glDepthMask(false);
        GL11.glDisable(2896);
        if (usedShadowDepthBuffers >= 1) {
            GL13.glActiveTexture(33988);
            GL11.glBindTexture(3553, sfbDepthTextures.get(0));
            if (usedShadowDepthBuffers >= 2) {
                GL13.glActiveTexture(33989);
                GL11.glBindTexture(3553, sfbDepthTextures.get(1));
            }
        }
        for (int i = 0; i < usedColorBuffers; i++) {
            GL13.glActiveTexture(33984 + colorTextureTextureImageUnit[i]);
            GL11.glBindTexture(3553, dfbColorTexturesA[i]);
        }
        GL13.glActiveTexture(33990);
        GL11.glBindTexture(3553, dfbDepthTextures.get(0));
        if (usedDepthBuffers >= 2) {
            GL13.glActiveTexture(33995);
            GL11.glBindTexture(3553, dfbDepthTextures.get(1));
            if (usedDepthBuffers >= 3) {
                GL13.glActiveTexture(33996);
                GL11.glBindTexture(3553, dfbDepthTextures.get(2));
            }
        }
        for (int i2 = 0; i2 < usedShadowColorBuffers; i2++) {
            GL13.glActiveTexture(33997 + i2);
            GL11.glBindTexture(3553, sfbColorTextures.get(i2));
        }
        if (noiseTextureEnabled) {
            GL13.glActiveTexture(33984 + noiseTexture.textureUnit);
            GL11.glBindTexture(3553, noiseTexture.getID());
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
        }
        GL13.glActiveTexture(33984);
        for (int i3 = 0; i3 < usedColorBuffers; i3++) {
            GL30.glFramebufferTexture2D(36160, 36064 + i3, 3553, dfbColorTexturesA[8 + i3], 0);
        }
        GL30.glFramebufferTexture2D(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
        GL20.glDrawBuffers(dfbDrawBuffers);
        checkGLError("pre-composite");
        for (int cp = 0; cp < 8; cp++) {
            if (programsID[21 + cp] != 0) {
                useProgram(21 + cp);
                checkGLError(programNames[21 + cp]);
                if (activeCompositeMipmapSetting != 0) {
                    genCompositeMipmap();
                }
                drawComposite();
                for (int i4 = 0; i4 < usedColorBuffers; i4++) {
                    if (programsToggleColorTextures[21 + cp][i4]) {
                        int t0 = colorTexturesToggle[i4];
                        int t1 = 8 - t0;
                        colorTexturesToggle[i4] = t1;
                        GL13.glActiveTexture(33984 + colorTextureTextureImageUnit[i4]);
                        GL11.glBindTexture(3553, dfbColorTexturesA[t1 + i4]);
                        GL30.glFramebufferTexture2D(36160, 36064 + i4, 3553, dfbColorTexturesA[t0 + i4], 0);
                    }
                }
                GL13.glActiveTexture(33984);
            }
        }
        checkGLError("composite");
        isRenderingDfb = false;
        f2mc.b().a(true);
        cig.a(cig.c, cig.e, 3553, f2mc.b().g, 0);
        GL11.glViewport(0, 0, f2mc.d, f2mc.e);
        if (buo.a) {
            boolean maskR = buo.b != 0;
            GL11.glColorMask(maskR, !maskR, !maskR, true);
        }
        GL11.glDepthMask(true);
        GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0f);
        GL11.glClear(16640);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3008);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDepthFunc(519);
        GL11.glDepthMask(false);
        checkGLError("pre-final");
        useProgram(29);
        checkGLError("final");
        if (activeCompositeMipmapSetting != 0) {
            genCompositeMipmap();
        }
        drawComposite();
        checkGLError("renderCompositeFinal");
        isCompositeRendered = true;
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glEnable(3042);
        GL11.glDepthFunc(515);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        useProgram(0);
    }

    public static void endRender() {
        if (isShadowPass) {
            checkGLError("shadow endRender");
            return;
        }
        if (!isCompositeRendered) {
            renderCompositeFinal();
        }
        isRenderingWorld = false;
        GL11.glColorMask(true, true, true, true);
        useProgram(0);
        bhx.a();
        checkGLError("endRender end");
    }

    public static void beginSky() {
        isRenderingSky = true;
        fogEnabled = true;
        setDrawBuffers(dfbDrawBuffers);
        useProgram(5);
        pushEntity(-2, 0);
    }

    public static void setSkyColor(bhc v3color) {
        skyColorR = (float) v3color.b;
        skyColorG = (float) v3color.c;
        skyColorB = (float) v3color.d;
        setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
    }

    public static void drawHorizon() {
        bvc tess = bvc.a();
        bui wrr = tess.c();
        float farDistance = f2mc.t.e * 16;
        double xzq = farDistance * 0.9238d;
        double xzp = farDistance * 0.3826d;
        double xzn = -xzp;
        double xzm = -xzq;
        double bot = -cameraPositionY;
        wrr.a(7, cdw.e);
        wrr.b(xzn, bot, xzm);
        wrr.d();
        wrr.b(xzn, 16.0d, xzm);
        wrr.d();
        wrr.b(xzm, 16.0d, xzn);
        wrr.d();
        wrr.b(xzm, bot, xzn);
        wrr.d();
        wrr.b(xzm, bot, xzn);
        wrr.d();
        wrr.b(xzm, 16.0d, xzn);
        wrr.d();
        wrr.b(xzm, 16.0d, xzp);
        wrr.d();
        wrr.b(xzm, bot, xzp);
        wrr.d();
        wrr.b(xzm, bot, xzp);
        wrr.d();
        wrr.b(xzm, 16.0d, xzp);
        wrr.d();
        wrr.b(xzn, 16.0d, xzp);
        wrr.d();
        wrr.b(xzn, bot, xzp);
        wrr.d();
        wrr.b(xzn, bot, xzp);
        wrr.d();
        wrr.b(xzn, 16.0d, xzp);
        wrr.d();
        wrr.b(xzp, 16.0d, xzq);
        wrr.d();
        wrr.b(xzp, bot, xzq);
        wrr.d();
        wrr.b(xzp, bot, xzq);
        wrr.d();
        wrr.b(xzp, 16.0d, xzq);
        wrr.d();
        wrr.b(xzq, 16.0d, xzp);
        wrr.d();
        wrr.b(xzq, bot, xzp);
        wrr.d();
        wrr.b(xzq, bot, xzp);
        wrr.d();
        wrr.b(xzq, 16.0d, xzp);
        wrr.d();
        wrr.b(xzq, 16.0d, xzn);
        wrr.d();
        wrr.b(xzq, bot, xzn);
        wrr.d();
        wrr.b(xzq, bot, xzn);
        wrr.d();
        wrr.b(xzq, 16.0d, xzn);
        wrr.d();
        wrr.b(xzp, 16.0d, xzm);
        wrr.d();
        wrr.b(xzp, bot, xzm);
        wrr.d();
        wrr.b(xzp, bot, xzm);
        wrr.d();
        wrr.b(xzp, 16.0d, xzm);
        wrr.d();
        wrr.b(xzn, 16.0d, xzm);
        wrr.d();
        wrr.b(xzn, bot, xzm);
        wrr.d();
        tess.b();
    }

    public static void preSkyList() {
        GL11.glColor3f(fogColorR, fogColorG, fogColorB);
        drawHorizon();
        GL11.glColor3f(skyColorR, skyColorG, skyColorB);
    }

    public static void endSky() {
        isRenderingSky = false;
        setDrawBuffers(dfbDrawBuffers);
        useProgram(lightmapEnabled ? 3 : 2);
        popEntity();
    }

    public static void beginUpdateChunks() {
        checkGLError("beginUpdateChunks1");
        checkFramebufferStatus("beginUpdateChunks1");
        if (!isShadowPass) {
            useProgram(7);
        }
        checkGLError("beginUpdateChunks2");
        checkFramebufferStatus("beginUpdateChunks2");
    }

    public static void endUpdateChunks() {
        checkGLError("endUpdateChunks1");
        checkFramebufferStatus("endUpdateChunks1");
        if (!isShadowPass) {
            useProgram(7);
        }
        checkGLError("endUpdateChunks2");
        checkFramebufferStatus("endUpdateChunks2");
    }

    public static int shouldRenderClouds(bib gs) {
        checkGLError("shouldRenderClouds");
        return isShadowPass ? configCloudShadow ? 1 : 0 : gs.e();
    }

    public static void beginClouds() {
        fogEnabled = true;
        pushEntity(-3, 0);
        useProgram(6);
    }

    public static void endClouds() {
        disableFog();
        popEntity();
    }

    public static void beginEntities() {
        if (isRenderingWorld) {
            useProgram(16);
            if (programsID[activeProgram] != 0) {
                useEntityHurtFlash = (uniformEntityHurt == -1 && uniformEntityFlash == -1) ? false : true;
                if (uniformEntityHurt != -1) {
                    GL20.glUniform1i(uniformEntityHurt, 0);
                }
                if (uniformEntityHurt != -1) {
                    GL20.glUniform1i(uniformEntityFlash, 0);
                }
            } else {
                useEntityHurtFlash = false;
            }
            resetDisplayListModels();
        }
    }

    public static void nextEntity() {
        if (isRenderingWorld) {
            useProgram(16);
        }
    }

    public static void beginSpiderEyes() {
        if (isRenderingWorld && programsID[18] != programsID[0]) {
            useProgram(18);
            buq.e();
            buq.a(516, 0.0f);
            buq.b(770, 771);
        }
    }

    public static void endEntities() {
        if (isRenderingWorld) {
            useProgram(lightmapEnabled ? 3 : 2);
        }
    }

    public static void setEntityHurtFlash(int hurt, int flash) {
        if (useEntityHurtFlash && isRenderingWorld && !isShadowPass) {
            if (uniformEntityHurt != -1) {
                GL20.glUniform1i(uniformEntityHurt, hurt);
            }
            if (uniformEntityFlash != -1) {
                GL20.glUniform1i(uniformEntityFlash, flash >> 24);
            }
            checkGLError("setEntityHurtFlash");
        }
    }

    public static void resetEntityHurtFlash() {
        setEntityHurtFlash(0, 0);
    }

    public static void beginLivingDamage() {
        if (isRenderingWorld) {
            ShadersTex.bindTexture(defaultTexture);
            if (!isShadowPass) {
                setDrawBuffers(drawBuffersColorAtt0);
            }
        }
    }

    public static void endLivingDamage() {
        if (isRenderingWorld && !isShadowPass) {
            setDrawBuffers(programsDrawBuffers[16]);
        }
    }

    public static void beginBlockEntities() {
        if (isRenderingWorld) {
            checkGLError("beginBlockEntities");
            useProgram(13);
        }
    }

    public static void nextBlockEntity() {
        if (isRenderingWorld) {
            checkGLError("nextBlockEntity");
            useProgram(13);
            GL20.glVertexAttrib4s(entityAttrib, (short) 0, (short) 0, (short) 0, (short) 0);
            GL20.glVertexAttrib4s(tangentAttrib, (short) 0, (short) 0, (short) 0, (short) 0);
            GL20.glVertexAttrib2f(midTexCoordAttrib, 0.0f, 0.0f);
        }
    }

    public static void endBlockEntities() {
        if (isRenderingWorld) {
            checkGLError("endBlockEntities");
            useProgram(lightmapEnabled ? 3 : 2);
            ShadersTex.bindTexture(defaultTexture);
        }
    }

    public static void beginLitParticles() {
        useProgram(3);
    }

    public static void beginParticles() {
        useProgram(2);
    }

    public static void endParticles() {
        useProgram(3);
    }

    public static void readCenterDepth() {
        if (!isShadowPass && centerDepthSmoothEnabled) {
            tempDirectFloatBuffer.clear();
            GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
            centerDepth = tempDirectFloatBuffer.get(0);
            float fadeScalar = diffSystemTime * 0.01f;
            float fadeFactor = (float) Math.exp((Math.log(0.5d) * fadeScalar) / centerDepthSmoothHalflife);
            centerDepthSmooth = (centerDepthSmooth * fadeFactor) + (centerDepth * (1.0f - fadeFactor));
        }
    }

    public static void beginWeather() {
        if (!isShadowPass) {
            if (usedDepthBuffers >= 3) {
                GL13.glActiveTexture(33996);
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
                GL13.glActiveTexture(33984);
            }
            GL11.glEnable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
            useProgram(20);
        }
    }

    public static void endWeather() {
        GL11.glDisable(3042);
        useProgram(3);
    }

    public static void preWater() {
        if (usedDepthBuffers >= 2) {
            GL13.glActiveTexture(33995);
            checkGLError("pre copy depth");
            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
            checkGLError("copy depth");
            GL13.glActiveTexture(33984);
        }
        ShadersTex.bindNSTextures(Dummy.invoke_iTextureObject_getMultiTexID(defaultTexture));
    }

    public static void beginWater() {
        if (isRenderingWorld) {
            if (!isShadowPass) {
                useProgram(12);
                GL11.glEnable(3042);
                GL11.glDepthMask(true);
                return;
            }
            GL11.glDepthMask(true);
        }
    }

    public static void endWater() {
        if (isRenderingWorld) {
            if (isShadowPass) {
            }
            useProgram(lightmapEnabled ? 3 : 2);
        }
    }

    public static void beginProjectRedHalo() {
        if (isRenderingWorld) {
            useProgram(1);
        }
    }

    public static void endProjectRedHalo() {
        if (isRenderingWorld) {
            useProgram(3);
        }
    }

    public static void applyHandDepth() {
        if (configHandDepthMul != 1.0d) {
            GL11.glScaled(1.0d, 1.0d, configHandDepthMul);
        }
    }

    public static void beginHand() {
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5888);
        useProgram(19);
        checkGLError("beginHand");
        checkFramebufferStatus("beginHand");
    }

    public static void endHand() {
        checkGLError("pre endHand");
        checkFramebufferStatus("pre endHand");
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glBlendFunc(770, 771);
        checkGLError("endHand");
    }

    public static void beginFPOverlay() {
        GL11.glDisable(2896);
        GL11.glDisable(3042);
    }

    public static void endFPOverlay() {
    }

    public static void glEnableWrapper(int cap) {
        GL11.glEnable(cap);
        if (cap == 3553) {
            enableTexture2D();
        } else if (cap == 2912) {
            enableFog();
        }
    }

    public static void glDisableWrapper(int cap) {
        GL11.glDisable(cap);
        if (cap == 3553) {
            disableTexture2D();
        } else if (cap == 2912) {
            disableFog();
        }
    }

    public static void sglEnableT2D(int cap) {
        GL11.glEnable(cap);
        enableTexture2D();
    }

    public static void sglDisableT2D(int cap) {
        GL11.glDisable(cap);
        disableTexture2D();
    }

    public static void sglEnableFog(int cap) {
        GL11.glEnable(cap);
        enableFog();
    }

    public static void sglDisableFog(int cap) {
        GL11.glDisable(cap);
        disableFog();
    }

    public static void enableTexture2D() {
        if (isRenderingSky) {
            useProgram(5);
        } else if (activeProgram == 1) {
            useProgram(lightmapEnabled ? 3 : 2);
        }
    }

    public static void disableTexture2D() {
        if (isRenderingSky) {
            useProgram(4);
        } else if (activeProgram == 2 || activeProgram == 3) {
            useProgram(1);
        }
    }

    public static void enableFog() {
        fogEnabled = true;
        setProgramUniform1i("fogMode", fogMode);
    }

    public static void disableFog() {
        fogEnabled = false;
        setProgramUniform1i("fogMode", 0);
    }

    public static void setFog(m fogMode2) {
        buq.a(fogMode2);
        fogMode = fogMode2.d;
        if (fogEnabled) {
            setProgramUniform1i("fogMode", fogMode);
        }
    }

    public static void sglFogi(int pname, int param) {
        GL11.glFogi(pname, param);
        if (pname == 2917) {
            fogMode = param;
            if (fogEnabled) {
                setProgramUniform1i("fogMode", fogMode);
            }
        }
    }

    public static void enableLightmap() {
        lightmapEnabled = true;
        if (activeProgram == 2) {
            useProgram(3);
        }
    }

    public static void disableLightmap() {
        lightmapEnabled = false;
        if (activeProgram == 3) {
            useProgram(2);
        }
    }

    public static int getEntityData() {
        return entityData[entityDataIndex * 2];
    }

    public static int getEntityData2() {
        return entityData[(entityDataIndex * 2) + 1];
    }

    public static int setEntityData1(int data1) {
        entityData[entityDataIndex * 2] = (entityData[entityDataIndex * 2] & 65535) | (data1 << 16);
        return data1;
    }

    public static int setEntityData2(int data2) {
        entityData[(entityDataIndex * 2) + 1] = (entityData[(entityDataIndex * 2) + 1] & (-65536)) | (data2 & 65535);
        return data2;
    }

    public static void pushEntity(int data0, int data1) {
        entityDataIndex++;
        entityData[entityDataIndex * 2] = (data0 & 65535) | (data1 << 16);
        entityData[(entityDataIndex * 2) + 1] = 0;
    }

    public static void pushEntity(int data0) {
        entityDataIndex++;
        entityData[entityDataIndex * 2] = data0 & 65535;
        entityData[(entityDataIndex * 2) + 1] = 0;
    }

    public static void pushEntity(aou block) {
        entityDataIndex++;
        entityData[entityDataIndex * 2] = (aou.h.a(block) & 65535) | (block.a(block.t()).ordinal() << 16);
        entityData[(entityDataIndex * 2) + 1] = 0;
    }

    public static void pushEntity(awr state) {
        aou block = state.u();
        entityDataIndex++;
        entityData[entityDataIndex * 2] = (aou.h.a(block) & 65535) | (block.a(state).ordinal() << 16);
        entityData[(entityDataIndex * 2) + 1] = 0;
    }

    public static void popEntity() {
        entityData[entityDataIndex * 2] = 0;
        entityData[(entityDataIndex * 2) + 1] = 0;
        entityDataIndex--;
    }

    public static void mcProfilerEndSection() {
        f2mc.B.b();
    }
}
