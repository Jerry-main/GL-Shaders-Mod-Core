package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureMap.class */
public class STTextureMap implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureMap$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;
        boolean endFields;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
            this.endFields = false;
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (name.equals("atlasWidth") || name.equals("atlasHeight")) {
                return null;
            }
            return super.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.endFields) {
                this.endFields = true;
                FieldVisitor fv = this.cv.visitField(1, "atlasWidth", "I", (String) null, (Object) null);
                fv.visitEnd();
                FieldVisitor fv2 = this.cv.visitField(1, "atlasHeight", "I", (String) null, (Object) null);
                fv2.visitEnd();
            }
            if (Names.textureMap_loadTexture.equalsNameDesc(name, desc)) {
                return new MVloadTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.textureMap_loadTextureAtlas.equalsNameDesc(name, desc)) {
                return new MVloadAtlas(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.textureMap_updateAnimations.equalsNameDesc(name, desc)) {
                return new MVanimation(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (name.equals("callGetIconResLoc") && desc.equals("(" + Names.textureMap_.desc + Names.resourceLocation_.desc + "I)" + Names.resourceLocation_.desc)) {
                return null;
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            AccessorGenerator.generateFieldAccessor(this.cv, "basePath", Names.textureMap_basePath);
            super.visitEnd();
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureMap$MVloadTexture.class */
    private static class MVloadTexture extends MethodVisitor {
        public MVloadTexture(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            super.visitCode();
            this.mv.visitVarInsn(25, 1);
            this.mv.visitFieldInsn(179, "shadersmod/client/ShadersTex", "resManager", Names.iResourceManager_.desc);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureMap$MVloadAtlas.class */
    private static class MVloadAtlas extends MethodVisitor {
        int varStitcher;
        boolean isStitcher;
        int state;

        public MVloadAtlas(MethodVisitor mv) {
            super(262144, mv);
            this.varStitcher = 0;
            this.isStitcher = false;
            this.state = 0;
        }

        public void visitCode() {
            super.visitCode();
            this.mv.visitVarInsn(25, 1);
            this.mv.visitFieldInsn(179, "shadersmod/client/ShadersTex", "resManager", Names.iResourceManager_.desc);
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 188 && operand == 10) {
                this.mv.visitInsn(6);
                this.mv.visitInsn(104);
            }
            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitVarInsn(int opcode, int var) {
            if (opcode == 58 && this.isStitcher) {
                this.isStitcher = false;
                this.varStitcher = var;
            }
            this.mv.visitVarInsn(opcode, var);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (opcode == 183 && Names.equals(Names.stitcher_.clasName, "<init>", "(IIII)V", owner, name, desc)) {
                this.isStitcher = true;
            } else {
                if (Names.iResourceManager_getResource.equals(owner, name, desc)) {
                    SMCLog.finest("    %s", "loadRes");
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "loadResource", "(" + Names.iResourceManager_.desc + Names.resourceLocation_.desc + ")" + Names.iResource_.desc);
                    return;
                }
                if (Names.textureUtil_allocateTextureImpl.equals(owner, name, desc)) {
                    SMCLog.finest("    %s", "allocateTextureMap");
                    this.mv.visitVarInsn(25, this.varStitcher);
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "allocateTextureMap", "(IIII" + Names.stitcher_.desc + Names.textureMap_.desc + ")V");
                    this.state = 1;
                    return;
                }
                if (this.state == 1 && Names.textureAtlasSpri_getIconName.equals(owner, name, desc)) {
                    SMCLog.finest("    %s", "setSprite setIconName");
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "setSprite", "(" + Names.textureAtlasSpri_.desc + ")" + Names.textureAtlasSpri_.desc);
                    this.mv.visitMethodInsn(opcode, owner, name, desc);
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "setIconName", "(Ljava/lang/String;)Ljava/lang/String;");
                    this.state = 0;
                    return;
                }
                if (Names.textureUtil_uploadTexSub.equals(owner, name, desc)) {
                    SMCLog.finest("    %s", "uploadTexSubForLoadAtlas");
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "uploadTexSubForLoadAtlas", "([[IIIIIZZ)V");
                    return;
                }
            }
            this.mv.visitMethodInsn(opcode, owner, name, desc);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureMap$MVanimation.class */
    private static class MVanimation extends MethodVisitor {
        public MVanimation(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(25, 0);
            this.mv.visitMethodInsn(182, Names.textureMap_.clasName, "getMultiTexID", "()Lshadersmod/client/MultiTexID;");
            this.mv.visitFieldInsn(179, "shadersmod/client/ShadersTex", "updatingTex", "Lshadersmod/client/MultiTexID;");
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitInsn(1);
                this.mv.visitFieldInsn(179, "shadersmod/client/ShadersTex", "updatingTex", "Lshadersmod/client/MultiTexID;");
            }
            this.mv.visitInsn(opcode);
        }
    }
}
