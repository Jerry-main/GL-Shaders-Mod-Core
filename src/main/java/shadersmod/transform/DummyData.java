package shadersmod.transform;

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import shadersmod.transform.Names;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/DummyData.class */
public class DummyData {
    static Map<String, InsnList> mapDummyToInsnList = new HashMap();

    static {
        addField("vertexBuffer", "sVertexBuffer", Names.vertexBuffer_sVertexBuffer);
        addField("vertexFormat", "tangentElementOffset", Names.vertexFormat_tangentElementOffset);
        addField("vertexFormat", "entityElementOffset", Names.vertexFormat_entityElementOffset);
        addField("vertexFormat", "miduvElementOffset", Names.vertexFormat_miduvElementOffset);
        addField("abstractTexture", "multiTex", Names.abstractTexture_multiTex);
        addField("textureMap", "atlasWidth", Names.textureMap_atlasWidth);
        addField("textureMap", "atlasHeight", Names.textureMap_atlasHeight);
        addMethodVirtual("modelRenderer", "resetDisplayList", Names.modelRenderer_resetDisplayList);
        addMethodInterface("iTextureObject", "getMultiTexID", Names.iTextureObject_getMultiTexID);
        addMethodStatic("forgeHooksClient", "setRenderPass", Names.forgeHooksClient_setRenderPass);
        addFieldAccessor("textureMap", "basePath", Names.textureMap_basePath);
        addFieldAccessor("entityRenderer", "frameCount", Names.entityRenderer_frameCount);
        addFieldAccessor("vertexBuffer", "rawIntBuffer", Names.vertexBuffer_rawIntBuffer);
        addFieldAccessor("vertexBuffer", "rawFloatBuffer", Names.vertexBuffer_rawFloatBuffer);
        addFieldAccessor("vertexBuffer", "vertexCount", Names.vertexBuffer_vertexCount);
        addFieldAccessor("vertexBuffer", "drawMode", Names.vertexBuffer_drawMode);
        addFieldAccessor("vertexBuffer", "vertexElement", Names.vertexBuffer_vertexElement);
        addFieldAccessor("vertexBuffer", "vertexElementIndex", Names.vertexBuffer_vertexElementIndex);
        addFieldAccessor("vertexFormat", "offsets", Names.vertexFormat_offsets);
        addFieldAccessor("vertexFormat", "nextOffset", Names.vertexFormat_nextOffset);
        addFieldAccessor("vertexFormat", "colorElementOffset", Names.vertexFormat_colorElementOffset);
        addFieldAccessor("vertexFormat", "uvOffsets", Names.vertexFormat_uvOffsets);
        addFieldAccessor("vertexFormat", "normalElementOffset", Names.vertexFormat_normalElementOffset);
        addFieldAccessor("renderManager", "entityRenderMap", Names.renderManager_entityRenderMap);
        addFieldAccessor("abstractTexture", "glTextureId", Names.abstractTexture_glTextureId);
        addFieldAccessor("texturedQuad", "invertNormal", Names.texturedQuad_invertNormal);
        addMethodSpecialAccessor("entityRenderer", "renderHand", Names.entityRenderer_renderHand);
        addMethodSpecialAccessor("entityRenderer", "setupCameraTransform", Names.entityRenderer_setupCameraTransform);
    }

    static void addFieldAccessor(String aclas, String aname, Names.Fiel target) {
        String dummyName = "get_" + aclas + "_" + aname;
        String accessorName = "get_" + aname;
        String accessorDesc = "(" + target.clasRef.desc + ")" + target.desc;
        InsnList insnList = new InsnList();
        insnList.add(new MethodInsnNode(184, target.clasName, accessorName, accessorDesc));
        mapDummyToInsnList.put(dummyName, insnList);
        String dummyName2 = "put_" + aclas + "_" + aname;
        String accessorName2 = "put_" + aname;
        String accessorDesc2 = "(" + target.clasRef.desc + target.desc + ")V";
        InsnList insnList2 = new InsnList();
        insnList2.add(new MethodInsnNode(184, target.clasName, accessorName2, accessorDesc2));
        mapDummyToInsnList.put(dummyName2, insnList2);
    }

    static void addField(String aclas, String aname, Names.Fiel target) {
        String dummyName = "get_" + aclas + "_" + aname;
        InsnList insnList = new InsnList();
        insnList.add(new FieldInsnNode(180, target.clasName, target.name, target.desc));
        mapDummyToInsnList.put(dummyName, insnList);
        String dummyName2 = "put_" + aclas + "_" + aname;
        InsnList insnList2 = new InsnList();
        insnList2.add(new FieldInsnNode(181, target.clasName, target.name, target.desc));
        mapDummyToInsnList.put(dummyName2, insnList2);
    }

    static void addMethodSpecialAccessor(String aclas, String aname, Names.Meth target) {
        String dummyName = "invoke_" + aclas + "_" + aname;
        String accessorName = "invoke_" + aname;
        String accessorDesc = "(" + target.clasRef.desc + target.desc.substring(1);
        InsnList insnList = new InsnList();
        insnList.add(new MethodInsnNode(184, target.clasName, accessorName, accessorDesc));
        mapDummyToInsnList.put(dummyName, insnList);
    }

    static void addMethodStatic(String aclas, String aname, Names.Meth target) {
        String dummyName = "invoke_" + aclas + "_" + aname;
        InsnList insnList = new InsnList();
        insnList.add(new MethodInsnNode(184, target.clasName, target.name, target.desc));
        mapDummyToInsnList.put(dummyName, insnList);
    }

    static void addMethodVirtual(String aclas, String aname, Names.Meth target) {
        String dummyName = "invoke_" + aclas + "_" + aname;
        InsnList insnList = new InsnList();
        insnList.add(new MethodInsnNode(182, target.clasName, target.name, target.desc));
        mapDummyToInsnList.put(dummyName, insnList);
    }

    static void addMethodInterface(String aclas, String aname, Names.Meth target) {
        String dummyName = "invoke_" + aclas + "_" + aname;
        InsnList insnList = new InsnList();
        insnList.add(new MethodInsnNode(185, target.clasName, target.name, target.desc));
        mapDummyToInsnList.put(dummyName, insnList);
    }
}
