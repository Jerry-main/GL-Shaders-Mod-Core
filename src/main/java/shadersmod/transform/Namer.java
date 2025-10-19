package shadersmod.transform;

import java.util.ArrayList;
import shadersmod.transform.Names;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/Namer.class */
public class Namer {

    /* renamed from: ac */
    ArrayList<Names.Clas> f4ac = new ArrayList<>();

    /* renamed from: af */
    ArrayList<Names.Fiel> f5af = new ArrayList<>();

    /* renamed from: am */
    ArrayList<Names.Meth> f6am = new ArrayList<>();

    /* renamed from: c */
    Names.Clas mo15c(String name) {
        Names.Clas x = new Names.Clas(name);
        if (this.f4ac != null) {
            this.f4ac.add(x);
        }
        return x;
    }

    /* renamed from: f */
    Names.Fiel mo16f(Names.Clas clas, String name, String desc) {
        Names.Fiel x = new Names.Fiel(clas, name, desc);
        if (this.f5af != null) {
            this.f5af.add(x);
        }
        return x;
    }

    /* renamed from: fd */
    Names.Fiel m17fd(Names.Clas clas, Names.Fiel fiel) {
        Names.Fiel x = new Names.Fiel(clas, fiel.name, fiel.desc);
        return x;
    }

    /* renamed from: m */
    Names.Meth mo18m(Names.Clas clas, String name, String desc) {
        Names.Meth x = new Names.Meth(clas, name, desc);
        if (this.f6am != null) {
            this.f6am.add(x);
        }
        return x;
    }

    /* renamed from: md */
    Names.Meth m19md(Names.Clas clas, Names.Meth meth) {
        Names.Meth x = new Names.Meth(clas, meth.name, meth.desc);
        return x;
    }

    public void setNames() {
        beginSetNames();
        setNamesCC();
        setNamesCO();
        setNamesCD();
        setNamesFC();
        setNamesMC();
        setNamesFO();
        setNamesMO();
        rename();
        setNamesFD();
        setNamesMD();
        endSetNames();
    }

    public void beginSetNames() {
    }

    public void rename() {
    }

    public void endSetNames() {
    }

    public void setNamesCC() {
        Names.ofConnectedTextures_ = new Names.Clas("net/minecraft/src/ConnectedTextures");
        Names.ofReflector_ = new Names.Clas("net/minecraft/src/Reflector");
        Names.forgeHooksClient_ = new Names.Clas("net/minecraftforge/client/ForgeHooksClient");
        Names.forgeAttributes_ = new Names.Clas("net/minecraftforge/client/model/Attributes");
    }

    public void setNamesCO() {
    }

    public void setNamesCD() {
    }

    public void setNamesFC() {
        Names.vertexBuffer_sVertexBuffer = new Names.Fiel(Names.vertexBuffer_, "sVertexBuffer", Names.sVertexBuffer_.desc);
        Names.vertexFormat_tangentElementOffset = new Names.Fiel(Names.vertexFormat_, "tangentElementOffset", "I");
        Names.vertexFormat_entityElementOffset = new Names.Fiel(Names.vertexFormat_, "entityElementOffset", "I");
        Names.vertexFormat_miduvElementOffset = new Names.Fiel(Names.vertexFormat_, "miduvElementOffset", "I");
        Names.abstractTexture_multiTex = new Names.Fiel(Names.abstractTexture_, "multiTex", Names.multiTexID_.desc);
        Names.textureMap_atlasWidth = new Names.Fiel(Names.textureMap_, "atlasWidth", "I");
        Names.textureMap_atlasHeight = new Names.Fiel(Names.textureMap_, "atlasWidth", "I");
        Names.forgeAttributes_defBakedFormat = new Names.Fiel(Names.forgeAttributes_, "DEFAULT_BAKED_FORMAT", Names.vertexFormat_.desc);
    }

    public void setNamesFO() {
    }

    public void setNamesFD() {
        Names.guiOptions_buttonList = m17fd(Names.guiOptions_, Names.guiScreen_buttonList);
        Names.guiOptions_width = m17fd(Names.guiOptions_, Names.guiScreen_width);
        Names.guiOptions_height = m17fd(Names.guiOptions_, Names.guiScreen_height);
        Names.guiOptions_mc = m17fd(Names.guiOptions_, Names.guiScreen_mc);
        Names.bakedQuadRetextured_vertexData = m17fd(Names.bakedQuadRetextured_, Names.bakedQuad_vertexData);
    }

    public void setNamesMC() {
        Names.modelRenderer_resetDisplayList = new Names.Meth(Names.modelRenderer_, "resetDisplayList", "()V");
        Names.iTextureObject_getMultiTexID = new Names.Meth(Names.iTextureObject_, "getMultiTexID", "()" + Names.multiTexID_.desc);
        Names.forgeHooksClient_setRenderPass = new Names.Meth(Names.forgeHooksClient_, "setRenderPass", "(I)V");
        Names.forgeAttributes_clinit = new Names.Meth(Names.forgeAttributes_, "<clinit>", "()V");
        Names.faceBakery_makeQuadVertexDataForge = new Names.Meth(Names.faceBakery_, "makeQuadVertexData", "(" + Names.blockPartFace_.desc + "" + Names.textureAtlasSpri_.desc + "" + Names.enumFacing_.desc + "[FLnet/minecraftforge/client/model/ITransformation;" + Names.blockPartRotation_.desc + "ZZ)[I");
        Names.sVertexFormat_onAddElement = new Names.Meth(Names.sVertexFormat_, "onAddElement", "(" + Names.vertexFormat_.desc + Names.vertexFormatElement_.desc + ")V");
        Names.sVertexFormat_onDefaultVertexFormatsClinit = new Names.Meth(Names.sVertexFormat_, "onDefaultVertexFormatsClinit", "()V");
        Names.sBakedQuad_onMakeQuadData = new Names.Meth(Names.sBakedQuad_, "onMakeQuadData", "([I)[I");
        Names.sBakedQuad_onRemapQuad = new Names.Meth(Names.sBakedQuad_, "onRemapQuad", "([I)V");
        Names.sVertexBuffer_enableCalcNormal = new Names.Meth(Names.sVertexBuffer_, "enableCalcNormal", "(" + Names.vertexBuffer_.desc + ")V");
        Names.sTexturedQuad_draw = new Names.Meth(Names.sTexturedQuad_, "draw", "(" + Names.texturedQuad_.desc + Names.vertexBuffer_.desc + "F)V");
        Names.vertexFormatElement_init = new Names.Meth(Names.vertexFormatElement_, "<init>", "(I" + Names.vertexFormatElementEnumType_.desc + Names.vertexFormatElementEnumUsage_.desc + "I)V");
        Names.sVertexElement_init = new Names.Meth(Names.sVertexElement_, "<init>", "(I" + Names.vertexFormatElementEnumType_.desc + Names.vertexFormatElementEnumUsage_.desc + "I)V");
        Names.vertexFormat_init_vertexFormat = new Names.Meth(Names.vertexFormat_, "<init>", "(" + Names.vertexFormat_.desc + ")V");
        Names.chunkRenderDispatcher_init = new Names.Meth(Names.chunkRenderDispatcher_, "<init>", "()V");
        Names.regionRenderCacheBuilder_init = new Names.Meth(Names.regionRenderCacheBuilder_, "<init>", "()V");
        Names.vertexBufferDrawer_init = new Names.Meth(Names.vertexBufferDrawer_, "<init>", "()V");
        Names.sVertexBufferDrawer_init = new Names.Meth(Names.sVertexBufferDrawer_, "<init>", "()V");
        Names.sVertexBufferDrawerChunk_init = new Names.Meth(Names.sVertexBufferDrawerChunk_, "<init>", "()V");
    }

    public void setNamesMO() {
    }

    public void setNamesMD() {
        Names.guiOptions_initGui = m19md(Names.guiOptions_, Names.guiScreen_initGui);
        Names.guiVideoSettings_initGui = m19md(Names.guiVideoSettings_, Names.guiScreen_initGui);
        Names.frustrum_setPosition = m19md(Names.frustum_, Names.iCamera_setPosition);
        Names.worldClient_getCelestialAngle = m19md(Names.worldClient_, Names.world_getCelestialAngle);
        Names.worldClient_getRainStrength = m19md(Names.worldClient_, Names.world_getRainStrength);
    }
}
