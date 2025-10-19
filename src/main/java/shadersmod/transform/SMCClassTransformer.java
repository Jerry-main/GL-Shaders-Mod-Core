package shadersmod.transform;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;
import shadersmod.common.SMCLog;
import shadersmod.common.SMCVersion;
import shadersmod.transform.Names;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/SMCClassTransformer.class */
public class SMCClassTransformer implements IClassTransformer {
    Names names;
    protected Map<String, IClassTransformer> ctMap;
    IClassTransformer modTransformer;

    public void put(Names.Clas clas, IClassTransformer ct) {
        this.ctMap.put(clas.clasName.replace('/', '.'), ct);
    }

    public SMCClassTransformer() {
        SMCLog.info("ShadersMod %s", SMCVersion.versionString);
        this.names = new Names();
        SMCConfig.getNamer().setNames();
        this.ctMap = new HashMap();
        put(Names.minecraft_, new STMinecraft());
        put(Names.guiOptions_, new STGuiOptions());
        put(Names.openGlHelper_, new STOpenGlHelper());
        put(Names.iTextureObject_, new STTextureObject());
        put(Names.abstractTexture_, new STTextureAbstract());
        put(Names.simpleTexture_, new STTextureSimple());
        put(Names.layeredTexture_, new STTextureLayered());
        put(Names.dynamicTexture_, new STTextureDynamic());
        put(Names.textureAtlasSpri_, new STTextureAtlasSprite());
        put(Names.textureMap_, new STTextureMap());
        put(Names.textureDownload_, new STTextureDownload());
        put(Names.textureManager_, new STTextureManager());
        put(Names.vertexFormat_, new STVertexFormat());
        put(Names.defVertexFormat_, new STDefVertexFormat());
        put(Names.vertexBuffer_, new STVertexBuffer());
        put(Names.vertexBufferDrawer_, new STVertexBufferDrawer());
        put(Names.vboRenderList_, new STVboRenderList());
        put(Names.renderGlobal_, new STRenderGlobal());
        put(Names.entityRenderer_, new STEntityRenderer());
        put(Names.bakedQuadRetextured_, new STBakedQuadRetextured());
        put(Names.faceBakery_, new STFaceBakery());
        put(Names.blockModelRenderer_, new STBlockModelRenderer());
        put(Names.blockRendererDispatcher_, new STBlockRendererDispatcher());
        put(Names.chunkRenderDispatcher_, new STChunkRenderDispatcher());
        put(Names.regionRenderCacheBuilder_, new STRegionRenderCacheBuilder());
        put(Names.renderItemFrame_, new STRenderItemFrame());
        put(Names.tileEntityBeaconRenderer_, new STTileEntityBeaconRenderer());
        put(Names.texturedQuad_, new STTexturedQuad());
        put(Names.modelBox_, new STModelBox());
        put(Names.modelRenderer_, new STModelRenderer());
        put(Names.render_, new STRender());
        put(Names.renderManager_, new STRenderManager());
        this.modTransformer = new DummyToAccessor();
    }

    public byte[] transform(String name, String transformedName, byte[] byteCode) {
        byte[] bytecode = byteCode;
        IClassTransformer ct = this.ctMap.get(name);
        if (ct == null && name.startsWith("shadersmod.client.")) {
            ct = this.modTransformer;
        }
        if (ct != null) {
            bytecode = ct.transform(name, transformedName, bytecode);
            int length = byteCode.length;
            int length2 = bytecode.length;
        }
        return bytecode;
    }
}
